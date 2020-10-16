A tener en cuenta antes de ejecutar el programa:

    Esta hecho para funcionar en Java 7, la version que esta instalada en la uco.
    En principio, no deberia haber problemas para ejecutar los ejercicios en otras versiones de java posteriores

    Hay que modificar los ficheros datos1.properties y datos2.properties con las direcciones donde quiera que se almacenen los ficheros de datos. Dejar los ficheros .properties en donde se vaya a ejecutar el programa

Ejecucion del Programa: 

    Ejecutando el jar desde la terminal:

    Ejercicio 1: java -jar Ejercicio1.jar datos1.properties
    Ejercicio 2: java -jar Ejercicio2.jar datos2.properties

    Teniendo en cuenta que el .properties este en la misma ubicacion del jar. Podrias añadir el path como argumento sino fuera el caso.

    Compilando el proyecto desde eclipse:

    Solo habria que añadir como argumento el .properties respectivo a cada ejercicio al ejecutarlos

Funcionamiento del Programa(Para el ejercicio 2):

En el programa existen 4 apartados. Para empezar a crear anuncios, primero necesitamos configurar los posibles intereses que se pueden añadir desde el apartado de configuracion. Despues, es necesario crear un usuario desde el primer apartado. Ahora podremos registrarnos en el tablon de anuncios(tercer apartado) y crear el anuncio, aunque para poder verlo en el tablon del tercer apartado habria que publicarlo anteriormente desde el segundo apartado.

Configuracion de intereses:

Ejercicio 1: Se podran añadir o eliminar intereses desde la enumeracion en la clase Contacto, pero habria que compilar el codigo y no serviria el ejecutable.
Ejercicio 2: Se podran añadir desde el cuarto apartado al ejecutar el programa, o una vez que se haya ejecutado el programa al menos una vez para que se cree el fichero intereses.txt en el que se pueden añadir manualmente
