#!/usr/bin/env python
# Aplicación cliente servidor con sockets no bloqueantes usando la utilidad
# de ForkingMixIn
# Más detalles en: http://docs.python.org/2/library/socketserver.html
#                  http://docs.python.org/3/library/socketserver.html

import os
import socket
import threading
import socketserver


SERVER_HOST = 'localhost'
SERVER_PORT = 0 # Le decimos al nucleo que elija un puerto de forma dinámica
BUF_SIZE = 1024
ECHO_MSG = 'Hola mundo con ForkingMixIn'


class ForkedClient():
    """ Cliente de prueba"""    
    def __init__(self, ip, port):
        # Creamos el socket
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        # Nos conectamos con el servidor
        self.sock.connect((ip, port))
    
    def run(self):
        """ Cliente trabajando con el servidor"""
        # Enviamos datos al servidor
        current_process_id = os.getpid()
        print ('PID %s Enviando el mensaje al servidor : "%s"' % (current_process_id, ECHO_MSG))

        sent_data_length = self.sock.send(bytes(ECHO_MSG, 'utf-8'))

        print ("Enviando: %d caracteres..." %sent_data_length)
        
        # Respuesta del servidor
        response = self.sock.recv(BUF_SIZE)
        print ("PID %s recibidos: %s" % (current_process_id, response[5:]))
    
    def shutdown(self):
        """ Cerrando el socket del cliente """
        self.sock.close()
      
  
class ForkingServerRequestHandler(socketserver.BaseRequestHandler):
    def handle(self):        
        # Enviando el mensaje de regreso al cliente

        #received = str(sock.recv(1024), "utf-8")
        data = str(self.request.recv(BUF_SIZE), 'utf-8')

        current_process_id = os.getpid()
        response = '%s: %s' % (current_process_id, data)
        print ("Servidor enviando la respuesta [PID: datos] = [%s]" %response)
        self.request.send(bytes(response, 'utf-8'))
        return

  
class ForkingServer(socketserver.ForkingMixIn,
                    socketserver.TCPServer,
                    ):
    """Nada que agregar en esta parte, heredó lo necesario del padre"""
    pass


def main():
    # Lanzamos el servidor
    server = ForkingServer((SERVER_HOST, SERVER_PORT), ForkingServerRequestHandler)
    ip, port = server.server_address # Recuperamos el número de puerto
    server_thread = threading.Thread(target=server.serve_forever)
    server_thread.daemon = True
    #server_thread.setDaemon(True) # Termina cuando el hilo principal termina
    server_thread.start()
    print ("Lazo infinito del servidor PID: %s" %os.getpid())
    
    # Lanzamos los clientes

    client1 =  ForkedClient(ip, port)
    client1.run()

    print("Primer cliente corriendo")
    
    client2 =  ForkedClient(ip, port)
    client2.run()

    print("Segundo cliente corriendo")

    # Terminamos y limpiamos
    server.shutdown()
    client1.shutdown()
    client2.shutdown()
    server.socket.close()


if __name__ == '__main__':
    main()
