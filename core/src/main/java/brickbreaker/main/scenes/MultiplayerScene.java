package brickbreaker.main.scenes;

import brickbreaker.main.UI.ImGuiUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.type.ImInt;
import imgui.type.ImString;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Objects;


public final class MultiplayerScene extends Scene {
    private final ImString ipAddress = new ImString();
    private final ImInt port = new ImInt();

    private boolean positionAndSizeSet = false;
    private boolean canConnect = true;
    private String messageString;
    private PrintWriter out;
    @SuppressWarnings("FieldCanBeLocal")
    private BufferedReader in;
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

        if(ImGui.button("Connect") && canConnect) {
            System.out.printf("Connecting to %s:%d%n", ipAddress.get(), port.get());

            try {
                socket = Gdx.net.newClientSocket(Net.Protocol.TCP, ipAddress.get(), port.get(), new SocketHints());

                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("hello");
                out.println("world");
                out.println("P1X45");

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if(Objects.equals(in.readLine(), "cli_wait")) {
                    messageString = "cli_wait";

                    canConnect = false;
                }
            } catch(Exception e) {
                messageString = e.getCause().toString();
                System.err.println(messageString);
            }
        }

        if(ImGui.button("Hello") && out != null) out.println("hello world");

        if(Objects.equals(messageString, "java.net.ConnectException: Connection refused")) ImGui.text("Connection refused");
        if(Objects.equals(messageString, "cli_wait")) ImGui.text("Waiting for another player");

        ImGui.end();
        ImGuiUI.render();
    }

    @Override
    protected void extraDisposal() {
        if(out != null) out.close();
        if(socket != null) socket.dispose();
    }
}
