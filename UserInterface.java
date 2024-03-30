import java.util.ArrayList;

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
