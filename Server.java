
import java.io.*;
import java.net.*;

public class Server {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName("127.0.0.1"));
        System.out.println("Server started on port " + PORT);

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected.");

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String message;
        while ((message = in.readLine()) != null) {
            if (Filter.isFlagged(message)) {
                out.println("[WARNING] Message blocked: Filtered message");
            } else {
                System.out.println("Client: " + message);
                out.println("Server received: " + message);
            }
        }
    }
}
