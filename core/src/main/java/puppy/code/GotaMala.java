package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import java.util.Objects;

public class GotaMala extends Gota {

    private final Sound sonidoHerido;

    public GotaMala(
            float x,
            float y,
            Texture textura,
            Sound sonidoHerido,
            EstrategiaMovimiento estrategia) {

        super(x, y, textura, estrategia);

        this.sonidoHerido = Objects.requireNonNull(
                sonidoHerido,
                "El sonido de la gota mala no puede ser null"
        );
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.dañar();
    }

    @Override
    protected void reproducirSonido() {
        sonidoHerido.play();
    }
}
