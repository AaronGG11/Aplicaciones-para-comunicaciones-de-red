#aplic_python_1_05.py
#Convertir un entero a formato de red
import socket

def conv_int():
    dato = 1234
    # 32-bit
    print ("Original: %s => Formato largo: %s, Ordenado para red: %s" %(dato, socket.ntohl(dato), socket.htonl(dato)))
    # 16-bit
    print ("Original: %s => Formato corto: %s, Ordenado para red: %s" %(dato, socket.ntohs(dato), socket.htons(dato)))

    
if __name__ == '__main__':
    conv_int()
