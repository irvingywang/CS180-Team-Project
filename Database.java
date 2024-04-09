import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Project05 -- Database
 *
 * This class is used to manage the database of users, messages, and relationships.
 * It is a singleton class, meaning there should only be one instance of it.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 *
 * @version April 1, 2024
 *
 */
public class Database implements DatabaseInterface {
    private static Database instance;
    private static final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>(); // username, user
    private static final String DIR = "data/";
    private static String DATA_FILE = DIR + "data.ser";
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

    public void setDataFile(String filename) {
        DATA_FILE = DIR + filename;
    }

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
     * Clears everything, including the data file.
     */
    public void reset() {
        clearLogFile();
        users.clear();
        serializeDatabase();
    }

    /**
     * This method is used to clear the log file.
     * It should only be called once at the start of the program.
     * Then it logs that the log file has been cleared.
     * If an Exception occurs, it prints an error message to the console.
     */
    private synchronized void clearLogFile() {
        try (FileWriter writer = new FileWriter(LOG_FILE, false)) {
            writeLog("Log file cleared.");
        } catch (IOException e) {
            System.out.printf("Error clearing log file: %s%n", e.getMessage());
        }
    }

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
     * Loads users from a file, validates them, and adds them to the users map.
     */
    public synchronized void loadDatabase() {
        writeLog("Loading database from file.");
        users.clear();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Object object = inputStream.readObject();
            if (object instanceof ConcurrentHashMap) {
                users.putAll((ConcurrentHashMap<String, User>) object);
                if (users.isEmpty()) {
                    writeLog("No users found in database file.");
                } else {
                    writeLog("Database successfully loaded from file.");
                }
            }
        } catch (Exception e) {
            writeLog(String.format("Failed to load database from file: %s", e.getMessage()));
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
            return null;
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

    public synchronized void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    /**
     * Removes a User object from the users map using the provided username.
     * If the user does not exist, logs the event.
     *
     * @param username - the username of the user to be removed
     */
    public synchronized void removeUser(String username) {
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
        writeLog("Saving database to file.");
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            outputStream.writeObject(users);
        } catch (IOException e) {
            writeLog("Failed to write database to file.");
        } catch (Exception e) {
            writeLog(String.format("An unexpected error occurred while saving database: %s", e.getMessage()));

        }
        writeLog("Database saved to file.");
    }
}
