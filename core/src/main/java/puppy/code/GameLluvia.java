
package puppy.code;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

    // 0 = Menú principal | 1 = Jugando | 2 = Game Over
    private int estado = 0;

    private static final float ANCHO = 800;
    private static final float ALTO = 480;

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

        font.setColor(Color.WHITE);
        tituloFont.setColor(new Color(0.35f, 0.9f, 1f, 1f));
        subtituloFont.setColor(Color.WHITE);
        botonFont.setColor(Color.WHITE);

        // Jugador
        Sound hurtSound =
            Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));

        Texture bucketTexture =
            new Texture(Gdx.files.internal("bucket.png"));

        tarro = new Tarro(bucketTexture, hurtSound);

        // Gotas
        gotaBuenaTexture =
            new Texture(Gdx.files.internal("gotaAgua3.png"));

        gotaMalaTexture =
            new Texture(Gdx.files.internal("gotaToxica.png"));

        // Fondo y vidas
        backgroundTexture =
            new Texture(Gdx.files.internal("fondo.png"));

        corazonTexture =
            new Texture(Gdx.files.internal("corazonRojo2.png"));

        // Audio
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
    }

    private void iniciarJuego() {
        GameManager.getInstancia().reiniciar();
        tarro.crear();
        lluvia.crear();
        estado = 1;
    }

    private void dibujarCentrado(
        BitmapFont fuente,
        String texto,
        float y
    ) {
        layout.setText(fuente, texto);

        fuente.draw(
            batch,
            texto,
            (ANCHO - layout.width) / 2f,
            y
        );
    }

    // Panel oscuro y elementos decorativos del menú
    private void dibujarPanelMenu() {

        shapes.setProjectionMatrix(camera.combined);

        float x = 105;
        float y = 35;
        float w = 590;
        float h = 265;
        float r = 12;

        // Fondo oscuro con esquinas redondeadas
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        shapes.setColor(0.035f, 0.045f, 0.065f, 0.97f);

        shapes.rect(x + r, y, w - 2 * r, h);
        shapes.rect(x, y + r, w, h - 2 * r);

        shapes.circle(x + r, y + r, r, 16);
        shapes.circle(x + w - r, y + r, r, 16);
        shapes.circle(x + r, y + h - r, r, 16);
        shapes.circle(x + w - r, y + h - r, r, 16);

        // Botón de inicio
        shapes.setColor(0.035f, 0.24f, 0.38f, 1f);
        shapes.rect(235, 220, 330, 38);

        shapes.end();

        // Borde del botón
        shapes.begin(ShapeRenderer.ShapeType.Line);

        shapes.setColor(0.2f, 0.75f, 1f, 1f);
        shapes.rect(235, 220, 330, 38);

        shapes.end();

        // Líneas divisorias
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        shapes.setColor(0.18f, 0.2f, 0.23f, 1f);
        shapes.rect(125, 205, 550, 1);
        shapes.rect(125, 95, 550, 1);

        // Etiqueta verde de puntos
        shapes.setColor(0.08f, 0.22f, 0.13f, 1f);
        shapes.rect(594, 155, 74, 23);

        // Etiqueta roja de vida
        shapes.setColor(0.28f, 0.09f, 0.11f, 1f);
        shapes.rect(594, 113, 74, 23);

        shapes.end();

        // Borde exterior del panel
        shapes.begin(ShapeRenderer.ShapeType.Line);

        shapes.setColor(0.22f, 0.25f, 0.3f, 1f);
        shapes.rect(x, y, w, h);

        shapes.end();
    }

    // Textos e imágenes del menú
    private void dibujarContenidoMenu() {

        batch.begin();

        // Título principal
        tituloFont.setColor(0.35f, 0.9f, 1f, 1f);

        dibujarCentrado(
            tituloFont,
            "RECOLECTOR DE LLUVIA",
            405
        );

        // Subtítulo
        subtituloFont.setColor(Color.WHITE);

        dibujarCentrado(
            subtituloFont,
            "La ciudad te necesita.",
            373
        );

        // Descripción
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

        // Texto del botón
        botonFont.setColor(0.35f, 0.9f, 1f, 1f);

        dibujarCentrado(
            botonFont,
            "▶ PRESIONA ENTER PARA JUGAR",
            243
        );

        // Gota de agua
        batch.draw(
            gotaBuenaTexture,
            135,
            153,
            24,
            29
        );

        font.setColor(Color.WHITE);
        font.draw(batch, "Gota de agua", 170, 177);

        font.setColor(0.68f, 0.7f, 0.74f, 1f);
        font.draw(
            batch,
            "Recolectala para sumar puntos.",
            170,
            158
        );

        // Etiqueta de puntos
        font.setColor(0.65f, 1f, 0.72f, 1f);
        font.draw(batch, "+ Puntos", 601, 170);

        // Gota tóxica
        batch.draw(
            gotaMalaTexture,
            135,
            111,
            24,
            29
        );

        font.setColor(Color.WHITE);
        font.draw(batch, "Gota toxica", 170, 135);

        font.setColor(0.68f, 0.7f, 0.74f, 1f);
        font.draw(
            batch,
            "Esquiva su trayectoria en zigzag.",
            170,
            116
        );

        // Etiqueta de vida
        font.setColor(1f, 0.58f, 0.58f, 1f);
        font.draw(batch, "- 1 vida", 606, 128);

        // Controles inferiores
        font.setColor(0.78f, 0.83f, 0.9f, 1f);

        dibujarCentrado(
            font,
            "Moverse: A / D o flechas izquierda y derecha",
            68
        );

        font.setColor(Color.WHITE);

        batch.end();
    }

    @Override
    public void render() {

        ScreenUtils.clear(0, 0, 0.08f, 1);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        shapes.setProjectionMatrix(camera.combined);

        // Dibujar fondo
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

        // MENÚ PRINCIPAL
        if (estado == 0) {

            dibujarPanelMenu();
            dibujarContenidoMenu();

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                iniciarJuego();
            }

        // JUEGO EN CURSO
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

            // Movimiento del jugador y actualización de las gotas
            if (!tarro.estaHerido()) {

                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)
                    || Gdx.input.isKeyPressed(Input.Keys.A)) {

                    tarro.moverIzquierda(
                        Gdx.graphics.getDeltaTime()
                    );
                }

                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                    || Gdx.input.isKeyPressed(Input.Keys.D)) {

                    tarro.moverDerecha(
                        Gdx.graphics.getDeltaTime()
                    );
                }

                lluvia.actualizarMovimiento(tarro);
            }

            // Dibujar jugador y gotas
            batch.begin();

            tarro.dibujar(batch);
            lluvia.actualizarDibujoLluvia(batch);

            batch.end();

            // Comprobar Game Over
            if (GameManager.getInstancia().getVidas() <= 0) {
                lluvia.detener();
                estado = 2;
            }

        // GAME OVER
        } else if (estado == 2) {

            batch.begin();

            tituloFont.setColor(0.35f, 0.9f, 1f, 1f);

            dibujarCentrado(
                tituloFont,
                "¡HAS PERDIDO!",
                310
            );

            font.setColor(Color.WHITE);

            dibujarCentrado(
                font,
                "Lograste "
                    + GameManager.getInstancia().getPuntos()
                    + " puntos",
                260
            );

            dibujarCentrado(
                font,
                "Presiona ENTER para volver a jugar",
                220
            );

            batch.end();

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                iniciarJuego();
            }
        }
    }

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

        tarro.destruir();
        lluvia.destruir();

        batch.dispose();
        shapes.dispose();

        font.dispose();
        tituloFont.dispose();
        subtituloFont.dispose();
        botonFont.dispose();
    }
}
