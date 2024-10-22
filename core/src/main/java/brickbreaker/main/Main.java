package brickbreaker.main;

import brickbreaker.main.UI.ImGuiUI;
import brickbreaker.main.objects.Ball;
import brickbreaker.main.objects.Paddle;
import brickbreaker.main.objects.bricks.BrickManager;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.type.ImBoolean;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public final class Main extends ApplicationAdapter {
    private Paddle paddle;
    public Ball ball;
    public byte ballDirection = -1;
    public byte sideDirection = -1;
    public int score;
    private final ImBoolean mouseMode = new ImBoolean();
    private final ImBoolean settingsVisible = new ImBoolean();
    private boolean positionAndSizeSet = false;
    public Label scoreLabel;
    private Stage stage;
    public Controller controller;
    private BitmapFont font;
    public Sound sound;

    @Override
    public void create() {
        PooledEngine engine = new PooledEngine();

        paddle = new Paddle(engine, this);
        ball = new Ball(engine, this);

        BrickManager.initializeBricks(engine, this);

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

        paddle.render(mouseMode, ball);
        ball.render(5);

        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) settingsVisible.set(!settingsVisible.get());

        BrickManager.renderBricks();

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
    }

    @Override
    public void dispose() {
        font.dispose();
        stage.dispose();
        ImGuiUI.dispose();
    }
}
