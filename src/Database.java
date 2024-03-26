import java.util.ArrayList;

public class Database {
    private static ArrayList<User> users;
    private static ArrayList<Message> messages;

    public Database() {
        users = new ArrayList<User>();
    }

    public static void createUser(String username, String password, String displayName) {
        User newUser = new User(username, password, displayName);
        users.add(newUser);
    }

    public synchronized static void createUser(User user) {
        users.add(user);
    }

    public synchronized static void removeUser(User user) {
        users.remove(user);
    }
}
