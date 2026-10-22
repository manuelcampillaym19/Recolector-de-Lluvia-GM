
package puppy.code;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameLluvia extends ApplicationAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;

    private Tarro tarro;
    private Lluvia lluvia;

    private Texture backgroundTexture;
    private Texture corazonTexture;

    // 0 = Menú principal | 1 = Jugando | 2 = Game Over
    private int estado = 0;

    @Override
    public void create() {
        font = new BitmapFont();

        // Cargar recursos de audio y texturas
        Sound hurtSound =
            Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));

        Texture bucketTexture =
            new Texture(Gdx.files.internal("bucket.png"));

        tarro = new Tarro(bucketTexture, hurtSound);

        Texture gotaBuena =
            new Texture(Gdx.files.internal("gotaAgua.png"));

        Texture gotaMala =
            new Texture(Gdx.files.internal("gotaToxica.png"));

        backgroundTexture =
            new Texture(Gdx.files.internal("fondo.png"));

        corazonTexture =
            new Texture(Gdx.files.internal("corazonRojo2.png"));

        Sound dropSound =
            Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

        Music rainMusic =
            Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        rainMusic.setVolume(0.8f);

        lluvia = new Lluvia(
            gotaBuena,
            gotaMala,
            dropSound,
            rainMusic
        );

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();
    }

    // Prepara una partida nueva
    private void iniciarJuego() {
        GameManager.getInstancia().reiniciar();
        tarro.crear();
        lluvia.crear();
        estado = 1;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Dibujar fondo
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, 800, 480);
        }

        // MENÚ PRINCIPAL
        if (estado == 0) {
            font.draw(batch, "RECOLECTOR DE LLUVIA", 320, 300);
            font.draw(batch, "Presiona ENTER para comenzar", 300, 250);
            font.draw(batch, "Gota de agua: +1 punto", 300, 210);
            font.draw(batch, "Gota toxica: pierdes una vida", 300, 185);

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                iniciarJuego();
            }

        // JUEGO EN CURSO
        } else if (estado == 1) {

            font.draw(
                batch,
                "Gotas totales: " + GameManager.getInstancia().getPuntos(),
                5,
                475
            );

            // Mostrar las vidas restantes con corazones
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

            // Movimiento del tarro
            if (!tarro.estaHerido()) {

                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)
                    || Gdx.input.isKeyPressed(Input.Keys.A)) {
                    tarro.moverIzquierda(Gdx.graphics.getDeltaTime());
                }

                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                    || Gdx.input.isKeyPressed(Input.Keys.D)) {
                    tarro.moverDerecha(Gdx.graphics.getDeltaTime());
                }

                lluvia.actualizarMovimiento(tarro);
            }

            // Dibujar tarro y gotas
            tarro.dibujar(batch);
            lluvia.actualizarDibujoLluvia(batch);

            // Comprobar si se acabaron las vidas
            if (GameManager.getInstancia().getVidas() <= 0) {
                lluvia.detener();
                estado = 2;
            }

        // GAME OVER
        } else if (estado == 2) {
            font.draw(batch, "¡Has perdido!", 360, 300);

            font.draw(
                batch,
                "Lograste " + GameManager.getInstancia().getPuntos() + " puntos",
                340,
                260
            );

            font.draw(
                batch,
                "Presiona ENTER para volver a jugar",
                290,
                220
            );

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                iniciarJuego();
            }
        }

        batch.end();
    }

    @Override
    public void dispose() {
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }

        if (corazonTexture != null) {
            corazonTexture.dispose();
        }

        tarro.destruir();
        lluvia.destruir();
        batch.dispose();
        font.dispose();
    }
}
