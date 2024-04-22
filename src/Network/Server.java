package Network;

import Database.Database;
import Objects.Chat;
import Objects.Message;
import Objects.User;
import Database.LogType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Project05 - Server
 * <p>
 * This is the server for our messaging program. It processes
 * requests from the client.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
public class Server implements ServerInterface, Runnable {
    public static final int PORT = 1234;
    public static final String SERVER_ADDRESS = "localhost";
    private Database database = Database.getInstance();
    private ServerSocket serverSocket;
    ObjectInputStream in;
    ObjectOutputStream out;
    private static final Identifier IDENTIFIER = Identifier.SERVER;
    static Thread serverThread;

    /**
     * The entry point of the server application.
     * It creates a server instance and starts a new thread to run it.
     *
     */
    public static void start() {
        Server server = new Server();
        serverThread = new Thread(server);
        serverThread.start();
    }

    public static Thread getThread() {
        return serverThread;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to client");
                handleCommands(clientSocket);
            }
        } catch (IOException e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Server socket" +
                    " failed to open: " + e.getMessage());
        }
    }

    /**
     * Sends a message to a connected client.
     *
     * @param message The message to send.
     * @return True if the message was successfully sent, false otherwise.
     */
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

    /**
     * Reads a message received from a client.
     *
     * @return The received message, or null if an error occurred.
     */
    @Override
    public NetworkMessage readMessage() {
        try {
            return (NetworkMessage) in.readObject();
        } catch (Exception e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Reading" +
                    " failure: " + e.getMessage());
            return null;
        }
    }

    /**
     * Handles a connection from a client.
     *
     * @param clientSocket The socket representing the client connection.
     */
    @Override
    public void handleCommands(Socket clientSocket) {
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
                        case CREATE_USER -> {
                            String[] userInfo = ((String) message.getObject()).split(",");
                            if (createUser(userInfo[0], userInfo[1], userInfo[2])) {
                                sendToClient(new NetworkMessage(ClientCommand.CREATE_USER_SUCCESS,
                                        IDENTIFIER, database.getUser(userInfo[0])));
                            } else {
                                sendToClient(new NetworkMessage(ClientCommand.CREATE_USER_FAILURE,
                                        IDENTIFIER, null));
                            }
                        }
                        case SEARCH_USER -> {
                            String query = (String) message.getObject();
                            ArrayList<User> matchedUsers = new ArrayList<>();
                            query = query.toLowerCase();
                            for (User user : database.getUsers()) {
                                if (user.getUsername().toLowerCase().contains(query) ||
                                        user.getDisplayName().toLowerCase().contains(query)) {
                                    matchedUsers.add(user);
                                }
                            }
                            User[] results = matchedUsers.toArray(new User[0]);

                            sendToClient(new NetworkMessage(ClientCommand.SEARCH_USER_RESULT,
                                    IDENTIFIER, results));
                        }
                        case SAVE_PROFILE -> {
                            String[] profileInfo = ((String) message.getObject()).split(",");
                            User user = database.getUser(profileInfo[1]);
                            if (user != null) {
                                user.setDisplayName(profileInfo[0]);
                                user.setPassword(profileInfo[2]);
                                user.setPublicProfile(profileInfo[3].equals("Public"));
                                database.serializeDatabase();
                                sendToClient(new NetworkMessage(ClientCommand.SAVE_PROFILE_SUCCESS,
                                        IDENTIFIER, user));
                            } else {
                                sendToClient(new NetworkMessage(ClientCommand.SAVE_PROFILE_FAILURE,
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

    /**
     * Authenticates a user login.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return True if the login is successful, false otherwise.
     */

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
     * //FIXME the public profile is set public by default
     */
    @Override
    public synchronized boolean createUser(String username,
                                           String password, String displayName) {
        if (database.getUser(username) != null) {
            // FIXME show this in the GUI
            Database.writeLog(LogType.INFO, IDENTIFIER,
                    String.format("User %s already exists.", username));
            return false;
        } else {
            User newUser = new User(username, password, displayName, true);
            database.addUser(newUser);
            Database.writeLog(LogType.INFO, IDENTIFIER,
                    String.format("Created user %s.", username));
            database.serializeDatabase();
            return true;
        }
    }

    /**
     * Removes a user from the database.
     *
     * @param username - the username of the user to be removed
     * @return true if the user is successfully removed, false if the user is not found
     */

    public synchronized boolean removeUser(String username) {
        User removed = database.getUser(username);
        if (removed != null) {
            database.removeUser(removed.getUsername());
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

    /**
     * Retrieves a user from the database.
     *
     * @param username - the username of the user to retrieve
     * @return the User object corresponding to the given username, or null if not found
     */
    public synchronized User getUser(String username) {
        return database.getUser(username);
    }


    /**
     * Adds a user to the database.
     *
     * @param user - the User object to add
     * @return true if the user is added successfully, false if the user already exists
     */
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

    /**
     * Sends a message to a user or displays it in the server.
     *
     * @param message  - the message to be sent
     * @param username - the username of the recipient, or null if it's a server message
     * @return true if the message is sent successfully, false otherwise (e.g., if the recipient is not found)
     */

    public synchronized boolean sendMessage(String message, String username) {
        NetworkMessage networkMessage;

        if (username != null) {
            networkMessage = new NetworkMessage(ClientCommand.SEND_MESSAGE, IDENTIFIER, message);
        } else {
            networkMessage = new NetworkMessage(ServerCommand.SHOW_MESSAGE, IDENTIFIER, message);
        }
        if (username != null) {
            User receiver = database.getUser(username);
            if (receiver != null) {
                return sendToClient(networkMessage);
            } else {
                Database.writeLog(LogType.ERROR, IDENTIFIER,
                        String.format("User %s not found.", username));
                return false;
            }
        } else {
            Database.writeLog(LogType.ERROR, IDENTIFIER,
                    "No user to send message to.");
            return false;
        }
    }

    /**
     * Deletes a specific message from a chat.
     *
     * @param sender      - the user who sent the message to be deleted
     * @param chat        - the chat from which the message should be deleted
     * @param messageText - the content of the message to be deleted
     * @return true if the message is successfully deleted, false otherwise (e.g., if the message is not found)
     */
    public synchronized boolean deleteMessage(User sender, Chat chat, String messageText) {
        Database database = Database.getInstance();
        Chat chat1 = database.getChat(chat.getName());
        if (chat1 != null) {
            List<Message> chat1Messages = chat1.getMessages();
            Message deleted = null;
            for (Message m : chat1Messages) {
                if (m.getSender().equals(sender) &&
                        m.getMessage().equals(messageText)) {
                    deleted = m;
                    break;
                }
            }
            if (deleted != null) {
                chat1Messages.remove(deleted);
                Database.writeLog(LogType.INFO, IDENTIFIER, "Message deleted");
                return true;
            } else {
                Database.writeLog(LogType.INFO, IDENTIFIER, String.format("Message" +
                        " from %s not found", sender.getUsername()));
                return false;
            }
        } else {
            Database.writeLog(LogType.INFO, IDENTIFIER, String.format("Chat %s not found", chat.getName()));
            return false;
        }
    }

    /**
     * Blocks a user from interacting with another user.
     *
     * @param blockedUser - the user to be blocked
     * @param mainUser    - the user who is blocking the other user
     * @return true if the user is successfully blocked, false otherwise
     */
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

    /**
     * Deletes a user account from the database.
     *
     * @param deleted - the user account to be deleted
     * @return true if the account is successfully deleted, false otherwise
     */
    public synchronized boolean deleteAccount(User deleted) {
        database.removeUser(deleted.getUsername());
        if (database.getUser(deleted.getUsername()) == null) {
            Database.writeLog(LogType.INFO, IDENTIFIER, String.format("Deleted" +
                    " user %s.", deleted.getUsername()));
            return true;
        } else {
            Database.writeLog(LogType.ERROR, IDENTIFIER, String.format("Cannot" +
                    " delete user %s.", deleted.getUsername()));
            return false;
        }
    }

    /**
     * Changes the username of a user.
     *
     * @param user    - the user whose username is to be changed
     * @param newName - the new username to be assigned
     * @return true if the username is successfully changed, false otherwise (e.g., if the new username is already taken)
     */
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

    /**
     * Changes the password of a user.
     *
     * @param user  - the user whose password is to be changed
     * @param newPW - the new password to be assigned
     * @return true if the password is successfully changed, false otherwise
     */
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

