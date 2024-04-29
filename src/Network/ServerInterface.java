package Network;

import Objects.Chat;
import Objects.User;

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
    void login(NetworkMessage message);
    void createUser(NetworkMessage message);
    void getChats(NetworkMessage message);
    void searchUser(NetworkMessage message);
    void createChat(NetworkMessage message);
    //void viewChat(NetworkMessage message, User sender, Chat chat);
    void saveProfile(NetworkMessage message);
}
