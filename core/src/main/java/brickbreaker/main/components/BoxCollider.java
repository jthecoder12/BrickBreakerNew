package brickbreaker.main.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

import java.awt.Dimension;

public final class BoxCollider implements Component {
    private final Rectangle box;

    public BoxCollider(Vector2 position, @NotNull Dimension size) {
        box = new Rectangle();

        box.setPosition(position);
        box.setSize((float)size.width, (float)size.height);
    }

    public void updatePosition(Vector2 position) {
        box.setPosition(position);
        System.out.println(position.x);
        System.out.println(box.x);
    }

    @SuppressWarnings("unused")
    public boolean checkWith(@NotNull BoxCollider boxCollider) {
        return box.overlaps(boxCollider.getBox());
    }

    public boolean checkWith(@NotNull CircleCollider circleCollider) {
        return Intersector.overlaps(circleCollider.getCircle(), box);
    }

    Rectangle getBox() {
        return box;
    }
}
