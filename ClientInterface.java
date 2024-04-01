import java.util.List;

/**
 * Project05 -- ClientInterface
 *
 * Creates an interface for the client class.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 *
 * @version April 1, 2024
 *
 */
public interface ClientInterface {
    void connectToServer(String server);
    void close();
    void sendToServer(String message);
    void receiveFromServer(String message);
    void confirmation(String message);
    void login(String username, String password);
    void createAccount(String username, String password);
    List<User> search(String info);
    void restrict(String message, boolean restricted);
    void delete(String message);
}
