#aplic_python_1_03.py
#Convertir una dirección IPv4 en un formato diferente
import socket
from binascii import hexlify


def convertir_ip4():
    for dir_ip in ['127.0.0.1', '192.168.0.1']:
        datos_dir_ip = socket.inet_aton(dir_ip)
        cambio_dir_ip = socket.inet_ntoa(datos_dir_ip)
        print ("Dir ip: %s => Original: %s, nuevo: %s" %(dir_ip, hexlify(datos_dir_ip), cambio_dir_ip))
   
if __name__ == '__main__':
    convertir_ip4()

