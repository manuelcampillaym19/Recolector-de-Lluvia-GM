package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public interface GotaBuilder {
    GotaBuilder setPosicion(float x, float y);
    GotaBuilder setTextura(Texture textura);
    GotaBuilder setEstrategia(EstrategiaMovimiento estrategia);
    Gota build();
}