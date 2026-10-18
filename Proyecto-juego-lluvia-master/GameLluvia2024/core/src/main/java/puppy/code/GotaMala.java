package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class GotaMala extends Gota {

    public GotaMala(float x, float y, Texture textura, EstrategiaMovimiento estrategia) {
        super(x, y, textura, estrategia);
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.dañar(); 
    }
}