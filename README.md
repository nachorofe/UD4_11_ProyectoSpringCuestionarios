# Proyecto Cuestionarios - Spring Boot

Este proyecto es una aplicación web desarrollada con Spring Boot que permite crear, consultar y gestionar preguntas del tipo "Cuestión" (una sola respuesta) y "test" (varias opciones de respuesta).

## URL del repositorio

https://github.com/nachorofe/UD4_11_ProyectoSpringCuestionarios/

## Funcionalidades

- Crear preguntas de tipo test o cuestión.
- Añadir y quitar etiquetas.
- Marcar opciones como correctas.
- Modificar y eliminar preguntas.
- Paginación de resultados.

## Tecnologías

- Java 23+.
- Spring Boot.
- Spring Data JPA.
- PostgreSQL.

## Guía de instalación y uso

1. Clona este repositorio o descárgalo en tu equipo
2. Instala JDK (Java Development Kit) para poder compilar el código y ejecutar la aplicación.
3. Configura la base de datos Postgres en tu equipo y asegúrate de que esté corriendo antes de ejecutar el programa.
4. Configura el archivo application.properties con los parámetros correspondientes a tu instalación 
5. Compila y ejecuta al archivo AppPreguntasApplication.java disponible en la ruta src/main/resources/org/dam.
6. Para probar los distintos métodos, puedes utilizar Postman. En las capturas se pueden ver ejemplos de los métodos que puedes probar.

## Documentación adicional

En la carpeta screenshots se incluyen capturas de pruebas con Postman.

## Consideraciones

Implementado hasta 05. Pruebas y mejoras con capa de servicio. Thymeleaf implementado parcialmente, pendiente de terminar.
