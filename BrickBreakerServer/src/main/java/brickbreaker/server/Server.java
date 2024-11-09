package brickbreaker.server;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.OneDarkTheme;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public final class Server implements Runnable {
    private boolean stopping = false;

    private PrintWriter output;
    private BufferedReader input;

    private ServerSocket serverSocket;
    private Socket clientSocket;

    @Override
    public void run() {
        LafManager.install(new OneDarkTheme());

        StringBuilder serverOutputString = new StringBuilder();

        JFrame frame = new JFrame("Brick Breaker Server");
        JTextPane serverOutput = new JTextPane();
        JSpinner portSpinner = new JSpinner();
        JButton startButton = new JButton("Start server");
        JButton stopButton = new JButton("Stop server and exit");
        JButton forceCloseButton = new JButton("Force close");

        serverOutput.setEditable(false);

        startButton.addActionListener(e -> new Thread(() -> {
            try {
                serverSocket = new ServerSocket((int)portSpinner.getValue());
                serverOutputString.append("Server started on port ").append((int)portSpinner.getValue()).append(". Waiting for client.");
                serverOutput.setText(serverOutputString.toString());

                clientSocket = serverSocket.accept();

                output = new PrintWriter(clientSocket.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while(!stopping) {
                    if(!Objects.equals(output, new PrintWriter(clientSocket.getOutputStream(), true))) output = new PrintWriter(clientSocket.getOutputStream(), true);
                    if(clientSocket.getInputStream().available() != 0) {
                        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        serverOutputString.append(input.readLine()).append("\n");
                        serverOutput.setText(serverOutputString.toString());
                    }
                }
            } catch (IOException ex) {
                if(!stopping) throw new RuntimeException(ex);
            }
        }).start());

        stopButton.addActionListener(e -> {
            stopping = true;

            try {
                if(clientSocket != null) clientSocket.close();
                serverSocket.close();
                if(output != null) output.close();
                if(input != null) input.close();
                frame.dispose();
                System.exit(0);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        forceCloseButton.addActionListener(e -> System.exit(130));

        frame.add(serverOutput);
        frame.add(portSpinner);
        frame.add(startButton);
        frame.add(stopButton);
        frame.add(forceCloseButton);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String @NotNull [] args) {
        new Server().run();
    }
}
