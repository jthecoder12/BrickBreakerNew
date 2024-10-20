package brickbreaker.main.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

public final class CircleCollider implements Component {
    private final Circle circle;

    public CircleCollider(Vector2 position, float radius) {
        circle = new Circle();

        circle.setPosition(position);
        circle.setRadius(radius);
    }

    public void updatePosition(Vector2 position) {
        circle.setPosition(position);
    }

    @SuppressWarnings("unused")
    public boolean checkWith(@NotNull CircleCollider circleCollider) {
        return circle.overlaps(circleCollider.getCircle());
    }

    @SuppressWarnings("unused")
    public boolean checkWith(@NotNull BoxCollider boxCollider) {
        return Intersector.overlaps(circle, boxCollider.getBox());
    }

    Circle getCircle() {
        return circle;
    }
}
