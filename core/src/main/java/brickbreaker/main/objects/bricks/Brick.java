package brickbreaker.main.objects.bricks;

import brickbreaker.main.components.BoxCollider;
import brickbreaker.main.components.RectComponent;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

import java.awt.Dimension;

public final class Brick extends Entity {
    public Brick(@NotNull Engine engine, @NotNull Vector2 position) {
        engine.addEntity(this);

        add(new RectComponent(position, new Dimension(Gdx.graphics.getWidth()/10, 30)));
        add(new BoxCollider(position, new Dimension(Gdx.graphics.getWidth()/10, 30)));
    }

    public void render() {
        getComponent(RectComponent.class).render();
    }
}
