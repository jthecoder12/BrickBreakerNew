package brickbreaker.main.scenes;

import brickbreaker.main.UI.ImGuiUI;
import com.badlogic.gdx.Gdx;
import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.type.ImInt;
import imgui.type.ImString;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public final class MultiplayerScene extends Scene {
    private final ImString ipAddress = new ImString();
    private final ImInt port = new ImInt();
    private boolean positionAndSizeSet = false;
    private Socket socket;

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

            try {
                socket = new Socket(ipAddress.get(), port.get());

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("hello");
                out.println("world");
                out.println("P1X45");

                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        ImGui.end();
        ImGuiUI.render();
    }

    @Override
    protected void extraDisposal() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
