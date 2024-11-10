package brickbreaker.main.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

import java.awt.Dimension;

public final class RectComponent extends Shape {
    private final Dimension size;
    private Color color;

    public RectComponent(@NotNull Vector2 position, @NotNull Dimension size) {
        init(position);
        this.size = size;
    }

    public void render() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        if(color != null) renderer.setColor(color);
        renderer.rect(position.x, position.y, size.width, size.height);
        renderer.end();
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public Dimension getSize() {
        return size;
    }
}
