package Database;

import Objects.Chat;
import Objects.User;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Project05 -- DatabaseContainer
 *
 * This class represents a container for a database, which holds users and their chats.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 *
 * @version April 14, 2024
 *
 */

public class DatabaseContainer implements Serializable {
    private ConcurrentHashMap<String, User> users;
    private ConcurrentHashMap<String, Chat> chats;

    /**
     * Constructs a new DatabaseContainer with the given users and chats.
     *
     * @param users The map of users to be stored.
     * @param chats The map of chats to be stored.
     */
    public DatabaseContainer(ConcurrentHashMap<String, User> users, ConcurrentHashMap<String, Chat> chats) {
        this.users = users;
        this.chats = chats;
    }
    
    /**
     * Gets the map of users stored in this container.
     *
     * @return The map of users.
     */
    public ConcurrentHashMap<String, User> getUsers() {
        return users;
    }

    /**
     * Gets the map of chats stored in this container.
     *
     * @return The map of chats.
     */
    public ConcurrentHashMap<String, Chat> getChats() {
        return chats;
    }
}
