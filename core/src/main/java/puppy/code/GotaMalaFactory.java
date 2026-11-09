
package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class GotaMalaFactory implements GotaFactory {

    private final Texture textura;

    public GotaMalaFactory(Texture textura) {
        this.textura = textura;
    }

    @Override
    public Gota crear(float x, float y) {

        GotaMalaBuilder builder = new GotaMalaBuilder();

        return builder.setPosicion(x, y)
                      .setTextura(textura)
                      .setEstrategia(new MovimientoZigZag())
                      .build();
    }

    @Override
    public int getPeso() {
        return 3;
    }
}
