import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Database implements DatabaseInterface {
    private static Database instance;
    private static final HashMap<String, User> users = new HashMap<>(); // username, user
    private static final String DIR = "data/";
    private static final String USERS_FILE = DIR + "users.txt";
    private static final String FRIENDS_FILE = DIR + "friends.txt";
    private static final String BLOCKED_FILE = DIR + "blocked.txt";
    private static final String MESSAGES_FILE = DIR + "messages.txt";
    private static final String LOG_FILE = DIR + "log.txt";
    private static final String DELIMITER = "; ";

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
        saveToLog("Starting database.");
        loadUsers();
        for (User user : users.values()) {
            user.setMessages(loadMessages(user.getUsername()));
            user.setFriends(loadRelationships(user.getUsername(), "friends"));
            user.setBlocked(loadRelationships(user.getUsername(), "blocked"));
        }
        saveToLog("Database initialized.");
    }

    /**
     * Saves everything and closes.
     */
    public void close() {
        saveToLog("Closing database.");
        saveAll();
        saveToLog("Database closed.");
    }

    /**
     * Clears everything, including written files.
     */
    public void clearDatabase() {
        clearLogFile();
        users.clear();
        saveAll();
    }

    /**
     * Writes the current state of the database to the files.
     */
    public void saveAll() {
        saveUsers();
        saveRelationships();
        saveMessages();
    }

    /**
     * This method is used to get the delimiter used to separate information
     * in the .txt files. Used in reading and writing to the files.
     *
     * @return DELIMITER - the delimiter by the database
     */
    public static String getDelimiter() {
        return DELIMITER;
    }

    // Logging

    /**
     * This method is used to save a time stamped message to the log file.
     * If an IOException occurs, it will print an error message to the console.
     *
     * @param message - the message to be saved to the log
     */
    public static synchronized void saveToLog(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestampedMessage = String.format("%s: %s",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), message);
            writer.write(timestampedMessage);
            writer.newLine();
        } catch (IOException e) {
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
            saveToLog("Log file cleared.");
        } catch (IOException e) {
            System.out.printf("Error clearing log file: %s%n", e.getMessage());
        }
    }

    // Load data from disk

    /**
     * Loads users from a file, validates them, and adds them to the users map.
     */
    public void loadUsers() {
        saveToLog("Loading users from file.");
        users.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User newUser = new User(line);
                if (newUser.isValid()) {
                    users.put(newUser.getUsername(), newUser);
                }
                saveToLog(String.format("User %s loaded from file.", newUser.getUsername()));
            }
            saveToLog("Users successfully loaded from file.");
        } catch (IOException e) {
            saveToLog("Failed to read users from file.");
        } catch (Exception e) {
            saveToLog(String.format("An unexpected error occurred: %s", e.getMessage()));
        }
    }

    /**
     * Loads messages for a specific user from a file and returns them as a list.
     *
     * @param username - the username of the user
     * @return messages - the list of messages for the user
     */
    public ArrayList<Message> loadMessages(String username) {
        saveToLog(String.format("Loading messages from file for user %s.", username));
        ArrayList<Message> messages = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader(MESSAGES_FILE))) {
            String line;
            boolean userFound = false;
            while ((line = bfr.readLine()) != null) {
                if (line.startsWith(String.format("Messages for %s:", username))) {
                    userFound = true;
                    while ((line = bfr.readLine()) != null && !line.isEmpty()) {
                        String[] parts = line.split(DELIMITER);
                        User sender = getUser(parts[0]);
                        User recipient = getUser(parts[1]);
                        String messageText = parts[2];
                        if (sender != null && recipient != null && sender.isValid() && recipient.isValid()) {
                            Message message = new Message(sender, recipient, messageText);
                            messages.add(message);
                        }
                    }
                }
            }
            if (!userFound) {
                saveToLog(String.format("No messages found for user %s.", username));
            } else {
                saveToLog(String.format("Messages successfully loaded from file for user %s.", username));
            }
            return messages;
        } catch (IOException e) {
            saveToLog(String.format("Failed to read messages from file for user %s: %s", username, e.getMessage()));
            return messages;
        } catch (Exception e) {
            saveToLog(String.format("An unexpected error occurred: %s", e.getMessage()));
            return messages;
        }
    }

    /**
     * Loads relationships (friends or blocked users) for a specific user from a file and returns them as a list.
     *
     * @param username     - the username of the user
     * @param relationship - the type of relationship ("friends" or "blocked")
     * @return relationships - the list of relationships for the user
     */
    public ArrayList<User> loadRelationships(String username, String relationship) {
        String relationshipFile = relationship.equals("friends") ? FRIENDS_FILE : BLOCKED_FILE;
        saveToLog(String.format("Loading %s from file for user %s.", relationship, username));

        ArrayList<User> relationships = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(relationshipFile))) {
            String line;
            boolean userFound = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(String.format("%s for %s:", capitalize(relationship), username))) {
                    userFound = true;
                    while ((line = reader.readLine()) != null && !line.isEmpty()) {
                        User relatedUser = users.get(line.trim());
                        if (relatedUser != null) {
                            relationships.add(relatedUser);
                        }
                    }
                }
            }
            if (!userFound) {
                saveToLog(String.format("No %s found for user %s.", relationship, username));
            } else {
                saveToLog(String.format("%s successfully loaded from file for user %s.",
                        capitalize(relationship), username));
            }
            return relationships;
        } catch (IOException e) {
            saveToLog(String.format("Failed to read %s from file for user %s: %s",
                    relationship, username, e.getMessage()));
            return relationships;
        } catch (Exception e) {
            saveToLog(String.format("An unexpected error occurred while loading %s for user %s: %s",
                    relationship, username, e.getMessage()));
            return relationships;
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
            saveToLog(String.format("User %s not found.", username));
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
            saveToLog(String.format("User %s already exists.", username));
        } else {
            User newUser = new User(username, password, displayName);
            users.put(username, newUser);
            saveToLog(String.format("User %s successfully created.", username));
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
            saveToLog(String.format("User %s removed.", username));
        } else {
            saveToLog(String.format("User %s not found.", username));
        }
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    // Save data to disk

    /**
     * This method writes user data to a file.
     * If an exception occurs, it logs the error.
     */
    public synchronized void saveUsers() {
        saveToLog("Saving users to file.");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users.values()) {
                writer.write(user.toString());
                writer.newLine();
                saveToLog(String.format("User %s successfully saved to file.", user.getUsername()));
            }
        } catch (IOException e) {
            saveToLog("Failed to open users file for writing.");
        } catch (Exception e) {
            saveToLog(String.format("An unexpected error occurred while saving users: %s", e.getMessage()));
        }
    }

    /**
     * This method writes all messages of each user to a file.
     * If an exception occurs, it logs the error.
     */
    public synchronized void saveMessages() {
        saveToLog("Saving messages to file.");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MESSAGES_FILE))) {
            for (User user : users.values()) {
                writer.write(String.format("Messages for %s:", user.getUsername()));
                writer.newLine();
                for (Message message : user.getMessages()) {
                    writer.write(message.toString());
                    writer.newLine();
                }
                writer.newLine();
            }
        } catch (IOException e) {
            saveToLog("Failed to open messages file for writing.");
        } catch (Exception e) {
            saveToLog(String.format("An unexpected error occurred while saving messages: %s", e.getMessage()));
        }
    }

    /**
     * This method writes user relationships to files.
     */
    public synchronized void saveRelationships() {
        saveFriends();
        saveBlocked();
    }

    /**
     * This method writes all friends of each user to a file.
     * If an exception occurs, it logs the error.
     */
    public synchronized void saveFriends() {
        saveToLog("Saving friends to file.");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FRIENDS_FILE))) {
            for (User user : users.values()) {
                writer.write(String.format("Friends for %s:", user.getUsername()));
                writer.newLine();
                for (User friend : user.getFriends()) {
                    writer.write(friend.getUsername());
                    writer.newLine();
                }
                writer.newLine();
            }
        } catch (IOException e) {
            saveToLog("Failed to write friends to file.");
        } catch (Exception e) {
            saveToLog(String.format("An unexpected error occurred while saving friends: %s", e.getMessage()));
        }
    }

    /**
     * This method writes all blocked users of each user to a file.
     * If an exception occurs, it logs the error.
     */
    public synchronized void saveBlocked() {
        saveToLog("Saving blocked users to file.");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BLOCKED_FILE))) {
            for (User user : users.values()) {
                writer.write(String.format("Blocked for %s:", user.getUsername()));
                writer.newLine();
                for (User blockedUser : user.getBlocked()) {
                    writer.write(blockedUser.getUsername());
                    writer.newLine();
                }
                writer.newLine();
            }
        } catch (IOException e) {
            saveToLog("Failed to write blocked users to file.");
        } catch (Exception e) {
            saveToLog(String.format("An unexpected error occurred while saving blocked users: %s", e.getMessage()));
        }
    }
}
