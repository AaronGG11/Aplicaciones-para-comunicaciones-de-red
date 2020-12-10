#apli_python_1_01.py
# Obtener el nombre y la dirección de una máquina
import socket

def print_info_maquina():
    nombre_host = socket.gethostname()
    dir_ip = socket.gethostbyname(nombre_host)
    print ("Nombre host: %s" %nombre_host)
    print ("Dirección IP: %s" %dir_ip)

if __name__ == '__main__':
    print_info_maquina()
