package brickbreaker.main.scenes;

import brickbreaker.main.UI.ImGuiUI;
import brickbreaker.main.objects.Ball;
import brickbreaker.main.objects.Paddle;
import brickbreaker.main.objects.bricks.BrickManager;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import imgui.ImGui;
import imgui.ImGuiStyle;

public final class SingleplayerScene extends GameScene {
    @Override
    protected void extraInit() {
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
        parameter.size = 75;
        largeFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        Label.LabelStyle largeLabelStyle = new Label.LabelStyle();
        largeLabelStyle.font = largeFont;

        scoreLabel = new Label("Score: 0", labelStyle);
        scoreLabel.setPosition(0, Gdx.graphics.getHeight()-50);
        scoreLabel.setColor(1, 0, 0, 1);

        winLabel = new Label("You win", largeLabelStyle);
        winLabel.setPosition(Gdx.graphics.getWidth()/2.5f, Gdx.graphics.getHeight()/2f);
        winLabel.setVisible(false);

        stage.addActor(scoreLabel);
        stage.addActor(winLabel);

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
    protected void extraRendering() {
        ScreenUtils.clear(new Color(100/255f, 100/255f, 100/255f, 1));

        paddle.render(mouseMode, ball);
        ball.render(5);

        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) settingsVisible.set(!settingsVisible.get());

        BrickManager.renderBricks();

        if(controller != null) {
            if(challengeMode.get() && controller.canVibrate()) controller.startVibration(100, 1);
            if(controller.getButton(controller.getMapping().buttonY) && !challengeModePressed) {
                challengeMode.set(!challengeMode.get());

                new Thread(() -> {
                    while(controller.getButton(controller.getMapping().buttonY)) {
                        challengeModePressed = true;
                    }

                    challengeModePressed = false;
                }).start();
            }
        }
    }

    @Override
    protected void imGuiRendering() {
        if(settingsVisible.get()) {
            ImGuiUI.loop();
            ImGui.begin("Settings", settingsVisible);
            if(!positionAndSizeSet) {
                ImGui.setWindowPos(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/4f);
                ImGui.setWindowSize(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/4f);

                positionAndSizeSet = true;
            }
            ImGui.checkbox("Mouse Mode", mouseMode);
            ImGui.checkbox("Challenge Mode for Controllers", challengeMode);
            ImGui.end();
            ImGuiUI.render();
        }
    }
}
