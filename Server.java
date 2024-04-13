import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
        database.initialize();
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to client");
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Server socket failed to open: " + e.getMessage());
        }
    }

    @Override
    public boolean sendToClient(NetworkMessage message) {
        try {
            out.writeObject(message);
            out.flush();
            return true;
        } catch (IOException e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Failed to send to client: " + e.getMessage());
            return false;
        }
    }

    @Override
    public NetworkMessage readMessage() {
        try {
            return (NetworkMessage) in.readObject();
        } catch (Exception e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Failed to read message: " + e.getMessage());
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
                            String[] loginInfo = ((String) message.getMessage()).split(",");
                            if (login(loginInfo[0], loginInfo[1])) {
                                Database.writeLog(LogType.INFO, IDENTIFIER, "Login successful");
                                sendToClient(new NetworkMessage(ClientCommand.LOGIN_SUCCESS, IDENTIFIER, database.getUser(loginInfo[0])));
                            } else {
                                System.out.println("Login failed");
                                //TODO send failure message
                            }
                        }
                        default -> {
                            Database.writeLog(LogType.ERROR, IDENTIFIER, "Invalid command received.");
                        }
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            Database.writeLog(LogType.ERROR, IDENTIFIER, "Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                Database.writeLog(LogType.ERROR, IDENTIFIER, "Failed to close client socket: " + e.getMessage());
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
            Database.writeLog(LogType.INFO, IDENTIFIER, String.format("User %s failed to log in.", username));
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
    public synchronized boolean createUser(String username, String password, String displayName, Boolean publicProfile) {
        if (database.getUser(username) != null) {
            //FIXME show this in the GUI
            Database.writeLog(LogType.INFO, IDENTIFIER, String.format("User %s already exists.", username));
            return false;
        } else {
            User newUser = new User(username, password, displayName, publicProfile);
            database.addUser(newUser);
            Database.writeLog(LogType.INFO, IDENTIFIER, String.format("User %s created.", username));
            database.serializeDatabase();
            return true;
        }
    }

    //TODO Server functionality

}

