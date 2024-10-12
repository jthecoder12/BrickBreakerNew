package brickbreaker.main;

import brickbreaker.main.components.RectComponent;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Entity paddle;

    @Override
    public void create() {
        PooledEngine engine = new PooledEngine();
        paddle = engine.createEntity();
        paddle.add(new RectComponent(new Vector2(Gdx.graphics.getWidth()/2f-30, Gdx.graphics.getHeight()/2f-250), new Dimension(150, 30)));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        paddle.getComponent(RectComponent.class).render();

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            paddle.getComponent(RectComponent.class).position.add(new Vector2(-15, 0));
        } else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            paddle.getComponent(RectComponent.class).position.add(new Vector2(15, 0));
        }
    }
}
