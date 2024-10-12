package brickbreaker.main.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

import java.awt.Dimension;

public class BoxCollider implements Component {
    private final Rectangle box;

    public BoxCollider(Vector2 position, @NotNull Dimension size) {
        box = new Rectangle();

        box.setPosition(position);
        box.setSize((float)size.getWidth(), (float)size.getHeight());
    }

    public void updatePosition(Vector2 position) {
        box.setPosition(position);
    }

    public boolean checkWith(@NotNull BoxCollider boxCollider) {
        return box.overlaps(boxCollider.getBox());
    }

    public Rectangle getBox() {
        return box;
    }
}
