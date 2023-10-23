import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client1 {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1232;
    private static JTextArea textArea;
    private static JTextField inputField;
    private static PrintWriter out;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Client");
            textArea = new JTextArea(20, 50);
            textArea.setEditable(false);
            frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
            inputField = new JTextField();
            frame.add(inputField, BorderLayout.SOUTH);
            inputField.addActionListener(e -> {
                String message = inputField.getText();
                sendMessage(message);
                inputField.setText("");
            });
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

        connectToServer();
    }

    private static void connectToServer() {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                final String message = serverMessage;
                SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(String message) {
        out.println(message);
    }
}
