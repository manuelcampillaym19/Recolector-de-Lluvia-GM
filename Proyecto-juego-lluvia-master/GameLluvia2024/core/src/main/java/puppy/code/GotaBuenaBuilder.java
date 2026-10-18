package puppy.code;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaBuenaBuilder implements GotaBuilder {
    private float x, y;
    private Texture textura;
    private EstrategiaMovimiento estrategia;
    private Sound dropSound;

    @Override
    public GotaBuilder setPosicion(float x, float y) { this.x = x; this.y = y; return this; }
    @Override
    public GotaBuilder setTextura(Texture textura) { this.textura = textura; return this; }
    @Override
    public GotaBuilder setEstrategia(EstrategiaMovimiento estrategia) { this.estrategia = estrategia; return this; }
    
    public GotaBuenaBuilder setSonido(Sound sonido) { this.dropSound = sonido; return this; }

    @Override
    public Gota build() { return new GotaBuena(x, y, textura, dropSound, estrategia); }
}