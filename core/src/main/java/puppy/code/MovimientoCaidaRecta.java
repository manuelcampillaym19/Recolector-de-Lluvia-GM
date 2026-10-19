package puppy.code;

import com.badlogic.gdx.math.Rectangle;

public class MovimientoCaidaRecta implements EstrategiaMovimiento {
    @Override
    public void mover(Rectangle area, float velocidad, float deltaTime) {
        area.y -= velocidad * deltaTime; // Cae directo hacia abajo
    }
}