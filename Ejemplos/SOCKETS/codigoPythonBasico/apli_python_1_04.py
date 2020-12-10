#aplic_python_1_04.py
#Obtener el nombre de un servicio a partir de su puerto y protocolo
import socket

def encuentra_nombre_servicio():
    protocolo = 'tcp'
    for puerto in [80, 25]:
        print ("Puerto: %s => nombre de servicio: %s" %(puerto, socket.getservbyport(puerto, protocolo)))
    print ("Puerto: %s => nombre de servicio: %s" %(53, socket.getservbyport(53, 'udp')))
    
if __name__ == '__main__':
    encuentra_nombre_servicio()

