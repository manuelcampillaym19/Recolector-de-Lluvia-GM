
package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaBuenaFactory implements GotaFactory {

    private final Texture textura;
    private final Sound dropSound;

    public GotaBuenaFactory(Texture textura, Sound dropSound) {
        this.textura = textura;
        this.dropSound = dropSound;
    }

    @Override
    public Gota crear(float x, float y) {

        GotaBuenaBuilder builder = new GotaBuenaBuilder(dropSound);

        return builder.setPosicion(x, y)
                      .setTextura(textura)
                      .setEstrategia(new MovimientoCaidaRecta())
                      .build();
    }

    @Override
    public int getPeso() {
        return 7;
    }
}
