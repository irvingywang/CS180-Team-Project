import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * This class is used to manage the database of users, messages, and relationships.
 * It is a singleton class, meaning there should only be one instance of it.
 */
public class Database implements DatabaseInterface {
    private static Database instance;
    private static final HashMap<String, User> users = new HashMap<>(); // username, user
    private static final String DIR = "data/";
    private static final String DATA_FILE = DIR + "data.ser";
    private static final String LOG_FILE = DIR + "log.txt";
    /**
     * There should only be one instance of the Database class.
     *
     * @return instance - the single instance of the Database class
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // Database actions

    /**
     * Clears the log file and loads everything from disk.
     */
    public void initialize() {
        clearLogFile();
        writeLog("Starting database.");
        loadDatabase();
        writeLog("Database initialized.");
    }

    /**
     * Saves everything and closes.
     */
    public void close() {
        writeLog("Closing database.");
        serializeDatabase();
        writeLog("Database closed.");
    }

    /**
     * Clears everything, including written files.
     */
    public void reset() {
        clearLogFile();
        users.clear();
        serializeDatabase();
    }

    // Logging

    /**
     * This method is used to save a time stamped message to the log file.
     * If an Exception occurs, it will print an error message to the console.
     *
     * @param message - the message to be saved to the log
     */
    public static synchronized void writeLog(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestampedMessage = String.format("%s: %s",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), message);
            writer.write(timestampedMessage);
            writer.newLine();
        } catch (Exception e) {
            System.out.printf("Failed to write to log: %s%n", e.getMessage());
        }
    }

    /**
     * This method is used to clear the log file.
     * It should only be called once at the start of the program.
     * Then it logs that the log file has been cleared.
     * If an Exception occurs, it prints an error message to the console.
     */
    private static void clearLogFile() {
        try (FileWriter writer = new FileWriter(LOG_FILE, false)) {
            writeLog("Log file cleared.");
        } catch (IOException e) {
            System.out.printf("Error clearing log file: %s%n", e.getMessage());
        }
    }

    // Load data from disk

    /**
     * Loads users from a file, validates them, and adds them to the users map.
     */
    public void loadDatabase() {
        writeLog("Loading users from file.");
        users.clear();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Object object = inputStream.readObject();
            if (object instanceof HashMap) {
                users.putAll((HashMap<String, User>) object);
                writeLog("Users successfully loaded from file.");
            }
        } catch (Exception e) {
            writeLog(String.format("Failed to load users from file: %s", e.getMessage()));
        }
    }

    /**
     * Retrieves a User object from the users map using the provided username.
     * If the user does not exist, logs the event and returns a new User object.
     *
     * @param username - the username of the user
     * @return user - the User object associated with the username, or a new User object if not found
     */
    public User getUser(String username) {
        User user = users.get(username);
        if (user == null) {
            writeLog(String.format("User %s not found.", username));
            return new User();
        }
        return user;
    }

    /**
     * Returns a list of all User objects in the users map.
     *
     * @return ArrayList<User> - a list of all users
     */
    public ArrayList<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    /**
     * Creates a new User object and adds it to the users map.
     * If a user with the same username already exists, logs the event and does not create a new user.
     *
     * @param username    - the username of the new user
     * @param password    - the password of the new user
     * @param displayName - the display name of the new user
     */
    public void createUser(String username, String password, String displayName) {
        if (users.containsKey(username)) {
            writeLog(String.format("User %s already exists.", username));
        } else {
            User newUser = new User(username, password, displayName);
            users.put(username, newUser);
            writeLog(String.format("User %s successfully created.", username));
        }
    }

    /**
     * Removes a User object from the users map using the provided username.
     * If the user does not exist, logs the event.
     *
     * @param username - the username of the user to be removed
     */
    public void removeUser(String username) {
        if (users.remove(username) != null) {
            writeLog(String.format("User %s removed.", username));
        } else {
            writeLog(String.format("User %s not found.", username));
        }
    }

    /**
     * This method writes all data to a binary file.
     * If an exception occurs, it logs the error.
     */
    public synchronized void serializeDatabase() {
        writeLog("Saving users to file.");
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            outputStream.writeObject(users);
        } catch (IOException e) {
            writeLog("Failed to write users to file.");
        } catch (Exception e) {
            writeLog(String.format("An unexpected error occurred while saving users: %s", e.getMessage()));

        }
    }
}
