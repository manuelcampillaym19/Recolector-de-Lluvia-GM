package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaMalaFactory implements GotaFactory {

    private final Texture textura;
    private final Sound sonidoHerido;

    public GotaMalaFactory(Texture textura, Sound sonidoHerido) {
        this.textura = textura;
        this.sonidoHerido = sonidoHerido;
    }

    @Override
    public Gota crear(float x, float y) {

        GotaMalaBuilder builder = new GotaMalaBuilder(sonidoHerido);

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
