package brickbreaker.main;

import brickbreaker.main.scenes.SceneManager;
import brickbreaker.main.scenes.TitleScene;
import com.badlogic.gdx.ApplicationAdapter;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public final class Main extends ApplicationAdapter {

    @Override
    public void create() {
        SceneManager.setCurrentScene(new TitleScene());
    }

    @Override
    public void resize(int width, int height) {
        SceneManager.resizeScene(width, height, true);
    }

    @Override
    public void render() {
        SceneManager.renderScene();
    }

    @Override
    public void dispose() {
        SceneManager.disposeScene();
    }
}
