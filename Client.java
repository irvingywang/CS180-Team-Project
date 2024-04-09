import java.io.*;
import java.net.Socket;

public class Client{
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", Server.PORT)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //TODO Functionality
        } catch (IOException e) {
            System.out.println("Client exception " + e.getMessage());
        }
    }

    //TODO Client Functions
}