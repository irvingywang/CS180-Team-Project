import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseContainer implements Serializable {
    private ConcurrentHashMap<String, User> users;
    private ConcurrentHashMap<String, Chat> chats;

    public DatabaseContainer(ConcurrentHashMap<String, User> users, ConcurrentHashMap<String, Chat> chats) {
        this.users = users;
        this.chats = chats;
    }

    public ConcurrentHashMap<String, User> getUsers() {
        return users;
    }

    public ConcurrentHashMap<String, Chat> getChats() {
        return chats;
    }
}
