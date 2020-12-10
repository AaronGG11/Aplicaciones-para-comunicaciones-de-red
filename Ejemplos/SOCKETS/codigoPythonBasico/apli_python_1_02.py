#aplic_python_1_02.py
#Obtener información de una máquina remota
import socket

def info_maquina_remota():
    host_remoto = 'www.python.org'
    try:
        print ("Direccion IP de %s: %s" %(host_remoto, socket.gethostbyname(host_remoto)))
    except socket.error as err_msg:
        print ("%s: %s" %(host_remoto, err_msg))
    
if __name__ == '__main__':
    info_maquina_remota()

