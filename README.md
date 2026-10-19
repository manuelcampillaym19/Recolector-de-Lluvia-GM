# Recolector de Lluvia GM

Proyecto desarrollado en Java utilizando el framework **libGDX**, para la asignatura de **Programación Avanzada**.

## Descripción

Recolector de Lluvia es un videojuego de escritorio en el que el jugador controla un tarro para recolectar gotas buenas y evitar gotas malas.

El proyecto utiliza programación orientada a objetos e incluye clases relacionadas con la gestión del juego, las gotas, las colisiones y las estrategias de movimiento.

## Tecnologías utilizadas

- Java 21
- libGDX
- Gradle
- LWJGL3

## Estructura del proyecto

- `assets/`: imágenes, sonidos y otros recursos del videojuego.
- `core/`: lógica principal del juego y clases Java.
- `lwjgl3/`: configuración y lanzador para escritorio.
- `gradle/`: archivos necesarios para ejecutar Gradle mediante el wrapper.

## Ejecución

**Requisito:** disponer de Java 21.

1. Clonar o descargar el repositorio.
2. Abrir una terminal en la carpeta principal del proyecto.
3. Ejecutar el siguiente comando en Windows:

   `gradlew.bat lwjgl3:run`

El juego se iniciará en una ventana de escritorio.

## Estado del proyecto

Proyecto en desarrollo, basado en una implementación inicial de Recolector de Lluvia, que será ampliada durante la asignatura.
