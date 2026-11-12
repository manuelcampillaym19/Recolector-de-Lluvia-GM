
package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Gota implements Colisionable {

    // Atributos privados para proteger el estado interno.
    private final Rectangle area;
    private final Texture textura;
    private final EstrategiaMovimiento estrategia;

    public Gota(float x, float y, Texture textura, EstrategiaMovimiento estrategia) {
        this.area = new Rectangle(x, y, 64, 64);
        this.textura = textura;
        this.estrategia = estrategia;
    }

    @Override
    public Rectangle getArea() {
        // Devolvemos una copia para evitar modificaciones externas.
        return new Rectangle(area);
    }

    public void caer(float velocidad, float deltaTime) {
        estrategia.mover(area, velocidad, deltaTime);
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y, area.width, area.height);
    }

    // PATRÓN TEMPLATE METHOD
    public final void procesarColision(Tarro tarro) {
        aplicarEfecto(tarro);
        reproducirSonido();
    }

    // Cada tipo de gota define su propio efecto.
    protected abstract void aplicarEfecto(Tarro tarro);

    // Cada tipo de gota define su propio sonido.
    protected abstract void reproducirSonido();
}
