#aplic_python_1_12_s.py
#Escribe una aplicación cliente servidor
import socket
import sys
import argparse

host = 'localhost'
carga_util = 2048
reserva = 5 


def servidor_eco(port):
    """ Servidor de eco simple"""
    # Crea un socket TCP
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM) # TCP -> FLUJO
    # Habilitamos la reusabilidad del socket 
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    # Ligamos al número de puerto
    dir_server = (host, port)
    print ("Activación del servidor en %s port %s" % dir_server)
    sock.bind(dir_server)
    # Activamos la escucha del servidor para un número determinado de clientes
    sock.listen(reserva) 
    while True: 
        print ("Esperando un mensaje del cliente")
        cliente, dir = sock.accept() 
        dato = cliente.recv(carga_util) 
        if dato:
            print ("Dato: %s" %dato)
            cliente.send(dato)
            print ("envia %s bytes de regreso %s" % (dato, dir))
        # término de la conexión
        cliente.close() 
   
if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Examplo de servidor de eco')
    parser.add_argument('--port', action="store", dest="puerto", type=int, required=True)
    given_args = parser.parse_args() 
    puerto = given_args.puerto
    servidor_eco(puerto)
