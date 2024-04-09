import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements ServerInterface, Runnable {
    public static final int PORT = 1234;
    private Database database = Database.getInstance();

    public static void main(String[] args) {
        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            Database.writeLog(String.format("Server started on port %d.", PORT));
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

                    //TODO Functionality
                } catch (Exception e) {

                }
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }

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
     * TODO fix documentation
     * Creates a new User object and adds it to the users map.
     * If a user with the same username already exists, logs the event and does not create a new user.
     *
     * @param username      - the username of the new user
     * @param password      - the password of the new user
     * @param displayName   - the display name of the new user
     * @param publicProfile - the public status of the new user
     */
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

