import java.util.ArrayList;

public interface DatabaseInterface {
    void initialize();
    void close();
    void reset();
    User getUser(String username);
    ArrayList<User> getUsers();
    void createUser(String username, String password, String displayName, Boolean publicProfile);
    void removeUser(String username);
    void loadDatabase();
    void serializeDatabase();
}
