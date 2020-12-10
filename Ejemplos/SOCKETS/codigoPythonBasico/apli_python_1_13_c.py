#aplic_python_1_13_c.py
#Cliente de eco con UDP
import socket
import sys
import argparse

host = 'localhost'
carga_util = 2048

def echo_client(puerto):
    """ Cliente de eco simple"""
    # Crea socket UDP
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    dir_server = (host, puerto)
    print ("Conectando a %s puerto %s" % dir_server)

    try:
        #Envia dato
        mensaje = "Mensaje a enviar."
        print ("Enviando %s" % mensaje)
        sent = sock.sendto(mensaje.encode('utf-8'), dir_server)

        # Recibe respuesta
        dato, server = sock.recvfrom(carga_util)
        print ("recibido %s" % dato)

    finally:
        print ("Cierra la conexión con el servidor")
        sock.close()

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Ejemplo de servidor de socket UDP')
    parser.add_argument('--port', action="store", dest="puerto", type=int, required=True)
    given_args = parser.parse_args() 
    puerto = given_args.puerto
    echo_client(puerto)
