package brickbreaker.main.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import imgui.ImFontConfig;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;

public final class ImGuiUI {
    private static ImGuiImplGlfw imGuiGlfw;
    private static ImGuiImplGl3 imGuiGl3;
    private static InputProcessor tmpProcessor;

    private ImGuiUI() {

    }

    public static void initImGui() {
        imGuiGlfw = new ImGuiImplGlfw();
        imGuiGl3 = new ImGuiImplGl3();

        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);
        final ImFontConfig fontConfig = new ImFontConfig();
        if(!System.getProperty("os.name").contains("Mac")) {
            io.getFonts().setFreeTypeRenderer(true);
            System.out.println("Using FreeType renderer");
        }
        io.getFonts().addFontFromMemoryTTF(Gdx.files.internal("Varela_Round/VarelaRound-Regular.ttf").readBytes(), 20, fontConfig);
        io.getFonts().build();
        fontConfig.destroy();
        imGuiGlfw.init(glfwGetCurrentContext(), true);
        imGuiGl3.init("#version 150");
    }

    public static void loop() {
        if(tmpProcessor != null) {
            Gdx.input.setInputProcessor(tmpProcessor);
            tmpProcessor = null;
        }

        imGuiGl3.newFrame();
        imGuiGlfw.newFrame();
        ImGui.newFrame();
    }

    public static void render() {
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if(ImGui.getIO().getWantCaptureKeyboard() || ImGui.getIO().getWantCaptureMouse()) {
            tmpProcessor = Gdx.input.getInputProcessor();
            Gdx.input.setInputProcessor(null);
        }
    }

    public static void dispose() {
        imGuiGl3.shutdown();
        imGuiGl3 = null;
        imGuiGlfw.shutdown();
        imGuiGlfw = null;
        ImGui.destroyContext();
    }
}
