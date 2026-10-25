# 🌧️ Recolector de Lluvia GM

Videojuego de escritorio desarrollado en **Java** con el framework **libGDX**, como proyecto para la asignatura de **Programación Avanzada**.

El objetivo es controlar un tarro para recolectar gotas de agua, sumar puntos y evitar las gotas tóxicas que ponen en riesgo la partida.

## 🎮 Descripción

**Recolector de Lluvia GM** es un videojuego inspirado en la mecánica clásica de recolección de objetos que caen desde la parte superior de la pantalla.

El jugador debe recoger las gotas de agua para aumentar su puntuación y evitar las gotas tóxicas para conservar sus vidas. A medida que se acumulan puntos, la velocidad de caída aumenta, haciendo que la partida sea progresivamente más desafiante.

El juego incluye un menú principal, una pantalla de *Game Over* y una tabla de récords, junto con recursos gráficos y sonoros personalizados.

## ✨ Características

- **Control del jugador:** movimiento horizontal del tarro mediante el teclado.
- **Gotas de agua:** permiten aumentar la puntuación.
- **Gotas tóxicas:** provocan la pérdida de vidas al colisionar con el tarro.
- **Movimiento diferenciado:** las gotas de agua caen verticalmente y las gotas tóxicas se desplazan en zigzag.
- **Dificultad progresiva:** la velocidad de las gotas aumenta según la puntuación obtenida.
- **Sistema de vidas:** la partida termina cuando se agotan las vidas disponibles.
- **Sistema de puntuación:** contabiliza los puntos obtenidos durante la partida.
- **Tabla de récords:** permite guardar y consultar las mejores puntuaciones localmente.
- **Interfaz gráfica:** incluye menú principal, pantalla de *Game Over* y pantalla de récords.
- **Recursos audiovisuales:** incorpora imágenes, fondos y efectos de sonido.

## 🛠️ Tecnologías utilizadas

- **Java:** lenguaje de programación.
- **libGDX:** framework para el desarrollo del videojuego.
- **Gradle:** herramienta de compilación y gestión de dependencias.
- **LWJGL3:** backend utilizado para ejecutar el juego en escritorio.

## 📁 Estructura del proyecto

| Directorio | Descripción |
|---|---|
| `assets/` | Imágenes, sonidos y otros recursos del juego. |
| `core/` | Clases Java y lógica principal del videojuego. |
| `lwjgl3/` | Lanzador y configuración para la ejecución en escritorio. |
| `gradle/` | Archivos del wrapper de Gradle. |

## ⚙️ Requisitos

- Tener un **JDK compatible con la configuración de Gradle** instalado.
- Utilizar Windows para los comandos de ejecución indicados a continuación.
- Disponer de una terminal para ejecutar Gradle.

## ▶️ Ejecución en Windows

1. Clona el repositorio o descarga el proyecto.
2. Abre una terminal en la carpeta raíz del proyecto.
3. Compila el proyecto:

   ```bat
   gradlew.bat build
   ```

4. Ejecuta el videojuego:

   ```bat
   gradlew.bat :lwjgl3:run
   ```

Al finalizar el proceso de inicio, se abrirá la ventana del videojuego.

## 👥 Integrantes

- **Mario Saavedra**
- **Manuel Campillay**

## 📌 Estado del proyecto

Proyecto académico desarrollado para la asignatura de **Programación Avanzada**. Parte de una implementación inicial de *Recolector de Lluvia* e incorpora modificaciones en las mecánicas de juego, el comportamiento de las gotas, la presentación visual y el sistema de récords.
