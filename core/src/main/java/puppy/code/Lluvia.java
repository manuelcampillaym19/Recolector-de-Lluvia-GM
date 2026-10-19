package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
    private Array<Gota> gotas; 
    private long lastDropTime;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Sound dropSound;
    private Music rainMusic;
       
    public Lluvia(Texture gotaBuena, Texture gotaMala, Sound ss, Music mm) {
        this.rainMusic = mm;
        this.dropSound = ss;
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
    }
    
    public void crear() {
        gotas = new Array<Gota>();
        crearGotaDeLluvia();
        rainMusic.setLooping(true);
        rainMusic.play();
    }
    
    private void crearGotaDeLluvia() {
        float x = MathUtils.random(0, 800 - 64);
        float y = 480;
        
        if (MathUtils.random(1, 10) < 3) {
            // Ensamblamos una gota mala usando su Builder específico
            GotaBuilder builder = new GotaMalaBuilder();
            builder.setPosicion(x, y).setTextura(gotaMala).setEstrategia(new MovimientoZigZag());
            gotas.add(builder.build());
        } else {
            // Ensamblamos una gota buena usando su Builder específico
            GotaBuenaBuilder builder = new GotaBuenaBuilder();
            builder.setSonido(dropSound);
            builder.setPosicion(x, y).setTextura(gotaBuena).setEstrategia(new MovimientoCaidaRecta());
            
            gotas.add(builder.build());
        }
        lastDropTime = TimeUtils.nanoTime();
    }
    
    public void actualizarMovimiento(Tarro tarro) { 
        if(TimeUtils.nanoTime() - lastDropTime > 100000000) {
            crearGotaDeLluvia();
        }
        
        for (int i = 0; i < gotas.size; i++) {
            Gota gota = gotas.get(i);
            // mecanica nueva de dificultad
            float velocidadDinamica = 200 + (GameManager.getInstancia().getPuntos() * 0.5f);
            
            if (velocidadDinamica > 600) {
                velocidadDinamica = 600;
            }

            gota.caer(velocidadDinamica, Gdx.graphics.getDeltaTime());
            
            if(gota.getArea().y + 64 < 0) {
                gotas.removeIndex(i); 
                continue; 
            }
            
            if(gota.getArea().overlaps(tarro.getArea())) { 
                // POLIMORFISMO: La gota decide que hacer al chocar
                gota.procesarColision(tarro);
                gotas.removeIndex(i);
            }
        }   
    }
   
    public void actualizarDibujoLluvia(SpriteBatch batch) { 
        for (Gota gota : gotas) {
            gota.dibujar(batch); 
        }
    }
    
 // Método para detener el juego
    public void detener() {
        rainMusic.stop();
    }
    
    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
    }
}