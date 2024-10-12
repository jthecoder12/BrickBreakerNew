package brickbreaker.main;

import brickbreaker.main.components.BoxCollider;
import brickbreaker.main.components.CircleComponent;
import brickbreaker.main.components.RectComponent;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Dimension;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Entity paddle;
    private Entity ball;
    private byte ballDirection = -1;

    @Override
    public void create() {
        PooledEngine engine = new PooledEngine();

        paddle = engine.createEntity();
        paddle.add(new RectComponent(new Vector2(Gdx.graphics.getWidth()/2f-30, Gdx.graphics.getHeight()/2f-250), new Dimension(150, 30)));
        paddle.add(new BoxCollider(new Vector2(Gdx.graphics.getWidth()/2f-30, Gdx.graphics.getHeight()/2f-250), new Dimension(150, 30)));

        ball = engine.createEntity();
        ball.add(new CircleComponent(new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f), 15));
        ball.add(new BoxCollider(new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f), new Dimension(30, 30)));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        ScreenUtils.clear(new Color(100/255f, 100/255f, 100/255f, 1));

        paddle.getComponent(RectComponent.class).render();
        ball.getComponent(CircleComponent.class).render();

        ball.getComponent(CircleComponent.class).position.add(0, 10*ballDirection);

        paddle.getComponent(BoxCollider.class).updatePosition(paddle.getComponent(RectComponent.class).position);
        ball.getComponent(BoxCollider.class).updatePosition(ball.getComponent(CircleComponent.class).position);

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            paddle.getComponent(RectComponent.class).position.add(new Vector2(-15, 0));
        } else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            paddle.getComponent(RectComponent.class).position.add(new Vector2(15, 0));
        }

        if(paddle.getComponent(BoxCollider.class).checkWith(ball.getComponent(BoxCollider.class))) {
            ballDirection = 1;
        }

        if(ball.getComponent(CircleComponent.class).position.y >= Gdx.graphics.getHeight()) {
            ballDirection = -1;
        }
    }
}
