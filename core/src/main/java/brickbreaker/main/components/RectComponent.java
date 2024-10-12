package brickbreaker.main.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

import java.awt.Dimension;

public class RectComponent implements Component {
    private final ShapeRenderer renderer;
    public final Vector2 position;
    private final Dimension size;

    public RectComponent(@NotNull Vector2 position, @NotNull Dimension size) {
        this.position = position;
        this.size = size;

        renderer = new ShapeRenderer();
    }

    public void render() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.rect(position.x, position.y, size.width, size.height);
        renderer.end();
    }
}
