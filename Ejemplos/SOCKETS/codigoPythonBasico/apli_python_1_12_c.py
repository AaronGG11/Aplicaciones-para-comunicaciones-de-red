#aplic_python_1_12_c.py
#Escribe una aplicación cliente servidor
import socket
import sys

import argparse

host = 'localhost'

def eco_cliente(port):
    """ Cliente de eco simple"""
    # Crea un socket TCP/IP
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # Conecta con el servidor
    dir_servidor= (host, puerto)
    print ("Conecta a %s puerto %s" % dir_servidor)
    sock.connect(dir_servidor)
    
    # Envia dato
    try:
        # Envia dato
        msg = "Este es un texto para enviar"
        print ("Enviando %s" % msg)
        sock.sendall(msg.encode('utf-8'))
        # Esperando respuesta
        recibidos= 0
        esperados = len(msg)
        while recibidos< esperados:
            dato = sock.recv(16)
            recibidos+= len(dato)
            print ("Recibido: %s" % dato)
    except socket.error as e:
        print ("Error socket: %s" %str(e))
    except Exception as e:
        print ("Otra excepcion: %s" %str(e))
    finally:
        print ("Cerrando la conexión con el servidor")
        sock.close()
    
if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Ejemplo Socket Server ')
    parser.add_argument('--port', action="store", dest="puerto", type=int, required=True)
    given_args = parser.parse_args() 
    puerto = given_args.puerto
    eco_cliente(puerto)
