package brickbreaker.main.scenes;

public final class SceneManager {
    private static Scene currentScene;

    private SceneManager() {

    }

    public static void setCurrentScene(Scene scene) {
        currentScene = scene;
    }

    public static void initScene() {
        currentScene.init();
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
