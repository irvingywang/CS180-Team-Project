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

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // Initialization and Cleanup
    public void initialize() {
        clearLogFile();
        saveToLog("Starting database.");
        loadUsers();
        loadRelationships();
        saveToLog("Database initialized.");
    }

    public void close() {
        saveToLog("Closing database.");
        saveAll();
        saveToLog("Database closed.");
    }

    public void clearDatabase() {
        clearLogFile();
        users.clear();
        saveAll();
    }

    public static String getDelimiter() {
        return DELIMITER;
    }

    // Logging
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

    private static void clearLogFile() {
        try (FileWriter writer = new FileWriter(LOG_FILE, false)) {
            saveToLog("Log file cleared.");
        } catch (IOException e) {
            System.out.printf("Error clearing log file: %s%n", e.getMessage());
        }
    }

    // User Management
    public static void loadUsers() {
        saveToLog("Loading users from file.");
        users.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = new User(line);
                if (user.isValid()) {
                    users.put(user.getUsername(), user);
                }
            }
            saveToLog("Users successfully loaded from file.");
        } catch (IOException e) {
            saveToLog("Failed to read users from file.");
        } catch (Exception e) {
            saveToLog(String.format("An unexpected error occurred: %s", e.getMessage()));
        }
    }

    public User getUser(String username) {
        User user = users.get(username);
        if (user == null) {
            saveToLog(String.format("User %s not found.", username));
            return new User();
        }
        return user;
    }

    public ArrayList<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    public void createUser(String username, String password, String displayName) {
        if (users.containsKey(username)) {
            saveToLog(String.format("User %s already exists.", username));
        } else {
            User newUser = new User(username, password, displayName);
            users.put(username, newUser);
            saveToLog(String.format("User %s successfully created.", username));
        }
    }

    public void removeUser(String username) {
        if (users.remove(username) != null) {
            saveToLog(String.format("User %s removed.", username));
        } else {
            saveToLog(String.format("User %s not found.", username));
        }
    }

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
                        if (parts.length >= 3) {
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

    // Relationship Management
    private void loadRelationships() {
        for (User user : users.values()) {
            ArrayList<User> friends = loadUserRelationships(user.getUsername(), "friends");
            ArrayList<User> blocked = loadUserRelationships(user.getUsername(), "blocked");
            user.setFriends(friends);
            user.setBlocked(blocked);
        }
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public ArrayList<User> loadUserRelationships(String username, String relationship) {
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

    // Data Saving
    public void saveAll() {
        saveUsers();
        saveRelationships();
        saveMessages();
    }

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

    public synchronized void saveRelationships() {
        saveFriends();
        saveBlocked();
    }

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
}
