package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import java.util.Objects;

public class GotaBuenaBuilder implements GotaBuilder {

    private float x, y;
    private Texture textura;
    private EstrategiaMovimiento estrategia;
    private final Sound dropSound;

    public GotaBuenaBuilder(Sound dropSound) {
        this.dropSound = Objects.requireNonNull(
                dropSound,
                "El sonido no puede ser null"
        );
    }

    @Override
    public GotaBuenaBuilder setPosicion(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public GotaBuenaBuilder setTextura(Texture textura) {
        this.textura = Objects.requireNonNull(textura);
        return this;
    }

    @Override
    public GotaBuenaBuilder setEstrategia(EstrategiaMovimiento estrategia) {
        this.estrategia = Objects.requireNonNull(estrategia);
        return this;
    }

    @Override
    public GotaBuena build() {
        return new GotaBuena(x, y, textura, dropSound, estrategia);
    }
}
