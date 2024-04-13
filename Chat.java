import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project05 -- Chat
 * <p>
 * Represents a chat which has a list of members and messages.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @version April 1, 2024
 */
public class Chat implements ChatInterface {
    private String id;
    private String name;
    private ArrayList<User> members; //username, user
    private HashMap<Message, User> messages;

    /**
     * Constructs a new Chat object.
     *
     * @param name  - the name of the chat
     * @param users - the list of users to be added to the chat
     */
    public Chat(String name, ArrayList<User> users) {
        this.name = name;
        members = new ArrayList<>();
        messages = new HashMap<>();
        for (User user : users) {
            members.add(user);
        }
    }

    /**
     * Adds a message to the chat.
     *
     * @param sender  - the sender of the message
     * @param message - the message to be added
     * @return true if the message was added, false otherwise
     */
    @Override
    public boolean addMessage(User sender, Message message) {

        for (User e : members) {
            if (sender.getUsername().equals(sender.getUsername())) {
                messages.put(message, sender);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the list of messages in the chat.
     *
     * @return the list of messages
     */
    @Override
    public ArrayList<Message> getMessages() {
        ArrayList<Message> list = new ArrayList<Message>();

        for (Message e : messages.keySet()) {
            list.add(e);
        }

        return list;
    }

    /**
     * Returns the map of members in the chat.
     *
     * @return the map of members
     */
    @Override
    public ArrayList<User> getMembers() {
        return members;
    }

    /**
     * Checks if a user is a member of the chat.
     *
     * @param user - the user to check
     * @return true if the user is a member, false otherwise
     */
    @Override
    public boolean isMember(User user) {
        for (User e : members) {
            if (e.getUsername().equals(user.getUsername())) return true;
        }

        return false;
    }
}