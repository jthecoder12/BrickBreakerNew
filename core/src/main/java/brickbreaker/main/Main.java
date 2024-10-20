package brickbreaker.main;

import brickbreaker.main.components.BoxCollider;
import brickbreaker.main.components.CircleCollider;
import brickbreaker.main.components.CircleComponent;
import brickbreaker.main.components.RectComponent;
import brickbreaker.main.objects.Ball;
import brickbreaker.main.objects.Paddle;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisWindow;

import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public final class Main extends ApplicationAdapter {
    private Paddle paddle;
    private Ball ball;
    private byte ballDirection = -1;
    private byte sideDirection = -1;
    private int score;
    private Label scoreLabel;
    private Stage stage;
    private VisCheckBox mouseModeCheckbox;
    private Controller controller;
    private final ArrayList<Brick> bricks = new ArrayList<>();

    @Override
    public void create() {
        PooledEngine engine = new PooledEngine();

        paddle = new Paddle(engine);
        ball = new Ball(engine);

        bricks.add(new Brick(engine, new Vector2(0, Gdx.graphics.getHeight()/1.04499274311f)));
        bricks.add(new Brick(engine, new Vector2(Gdx.graphics.getWidth()/1.10249784668f, Gdx.graphics.getHeight()/1.04499274311f)));

        controller = Controllers.getCurrent();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        VisUI.load();

        BitmapFont font = new BitmapFont(Gdx.files.internal("Varela_Round/Varela_Round.fnt"));

        VisWindow window = new VisWindow("Settings");
        window.setResizable(true);
        window.setVisible(false);
        window.setSize(300, 300);
        window.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        scoreLabel = new Label("Score: 0", labelStyle);
        scoreLabel.setPosition(0, Gdx.graphics.getHeight()-50);
        scoreLabel.setColor(1, 0, 0, 1);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;

        TextButton menuButton = new TextButton("Menu", buttonStyle);
        menuButton.setPosition(Gdx.graphics.getWidth()/1.07f, 0);
        menuButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(button == 0) {
                    window.setVisible(!window.isVisible());
                }

                return true;
            }
        });

        mouseModeCheckbox = new VisCheckBox("Mouse Mode");
        window.add(mouseModeCheckbox);

        stage.addActor(scoreLabel);
        stage.addActor(menuButton);
        stage.addActor(window);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        ScreenUtils.clear(new Color(100/255f, 100/255f, 100/255f, 1));

        paddle.getComponent(RectComponent.class).render();
        ball.getComponent(CircleComponent.class).render();

        int speed = 10;
        ball.getComponent(CircleComponent.class).position.add((speed /2f-2)*sideDirection, speed *ballDirection);

        paddle.getComponent(BoxCollider.class).updatePosition(paddle.getComponent(RectComponent.class).position);
        ball.getComponent(CircleCollider.class).updatePosition(ball.getComponent(CircleComponent.class).position);

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            paddle.getComponent(RectComponent.class).position.add(new Vector2(-15, 0));
        } else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            paddle.getComponent(RectComponent.class).position.add(new Vector2(15, 0));
        }

        if(mouseModeCheckbox.isChecked()) {
            paddle.getComponent(RectComponent.class).position.x = Gdx.input.getX()-(Gdx.graphics.getWidth()/2f-30)/4f;
        }

        if(paddle.getComponent(BoxCollider.class).checkWith(ball.getComponent(CircleCollider.class)) && ball.getComponent(CircleComponent.class).position.y > Gdx.graphics.getHeight()/2f-235) {
            ball.getComponent(CircleComponent.class).position.y += 10;

            ballDirection = 1;

            if(ball.getComponent(CircleComponent.class).position.x <= paddle.getComponent(RectComponent.class).position.x) {
                sideDirection = -1;
            } else {
                sideDirection = 1;
            }

            if(controller != null) {
                if(controller.canVibrate()) {
                    controller.startVibration(100, 0.5f);
                }
            }
        }

        if(ball.getComponent(CircleComponent.class).position.y >= Gdx.graphics.getHeight()) {
            ballDirection = -1;

            sideDirection = (byte)-sideDirection;
        }

        if(ball.getComponent(CircleComponent.class).position.y <= -Gdx.graphics.getHeight()) {
            ball.getComponent(CircleComponent.class).position.set(new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f));
        }

        for(int i=0; i<bricks.size(); i++) {
            bricks.get(i).render();

            if(bricks.get(i).getComponent(BoxCollider.class).checkWith(ball.getComponent(CircleCollider.class))) {
                bricks.remove(bricks.get(i));

                ballDirection = -1;
                sideDirection = (byte)-sideDirection;
                score++;
                scoreLabel.setText(String.format("Score: %d", score));
            }
        }

        if(controller != null) {
            if(controller.getButton(controller.getMapping().buttonDpadLeft)) {
                paddle.getComponent(RectComponent.class).position.add(new Vector2(-15, 0));
            } else if(controller.getButton(controller.getMapping().buttonDpadRight)) {
                paddle.getComponent(RectComponent.class).position.add(new Vector2(15, 0));
            }
        }

        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void dispose() {
        VisUI.dispose();
    }
}
