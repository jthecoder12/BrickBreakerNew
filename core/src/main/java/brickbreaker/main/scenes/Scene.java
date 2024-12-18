package brickbreaker.main.scenes;

import brickbreaker.main.UI.ImGuiUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.LinkedList;

public abstract class Scene {
    protected Stage stage;
    private boolean alreadyInitialized = false;
    private final static LinkedList<Stage> stages = new LinkedList<>();

    protected abstract void extraInit();
    protected abstract void extraRendering();
    protected void imGuiRendering() {

    }
    protected void extraDisposal() {

    }

    void init() {
        if(!alreadyInitialized) {
            stage = new Stage(new ScreenViewport());
            stages.add(stage);
            extraInit();
            if(!SceneManager.imGuiInitialized) {
                ImGuiUI.initImGui();
                SceneManager.imGuiInitialized = true;
            }
            Gdx.input.setInputProcessor(stage);
            alreadyInitialized = true;
        }
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

        for(Stage stage1 : stages) stage1.dispose();

        ImGuiUI.dispose();
    }

    void resizeScene(int width, int height, boolean centerCamera) {
        stage.getViewport().update(width, height, centerCamera);
    }
}
