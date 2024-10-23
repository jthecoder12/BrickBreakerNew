package brickbreaker.main.scenes;

import org.jetbrains.annotations.NotNull;

public final class SceneManager {
    private static Scene currentScene;

    private SceneManager() {

    }

    public static void setCurrentScene(@NotNull Scene scene) {
        currentScene = scene;
        scene.init();
    }

    public static void renderScene() {
        currentScene.render();
    }

    public static void disposeScene() {
        currentScene.dispose();
    }

    public static void resizeScene(int width, int height, boolean centerCamera) {
        currentScene.resizeScene(width, height, centerCamera);
    }
}
