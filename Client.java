import java.io.*;
import java.net.Socket;

public class Client implements ClientInterface, Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final ClientGUI clientGUI = new ClientGUI(this);
    private User user;
    public static final Identifier IDENTIFIER = Identifier.CLIENT;

    public static void main(String[] args) {
        Client client = new Client();
        Thread clientThread = new Thread(client);
        clientThread.start();
    }

    @Override
    public void run() {
        if (connectToServer()) {
            clientGUI.welcomePage();
            clientGUI.loginPage();
        } else {
            clientGUI.showError("Connection to server failed.");
        }
    }

    /**
     * Sets the User object for this client.
     *
     * @param user - the user to be set
     */
    @Override
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Connects the client to the server.
     *
     * It also initializes the ObjectOutputStream and ObjectInputStream for server communication.
     *
     * @return true if the connection is successful, false if an exception is thrown
     */
    @Override
    public boolean connectToServer() {
        try {
            socket = new Socket(Server.SERVER_ADDRESS, Server.PORT);
            out = new ObjectOutputStream(socket.getOutputStream()); //Output must be initialized first
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Sends a NetworkMessage to the server.
     *
     * This method attempts to write the NetworkMessage object to the ObjectOutputStream and flushes the stream.
     * Logs an error if an exception is thrown.
     *
     * @param networkMessage - the NetworkMessage to be sent
     * @return true if the message is sent successfully, false if an exception is thrown
     */
    @Override
    public boolean sendToServer(NetworkMessage networkMessage) {
        try {
            out.writeObject(networkMessage);
            out.flush();
            return true;
        } catch (Exception e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, e.getMessage());
            return false;
        }
    }

    /**
     * Reads a NetworkMessage from the server.
     *
     * @return the NetworkMessage read from the server, or null if an exception is thrown
     */
    @Override
    public NetworkMessage readMessage() {
        try {
            return (NetworkMessage) in.readObject();
        } catch (Exception e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, e.getMessage());
            return null;
        }
    }

    /**
     * Listens for a single message from the server.
     *
     * This method reads a NetworkMessage from the server and immediately returns it.
     * It does not close the socket after reading the message to allow continuous communication.
     *
     * @return the NetworkMessage read from the server, or null if an exception occurs
     */
    @Override
    public NetworkMessage listenToServer() {
        try {
            return readMessage();  // Directly return the message read from the server
        } catch (Exception e) {
            clientGUI.showError("Error listening to server: " + e.getMessage());
            Database.writeLog(LogType.ERROR, IDENTIFIER, e.getMessage());
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ioException) {
                clientGUI.showError("Error closing socket: " + ioException.getMessage());
            }
            return null;
        }
    }

    //TODO simplify network communication with requests
//    public NetworkMessage sendRequestToServer(NetworkMessage request) {
//        try {
//            out.writeObject(request);
//            out.flush();
//            // Directly read the response after sending the request
//            return (NetworkMessage) in.readObject();
//        } catch (Exception e) {
//            clientGUI.showError("Communication error: " + e.getMessage());
//            return null;
//        }
//    }

    //TODO Client Functions
}