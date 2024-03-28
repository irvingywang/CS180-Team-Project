import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Database {
    private static ArrayList<User> users = new ArrayList<User>();
    private static final String USERS_FILE = "users.txt";
    private static final String MESSAGES_FILE = "messages.txt";
    private static final String LOG_FILE = "log.txt";

    public static void start() {
        clearLogFile();
        writeToLog("Starting database.");
        readUsers();
        readMessages();
    }

    private static void clearLogFile() {
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(LOG_FILE, false))) {
            //opening the file will clear it
        } catch (Exception e) {
            System.out.println("Error clearing log file: " + e.getMessage());
        }
    }

    public static boolean readUsers() {
        try (BufferedReader bfr = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                users.add(new User(line));
            }
            for (User user : users) { //remove invalid users
                if (!user.isValid()) {
                    users.remove(user);
                }
            }
            return true;
        } catch (IOException e) {
            writeToLog("Failed to read users from file.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean readMessages() {
        try (BufferedReader bfr = new BufferedReader(new FileReader(MESSAGES_FILE))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                //TODO process messages
            }
            return true;
        } catch (IOException e) {
            writeToLog("Failed to read messages from file.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void createUser(String username, String password, String displayName) {
        User newUser = new User(username, password, displayName);
        users.add(newUser);
    }

    public static synchronized void createUser(User user) {
        users.add(user);
        writeUsers();
    }

    public static synchronized void removeUser(User user) {
        users.remove(user);
        writeUsers();
    }

    public static synchronized void writeToLog(String message) {
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            bfw.write(message);
            bfw.newLine();
        } catch (Exception e) {
            e.printStackTrace(); //FIXME better handling?
        }
    }

    public static synchronized void writeUsers() {
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                try {
                    String line = String.format("%s,%s,%s", user.getUsername(), user.getPassword(), user.getDisplayName());
                    bfw.write(line);
                    bfw.newLine();
                } catch (IOException e) {
                    writeToLog("Failed to write user " + user.getUsername() + " to file.");
                } catch (Exception e) {
                    writeToLog("An unexpected error occurred: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            writeToLog("Failed to open users file for writing.");
        } catch (Exception e) {
            writeToLog("An unexpected error occurred: " + e.getMessage());
        }
    }

    public static synchronized void writeMessages() {
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(MESSAGES_FILE))) {
            for (User user : users) {
                try {
                    bfw.write("Messages for " + user.getUsername() + ":");
                    bfw.newLine();
                    for (Message message : user.getMessages()) {
                        try {
                            String line = String.format("%s,%s,%s,%s", user.getUsername(), message.getSender().getUsername(), message.getRecipient().getUsername(), message.getMessage());
                            bfw.write(line);
                            bfw.newLine();
                        } catch (Exception e) {
                            writeToLog("Failed to write message for user " + user.getUsername() + ": " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    writeToLog("Failed to process messages for user " + user.getUsername() + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            writeToLog("Failed to open messages file for writing: " + e.getMessage());
        } catch (Exception e) {
            writeToLog("An unexpected error occurred: " + e.getMessage());
        }
    }


}
