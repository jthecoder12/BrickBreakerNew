package brickbreaker.main;

import brickbreaker.main.UI.ImGuiUI;
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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.type.ImBoolean;

import java.util.ArrayList;
import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public final class Main extends ApplicationAdapter {
    private Paddle paddle;
    private Ball ball;
    private byte ballDirection = -1;
    private byte sideDirection = -1;
    private int score;
    private final ImBoolean mouseMode = new ImBoolean();
    private final ImBoolean settingsVisible = new ImBoolean();
    private boolean positionAndSizeSet = false;
    private Label scoreLabel;
    private Stage stage;
    private Controller controller;
    private BitmapFont font;
    private Sound sound;
    private final Random random = new Random();
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

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Varela_Round/VarelaRound-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;

        font = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        scoreLabel = new Label("Score: 0", labelStyle);
        scoreLabel.setPosition(0, Gdx.graphics.getHeight()-50);
        scoreLabel.setColor(1, 0, 0, 1);

        stage.addActor(scoreLabel);

        ImGuiUI.initImGui();

        final ImGuiStyle style = ImGui.getStyle();
        final float borderRadius = 8;
        style.setTabRounding(borderRadius);
        style.setFrameRounding(borderRadius);
        style.setGrabRounding(borderRadius);
        style.setWindowRounding(borderRadius);
        style.setPopupRounding(borderRadius);

        ImGui.styleColorsDark(style);

        sound = Gdx.audio.newSound(Gdx.files.internal("ping_pong_8bit_beeep.ogg"));
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

        // Speed
        int speed = 5;
        ball.getComponent(CircleComponent.class).position.add(speed*1.5f*sideDirection, speed*ballDirection);

        paddle.getComponent(BoxCollider.class).updatePosition(paddle.getComponent(RectComponent.class).position);
        ball.getComponent(CircleCollider.class).updatePosition(ball.getComponent(CircleComponent.class).position);

        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) settingsVisible.set(!settingsVisible.get());

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            paddle.getComponent(RectComponent.class).position.add(new Vector2(-15, 0));
        } else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            paddle.getComponent(RectComponent.class).position.add(new Vector2(15, 0));
        }

        if(mouseMode.get()) {
            paddle.getComponent(RectComponent.class).position.x = Gdx.input.getX()-(Gdx.graphics.getWidth()/2f-30)/4f;
        }

        if(paddle.getComponent(BoxCollider.class).checkWith(ball.getComponent(CircleCollider.class)) && ball.getComponent(CircleComponent.class).position.y > Gdx.graphics.getHeight()/2f-235) {
            ball.getComponent(CircleComponent.class).position.y += 10;

            ballDirection = 1;

            if(ball.getComponent(CircleComponent.class).position.x < paddle.getComponent(RectComponent.class).position.x+(Gdx.graphics.getWidth()/2f-30)/4f) {
                sideDirection = -1;
            } else if(ball.getComponent(CircleComponent.class).position.x > paddle.getComponent(RectComponent.class).position.x+(Gdx.graphics.getWidth()/2f-30)/4f) {
                sideDirection = 1;
            } else {
                if(random.nextInt(2) == 0) {
                    sideDirection = 1;
                } else {
                    sideDirection = -1;
                }
            }

            if(controller != null) {
                if(controller.canVibrate()) {
                    controller.startVibration(100, 0.5f);
                }
            }

            sound.play(1);
        }

        // Top
        if(ball.getComponent(CircleComponent.class).position.y >= Gdx.graphics.getHeight()-10) {
            ballDirection = -1;

            sound.play(1);
        }

        // Reset
        if(ball.getComponent(CircleComponent.class).position.y <= 0) {
            ballDirection = -1;
            sideDirection = -1;

            new Thread(() -> {
                long time = System.currentTimeMillis();

                while(System.currentTimeMillis() < time+500) {
                    Gdx.app.postRunnable(() -> ball.getComponent(CircleComponent.class).position.set(new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f)));
                }
            }).start();
        }

        // Left
        if(ball.getComponent(CircleComponent.class).position.x <= 10) {
            sideDirection = 1;

            sound.play(1);
        }
        // Right
        else if(ball.getComponent(CircleComponent.class).position.x > Gdx.graphics.getWidth()-13) {
            sideDirection = -1;

            sound.play(1);
        }

        for(int i=0; i<bricks.size(); i++) {
            bricks.get(i).render();

            if(bricks.get(i).getComponent(BoxCollider.class).checkWith(ball.getComponent(CircleCollider.class))) {
                bricks.remove(bricks.get(i));

                ballDirection = -1;
                sideDirection = (byte)-sideDirection;
                score++;
                scoreLabel.setText(String.format("Score: %d", score));

                sound.play(1);
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

        if(settingsVisible.get()) {
            ImGuiUI.loop();
            ImGui.begin("Settings", settingsVisible);
            if(!positionAndSizeSet) {
                ImGui.setWindowPos(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/4f);
                ImGui.setWindowSize(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/4f);

                positionAndSizeSet = true;
            }
            ImGui.checkbox("Mouse Mode", mouseMode);
            ImGui.end();
            ImGuiUI.render();
        }

        if(bricks.isEmpty()) System.out.println("You win");
    }

    @Override
    public void dispose() {
        font.dispose();
        stage.dispose();
        ImGuiUI.dispose();
    }
}
