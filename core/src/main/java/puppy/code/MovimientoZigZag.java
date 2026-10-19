package puppy.code;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class MovimientoZigZag implements EstrategiaMovimiento {
    @Override
    public void mover(Rectangle area, float velocidad, float deltaTime) {
        area.y -= velocidad * deltaTime; // Cae hacia abajo
        
        // Zig-zag
        float tiempo = TimeUtils.nanoTime() * 1e-9f;
        area.x += MathUtils.sin(tiempo * 3f) * 80 * deltaTime; 
    }
}