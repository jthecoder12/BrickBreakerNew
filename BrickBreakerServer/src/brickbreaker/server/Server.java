package brickbreaker.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public final class Server implements Runnable {
    private final int port;

    private Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();

            PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            serverOutput.println("Sent message");
            System.out.printf("Got: %s%n", serverInput.readLine());

            System.out.print("Command: ");
            Scanner input = new Scanner(System.in);

            if(Objects.equals(input.nextLine(), "close")) {
                serverInput.close();
                serverOutput.close();
                socket.close();
                serverOutput.close();

                System.exit(0);
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            System.out.printf("Using port: %s%n", args[0]);
            new Server(Integer.parseInt(args[0])).run();
        } catch (Exception e) {
            throw new RuntimeException("Invalid arguments");
        }
    }
}
