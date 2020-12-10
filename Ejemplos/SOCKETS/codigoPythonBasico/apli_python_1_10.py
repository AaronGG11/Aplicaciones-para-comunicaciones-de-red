#aplic_python_1_10.py
#Hacer una dirección de socket reutilizable
import socket
import sys


def reuse_dir_socket():
    sock = socket.socket( socket.AF_INET, socket.SOCK_STREAM )

    # Obtener el estado original de la opción SO_REUSEADDR
    anterior = sock.getsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR )
    print ("Estado anterior: %s" %anterior)

    # Activar la opción SO_REUSEADDR
    sock.setsockopt( socket.SOL_SOCKET, socket.SO_REUSEADDR, 1 )
    nuevo = sock.getsockopt( socket.SOL_SOCKET, socket.SO_REUSEADDR )
    print ("Estado nuevo del socket: %s" %nuevo)

    puerto_local = 8282
    
    srv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    srv.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    srv.bind( ('', puerto_local) )
    srv.listen(1)
    print ("Escucha en el puerto: %s " %puerto_local)
    while True:
        try:
            conexion, dir = srv.accept()
            print ('Conección desde %s:%s' % (dir[0], dir[1]))
        except KeyboardInterrupt:
            break
        except socket.error as msg:
            print ('%s' % (msg,))


if __name__ == '__main__':
    reuse_dir_socket()
