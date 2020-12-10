#aplic_python_1_11.py
#Obtener el tiempo de un servidor de tiempo
import ntplib
from time import ctime

def print_tiempo():
    cliente_ntp = ntplib.NTPClient()
    respuesta = cliente_ntp.request('pool.ntp.org')
    print (ctime(respuesta.tx_time))

if __name__ == '__main__':
    print_tiempo()
