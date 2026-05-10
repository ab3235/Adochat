
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    private static final int PORT = 8080;
    static ArrayList<PrintWriter> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName("127.0.0.1"));
        System.out.println("Server started on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }
    // runs client on threads 

    static void handleClient(Socket socket) {
        String username = "unknown";
        PrintWriter out = null;

        // read write for client and adds to a list
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            clients.add(out);

            // track the warns
            Warnings warnings = new Warnings();

            // Input of first message is the username
            username = in.readLine();
            System.out.println("User joined: " + username);
            broadcast(username + " joined the chat", out);

            String message;
            while ((message = in.readLine()) != null) {
                // filter warn
                if (Filter.isFlagged(message)) {
                    if (warnings.addWarning(out)) {
                        System.out.println(username + " was kicked");
                        broadcast(username + " was kicked", out);
                        break;
                    }

                } else {
                    broadcast(username + ": " + message, out);
                    out.println(username + " (you): " + message);
                }
            }
        } // catches unexpentencys
        catch (IOException e) {
            System.out.println(username + " disconnected");
        } // Catches disconnection and removes clients
        finally {
            if (out != null) {
                clients.remove(out);
            }
            broadcast(username + " left the chat", null);
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }
    // sends to everyone except sender

    static synchronized void broadcast(String msg, PrintWriter sender) {
        for (PrintWriter client : clients) {
            if (client != sender) {
                client.println(msg);
            }
        }
    }
}
