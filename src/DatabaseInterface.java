import java.util.ArrayList;

public interface DatabaseInterface {
    void initialize();
    void close();
    void clearDatabase();
    User getUser(String username);
    ArrayList<User> getUsers();
    void createUser(String username, String password, String displayName);
    void removeUser(String username);
    ArrayList<Message> loadMessages(String username);
    ArrayList<User> loadUserRelationships(String username, String relationship);
    void saveAll();
    void saveUsers();
    void saveRelationships();
    void saveFriends();
    void saveBlocked();
    void saveMessages();
}
