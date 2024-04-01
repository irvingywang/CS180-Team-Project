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
    void initialize();
    void close();
    void connectToClient(String client);
    void sendToClient(String sender, String receiver, String message);
    void receiveFromClient(String sender, String receiver, String message);
    void show(String client, String message);
    void store(String message);
    void restrict(String sender, String message, boolean restricted);
}
