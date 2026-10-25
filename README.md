# Recolector de Lluvia GM

Proyecto desarrollado en **Java** utilizando el framework **libGDX**, para la asignatura de **Programación Avanzada**.

## Integrantes

- **Mario Saavedra**
- **Manuel Campillay**

## Descripción

**Recolector de Lluvia GM** es un videojuego de escritorio en el que el jugador controla un tarro para recolectar gotas de agua y evitar las gotas tóxicas.

El juego incorpora mecánicas de movimiento, detección de colisiones, gestión de puntuación y control de vidas. Además, cuenta con una interfaz gráfica personalizada que incluye un menú principal, una pantalla de *Game Over* y una tabla de récords.

El proyecto se basa en una implementación inicial de Recolector de Lluvia, sobre la cual se realizaron modificaciones y mejoras en la jugabilidad y la presentación visual.

## Características principales

- **Movimiento del jugador:** control del tarro mediante el teclado.
- **Recolección de gotas:** las gotas de agua permiten acumular puntos.
- **Gotas tóxicas:** representan un peligro para el jugador y pueden provocar la pérdida de vidas.
- **Movimiento de las gotas:** las gotas de agua descienden verticalmente, mientras que las gotas tóxicas realizan un movimiento en zigzag durante su descenso.
- **Sistema de vidas:** la partida termina cuando el jugador pierde todas sus vidas.
- **Sistema de puntuación:** registra los puntos obtenidos durante la partida.
- **Tabla de récords:** permite guardar y visualizar los mejores puntajes.
- **Interfaz gráfica:** incluye menú principal, pantalla de *Game Over* y pantalla de récords.
- **Recursos audiovisuales:** incorpora imágenes, fondos y efectos de sonido.

## Tecnologías utilizadas

- **Java 21:** lenguaje de programación.
- **libGDX:** framework para el desarrollo del videojuego.
- **Gradle:** herramienta de compilación y gestión del proyecto.
- **LWJGL3:** backend utilizado para ejecutar el juego en escritorio.

## Estructura del proyecto

- `assets/`: contiene las imágenes, los sonidos y otros recursos utilizados por el videojuego.
- `core/`: contiene la lógica principal del juego y las clases Java.
- `lwjgl3/`: contiene la configuración y el lanzador para la ejecución en escritorio.
- `gradle/`: contiene los archivos necesarios para ejecutar Gradle mediante el wrapper.

## Requisitos

- Tener **Java 21** instalado y configurado correctamente.
- Disponer de una terminal para ejecutar los comandos de Gradle.

## Ejecución

En Windows, sigue estos pasos:

1. Clonar o descargar el repositorio.
2. Abrir una terminal en la carpeta principal del proyecto.
3. Compilar el proyecto con el siguiente comando:

   ```bat
   gradlew.bat build
   ```

4. Ejecutar el videojuego con el siguiente comando:

   ```bat
   gradlew.bat lwjgl3:run
   ```

Una vez completada la compilación, se abrirá la ventana del videojuego.

## Estado del proyecto

Proyecto académico desarrollado para la asignatura de **Programación Avanzada**, basado en una implementación inicial de Recolector de Lluvia y ampliado mediante modificaciones en sus mecánicas de juego, interfaz gráfica y sistema de récords.
