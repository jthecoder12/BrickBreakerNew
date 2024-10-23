package brickbreaker.main.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

public final class TitleScene extends Scene {
    private BitmapFont font;

    @Override
    protected void extraInit() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Varela_Round/VarelaRound-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;

        font = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        Label titleLabel = new Label("Brick Breaker", labelStyle);
        titleLabel.setPosition(Gdx.graphics.getWidth()/2.4f, Gdx.graphics.getHeight()-50);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;

        TextButton singlePlayerButton = new TextButton("Singleplayer", buttonStyle);
        singlePlayerButton.setPosition(Gdx.graphics.getWidth()/2.4f, Gdx.graphics.getHeight()/2f);

        stage.addActor(titleLabel);
        stage.addActor(singlePlayerButton);
    }

    @Override
    protected void extraRendering() {
        ScreenUtils.clear(new Color(100/255f, 100/255f, 100/255f, 1));
    }

    @Override
    protected void extraDisposal() {
        font.dispose();
    }
}
