package brickbreaker.main.objects;

import brickbreaker.main.components.BoxCollider;
import brickbreaker.main.components.RectComponent;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

import java.awt.Dimension;

public final class Paddle extends Entity {
    public Paddle(@NotNull Engine engine) {
        engine.addEntity(this);

        add(new RectComponent(new Vector2(Gdx.graphics.getWidth()/2f-30, Gdx.graphics.getHeight()/2f-250), new Dimension(300, 20)));
        add(new BoxCollider(new Vector2(Gdx.graphics.getWidth()/2f-30, Gdx.graphics.getHeight()/2f-250), new Dimension(300, 20)));
    }
}
