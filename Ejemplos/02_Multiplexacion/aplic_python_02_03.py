#!/usr/bin/env python
# Aplicación chat usando un servidor con sockets no bloqueantes usando 
# select.select

import select
import socket
import sys
import signal
import pickle
import struct
import argparse

SERVER_HOST = 'localhost'
CHAT_SERVER_NAME = 'servidor'

# Utilerias
def send(channel, *args):
    buffer = pickle.dumps(args)
    value = socket.htonl(len(buffer))
    size = struct.pack("L",value)
    channel.send(size)
    channel.send(buffer)

def receive(channel):
    size = struct.calcsize("L")
    size = channel.recv(size)
    try:
        size = socket.ntohl(struct.unpack("L", size)[0])
    except struct.error as e:
        return ''
    buf = ""
    while len(buf) < size:
        buf = channel.recv(size - len(buf))
    return pickle.loads(buf)[0]


class ChatServer(object):
    """ Un ejemplo de chat usando select """
    def __init__(self, port, backlog=5):
        self.clients = 0
        self.clientmap = {}
        self.outputs = [] # lista de salida de los sockets
        self.server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.server.bind((SERVER_HOST, port))
        print ('Servidor escuchando desde el puerto: %s ...' %port)
        self.server.listen(backlog)
        # Capturando las interrupciones del teclado
        signal.signal(signal.SIGINT, self.sighandler)
        
    def sighandler(self, signum, frame):
        """ Limpiando las conexiones de los clientes"""
        # Cerramos el servidor
        print ('Cerrando el servidor...')
        # Cerramos los clientes existentes
        for output in self.outputs:
            output.close()            
        self.server.close()

    def get_client_name(self, client):
        """ Regresamos el nombre del cliente """
        info = self.clientmap[client]
        host, name = info[0][0], info[1]
        return '@'.join((name, host))
        
    def run(self):
        inputs = [self.server, sys.stdin]
        self.outputs = []
        running = True
        while running:
            try:
                readable, writeable, exceptional = select.select(inputs, self.outputs, [])
            except select.error as e:
                break

            for sock in readable:
                if sock == self.server:
                    # Manejador del servidor
                    client, address = self.server.accept()
                    print ("Servidor de chat: conexion %d desde %s" % (client.fileno(), address))
                    # Leemos el nombre de acceso
                    cname = receive(client).split('NOMBRE: ')[1]
                    
                    # Registro del nombre del cliente
                    self.clients += 1
                    send(client, 'CLIENTE: ' + str(address[0]))
                    inputs.append(client)
                    self.clientmap[client] = (address, cname)
                    # Enviando la información de conexión a otros clientes
                    msg = "\n(Conectado: Nuevo cliente (%d) desde %s)" % (self.clients, self.get_client_name(client))
                    for output in self.outputs:
                        send(output, msg)
                    self.outputs.append(client)

                elif sock == sys.stdin:
                    # Manejador de la entrada estandar
                    junk = sys.stdin.readline()
                    running = False
                else:
                    # Manejador de los clientes
                    try:
                        data = receive(sock)
                        if data:
                            # Enviando un nuevo mensaje a los clientes...
                            msg = '\n#[' + self.get_client_name(sock) + ']>>' + data
                            # Enviando datos a los otros menos a uno mismo
                            for output in self.outputs:
                                if output != sock:
                                    send(output, msg)
                        else:
                            print ("Servidor chat: %d desconexion" % sock.fileno())
                            self.clients -= 1
                            sock.close()
                            inputs.remove(sock)
                            self.outputs.remove(sock)

                            # Enviando la información de la desconexión del cliente
                            msg = "\n(Fin de conexión: Cliente %s)" % self.get_client_name(sock)
                            for output in self.outputs:                                
                                send(output, msg)                                
                    except socket.error as e:
                        # eliminar
                        inputs.remove(sock)
                        self.outputs.remove(sock)
        self.server.close()


class ChatClient(object):
    """ Cliente de chat de linea de comandos usando select """

    def __init__(self, name, port, host=SERVER_HOST):
        self.name = name
        self.connected = False
        self.host = host
        self.port = port
        # Iniciamos el promt
        self.prompt='[' + '@'.join((name, socket.gethostname().split('.')[0])) + ']> '
        # Conectado al puerto del servidor
        try:
            self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self.sock.connect((host, self.port))
            print ("Nos conectamos al servidor del chat @ puerto %d" % self.port)
            self.connected = True
            # Enviando mi nombre...
            send(self.sock,'NOMBRE: ' + self.name) 
            data = receive(self.sock)
            # Construyendo los datos
            addr = data.split('CLIENTE: ')[1]
            self.prompt = '[' + '@'.join((self.name, addr)) + ']> '
        except socket.error as e:
            print ("Error de conexion al servidor @ puerto %d" % self.port)
            sys.exit(1)

    def run(self):
        """ Lazo principal del chat """
        while self.connected:
            try:
                sys.stdout.write(self.prompt)
                sys.stdout.flush()
                # Espera por datos desde stdin y socket
                readable, writeable,exceptional = select.select([0, self.sock], [],[])
                for sock in readable:
                    if sock == 0:
                        data = sys.stdin.readline().strip()
                        if data: send(self.sock, data)
                    elif sock == self.sock:
                        data = receive(self.sock)
                        if not data:
                            print ('Cliente deteniendose.')
                            self.connected = False 
                            break
                        else:
                            sys.stdout.write(data + '\n')
                            sys.stdout.flush()
                            
            except KeyboardInterrupt:
                print (" Cliente detenido. """)
                self.sock.close()
                break


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Ejemplo de servidor usando Select')
    parser.add_argument('--nombre', action="store", dest="name", required=True)
    parser.add_argument('--puerto', action="store", dest="port", type=int, required=True)
    given_args = parser.parse_args() 
    port = given_args.port
    name = given_args.name
    if name == CHAT_SERVER_NAME:
        server = ChatServer(port)
        server.run()
    else:
        client = ChatClient(name=name, port=port)
        client.run()
    
