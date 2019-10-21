# UDPsockets-stream-client
El siguiente es un cliente en Java para visualizar streaming de archivos de vídeo a través de RTP y 
haciendo uso de la librería de [vlcj](https://github.com/caprica/vlcj).

## Requisitos Previos
* Es imperativo que el dispositivo donde se corra el servidor tenga instalado [VLC](https://www.videolan.org/vlc/download-windows.es.html) debido 
a que si este no es instalado, no podrá funcionar el programa a pesar de que las librerías estén referenciadas y en local. Es probable que 
el cliente si funcione a pesar de no tener [VLC](https://www.videolan.org/vlc/download-windows.es.html) instalado, pero es preferible 
realizar la instalación del mismo.

## Funcionamiento
El cliente se ejecuta posteriormente de haber iniciado el servidor su funcionamiento. Luego de esto, se abrirá una ventana en la cual el cliente 
debe iniciar sesión. Las credenciales para esto son:
* Usuario: cualquiera que se quiera ingresar
* Clave: 1234


Luego de esto se iniciará la interfaz principal, donde el usuario podrá seleccionar alguno de los canales enlistados en el ítem de selección 
y allí, luego de presionar el botón de `Conectar`, simplemente se iniciará a recibir el Streaming.
Es posible cambiar de canal al seleccionar alguno diferente y hacer clic nuevamente en `Conectar`.


Los controles de la parte inferior sirven para pausar el vídeo, reanudar el mismo si está pausado, bajar el volumen y subir el volumen. 


Además, el usuario puede enviar vídeos al servidor para que este los añada a la lista de reproducción y también los envíe por Streaming a una nueva 
dirección multicast. Esto se realiza en la parte superior, donde al seleccionar un archivo (que inicialmente es recomendable que esté en 
la carpeta `./data` **SE DEBE CREAR DICHA CARPETA**), se puede enviar. Esto se realiza de forma asíncrona, por lo que se puede continuar a ver el streaming mientras 
se envía un archivo.
