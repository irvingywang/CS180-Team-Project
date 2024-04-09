import java.io.*;
import java.net.Socket;

public class Client implements ClientInterface, Runnable {
    public static void main(String[] args) {
        Client client = new Client();
        Thread clientThread = new Thread(client);
        clientThread.start();
    }

    @Override
    public void run() {
        try (Socket socket = new Socket("localhost", Server.PORT)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //TODO Functionality
        } catch (IOException e) {
            System.out.println("Client exception " + e.getMessage());
        }
    }

    @Override
    public void connectToServer() {
        //TODO Functionality
    }

    //TODO Client Functions
}