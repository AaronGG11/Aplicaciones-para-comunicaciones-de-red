#aplic_python_1_06.py
#Manipular el tiempo de espera de un socket
import socket

def tiempo_vida_socket():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM) # Socket de flujo
    print ("Default tiempo del socket: %s" %s.gettimeout())
    s.settimeout(100)
    print ("Tiempo nuevo: %s" %s.gettimeout())    
    
if __name__ == '__main__':
    tiempo_vida_socket()
