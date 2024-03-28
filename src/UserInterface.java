import java.util.ArrayList;

public interface UserInterface {
    public String getUsername();
    public String getPassword();
    public String getDisplayName();
    public ArrayList<Message> getMessages();
    public boolean addFriend(User friend);
    public boolean removeFriend(User friend);
    public boolean blockUser(User user);
    public boolean unblockUser(User user);
    public boolean isFriend(User user);
    public boolean isBlocked(User user);
    public boolean sendMessage(User recipient, String message);
}
