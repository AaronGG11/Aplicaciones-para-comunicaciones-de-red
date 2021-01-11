#!/usr/bin/env python
# Aplicación cliente servidor con sockets no bloqueantes usando la utilidad
# de ThreadingMixIn
# Más detalles en: http://docs.python.org/2/library/socketserver.html
#                  http://docs.python.org/3/library/socketserver.html

import os
import socket
import threading
import socketserver

SERVER_HOST = 'localhost'
SERVER_PORT = 0 # Le decimos al nucleo que elija un puerto de forma dinámica
BUF_SIZE = 1024


def client(ip, port, message):
    """ Cliente para probar el servidor usando hilos"""    
    # Conectado con el servidor
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect((ip, port))
    try:
        sock.sendall(bytes(message, 'utf-8'))
        response = sock.recv(BUF_SIZE)
        print ("Recibido por el cliente: %s" %response)
    finally:
        sock.close()


class ThreadedTCPRequestHandler(socketserver.BaseRequestHandler):
    """ Manejador del hilo TCP """
    def handle(self):
        data = self.request.recv(1024)
        cur_thread = threading.current_thread()
        response = "%s: %s" %(cur_thread.name, data)
        self.request.sendall(bytes(response, 'utf-8'))

class ThreadedTCPServer(socketserver.ThreadingMixIn, socketserver.TCPServer):
    """Nada que agregar en esta parte, heredó lo necesario del padre"""
    pass


if __name__ == "__main__":
    # Corriendo el servidor
    server = ThreadedTCPServer((SERVER_HOST, SERVER_PORT), ThreadedTCPRequestHandler)
    ip, port = server.server_address # recuperamos los datos de conexión

    # Lanzamos el hilo del servidor
    server_thread = threading.Thread(target=server.serve_forever)
    # Salimos del servidor cuando el hilo principal termine
    server_thread.daemon = True
    server_thread.start()
    print ("Lazo corriendo desde el hilo del servidor: %s"  %server_thread.name)
    
    # Corremos los clientes
    client(ip, port, "Hola desde el cliente 1")
    client(ip, port, "Hola desde el cliente 2")
    client(ip, port, "Hola desde el cliente 3")
    
    # Terminamos con el servidor
    server.shutdown()
