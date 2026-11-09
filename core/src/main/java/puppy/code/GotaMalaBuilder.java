package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import java.util.Objects;

public class GotaMalaBuilder implements GotaBuilder {

    private float x, y;
    private Texture textura;
    private EstrategiaMovimiento estrategia;

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
        return new GotaMala(x, y, textura, estrategia);
    }
}
