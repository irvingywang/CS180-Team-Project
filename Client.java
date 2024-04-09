import javax.swing.*;
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

            JOptionPane.showMessageDialog(null, "Welcome to App!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Server is not running.");
            System.out.println("Client exception " + e.getMessage());
        }
    }

    @Override
    public void connectToServer() {
        //TODO Functionality
    }

    //TODO Client Functions
}