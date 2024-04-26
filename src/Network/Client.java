package Network;

import Database.LogType;
import Database.Database;
import GUI.Window;
import Objects.Chat;
import Objects.User;
import Pages.WelcomePage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Project05 - Client
 * <p>
 * This is the client for our messaging program. It allows users to
 * make any requests from the program.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 *
 * @version April 14, 2024
 */
public class Client implements ClientInterface, Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    //private final ClientGUI clientGUI = new ClientGUI(this);
    private User user;
    public static final Identifier IDENTIFIER = Identifier.CLIENT;
    public static Thread clientThread;

    public static void start() {
        Client client = new Client();
        clientThread = new Thread(client);
        clientThread.start();
    }

    @Override
    public void run() {
        if (connectToServer()) {
            Window.getInstance().switchPage(new WelcomePage(this));
        } else {
            //clientGUI.showError("Connection to server failed.");
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Failed to connect to server");
        }
    }

    /**
     * Retrieves the username of the User object associated with this client.
     *
     * @return the username of the User object associated with this client
     */
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Retrieves the display name of the User object associated with this client.
     *
     * @return the display name of the User object associated with this client
     */
    public String getDisplayName() {
        return user.getDisplayName();
    }

    /**
     * Retrieves the password of the User object associated with this client.
     *
     * @return the password of the User object associated with this client
     */
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Checks if the User object associated with this client has a public profile.
     *
     * @return true if the User object associated with this client has a public profile, false otherwise
     */
    public boolean isPublicProfile() {
        return user.isPublicProfile();
    }

    /**
     * Retrieves the chats associated with the User object of this client.
     * This method sends a GET_CHATS command to the server and waits for a response.
     * The response is expected to be an array of Chat objects.
     *
     * @return an array of Chat objects associated with the User object of this client
     */
    public Chat[] getChats() {
        sendToServer(new NetworkMessage(ServerCommand.GET_CHATS, IDENTIFIER, user));
        NetworkMessage response = listenToServer();
        return (Chat[]) response.getObject();
    }

    /**
     * Retrieves the status of the User object associated with this client.
     *
     * @return the status of the User object associated with this client
     */
    public String getStatus() {
        return user.getStatus();
    }

    /**
     * Retrieves the User object associated with this client.
     *
     * @return the User object associated with this client
     */
    public User getUser() {
        return user;
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
     * <p>
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
     * Disconnects the client from the server.
     * Logs any errors that occur during disconnection.
     */
    public void disconnectFromServer() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, e.getMessage());
        }
    }

    /**
     * Sends a NetworkMessage to the server.
     * <p>
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
    public NetworkMessage readNetworkMessage() {
        try {
            return (NetworkMessage) in.readObject();
        } catch (Exception e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, e.getMessage());
            return null;
        }
    }

    /**
     * Listens for a single message from the server.
     * <p>
     * This method reads a NetworkMessage from the server and immediately returns it.
     * It does not close the socket after reading the message to allow continuous communication.
     *
     * @return the NetworkMessage read from the server, or null if an exception occurs
     */
    @Override
    public NetworkMessage listenToServer() {
        try {
            return readNetworkMessage();  // Directly return the message read from the server
        } catch (Exception e) {
            //clientGUI.showError("Error listening to server: " + e.getMessage());
            Database.writeLog(LogType.ERROR, IDENTIFIER, e.getMessage());
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ioException) {
                //clientGUI.showError("Error closing socket: " + ioException.getMessage());
                Database.writeLog(LogType.ERROR, IDENTIFIER, "Failed to connect to server");
            }
            return null;
        }
    }
//    public synchronized boolean removeUser(String username) {
//        return sendToServer(new NetworkMessage(ServerCommand.REMOVE_USER, IDENTIFIER, username));
//    }
//    public synchronized boolean addUser(User user) {
//        return sendToServer(new NetworkMessage(ServerCommand.ADD_USER, IDENTIFIER, user));
//    }
    public synchronized boolean sendMessage(String message, String username) {
        return sendToServer(new NetworkMessage(ClientCommand.SEND_MESSAGE, IDENTIFIER, message));
    }
//    public synchronized boolean deleteMessage(Chat chat, String messageText) {
//        return sendToServer(new NetworkMessage(ServerCommand.DELETE_MESSAGE, IDENTIFIER, new Object[]{user, chat, messageText}));
//    }
    public synchronized boolean blockUser(User blockedUser) {
        return sendToServer(new NetworkMessage(ServerCommand.BLOCK_USER, IDENTIFIER, blockedUser));
    }
//    public synchronized boolean deleteAccount() {
//        return sendToServer(new NetworkMessage(ServerCommand.DELETE_ACCOUNT, IDENTIFIER, user));
//    }
//    public synchronized boolean changeName(String newName) {
//        return sendToServer(new NetworkMessage(ServerCommand.CHANGE_NAME, IDENTIFIER, new Object[]{user, newName}));
//    }
//    public synchronized boolean changePW(String newPW) {
//        return sendToServer(new NetworkMessage(ServerCommand.CHANGE_PASSWORD, IDENTIFIER, new Object[]{user, newPW}));
//    }
    public synchronized boolean createChat(String chatName, String[] members) {
        String chat = String.join(",", members);

        return sendToServer(new NetworkMessage(ServerCommand.CREATE_CHAT, IDENTIFIER, chatName + "," + chat));
    }
    public synchronized boolean searchUsers(String searchQuery) {
        return sendToServer(new NetworkMessage(ServerCommand.SEARCH_USER, IDENTIFIER, searchQuery));
    }
}