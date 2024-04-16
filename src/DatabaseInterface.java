import java.util.ArrayList;

/**
 * Project05 -- DatabaseInterface
 *
 * Creates an interface for the database class.
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
public interface DatabaseInterface {
    void initialize();
    void close();
    void reset();
    User getUser(String username);
    ArrayList<User> getUsers();
    void addUser(User user);
    void removeUser(String username);
    void loadDatabase();
    void serializeDatabase();
}
