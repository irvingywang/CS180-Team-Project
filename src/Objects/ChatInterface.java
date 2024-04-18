package Objects;

import java.util.ArrayList;

/**
 * Project05 - ChatInterface
 * Creates an interface for the chat class.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 **/
public interface ChatInterface {
    boolean addMessage(Message message);
    ArrayList<Message> getMessages();
    ArrayList<User> getMembers();
    String getName();
    boolean isMember(User user);
}