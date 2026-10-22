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
    // VARIABLES DE ESTADO DEL JUEGO
    // 0 = Menú Principal | 1 = Jugando | 2 = Game Over
    private int estado = 0; 

    @Override
    public void create () {
        font = new BitmapFont(); 
        
        // Cargar recursos (Audio y Texturas)
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")), hurtSound);
        
        Texture gotaBuena = new Texture(Gdx.files.internal("GotaBuena.png"));
        Texture gotaMala = new Texture(Gdx.files.internal("gotaToxica.png"));
        backgroundTexture = new Texture(Gdx.files.internal("fondo.png"));
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        
        rainMusic.setVolume(0.8f);
        lluvia = new Lluvia(gotaBuena, gotaMala, dropSound, rainMusic);
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
    }
    
    // Metodo que prepara todo para empezar una partida nueva
    private void iniciarJuego() {
        GameManager.getInstancia().reiniciar(); // Resetea vidas y puntos
        tarro.crear();
        lluvia.crear();
        estado = 1;
    }

    @Override
    public void render () {
        // Limpia la pantalla
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        //  MAQUINA DE ESTADOS 
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, 800, 480);
        }
        
        if (estado == 0) {
            font.draw(batch, "RECOLECTOR DE LLUVIA", 320, 300);
            font.draw(batch, "Presiona ENTER para comenzar", 300, 250);
            font.draw(batch, "Gota azul: +1 punto", 300, 210);
            font.draw(batch, "Gota toxica: pierdes una vida", 300, 185);
            
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                iniciarJuego();
            }
            
        } else if (estado == 1) {
            font.draw(batch, "Gotas totales: " + GameManager.getInstancia().getPuntos(), 5, 475);
            int vidas = GameManager.getInstancia().getVidas();

            String corazones = "";

            for (int i = 0; i < vidas; i++) {
                corazones += "♥ ";
            }

font.draw(batch, "Vidas: " + corazones, 650, 475);
            
            if (!tarro.estaHerido()) {
                // ahora GameLluvia controla el input y le da la orden al tarro
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                    tarro.moverIzquierda(Gdx.graphics.getDeltaTime());
                }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                    tarro.moverDerecha(Gdx.graphics.getDeltaTime());
                }
                lluvia.actualizarMovimiento(tarro);    
            }
            
            tarro.dibujar(batch);
            lluvia.actualizarDibujoLluvia(batch);
            
            if (GameManager.getInstancia().getVidas() <= 0) {
                lluvia.detener();
                estado = 2;
            }
            
        } else if (estado == 2) {
            font.draw(batch, "¡Has perdido!", 360, 300);
            font.draw(batch, "Lograste " + GameManager.getInstancia().getPuntos() + " puntos", 340, 260);
            font.draw(batch, "Presiona ENTER para volver a jugar", 290, 220);
            
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                iniciarJuego();
            }
        }
        
        batch.end();    
    }
    
    @Override
    public void dispose () {
    	// DESTRUIR LA TEXTURA DEL FONDO
        if (backgroundTexture != null) backgroundTexture.dispose();
        
        tarro.destruir();
        lluvia.destruir();
        batch.dispose();
        font.dispose();
    }
}
