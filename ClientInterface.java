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
    boolean connectToServer();
    void sendToServer(String message);
}
