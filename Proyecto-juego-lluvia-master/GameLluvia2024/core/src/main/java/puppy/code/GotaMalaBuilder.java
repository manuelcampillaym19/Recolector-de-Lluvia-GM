package puppy.code;
import com.badlogic.gdx.graphics.Texture;

public class GotaMalaBuilder implements GotaBuilder {
    private float x, y;
    private Texture textura;
    private EstrategiaMovimiento estrategia;

    @Override
    public GotaBuilder setPosicion(float x, float y) { this.x = x; this.y = y; return this; }
    @Override
    public GotaBuilder setTextura(Texture textura) { this.textura = textura; return this; }
    @Override
    public GotaBuilder setEstrategia(EstrategiaMovimiento estrategia) { this.estrategia = estrategia; return this; }

    @Override
    public Gota build() { return new GotaMala(x, y, textura, estrategia); }
}