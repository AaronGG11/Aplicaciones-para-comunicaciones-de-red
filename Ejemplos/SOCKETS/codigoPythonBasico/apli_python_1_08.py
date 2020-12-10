#aplic_python_1_08.py
#Modificando el tamaño del buffer del socket
import socket

TAM_BUF_ENVIO= 4096
TAM_BUF_RECEP= 4096

def modi_tam_bufer():
    sock = socket.socket( socket.AF_INET, socket.SOCK_STREAM )
    
    # Tamaño del buffer de envio
    tambuf = sock.getsockopt(socket.SOL_SOCKET, socket.SO_SNDBUF)
    print ("Tamaño bufer [Antes]:%d" %tambuf)
    
    sock.setsockopt(socket.SOL_TCP, socket.TCP_NODELAY, 1)
    sock.setsockopt(
            socket.SOL_SOCKET,
            socket.SO_SNDBUF,
            TAM_BUF_ENVIO)
    sock.setsockopt(
            socket.SOL_SOCKET,
            socket.SO_RCVBUF,
            TAM_BUF_RECEP)
    tambuf = sock.getsockopt(socket.SOL_SOCKET, socket.SO_SNDBUF)
    print ("Tamaño bufer [despues]:%d" %tambuf)

if __name__ == '__main__':
    modi_tam_bufer()
