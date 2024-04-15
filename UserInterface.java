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
 * @author Karan Vankwani, L08
 *
 * @version April 14, 2024
 *
 */
public interface UserInterface {
    String getUsername();
    String getPassword();
    String getDisplayName();
    boolean isPublicProfile();
    boolean isFriend(User user);
    boolean isBlocked(User user);
    boolean addFriend(User friend);
    boolean removeFriend(User friend);
    boolean blockUser(User user);
    boolean unblockUser(User user);
    boolean sendMessage(Message message);
    void setUsername(String username);
    void setPassword(String password);
    void setDisplayName(String displayName);
    void setPublicProfile(Boolean publicProfile);
}
