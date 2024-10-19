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
        box.setSize((float)size.width, (float)size.height+10);
    }

    public void updatePosition(@NotNull Vector2 position) {
        box.setPosition(position);
        System.out.println(position.x);
        System.out.println(box.x);
    }

    public boolean checkWith(@NotNull BoxCollider boxCollider) {
        return box.overlaps(boxCollider.getBox());
    }

    private Rectangle getBox() {
        return box;
    }
}
