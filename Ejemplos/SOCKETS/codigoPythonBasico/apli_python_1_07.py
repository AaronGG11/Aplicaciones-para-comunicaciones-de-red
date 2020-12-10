#aplic_python_1_07.py
#Manejo formal de errores en el socket
import sys
import socket
import argparse 


def main():
    # Configurando argumentos de entrada
    parser = argparse.ArgumentParser(description='Ejemplo de errores de socket')
    parser.add_argument('--host', action="store", dest="host", required=False)
    parser.add_argument('--port', action="store", dest="port", type=int, required=False)
    parser.add_argument('--file', action="store", dest="file", required=False)
    given_args = parser.parse_args()
    host = given_args.host
    port = given_args.port
    filename = given_args.file
    
    # Primer bloque try-except -- creación del socket
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    except socket.error as e:
        print ("Error creando el socket: %s" % e)
        sys.exit(1)
    
    # Segundo bloque try-except -- conectando al host/puerto indicado
    try:
        s.connect((host, port))
    except socket.gaierror as e:
        print ("Error dirección de conexión del servidor: %s" % e)
        sys.exit(1)
    except socket.error as e:
        print ("Error de conexión: %s" % e)
        sys.exit(1)
    
    # Tercer bloque try-except -- envio de datos
    try:
        msg = "GET %s HTTP/1.0\r\n\r\n" % filename
        s.sendall(msg.encode('utf-8'))
    except socket.error as e:
        print ("Error enviando datos: %s" % e)
        sys.exit(1)
    
    while 1:
        # Cuarto bloque try-except -- espera para la recepción de datos del host remoto
        try:
            buf = s.recv(2048)
        except socket.error as e:
            print ("Error recibiendo datos: %s" % e)
            sys.exit(1)
        if not len(buf):
            break
        # escribiendo los datos recibidos
        sys.stdout.write(buf.decode('utf-8')) 
    
if __name__ == '__main__':
    main()
