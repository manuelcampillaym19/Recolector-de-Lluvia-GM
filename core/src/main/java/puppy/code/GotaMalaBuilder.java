package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import java.util.Objects;

public class GotaMalaBuilder implements GotaBuilder {

    private float x, y;
    private Texture textura;
    private EstrategiaMovimiento estrategia;
    private final Sound sonidoHerido;

    public GotaMalaBuilder(Sound sonidoHerido) {
        this.sonidoHerido = Objects.requireNonNull(
                sonidoHerido,
                "El sonido de la gota mala no puede ser null"
        );
    }

    @Override
    public GotaMalaBuilder setPosicion(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public GotaMalaBuilder setTextura(Texture textura) {
        this.textura = Objects.requireNonNull(textura);
        return this;
    }

    @Override
    public GotaMalaBuilder setEstrategia(EstrategiaMovimiento estrategia) {
        this.estrategia = Objects.requireNonNull(estrategia);
        return this;
    }

    @Override
    public GotaMala build() {
        return new GotaMala(
                x,
                y,
                textura,
                sonidoHerido,
                estrategia
        );
    }
}
