package brickbreaker.main.components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

public final class CircleComponent extends Shape {
    private final float radius;

    public CircleComponent(@NotNull Vector2 position, float radius) {
        init(position);
        this.radius = radius;
    }

    public void render() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.circle(position.x, position.y, radius);
        renderer.end();
    }
}
