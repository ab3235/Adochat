
import java.io.*;
import java.net.*;

public class Server {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT, 1, InetAddress.getByName("127.0.0.1"));
        System.out.println("Server started on port " + PORT);

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected.");

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // track the warns
        Warnings warnings = new Warnings();

        //tracks users
        String username = in.readLine();
        System.out.println("User joined: " + username);

        String message;
        while ((message = in.readLine()) != null) {
            // filter warn
            if (Filter.isFlagged(message)) {
                if (warnings.addWarning(out)) {
                    System.out.println("Client has been kicked for too many warns");
                    break;
                }
            } else {
                System.out.println("Client: " + message);
                out.println("Server received: " + message);
            }
        }
    }
}
