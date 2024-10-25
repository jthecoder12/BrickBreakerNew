package brickbreaker.main.scenes;

import brickbreaker.main.UI.ImGuiUI;
import com.badlogic.gdx.Gdx;
import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiKey;
import imgui.type.ImInt;
import imgui.type.ImString;

public final class MultiplayerScene extends Scene {
    private final ImString ipAddress = new ImString();
    private final ImInt port = new ImInt();
    private boolean positionAndSizeSet = false;

    @Override
    protected void extraInit() {
        final ImGuiStyle style = ImGui.getStyle();
        final float borderRadius = 8;
        style.setTabRounding(borderRadius);
        style.setFrameRounding(borderRadius);
        style.setGrabRounding(borderRadius);
        style.setWindowRounding(borderRadius);
        style.setPopupRounding(borderRadius);

        ImGui.styleColorsDark(style);
    }

    @Override
    protected void extraRendering() {

    }

    @Override
    protected void imGuiRendering() {
        ImGuiUI.loop();
        ImGui.begin("Multiplayer setup");

        if(!positionAndSizeSet) {
            ImGui.setWindowPos(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/4f);
            ImGui.setWindowSize(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/4f);

            positionAndSizeSet = true;
        }

        ImGui.inputText("IP", ipAddress);
        ImGui.inputInt("Port", port);

        if(ImGui.button("Connect")) {
            System.out.printf("Connecting to %s:%d", ipAddress.get(), port.get());
        }

        ImGui.end();
        ImGuiUI.render();
    }
}
