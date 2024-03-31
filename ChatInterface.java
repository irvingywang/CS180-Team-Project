import java.util.ArrayList;
import java.util.HashMap;

public interface ChatInterface {
    public boolean addMessage(User sender, Message message);
    public ArrayList<Message> getMessages();
    public HashMap<String, User> getMembers();
    public boolean isMember(User user);
}
