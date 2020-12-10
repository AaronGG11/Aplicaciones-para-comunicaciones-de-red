#aplic_python_1_13_s.py
#Servidor de eco con UDP

import socket
import sys
import argparse

host = 'localhost'
carga_util = 2048

def servidor_eco(puerto):
    """ Servidor de eco simple """
    # Crea un socket UDP
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # Ligamos el socket con el puerto
    dir_server = (host, puerto)
    print ("Servidor levantado en %s puerto %s" % dir_server)

    sock.bind(dir_server)

    while True:
        print ("Esperando un mensaje de un cliente")
        dato, dir = sock.recvfrom(carga_util)
    
        print ("Recibi %s bytes desde %s" % (len(dato), dir))
        print ("Dato: %s" %dato)
    
        if dato:
            envio = sock.sendto(dato, dir)
            print ("Se envian %s bytes de regreso a %s" % (envio, dir))


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Ejemplo de servidor de eco')
    parser.add_argument('--port', action="store", dest="puerto", type=int, required=True)
    given_args = parser.parse_args() 
    puerto = given_args.puerto
    servidor_eco(puerto)
