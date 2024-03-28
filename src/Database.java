import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Database {
    // the key is the username and the value is the User object
    private static HashMap<String, User> users = new HashMap<>();
    private static final String USERS_FILE = "users.txt";
    private static final String MESSAGES_FILE = "messages.txt";
    private static final String LOG_FILE = "log.txt";
    private static final String DELIMITER = "; ";

    public static void initialize() {
        clearLogFile();
        saveToLog("Starting database.");
        loadUsers();
    }

    public static void close() {
        saveUsers();
        saveMessages();
        saveToLog("Closing database.");
    }

    public static void clearDatabase() {
        clearLogFile();
        users.clear();
        saveUsers();
        saveMessages();
    }

    public static String getDelimiter() {
        return DELIMITER;
    }

    private static void clearLogFile() {
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(LOG_FILE, false))) {
            // opening the file will clear it
            saveToLog("Log file cleared.");
        } catch (IOException e) {
            System.out.println("Error clearing log file: " + e.getMessage());
        }
    }

    public static boolean loadUsers() {
        users.clear();
        try (BufferedReader bfr = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                User user = new User(line);
                if (user.isValid()) {
                    users.put(user.getUsername(), user);
                }
            }
            return true;
        } catch (IOException e) {
            saveToLog("Failed to read users from file.");
            return false;
        } catch (Exception e) {
            saveToLog("An unexpected error occurred: " + e.getMessage());
            return false;
        }
    }

    public static User getUser(String username) {
        User user = users.get(username);
        if (user == null) {
            saveToLog("User " + username + " not found.");
            return new User(); //creates an invalid user
        }
        return user;
    }

    public static ArrayList<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    public static void createUser(String username, String password, String displayName) {
        if (users.containsKey(username)) {
            saveToLog("User " + username + " already exists.");
        } else {
            User newUser = new User(username, password, displayName);
            users.put(username, newUser);
            //saveUsers(); TODO save in batch?
            saveToLog("User " + username + " successfully created.");
        }
    }


    public static synchronized void removeUser(String username) {
        if (users.containsKey(username)) {
            users.remove(username);
            //saveUsers(); TODO save in batch?
        } else {
            saveToLog("User " + username + " not found.");
        }
    }

    public static synchronized void saveToLog(String message) {
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestampedMessage = String.format("%s: %s",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), message);
            bfw.write(timestampedMessage);
            bfw.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write to log: " + e.getMessage());
            System.err.println("Original log message: " + message);
        }
    }

    public static ArrayList<Message> loadMessages(String username) {
        ArrayList<Message> messages = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader(MESSAGES_FILE))) {
            String line;
            boolean userFound = false;
            while ((line = bfr.readLine()) != null) {
                if (line.startsWith("Messages for " + username + ":")) {
                    userFound = true;
                    while ((line = bfr.readLine()) != null && !line.isEmpty()) {
                        String[] parts = line.split(DELIMITER);
                        User sender = getUser(parts[1]);
                        User recipient = getUser(parts[2]);
                        String messageText = parts[3];
                        if (sender.isValid() && recipient.isValid()) {
                            Message message = new Message(sender, recipient, messageText);
                            messages.add(message);
                        }
                    }
                }
            }
            if (!userFound) {
                saveToLog("No messages found for user " + username + ".");
            }
            return messages;
        } catch (IOException e) {
            saveToLog("Failed to read messages from file for user " + username + ".");
            return messages;
        } catch (Exception e) {
            saveToLog("An unexpected error occurred: " + e.getMessage());
            return messages;
        }
    }


    public static synchronized void saveUsers() {
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users.values()) {
                try {
                    String line = String.format("%s%s%s%s%s", user.getUsername(),
                            DELIMITER, user.getPassword(), DELIMITER, user.getDisplayName());
                    bfw.write(line);
                    bfw.newLine();
                } catch (IOException e) {
                    saveToLog("Failed to write user " + user.getUsername() + " to file.");
                } catch (Exception e) {
                    saveToLog("An unexpected error occurred: " + e.getMessage());
                }
                saveToLog("User " + user.getUsername() + " successfully saved to file.");
            }
        } catch (IOException e) {
            saveToLog("Failed to open users file for writing.");
        } catch (Exception e) {
            saveToLog("An unexpected error occurred: " + e.getMessage());
        }
    }

    public static synchronized void saveMessages() {
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(MESSAGES_FILE))) {
            for (User user : users.values()) {
                try {
                    bfw.write("Messages for " + user.getUsername() + ":");
                    bfw.newLine();
                    for (Message message : user.getMessages()) {
                        try {
                            String line = String.format("%s%s%s%s%s%s%s", user.getUsername(),
                                    DELIMITER, message.getSender().getUsername(), DELIMITER,
                                    message.getRecipient().getUsername(), DELIMITER, message.getMessage());
                            bfw.write(line);
                            bfw.newLine();
                        } catch (Exception e) {
                            saveToLog("Failed to write message for user " + user.getUsername() + ": " + e.getMessage());
                        }
                        saveToLog("Message from " + user.getUsername() + " to " + message.getRecipient().getUsername() + " successfully saved.");
                    }
                } catch (Exception e) {
                    saveToLog("Failed to process messages for user " + user.getUsername() + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            saveToLog("Failed to open messages file for writing: " + e.getMessage());
        } catch (Exception e) {
            saveToLog("An unexpected error occurred: " + e.getMessage());
        }
    }
}
