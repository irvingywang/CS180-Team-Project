package Network;

import Database.Database;
import Objects.Chat;
import Objects.InvalidChatException;
import Objects.Message;
import Objects.User;
import Database.LogType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    private User mainUser;

    /**
     * The entry point of the server application.
     * It creates a server instance and starts a new thread to run it.
     */
    public static void start() {
        Server server = new Server();
        serverThread = new Thread(server);
        serverThread.start();
    }

    /**
     * Returns the server thread.
     *
     * @return The server thread.
     */
    public static Thread getThread() {
        return serverThread;
    }

    /**
     * Starts the server and handles client connections.
     */
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
                        case CREATE_CHAT -> {
                            String[] parts = ((String) message.getObject()).split(",");
                            String chatName = parts[0];
                            String[] members = Arrays.copyOfRange(parts, 1, parts.length);

                            createChat(chatName, members);

                            sendToClient(new NetworkMessage(ClientCommand.CREATE_CHAT_SUCCESS, Server.IDENTIFIER, chatName));
                        }
                        case SEARCH_USER -> {
                            String searchQuery = (String) message.getObject();
                            List<User> foundUsers = searchUsers(searchQuery);
                            if (!foundUsers.isEmpty()) {
                                String[] usernames = foundUsers.stream().map(User::getUsername).toArray(String[]::new);
                                sendToClient(new NetworkMessage(ClientCommand.SEARCH_SUCCESS, IDENTIFIER, usernames));
                            } else {
                                sendToClient(new NetworkMessage(ClientCommand.SEARCH_FAILURE, IDENTIFIER, "No users found"));
                            }
                        }
                        case ADD_USER -> {
                            User user = (User) message.getObject();
                            if (database.addUser(user)) {
                                sendToClient(new NetworkMessage(ClientCommand.CREATE_USER_SUCCESS, IDENTIFIER, "User added successfully"));
                            } else {
                                sendToClient(new NetworkMessage(ClientCommand.CREATE_USER_FAILURE, IDENTIFIER, "Failed to add user"));
                            }
                        }
                        case REMOVE_USER -> {
                            String username = (String) message.getObject();
                            if (database.removeUser(username)) {
                                sendToClient(new NetworkMessage(ClientCommand.DELETE_USER_SUCCESS, IDENTIFIER, "User deleted successfully"));
                            } else {
                                sendToClient(new NetworkMessage(ClientCommand.DELETE_USER_FAILURE, IDENTIFIER, "Failed to delete user"));
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
        } catch (IOException | InvalidChatException e) {
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
     * Searches for users based on a query.
     *
     * @param message Contains the search query.
     */
    @Override
    public synchronized void searchUser(NetworkMessage message) {
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

    /**
     * Processes a login request from a client.
     *
     * @param message The network message containing login credentials.
     */
    @Override
    public synchronized void login(NetworkMessage message) {
        String[] loginInfo = ((String) message.getObject()).split(",");
        String username = loginInfo[0];
        String password = loginInfo[1];

        User user = database.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            Database.writeLog(LogType.INFO, IDENTIFIER, String.format("User %s logged in.", username));
            sendToClient(new NetworkMessage(ClientCommand.LOGIN_SUCCESS,
                    IDENTIFIER, user));
        } else {
            Database.writeLog(LogType.INFO, IDENTIFIER,
                    String.format("Login attempt failed for user %s.", username));
            System.out.println("Login failed for user: " + username);
            sendToClient(new NetworkMessage(ClientCommand.LOGIN_FAILURE,
                    IDENTIFIER, null));
        }
    }


    /**
     * Updates a user's profile.
     *
     * @param message Contains the new profile information.
     */
    @Override
    public synchronized void saveProfile(NetworkMessage message) {
        String[] profileInfo = ((String) message.getObject()).split(":");
        User user = database.getUser(profileInfo[1]);
        if (user != null) {
            user.setDisplayName(profileInfo[0]);
            user.setUsername(profileInfo[1]);
            user.setPassword(profileInfo[2]);
            user.setStatus(profileInfo[3]);
            user.setPublicProfile(profileInfo[4].equals("Public"));
            database.serializeDatabase();
            database.loadDatabase();
            System.out.println("Profile saved on server");

            User updatedUser = database.getUser(profileInfo[1]);
            if (updatedUser != null) {
                sendToClient(new NetworkMessage(ClientCommand.SAVE_PROFILE_SUCCESS,
                        IDENTIFIER, updatedUser));
            } else {
                sendToClient(new NetworkMessage(ClientCommand.SAVE_PROFILE_FAILURE,
                        IDENTIFIER, null));
            }
        } else {
            sendToClient(new NetworkMessage(ClientCommand.SAVE_PROFILE_FAILURE,
                    IDENTIFIER, null));
        }
    }

    /**
     * Handles the creation of a new user based on the information provided in the message.
     *
     * @param message The network message containing user creation data.
     */
    @Override
    public synchronized void createUser(NetworkMessage message) {
        String[] userInfo = ((String) message.getObject()).split(",");
        String username = userInfo[0];
        String password = userInfo[1];
        String displayName = userInfo[2];

        if (database.getUser(username) != null) {
            Database.writeLog(LogType.INFO, IDENTIFIER,
                    String.format("Attempt to create user %s failed: user already exists.", username));
            sendToClient(new NetworkMessage(ClientCommand.CREATE_USER_FAILURE,
                    IDENTIFIER, "User already exists."));
        } else {
            User newUser = new User(username, password, displayName, true);
            database.addUser(newUser);
            Database.writeLog(LogType.INFO, IDENTIFIER,
                    String.format("Created user %s.", username));
            database.serializeDatabase();
            database.loadDatabase();

            sendToClient(new NetworkMessage(ClientCommand.CREATE_USER_SUCCESS,
                    IDENTIFIER, database.getUser(username)));
        }
    }

    /**
     * Creates a new chat.
     *
     * @param message Contains the chat information.
     */
    @Override
    public synchronized void createChat(NetworkMessage message) {
        String[] chatInfo = ((String[]) message.getObject());
        String chatName = chatInfo[0];
        String[] memberUsernames = chatInfo[1].split(",");
        ArrayList<User> chatMembers = new ArrayList<>();
        for (String member : memberUsernames) {
            User user = database.getUser(member);
            if (user != null) {
                chatMembers.add(user);
            } else {
                Database.writeLog(LogType.ERROR, IDENTIFIER, "User not found: " + member);
                sendToClient(new NetworkMessage(ClientCommand.CREATE_CHAT_FAILURE, IDENTIFIER, "Member not found."));
            }
        }
        try {
            Chat chat = new Chat(chatName, chatMembers);
            database.addChat(chat);
            database.serializeDatabase();
            database.loadDatabase();
            Database.writeLog(LogType.INFO, IDENTIFIER, "Chat created successfully.");
            sendToClient(new NetworkMessage(ClientCommand.CREATE_CHAT_SUCCESS, IDENTIFIER, database.getChat(chatName)));
        } catch (Exception e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Failed to create chat: " + e.getMessage());
            sendToClient(new NetworkMessage(ClientCommand.CREATE_CHAT_FAILURE, IDENTIFIER, "Failed to create chat."));
        }
    }

    /**
     * Retrieves chats for a user.
     *
     * @param message Contains the user information.
     */
    @Override
    public synchronized void getChats(NetworkMessage message) {
        sendToClient(new NetworkMessage(ClientCommand.GET_CHATS_RESULT, IDENTIFIER,
                database.getChats((User) message.getObject()).toArray(new Chat[0])));
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

    public synchronized void addFriend(User friend) {
        if (mainUser.addFriend(friend)) {
            Database.writeLog(LogType.INFO, IDENTIFIER, String.format("User %s added user %s as friend.",
                    mainUser.getUsername(), friend.getUsername()));
            sendToClient(new NetworkMessage(ClientCommand.ADD_FRIEND_SUCCESS, IDENTIFIER, friend));
        } else {
            Database.writeLog(LogType.ERROR, IDENTIFIER, String.format("Cannot " +
                    "add user %s as friend.", friend.getUsername()));
            sendToClient(new NetworkMessage(ClientCommand.ADD_FRIEND_FAILURE, IDENTIFIER, friend));
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

    private void createChat(String chatName, String[] members) throws InvalidChatException {
        ArrayList<User> memberList = new ArrayList<>();
        for (String username : members) {
            User user = database.getUser(username);
            if (user != null) {
                memberList.add(user);
            } else {
                return;
            }
        }
        Chat group = new Chat(chatName, memberList);
        database.addChat(group);
    }

    private List<User> searchUsers(String query) {
        return database.getUsers().stream()
                .filter(user -> user.getUsername().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}

