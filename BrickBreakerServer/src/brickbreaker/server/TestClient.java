package brickbreaker.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public final class TestClient implements Runnable {
    @Override
    public void run() {
        try {
            Socket socket = new Socket("127.0.0.1", 8000);
            PrintWriter clientOut = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            clientOut.println("Sent message");
            System.out.println(clientInput.readLine());

            clientInput.close();
            clientOut.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new TestClient().run();
    }
}
