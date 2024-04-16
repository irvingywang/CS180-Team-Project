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
 *
 * @version April 1, 2024
 *
 */
public class Database implements DatabaseInterface {
    private static Database instance;
    private static final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();// username, user
    private static final ConcurrentHashMap<String, Chat> chats = new ConcurrentHashMap<>();// chatId(name), chat
    private static final String DIR = "data/";
    private static String DATA_FILE = DIR + "data.ser";
    private static final String LOG_FILE = DIR + "log.txt";
    private static final Identifier IDENTIFIER = Identifier.DATABASE;

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
    @Override
    public void initialize() {
        clearLogFile();
        writeLog(LogType.INFO, IDENTIFIER, "Initializing database.");
        loadDatabase();
        writeLog(LogType.INFO, IDENTIFIER, "Database initialized.");
    }

    /**
     * Saves everything and closes.
     */
    @Override
    public void close() {
        writeLog(LogType.INFO, IDENTIFIER, "Closing database.");
        serializeDatabase();
        writeLog(LogType.INFO, IDENTIFIER, "Database closed.");
    }

    /**
     * Clears everything, including the data file.
     */
    @Override
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
            writeLog(LogType.INFO, IDENTIFIER, "Log file cleared.");
        } catch (IOException e) {
            System.out.printf("Error clearing log file: %s%n", e.getMessage());
        }
    }

    /**
     * This method is used to save a time stamped message to the log file.
     * If an Exception occurs, it will print an error message to the console.
     *
     * @param logType - the type of log message (ERROR, INFO, etc.)
     * @param identifier - the source of the log message
     * @param message - the message to be saved to the log
     */
    public static synchronized void writeLog(LogType logType, Identifier identifier, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String date = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
            String output =
                    String.format("[%s] %s %s %s", logType, identifier, date, message);
            writer.write(output);
            writer.newLine();
        } catch (Exception e) {
            System.out.printf("Failed to write to log: %s%n", e.getMessage());
        }
    }

    /**
     * Retrieves a User object from the users map using the provided username.
     * If the user does not exist, logs the event and returns a new User object.
     *
     * @param username - the username of the user
     * @return user - the User object associated with the username, or a new User object if not found
     */
    @Override
    public User getUser(String username) {
        User user = users.get(username);
        if (user == null) {
            writeLog(LogType.INFO, IDENTIFIER, String.format("User %s not found.", username));
            return null;
        }
        return user;
    }

    /**
     * Returns a list of all User objects in the users map.
     *
     * @return ArrayList<User> - a list of all users
     */
    @Override
    public ArrayList<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    /**
     * Adds a User object to the users map.
     *
     * @param user - the User object to be added
     */
    @Override
    public synchronized void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    /**
     * Removes a User object from the users map using the provided username.
     * If the user does not exist, logs the event.
     *
     * @param username - the username of the user to be removed
     */
    @Override
    public synchronized void removeUser(String username) {
        if (users.remove(username) != null) {
            writeLog(LogType.INFO, IDENTIFIER, String.format("User %s removed.", username));
        } else {
            writeLog(LogType.INFO, IDENTIFIER, String.format("User %s not found.", username));
        }
    }

    public Chat getChat(String name) {
        Chat chat = chats.get(name);
        if (chat == null) {
            writeLog(LogType.INFO, IDENTIFIER, String.format("Chat %s not found.", name));
            return null;
        }
        return chat;
    }

    public ArrayList<Chat> getChats() {
        return new ArrayList<>(chats.values());
    }

    public synchronized void addChat(Chat chat) {
        chats.put(chat.getName(), chat);
    }

    public synchronized void removeChat(String name) {
        if (chats.remove(name) != null) {
            writeLog(LogType.INFO, IDENTIFIER, String.format("Chat %s removed.", name));
        } else {
            writeLog(LogType.INFO, IDENTIFIER, String.format("Chat %s not found.", name));
        }
    }

    /**
     * Loads users and chats from a file, validates them, and adds them to the users map.
     */
    @Override
    public synchronized void loadDatabase() {
        writeLog(LogType.INFO, IDENTIFIER, "Loading database from file.");
        users.clear();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Object object = inputStream.readObject();
            if (object instanceof DatabaseContainer) {
                users.putAll(((DatabaseContainer) object).getUsers());
                chats.putAll(((DatabaseContainer) object).getChats());
                if (users.isEmpty()) {
                    writeLog(LogType.ERROR, IDENTIFIER, "No users found in database file.");
                }
                if (chats.isEmpty()) {
                    writeLog(LogType.ERROR, IDENTIFIER, "No chats found in database file.");
                }
                writeLog(LogType.INFO, IDENTIFIER, "Database loaded from file.");
            }
        } catch (Exception e) {
            writeLog(LogType.ERROR, IDENTIFIER, "Error loading database from file: " + e.getMessage());
        }
    }

    /**
     * This method writes all data to a binary file.
     * If an exception occurs, it logs the error.
     */
    @Override
    public synchronized void serializeDatabase() {
        writeLog(LogType.INFO, IDENTIFIER, "Saving database to file.");
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            outputStream.writeObject(new DatabaseContainer(users, chats));
        } catch (Exception e) {
            writeLog(LogType.ERROR, IDENTIFIER, "Failed to save database to file: " + e.getMessage());
        }
        writeLog(LogType.INFO, IDENTIFIER, "Database saved to file.");
    }
}
