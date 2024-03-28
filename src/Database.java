import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Database implements DatabaseInterface{
    private static ArrayList<User> users = new ArrayList<User>();
    private static final String USERS_FILE = "users.txt";
    private static final String MESSAGES_FILE = "messages.txt";

    public static boolean readUsers() {
        try (BufferedReader bfr = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length != 3) {
                    return false;
                }
                User user = new User(parts[0], parts[1], parts[2]);
                users.add(user);
                //TODO add friends, blocked users, and messages (probably done through overloaded User constructor)
            }
            return true;
        } catch (IOException e) {
            System.out.println("Failed to read users from file.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createUser(String username, String password, String displayName) {
        if (users.add(new User(username, password, displayName))) {
            writeUsers();
            System.out.println("User created successfully.");
            return true;
        }
        System.out.println("Failed to create user.");
        return false;
    }

    public static boolean createUser(User user) {
        if (users.add(user)) {
            writeUsers();
            System.out.println("User created successfully.");
            return true;
        }
        System.out.println("Failed to create user.");
        return false;
    }

    public static boolean removeUser(User user) {
        if (users.remove(user)) {
            writeUsers();
            System.out.println("User removed successfully.");
            return true;
        }
        System.out.println("Failed to remove user.");
        return false;
    }

    public static void writeUsers() {
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                String line = String.format("%s %s %s", user.getUsername(), user.getPassword(), user.getDisplayName());
                bfw.write(line);
                bfw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to write users to file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeMessages() {
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(MESSAGES_FILE))) {
            for (User user : users) {
                bfw.write("Messages for " + user.getUsername() + ":");
                bfw.newLine();
                for (Message message : user.getMessages()) {
                    String line = String.format("%s %s %s", user.getUsername(), message.getSender().getUsername(), message.getRecipient().getUsername(), message.getMessage());
                    bfw.write(line);
                    bfw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to write messages to file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 }
