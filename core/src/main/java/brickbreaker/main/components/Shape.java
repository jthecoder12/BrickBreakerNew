package brickbreaker.main.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

public class Shape implements Component {
    protected ShapeRenderer renderer;
    public Vector2 position;

    protected void init(@NotNull Vector2 position) {
        renderer = new ShapeRenderer();
        this.position = position;
    }
}
