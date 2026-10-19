package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Gota implements Colisionable {
    protected Rectangle area;
    protected Texture textura;
    protected EstrategiaMovimiento estrategia;

    public Gota(float x, float y, Texture textura, EstrategiaMovimiento estrategia) {
        this.area = new Rectangle();
        this.area.x = x;
        this.area.y = y;
        this.area.width = 64;
        this.area.height = 64;
        this.textura = textura;
        this.estrategia = estrategia;
    }

    @Override
    public Rectangle getArea() {
        return area;
    }

    public void caer(float velocidad, float deltaTime) {
        estrategia.mover(area, velocidad, deltaTime);
    }

    public void dibujar(SpriteBatch batch) {
    	batch.draw(textura, area.x, area.y, area.width, area.height);
    }

    // --- PATRÓN TEMPLATE METHOD ---
    // Este es el esqueleto: bloqueado con 'final' para que los hijos no lo rompan
    public final void procesarColision(Tarro tarro) {
        aplicarEfecto(tarro);  
        reproducirSonido();   
    }

    // Metodo abstracto: cada hijo debe decir que hace
    protected abstract void aplicarEfecto(Tarro tarro);

    // Metodo Hook: comportamiento por defecto (nada), los hijos pueden sobrescribirlo
    protected void reproducirSonido() {
    }
}