package Network;

import Objects.User;

/**
 * Project05 -- Client.ClientInterface
 *
 * Creates an interface for the client class.
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
public interface ClientInterface {
    void setUser(User user);
    boolean connectToServer();
    boolean sendToServer(NetworkMessage message);
    NetworkMessage readNetworkMessage();
    NetworkMessage listenToServer();
}
