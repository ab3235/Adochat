
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class GUI extends JFrame {

    private JTextArea chatArea = new JTextArea();
    private JTextField inputField = new JTextField();
    private JButton sendButton = new JButton("Send");
    private PrintWriter out;
    private String username;

    public GUI() {
        setTitle("AdoChat");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // layout 
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(inputField, BorderLayout.CENTER);
        bottom.add(sendButton, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        //listners 
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        setVisible(true);

        // ask for username before connecting
        username = JOptionPane.showInputDialog(this, "Enter your username:");
        if (username == null || username.isEmpty()) {
            System.exit(0);
        }
        chatArea.append("Logged in as: " + username + "\n");
        connect();
    }

    private void connect() {
        try {
            //connection to server
            Socket socket = new Socket("127.0.0.1", 8080);
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(username);
            chatArea.append("Connected to server.\n");

            new Thread(() -> {
                try {
                    // listens 
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        final String line = msg;
                        SwingUtilities.invokeLater(() -> chatArea.append(line + "\n"));
                    }
                } catch (IOException e) {
                    SwingUtilities.invokeLater(() -> chatArea.append("Disconnected.\n"));
                }
            }).start();

        } catch (IOException e) {
            chatArea.append("Could not connect: " + e.getMessage() + "\n");
        }
    }

    private void sendMessage() {
        String text = inputField.getText();
        if (!text.isEmpty() && out != null) {
            out.println(text);
            inputField.setText("");
        }
    }

    //display
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
