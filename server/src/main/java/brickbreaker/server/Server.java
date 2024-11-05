package brickbreaker.server;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public final class Server implements Runnable {
    private final int port;

    private float p1x, p1y, p2x, p2y;

    private Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();

            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String line;
            while((line = input.readLine()) != null) {
                System.out.println(line);

                if(line.startsWith("P1X")) System.out.println("Got a player 1 x coordinate");
            }

            input.close();
            output.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String @NotNull [] args) {
        System.out.printf("Using port: %d%n", Integer.parseInt(args[0]));

        new Server(Integer.parseInt(args[0])).run();
    }
}
