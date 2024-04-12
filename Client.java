import java.io.*;
import java.net.Socket;

public class Client implements ClientInterface, Runnable {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private final String serverAddress = "localhost";
    ClientGUI clientGUI = new ClientGUI(this);

    public static void main(String[] args) {
        Client client = new Client();
        Thread clientThread = new Thread(client);
        clientThread.start();
    }

    @Override
    public void run() {
        if (connectToServer()) {
            //connection successful
            clientGUI.welcomePage();
            clientGUI.loginPage();
        } else {
            clientGUI.showError("Connection to server failed.");
        }
    }

    @Override
    public boolean connectToServer() {
        try {
            socket = new Socket(serverAddress, Server.PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void sendToServer(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println("Client exception " + e.getMessage());
        }
    }

    //TODO Client Functions
}