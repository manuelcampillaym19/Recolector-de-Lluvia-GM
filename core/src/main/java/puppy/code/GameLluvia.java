package puppy.code;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class GameLluvia extends ApplicationAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapes;

    private BitmapFont font;
    private BitmapFont tituloFont;
    private BitmapFont subtituloFont;
    private BitmapFont botonFont;
    private GlyphLayout layout;

    private Tarro tarro;
    private Lluvia lluvia;

    private Texture backgroundTexture;
    private Texture corazonTexture;
    private Texture gotaBuenaTexture;
    private Texture gotaMalaTexture;

    private Preferences records;

    // 0 = Menú | 1 = Jugando | 2 = Game Over | 3 = Récords
    private int estado = 0;

    private static final float ANCHO = 800;
    private static final float ALTO = 480;
    private static final int MAX_RECORDS = 10;

    private String nombreJugador = "";
    private boolean esperandoNombre = false;
    private boolean recordGuardado = false;
    private boolean necesitaRegistrar = false;
    private boolean calificaRecord = false;

    private static class Record {
        String nombre;
        int puntos;

        Record(String nombre, int puntos) {
            this.nombre = nombre;
            this.puntos = puntos;
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (int) ANCHO, (int) ALTO);

        font = new BitmapFont();
        tituloFont = new BitmapFont();
        subtituloFont = new BitmapFont();
        botonFont = new BitmapFont();
        layout = new GlyphLayout();

        tituloFont.getData().setScale(1.8f);
        subtituloFont.getData().setScale(1.05f);
        botonFont.getData().setScale(1.1f);

        // IMPORTANTE: conservar el mismo nombre para los récords.
        records = Gdx.app.getPreferences("RecolectorDeLluviaRecords");

        Sound hurtSound =
                Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));

        Texture bucketTexture =
                new Texture(Gdx.files.internal("bucket.png"));

        tarro = new Tarro(bucketTexture, hurtSound);

        gotaBuenaTexture =
                new Texture(Gdx.files.internal("gotaAgua3.png"));

        gotaMalaTexture =
                new Texture(Gdx.files.internal("gotaToxica.png"));

        backgroundTexture =
                new Texture(Gdx.files.internal("imagenFondo2.png"));

        corazonTexture =
                new Texture(Gdx.files.internal("corazonRojo2.png"));

        Sound dropSound =
                Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

        Music rainMusic =
                Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        rainMusic.setVolume(0.8f);

        lluvia = new Lluvia(
                gotaBuenaTexture,
                gotaMalaTexture,
                dropSound,
                rainMusic
        );

        configurarTeclado();
    }

    // ==================================================
    // TECLADO
    // ==================================================

    private void configurarTeclado() {
        Gdx.input.setInputProcessor(new InputAdapter() {

            /*
             * Evita borrar varias letras cuando BACKSPACE
             * genera eventos repetidos mientras se mantiene pulsado.
             */
            private boolean retrocesoPresionado = false;

            @Override
            public boolean keyTyped(char character) {
                if (estado != 2 || !esperandoNombre) {
                    return false;
                }

                /*
                 * El borrado se maneja exclusivamente en keyDown().
                 * Aquí ignoramos el carácter de retroceso para no
                 * procesar dos veces la misma acción.
                 */
                if (character == '\b' || character == 127) {
                    return true;
                }

                if (Character.isLetterOrDigit(character)
                        || character == ' '
                        || character == '_'
                        || character == '-') {

                    if (nombreJugador.length() < 16) {
                        nombreJugador += character;
                    }

                    return true;
                }

                return false;
            }

            @Override
            public boolean keyDown(int keycode) {
                // MENÚ PRINCIPAL
                if (estado == 0) {
                    if (keycode == Input.Keys.ENTER) {
                        iniciarJuego();
                        return true;
                    }

                    if (keycode == Input.Keys.R) {
                        estado = 3;
                        return true;
                    }
                }

                // GAME OVER
                if (estado == 2) {
                    if (esperandoNombre) {
                        if (keycode == Input.Keys.ENTER) {
                            confirmarRecord();
                            return true;
                        }

                        if (keycode == Input.Keys.BACKSPACE) {
                            if (!retrocesoPresionado) {
                                retrocesoPresionado = true;

                                if (!nombreJugador.isEmpty()) {
                                    nombreJugador = nombreJugador.substring(
                                            0,
                                            nombreJugador.length() - 1
                                    );
                                }
                            }

                            return true;
                        }

                        return true;
                    }

                    if (keycode == Input.Keys.ENTER) {
                        iniciarJuego();
                        return true;
                    }

                    if (keycode == Input.Keys.R) {
                        estado = 3;
                        return true;
                    }
                }

                // TABLA DE RÉCORDS
                if (estado == 3) {
                    if (keycode == Input.Keys.ENTER
                            || keycode == Input.Keys.ESCAPE) {
                        estado = 0;
                        return true;
                    }
                }

                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.BACKSPACE) {
                    retrocesoPresionado = false;
                    return true;
                }

                return false;
            }
        });
    }

    private void iniciarJuego() {
        GameManager.getInstancia().reiniciar();

        tarro.crear();
        lluvia.crear();

        nombreJugador = "";
        esperandoNombre = false;
        recordGuardado = false;
        necesitaRegistrar = false;
        calificaRecord = false;

        estado = 1;
    }

    // ==================================================
    // TEXTO CENTRADO
    // ==================================================

    private void dibujarCentrado(
            BitmapFont fuente,
            String texto,
            float y) {

        layout.setText(fuente, texto);

        fuente.draw(
                batch,
                texto,
                (ANCHO - layout.width) / 2f,
                y
        );
    }

    private void dibujarTextoEnCentro(
            BitmapFont fuente,
            String texto,
            float centroX,
            float y) {

        layout.setText(fuente, texto);

        fuente.draw(
                batch,
                texto,
                centroX - layout.width / 2f,
                y
        );
    }

    // ==================================================
    // RÉCORDS LOCALES
    // ==================================================

    private List<Record> obtenerRecords() {
        List<Record> lista = new ArrayList<>();

        int cantidad = records.getInteger("cantidad", 0);

        for (int i = 0; i < cantidad; i++) {
            String nombre = records.getString(
                    "nombre" + i,
                    "Jugador"
            );

            int puntos = records.getInteger(
                    "puntos" + i,
                    0
            );

            lista.add(new Record(nombre, puntos));
        }

        return lista;
    }

    private boolean calificaParaRecord(int puntos) {
        List<Record> lista = obtenerRecords();

        if (lista.size() < MAX_RECORDS) {
            return true;
        }

        return puntos > lista.get(lista.size() - 1).puntos;
    }

    private void prepararGameOver() {
        int puntos = GameManager.getInstancia().getPuntos();

        calificaRecord = calificaParaRecord(puntos);
        necesitaRegistrar = calificaRecord;
        esperandoNombre = calificaRecord;
        recordGuardado = false;
        nombreJugador = "";
    }

    private void confirmarRecord() {
        if (!esperandoNombre || !necesitaRegistrar) {
            return;
        }

        String nombre = nombreJugador.trim();

        if (nombre.isEmpty()) {
            nombre = "Jugador";
        }

        int puntos = GameManager.getInstancia().getPuntos();

        guardarRecord(nombre, puntos);

        esperandoNombre = false;
        necesitaRegistrar = false;
        recordGuardado = true;
    }

    private void guardarRecord(String nombre, int puntos) {
        List<Record> lista = obtenerRecords();

        lista.add(new Record(nombre, puntos));

        lista.sort((a, b) -> Integer.compare(b.puntos, a.puntos));

        if (lista.size() > MAX_RECORDS) {
            lista = new ArrayList<>(
                    lista.subList(0, MAX_RECORDS)
            );
        }

        /*
         * Se conserva el sistema de almacenamiento original:
         * mismo Preferences, mismas claves y mismo formato.
         */
        records.clear();
        records.putInteger("cantidad", lista.size());

        for (int i = 0; i < lista.size(); i++) {
            Record record = lista.get(i);

            records.putString("nombre" + i, record.nombre);
            records.putInteger("puntos" + i, record.puntos);
        }

        records.flush();
    }

    // ==================================================
    // MENÚ PRINCIPAL
    // ==================================================

    private void dibujarPanelMenu() {
        float x = 105;
        float y = 35;
        float w = 590;
        float h = 265;
        float r = 12;

        shapes.begin(ShapeRenderer.ShapeType.Filled);

        shapes.setColor(0.035f, 0.045f, 0.065f, 0.97f);

        shapes.rect(x + r, y, w - 2 * r, h);
        shapes.rect(x, y + r, w, h - 2 * r);

        shapes.circle(x + r, y + r, r, 16);
        shapes.circle(x + w - r, y + r, r, 16);
        shapes.circle(x + r, y + h - r, r, 16);
        shapes.circle(x + w - r, y + h - r, r, 16);

        shapes.setColor(0.035f, 0.24f, 0.38f, 1f);
        shapes.rect(235, 220, 330, 38);

        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);

        shapes.setColor(0.2f, 0.75f, 1f, 1f);
        shapes.rect(235, 220, 330, 38);

        shapes.setColor(0.22f, 0.25f, 0.3f, 1f);
        shapes.rect(x, y, w, h);

        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Filled);

        // Triángulo de reproducción.
        shapes.setColor(0.35f, 0.9f, 1f, 1f);

        shapes.triangle(
                266, 231,
                266, 247,
                279, 239
        );

        // Separadores.
        shapes.setColor(0.18f, 0.2f, 0.23f, 1f);
        shapes.rect(125, 205, 550, 1);
        shapes.rect(125, 95, 550, 1);

        // Indicador de puntos.
        shapes.setColor(0.08f, 0.22f, 0.13f, 1f);
        shapes.rect(594, 155, 74, 23);

        // Indicador de pérdida de vida.
        shapes.setColor(0.28f, 0.09f, 0.11f, 1f);
        shapes.rect(594, 113, 74, 23);

        shapes.end();
    }

    private void dibujarContenidoMenu() {
        batch.begin();

        tituloFont.setColor(0.35f, 0.9f, 1f, 1f);

        dibujarCentrado(
                tituloFont,
                "RECOLECTOR DE LLUVIA",
                405
        );

        subtituloFont.setColor(Color.WHITE);

        dibujarCentrado(
                subtituloFont,
                "La ciudad te necesita.",
                373
        );

        subtituloFont.setColor(0.82f, 0.88f, 0.95f, 1f);

        dibujarCentrado(
                subtituloFont,
                "Recolecta agua, esquiva el peligro",
                348
        );

        dibujarCentrado(
                subtituloFont,
                "y sobrevive a la tormenta.",
                328
        );

        botonFont.setColor(0.35f, 0.9f, 1f, 1f);

        dibujarTextoEnCentro(
                botonFont,
                "PRESIONA ENTER PARA JUGAR",
                410,
                243
        );

        batch.draw(gotaBuenaTexture, 135, 153, 24, 29);

        font.setColor(Color.WHITE);
        font.draw(batch, "Gota de agua", 170, 177);

        font.setColor(0.68f, 0.7f, 0.74f, 1f);
        font.draw(
                batch,
                "Recolectala para sumar puntos.",
                170,
                158
        );

        font.setColor(0.65f, 1f, 0.72f, 1f);
        font.draw(batch, "+ Puntos", 601, 170);

        batch.draw(gotaMalaTexture, 135, 111, 24, 29);

        font.setColor(Color.WHITE);
        font.draw(batch, "Gota toxica", 170, 135);

        font.setColor(0.68f, 0.7f, 0.74f, 1f);
        font.draw(
                batch,
                "Esquiva su trayectoria en zigzag.",
                170,
                116
        );

        font.setColor(1f, 0.58f, 0.58f, 1f);
        font.draw(batch, "- 1 vida", 606, 128);

        // Instrucciones inferiores.
        font.setColor(0.78f, 0.83f, 0.9f, 1f);

        dibujarCentrado(
                font,
                "Moverse: A / D o flechas izquierda y derecha",
                83
        );

        font.setColor(0.35f, 0.9f, 1f, 1f);

        dibujarCentrado(
                font,
                "Presiona R para ver los records",
                59
        );

        batch.end();
    }

    // ==================================================
    // GAME OVER
    // ==================================================

    private void dibujarGameOver() {
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        shapes.setColor(0.035f, 0.045f, 0.065f, 0.97f);
        shapes.rect(170, 55, 460, 365);

        if (esperandoNombre) {
            // Campo de nombre.
            shapes.setColor(0.015f, 0.06f, 0.1f, 1f);
            shapes.rect(245, 201, 310, 38);

            // Botón de guardar.
            shapes.setColor(0.035f, 0.24f, 0.38f, 1f);
            shapes.rect(245, 147, 310, 36);
        }

        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);

        shapes.setColor(0.22f, 0.25f, 0.3f, 1f);
        shapes.rect(170, 55, 460, 365);

        if (esperandoNombre) {
            shapes.setColor(0.2f, 0.75f, 1f, 1f);
            shapes.rect(245, 201, 310, 38);
            shapes.rect(245, 147, 310, 36);
        }

        shapes.end();

        batch.begin();

        tituloFont.setColor(0.35f, 0.9f, 1f, 1f);

        dibujarCentrado(
                tituloFont,
                "¡HAS PERDIDO!",
                380
        );

        font.setColor(Color.WHITE);

        dibujarCentrado(
                font,
                "Lograste "
                        + GameManager.getInstancia().getPuntos()
                        + " puntos",
                340
        );

        if (esperandoNombre) {
            font.setColor(0.35f, 0.9f, 1f, 1f);

            dibujarCentrado(
                    font,
                    "¡Puedes entrar en los records!",
                    306
            );

            font.setColor(0.78f, 0.83f, 0.9f, 1f);

            dibujarCentrado(
                    font,
                    "Escribe tu nombre",
                    270
            );

            if (nombreJugador.isEmpty()) {
                font.setColor(0.5f, 0.6f, 0.7f, 1f);

                font.draw(
                        batch,
                        "Tu nombre...",
                        260,
                        225
                );
            } else {
                font.setColor(Color.WHITE);

                font.draw(
                        batch,
                        nombreJugador,
                        260,
                        225
                );
            }

            botonFont.setColor(0.35f, 0.9f, 1f, 1f);

            dibujarTextoEnCentro(
                    botonFont,
                    "GUARDAR RECORD",
                    400,
                    170
            );

            font.setColor(0.75f, 0.82f, 0.9f, 1f);

            dibujarCentrado(
                    font,
                    "Pulsa ENTER para guardar",
                    120
            );

        } else if (recordGuardado) {
            font.setColor(0.45f, 1f, 0.65f, 1f);

            dibujarCentrado(
                    font,
                    "¡Record guardado correctamente!",
                    290
            );

            font.setColor(Color.WHITE);

            dibujarCentrado(
                    font,
                    "ENTER: volver a jugar",
                    235
            );

            dibujarCentrado(
                    font,
                    "R: ver records",
                    205
            );

        } else {
            font.setColor(0.85f, 0.85f, 0.9f, 1f);

            dibujarCentrado(
                    font,
                    "Sigue jugando para entrar en el top 10",
                    290
            );

            font.setColor(Color.WHITE);

            dibujarCentrado(
                    font,
                    "ENTER: volver a jugar",
                    235
            );

            dibujarCentrado(
                    font,
                    "R: ver records",
                    205
            );
        }

        batch.end();
    }

    // ==================================================
    // TABLA DE RÉCORDS
    // ==================================================

    private void dibujarPantallaRecords() {
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        shapes.setColor(0.035f, 0.045f, 0.065f, 0.97f);
        shapes.rect(155, 45, 490, 390);

        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);

        shapes.setColor(0.22f, 0.25f, 0.3f, 1f);
        shapes.rect(155, 45, 490, 390);

        // Línea debajo de los encabezados.
        shapes.setColor(0.2f, 0.75f, 1f, 0.7f);
        shapes.line(185, 338, 615, 338);

        shapes.end();

        batch.begin();

        tituloFont.setColor(0.35f, 0.9f, 1f, 1f);

        dibujarCentrado(
                tituloFont,
                "TABLA DE RECORDS",
                395
        );

        font.setColor(0.65f, 0.75f, 0.85f, 1f);

        font.draw(batch, "POS.", 190, 350);
        font.draw(batch, "JUGADOR", 275, 350);
        font.draw(batch, "PUNTOS", 520, 350);

        // Leer los registros de las mismas preferencias de siempre.
        List<Record> lista = obtenerRecords();

        if (lista.isEmpty()) {
            font.setColor(Color.WHITE);

            dibujarCentrado(
                    font,
                    "Todavia no hay records guardados.",
                    255
            );

            dibujarCentrado(
                    font,
                    "Juega una partida para establecer el primero.",
                    225
            );

        } else {
            for (int i = 0; i < lista.size(); i++) {
                Record record = lista.get(i);
                float y = 315 - (i * 25);

                if (i == 0) {
                    font.setColor(1f, 0.82f, 0.35f, 1f);
                } else {
                    font.setColor(Color.WHITE);
                }

                font.draw(
                        batch,
                        String.valueOf(i + 1),
                        200,
                        y
                );

                String nombre = record.nombre;

                if (nombre.length() > 18) {
                    nombre = nombre.substring(0, 18);
                }

                font.draw(
                        batch,
                        nombre,
                        275,
                        y
                );

                font.draw(
                        batch,
                        String.valueOf(record.puntos),
                        535,
                        y
                );
            }
        }

        font.setColor(0.35f, 0.9f, 1f, 1f);

        dibujarCentrado(
                font,
                "ENTER o ESC para volver al menu",
                75
        );

        batch.end();
    }

    // ==================================================
    // RENDER
    // ==================================================

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.08f, 1);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        shapes.setProjectionMatrix(camera.combined);

        // Fondo.
        batch.begin();

        if (backgroundTexture != null) {
            batch.draw(
                    backgroundTexture,
                    0,
                    0,
                    ANCHO,
                    ALTO
            );
        }

        batch.end();

        // MENÚ
        if (estado == 0) {
            dibujarPanelMenu();
            dibujarContenidoMenu();

        // JUEGO
        } else if (estado == 1) {
            batch.begin();

            font.setColor(Color.WHITE);

            font.draw(
                    batch,
                    "Gotas totales: "
                            + GameManager.getInstancia().getPuntos(),
                    5,
                    475
            );

            int vidas = GameManager.getInstancia().getVidas();

            font.draw(batch, "Vidas:", 650, 475);

            for (int i = 0; i < vidas; i++) {
                batch.draw(
                        corazonTexture,
                        695 + (i * 32),
                        451,
                        25,
                        25
                );
            }

            batch.end();

            if (!tarro.estaHerido()) {
                float delta = Gdx.graphics.getDeltaTime();

                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)
                        || Gdx.input.isKeyPressed(Input.Keys.A)) {
                    tarro.moverIzquierda(delta);
                }

                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                        || Gdx.input.isKeyPressed(Input.Keys.D)) {
                    tarro.moverDerecha(delta);
                }

                lluvia.actualizarMovimiento(tarro);
            }

            batch.begin();

            tarro.dibujar(batch);
            lluvia.actualizarDibujoLluvia(batch);

            batch.end();

            if (GameManager.getInstancia().getVidas() <= 0) {
                lluvia.detener();

                estado = 2;
                prepararGameOver();
            }

        // GAME OVER
        } else if (estado == 2) {
            dibujarGameOver();

        // RÉCORDS
        } else if (estado == 3) {
            dibujarPantallaRecords();
        }
    }

    // ==================================================
    // LIBERAR RECURSOS
    // ==================================================

    @Override
    public void dispose() {
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }

        if (corazonTexture != null) {
            corazonTexture.dispose();
        }

        if (gotaBuenaTexture != null) {
            gotaBuenaTexture.dispose();
        }

        if (gotaMalaTexture != null) {
            gotaMalaTexture.dispose();
        }

        if (tarro != null) {
            tarro.destruir();
        }

        if (lluvia != null) {
            lluvia.destruir();
        }

        if (batch != null) {
            batch.dispose();
        }

        if (shapes != null) {
            shapes.dispose();
        }

        if (font != null) {
            font.dispose();
        }

        if (tituloFont != null) {
            tituloFont.dispose();
        }

        if (subtituloFont != null) {
            subtituloFont.dispose();
        }

        if (botonFont != null) {
            botonFont.dispose();
        }
    }
}
