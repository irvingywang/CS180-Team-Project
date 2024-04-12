import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements ServerInterface, Runnable {
    public static final int PORT = 1234;
    private Database database = Database.getInstance();
    private ServerSocket serverSocket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public static void main(String[] args) {
        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    @Override
    public void run() {
        while (true) {
            if (connectToClient()) {
                //connection successful
                //TODO Server functionality
            } else {
                //FIXME this line spams the log file
                //Database.writeLog("Connection to client failed.");
            }
        }
    }

    @Override
    public boolean connectToClient() {
        try {
            serverSocket = new ServerSocket(PORT);
            Socket clientSocket = serverSocket.accept();
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean login(String username, String password) {
        User user = database.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            Database.writeLog(String.format("User %s logged in.", username));
            return true;
        } else {
            Database.writeLog(String.format("Failed login attempt for user %s.", username));
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
            Database.writeLog(String.format("User %s already exists.", username));
            return false;
        } else {
            User newUser = new User(username, password, displayName, publicProfile);
            database.addUser(newUser);
            Database.writeLog(String.format("User %s successfully created.", username));
            database.serializeDatabase();
            return true;
        }
    }

    //TODO Server functionality

}

