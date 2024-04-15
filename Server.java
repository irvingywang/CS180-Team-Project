import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * Project05 -- Server
 *
 * This is the server for our messaging program. It processes
 * requests from the client.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 *
 * @version April 1, 2024
 *
 */
public class Server implements ServerInterface, Runnable {
    public static final int PORT = 1234;
    public static final String SERVER_ADDRESS = "localhost";
    private Database database = Database.getInstance();
    private ServerSocket serverSocket;
    ObjectInputStream in;
    ObjectOutputStream out;
    private static final Identifier IDENTIFIER = Identifier.SERVER;

    public static void main(String[] args) {
        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to client");
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Server socket" +
                    " failed to open: " + e.getMessage());
        }
    }

    @Override
    public boolean sendToClient(NetworkMessage message) {
        try {
            out.writeObject(message);
            out.flush();
            return true;
        } catch (IOException e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Failed" +
                    " to send to client: " + e.getMessage());
            return false;
        }
    }

    @Override
    public NetworkMessage readMessage() {
        try {
            return (NetworkMessage) in.readObject();
        } catch (Exception e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Failed" +
                    " to read message: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void handleClient(Socket clientSocket) {
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            while (true) {
                NetworkMessage message = readMessage();
                if (message != null) {
                    System.out.println(message);
                    switch ((ServerCommand) message.getCommand()) {
                        case LOGIN -> {
                            String[] loginInfo = ((String) message.getObject()).split(",");
                            if (login(loginInfo[0], loginInfo[1])) {
                                Database.writeLog(LogType.INFO, IDENTIFIER, "Login successful");
                                sendToClient(new NetworkMessage(ClientCommand.LOGIN_SUCCESS,
                                        IDENTIFIER, database.getUser(loginInfo[0])));
                            } else {
                                System.out.println("Login failed");
                                sendToClient(new NetworkMessage(ClientCommand.LOGIN_FAILURE,
                                        IDENTIFIER, null));
                            }
                        }
                        default -> {
                            Database.writeLog(LogType.ERROR, IDENTIFIER,
                                    "Invalid command received.");
                        }
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER,
                    "Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                Database.writeLog(LogType.ERROR, IDENTIFIER,
                        "Failed to close client socket: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean login(String username, String password) {
        User user = database.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            Database.writeLog(LogType.INFO, IDENTIFIER, String.format("User %s logged in.", username));
            return true;
        } else {
            Database.writeLog(LogType.INFO, IDENTIFIER,
                    String.format("User %s failed to log in.", username));
            return false;
        }
    }

    /**
     * Creates a new user and adds it to the database.
     * If the user already exists, the method will return false.
     *
     * @param username      - the username of the new user
     * @param password      - the password of the new user
     * @param displayName   - the display name of the new user
     * @param publicProfile - the public status of the new user
     */
    @Override
    public synchronized boolean createUser(String username,
                                           String password, String displayName, Boolean publicProfile) {
        if (database.getUser(username) != null) {
            //FIXME show this in the GUI
            Database.writeLog(LogType.INFO, IDENTIFIER,
                    String.format("User %s already exists.", username));
            return false;
        } else {
            User newUser = new User(username, password, displayName, publicProfile);
            database.addUser(newUser);
            Database.writeLog(LogType.INFO, IDENTIFIER,
                    String.format("User %s created.", username));
            database.serializeDatabase();
            return true;
        }
    }

    public synchronized boolean removeUser(String username) {
        User userToRemove = database.getUser(username);
        if (userToRemove != null) {
            database.removeUser(userToRemove.getUsername());
            Database.writeLog(LogType.INFO, IDENTIFIER,
                    String.format("Removed user %s.", username));
            database.serializeDatabase();
            return true;
        } else {
            Database.writeLog(LogType.INFO, IDENTIFIER,
                    String.format("User %s not found.", username));
            return false;
        }
    }

    public synchronized User getUser(String username) {
        return database.getUser(username);
    }

    public synchronized boolean addUser(User user) {
        if (database.getUser(user.getUsername()) == null) {
            database.addUser(user);
            Database.writeLog(LogType.INFO, IDENTIFIER,
                    String.format("Added user %s.", user.getUsername()));
            database.serializeDatabase();
            return true;
        } else {
            Database.writeLog(LogType.INFO, IDENTIFIER,
                    String.format("User %s previously added.", user.getUsername()));
            return false;
        }
    }

    public synchronized boolean sendMessage(String message, String username) {
        NetworkMessage networkMessage;

        if (username != null) {
            networkMessage = new NetworkMessage(ClientCommand.SEND_MESSAGE, IDENTIFIER, message);
        } else {
            networkMessage = new NetworkMessage(ServerCommand.SHOW_MESSAGE, IDENTIFIER, message);
        }
        if (username != null) {
            User recipient = database.getUser(username);
            if (recipient != null) {
                return sendToClient(networkMessage);
            } else {
                Database.writeLog(LogType.ERROR, IDENTIFIER,
                        String.format("User %s not found.", username));
                return false;
            }
        } else {
            Database.writeLog(LogType.ERROR, IDENTIFIER,
                    "No username provided.");
            return false;
        }
    }
    public synchronized void deleteMessage(User sender, Chat chat, String messageText) {
        Database database = Database.getInstance();
        Chat targetChat = database.getChat(chat.getName());
        if (targetChat != null) {
            List<Message> messages = targetChat.getMessages();
            Message messageToDelete = null;
            for (Message message : messages) {
                if (message.getSender().equals(sender) &&
                        message.getMessage().equals(messageText)) {
                    messageToDelete = message;
                    break;
                }
            }
            if (messageToDelete != null) {
                messages.remove(messageToDelete);
                Database.writeLog(LogType.INFO, IDENTIFIER, "Message deleted");
            } else {
                Database.writeLog(LogType.INFO, IDENTIFIER, String.format("Message" +
                        " from %s not found", sender.getUsername()));
            }
        } else {
            Database.writeLog(LogType.INFO, IDENTIFIER, String.format("Chat %s not found", chat.getName()));
        }
    }
    
    public synchronized boolean blockUser(User blockedUser, User mainUser) {
        if (blockedUser.blockUser(mainUser)) {
            Database.writeLog(LogType.INFO, IDENTIFIER, String.format("User %s blocked user %s.",
                    mainUser.getUsername(), blockedUser.getUsername()));
            return true;
        } else {
            Database.writeLog(LogType.ERROR, IDENTIFIER, String.format("Cannot" +
                    " block user %s.", blockedUser.getUsername()));
            return false;
        }
    }
    public synchronized boolean deleteAccount(User deletedAccount) {
        database.removeUser(deletedAccount.getUsername());
        if (database.getUser(deletedAccount.getUsername()) == null) {
            Database.writeLog(LogType.INFO, IDENTIFIER, String.format("Deleted" +
                    " user %s.", deletedAccount.getUsername()));
            return true;
        } else {
            Database.writeLog(LogType.ERROR, IDENTIFIER, String.format("Cannot" +
                    " delete user %s.", deletedAccount.getUsername()));
            return false;
        }
    }
    public synchronized boolean changeName(User user, String newName) {
        if (newName == null || newName.isEmpty()) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Invalid username.");
            return false;
        }

        String oldName = user.getUsername();
        if (oldName.equals(newName)) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "New username same as old.");
            return false;
        }
        if (database.getUser(newName) != null) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, String.format("Username" +
                    " %s is already taken.", newName));
            return false;
        }

        user.setUsername(newName);
        if (database.getUser(newName) != null) {
            Database.writeLog(LogType.INFO, IDENTIFIER, String.format("New username is %s.", newName));
            return true;
        } else {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Cannot change username.");
            return false;
        }
    }
    public synchronized boolean changePW(User user, String newPW) {
        if (newPW == null || newPW.isEmpty()) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Invalid password.");
            return false;
        }

        String oldPW = user.getPassword();
        if (oldPW.equals(newPW)) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "New password same as old.");
            return false;
        }

        user.setPassword(newPW);
        if (user.getPassword().equals(newPW)) {
            Database.writeLog(LogType.INFO, IDENTIFIER, String.format("New password is %s.", newPW));
            return true;
        } else {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Cannot change password.");
            return false;
        }
    }
}

