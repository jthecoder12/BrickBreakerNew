package brickbreaker.main.scenes;

import brickbreaker.main.objects.Ball;
import brickbreaker.main.objects.Paddle;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import imgui.type.ImBoolean;

public abstract class GameScene extends Scene {
    protected Paddle paddle;
    public Ball ball;
    public byte ballDirection = -1;
    public byte sideDirection = -1;
    public int score;
    protected final ImBoolean mouseMode = new ImBoolean();
    protected final ImBoolean challengeMode = new ImBoolean();
    protected final ImBoolean settingsVisible = new ImBoolean();
    protected boolean positionAndSizeSet = false;
    protected boolean challengeModePressed = false;
    public Label scoreLabel;
    public Controller controller;
    protected BitmapFont font;
    protected BitmapFont largeFont;
    public Label winLabel;
    public Sound sound;

    @Override
    protected void extraDisposal() {
        sound.dispose();
        font.dispose();
        largeFont.dispose();
    }
}
