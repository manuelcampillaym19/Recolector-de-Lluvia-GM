
package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {

    private Array<Gota> gotas;
    private Array<GotaFactory> fabricas;

    private long lastDropTime;

    private Music rainMusic;

    public Lluvia(Array<GotaFactory> fabricas, Music rainMusic) {

        if (fabricas == null || fabricas.size == 0) {
            throw new IllegalArgumentException(
                    "Debe existir al menos una fábrica de gotas"
            );
        }

        for (GotaFactory fabrica : fabricas) {
            if (fabrica == null || fabrica.getPeso() <= 0) {
                throw new IllegalArgumentException(
                        "Las fábricas deben ser válidas y tener un peso positivo"
                );
            }
        }

        this.fabricas = new Array<GotaFactory>(fabricas);
        this.rainMusic = rainMusic;
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

        int pesoTotal = 0;

        for (GotaFactory fabrica : fabricas) {
            pesoTotal += fabrica.getPeso();
        }

        int seleccion = MathUtils.random(1, pesoTotal);

        GotaFactory fabricaSeleccionada = null;

        for (GotaFactory fabrica : fabricas) {

            seleccion -= fabrica.getPeso();

            if (seleccion <= 0) {
                fabricaSeleccionada = fabrica;
                break;
            }
        }

        Gota nuevaGota = fabricaSeleccionada.crear(x, y);

        gotas.add(nuevaGota);

        lastDropTime = TimeUtils.nanoTime();
    }

    public void actualizarMovimiento(Tarro tarro) {

        if (TimeUtils.nanoTime() - lastDropTime > 100000000) {
            crearGotaDeLluvia();
        }

        Rectangle areaTarro = tarro.getArea();

        for (int i = 0; i < gotas.size; i++) {

            Gota gota = gotas.get(i);

            float velocidadDinamica =
                    200 + (GameManager.getInstancia().getPuntos() * 0.5f);

            if (velocidadDinamica > 600) {
                velocidadDinamica = 600;
            }

            gota.caer(
                    velocidadDinamica,
                    Gdx.graphics.getDeltaTime()
            );

            Rectangle areaGota = gota.getArea();

            if (areaGota.y + 64 < 0) {
                gotas.removeIndex(i);
                i--;
                continue;
            }

            if (areaGota.overlaps(areaTarro)) {
                gota.procesarColision(tarro);
                gotas.removeIndex(i);
                i--;
            }
        }
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {

        for (Gota gota : gotas) {
            gota.dibujar(batch);
        }
    }

    public void detener() {
        rainMusic.stop();
    }

    public void destruir() {
        rainMusic.dispose();
    }
}
