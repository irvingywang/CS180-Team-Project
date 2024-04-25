package Network;

import java.net.Socket;

/**
 * Project05 -- Network.ServerInterface
 *
 * Creates an interface for the server class.
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
public interface ServerInterface {
    boolean sendToClient(NetworkMessage message);
    NetworkMessage readMessage();
    void handleCommands(Socket clientSocket);

    void searchUser(NetworkMessage message);

    void login(NetworkMessage message);

    void saveProfile(NetworkMessage message);

    void createUser(NetworkMessage message);

    void createChat(NetworkMessage message);

    void getChats(NetworkMessage message);
}
