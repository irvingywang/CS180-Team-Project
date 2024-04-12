/**
 * Project05 -- ServerInterface
 *
 * Creates an interface for the server class.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 *
 * @version April 1, 2024
 *
 */
public interface ServerInterface {
    boolean sendToClient(String message);

    boolean connectToClient();

    boolean login(String username, String password);
    boolean createUser(String username, String password, String displayName, Boolean publicProfile);
}
