package brickbreaker.main.scenes;

import brickbreaker.main.UI.ImGuiUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class Scene {
    protected Stage stage;

    protected abstract void extraInit();
    protected abstract void extraRendering();
    protected abstract void imGuiRendering();
    protected abstract void extraDisposal();

    void init() {
        stage = new Stage(new ScreenViewport());
        extraInit();
    }

    void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        extraRendering();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        imGuiRendering();
    }

    void dispose() {
        extraDisposal();
        stage.dispose();
        ImGuiUI.dispose();
    }

    void resizeScene(int width, int height, boolean centerCamera) {
        stage.getViewport().update(width, height, centerCamera);
    }
}
