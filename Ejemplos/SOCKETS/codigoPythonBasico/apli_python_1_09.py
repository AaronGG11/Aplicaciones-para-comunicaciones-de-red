#aplic_python_1_09.py
#Modificar el modo de funcionamiento de un socket
import socket

def modif_modo_socket():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.setblocking(1) # no bloqueante
    s.settimeout(0.5)
    s.bind(("127.0.0.1", 0))
    
    dir_socket = s.getsockname()
    print ("Ejecución del socket: %s" %str(dir_socket))
    while(1):
        s.listen(1)
  
if __name__ == '__main__':
    modif_modo_socket()
