import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project05 -- ChatInterface*
 * Creates an interface for the chat class.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @version April 1, 2024
 **/
public interface ChatInterface {
    boolean addMessage(Message message);
    ArrayList<Message> getMessages();
    ArrayList<User> getMembers();
    String getName();
    boolean isMember(User user);
}