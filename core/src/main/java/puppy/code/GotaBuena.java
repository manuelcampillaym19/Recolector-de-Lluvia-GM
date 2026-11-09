package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import java.util.Objects;

public class GotaBuena extends Gota {

    private final Sound dropSound;

    public GotaBuena(
            float x,
            float y,
            Texture textura,
            Sound dropSound,
            EstrategiaMovimiento estrategia) {

        super(x, y, textura, estrategia);
        this.dropSound = Objects.requireNonNull(
                dropSound,
                "El sonido de la gota buena no puede ser null"
        );
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos(10);
    }

    @Override
    protected void reproducirSonido() {
        dropSound.play();
    }
}
