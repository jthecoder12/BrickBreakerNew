package brickbreaker.main.objects;

import brickbreaker.main.components.CircleCollider;
import brickbreaker.main.components.CircleComponent;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

public final class Ball extends Entity {
    public Ball(@NotNull Engine engine) {
        engine.addEntity(this);

        add(new CircleComponent(new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f), 15));
        add(new CircleCollider(new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f), 15));
    }
}
