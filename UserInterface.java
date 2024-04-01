import java.util.ArrayList;

/**
 * Project05 -- UserInterface
 *
 * Creates an interface for the user class.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 *
 * @version April 1, 2024
 *
 */
public interface UserInterface {
    String getUsername();
    String getPassword();
    String getDisplayName();
    boolean addFriend(User friend);
    boolean removeFriend(User friend);
    boolean blockUser(User user);
    boolean unblockUser(User user);
    boolean isFriend(User user);
    boolean isBlocked(User user);
    ArrayList<Message> getMessages();
    boolean sendMessage(User recipient, String message);
    boolean receiveMessage(Message message);
}
