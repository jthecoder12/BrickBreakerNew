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

    private PrintWriter output1;
    private BufferedReader input1;
    private PrintWriter output2;
    private BufferedReader input2;

    private ServerSocket serverSocket;
    private Socket player1;
    private Socket player2;

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
                serverOutputString.append("Server started on port ").append((int)portSpinner.getValue()).append(". Waiting for client.\n");
                serverOutput.setText(serverOutputString.toString());

                player1 = serverSocket.accept();

                output1 = new PrintWriter(player1.getOutputStream(), true);
                output1.println("cli_wait");

                serverOutputString.append("Client connected, waiting for another client.\n");
                serverOutput.setText(serverOutputString.toString());

                player2 = serverSocket.accept();

                serverOutputString.append("Second client connected.\n");
                serverOutput.setText(serverOutputString.toString());

                while(!stopping) {
                    if(!Objects.equals(output1, new PrintWriter(player1.getOutputStream(), true))) output1 = new PrintWriter(player1.getOutputStream(), true);
                    if(player1.getInputStream().available() != 0) {
                        input1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));

                        serverOutputString.append("Player 1: ").append(input1.readLine()).append("\n");
                        serverOutput.setText(serverOutputString.toString());
                    }

                    if(!Objects.equals(output2, new PrintWriter(player2.getOutputStream(), true))) output2 = new PrintWriter(player2.getOutputStream(), true);
                    if(player2.getInputStream().available() != 0) {
                        input2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));

                        serverOutputString.append("Player 2: ").append(input2.readLine()).append("\n");
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
                if(player1 != null) player1.close();
                if(player2 != null) player2.close();
                serverSocket.close();
                if(output1 != null) output1.close();
                if(input1 != null) input1.close();
                if(output2 != null) output2.close();
                if(input2 != null) input2.close();
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
