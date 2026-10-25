
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

        // Colores de texto
        font.setColor(Color.WHITE);
        tituloFont.setColor(new Color(0.35f, 0.9f, 1f, 1f));
        subtituloFont.setColor(Color.WHITE);
        botonFont.setColor(Color.WHITE);

        // Recursos del jugador
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

        // Sonido y música
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

    // Dibuja el panel del menú detrás del texto
    private void dibujarPanelMenu() {

        shapes.setProjectionMatrix(camera.combined);

        // Fondo oscuro semitransparente
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.015f, 0.035f, 0.12f, 0.88f);
        shapes.rect(100, 55, 600, 370);
        shapes.end();

        // Borde celeste
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.2f, 0.8f, 1f, 1f);
        shapes.rect(100, 55, 600, 370);
        shapes.end();
    }

    // Dibuja título, instrucciones y leyenda
    private void dibujarContenidoMenu() {

        batch.begin();

        // Título
        tituloFont.setColor(0.35f, 0.9f, 1f, 1f);
        dibujarCentrado(
            tituloFont,
            "RECOLECTOR DE LLUVIA",
            370
        );

        // Subtítulo
        subtituloFont.setColor(Color.WHITE);
        dibujarCentrado(
            subtituloFont,
            "La ciudad te necesita.",
            333
        );

        subtituloFont.setColor(0.72f, 0.88f, 1f, 1f);
        dibujarCentrado(
            subtituloFont,
            "Recolecta agua, esquiva el peligro",
            307
        );
        dibujarCentrado(
            subtituloFont,
            "y sobrevive a la tormenta.",
            284
        );

        // Botón visual para iniciar
        shapes.setProjectionMatrix(camera.combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.02f, 0.28f, 0.55f, 1f);
        shapes.rect(230, 218, 340, 43);
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.3f, 0.9f, 1f, 1f);
        shapes.rect(230, 218, 340, 43);
        shapes.end();

        botonFont.setColor(Color.WHITE);
        dibujarCentrado(
            botonFont,
            "PRESIONA ENTER PARA JUGAR",
            245
        );

        // Leyenda de las gotas
        batch.draw(gotaBuenaTexture, 175, 155, 32, 32);

        font.setColor(0.4f, 0.9f, 1f, 1f);
        font.draw(batch, "Gota de agua", 215, 177);

        font.setColor(Color.WHITE);
        font.draw(batch, "+ Suma puntos", 215, 155);

        batch.draw(gotaMalaTexture, 420, 155, 32, 32);

        font.setColor(1f, 0.4f, 0.4f, 1f);
        font.draw(batch, "Gota toxica", 460, 177);

        font.setColor(Color.WHITE);
        font.draw(batch, "- Resta una vida", 460, 155);

        // Controles
        font.setColor(0.75f, 0.85f, 1f, 1f);
        dibujarCentrado(
            font,
            "Movimiento: A / D o flechas izquierda y derecha",
            105
        );

        font.setColor(Color.WHITE);

        batch.end();
    }

    @Override
    public void render() {

        ScreenUtils.clear(0, 0, 0.08f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Fondo del juego
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

            batch.begin();
            tarro.dibujar(batch);
            lluvia.actualizarDibujoLluvia(batch);
            batch.end();

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
