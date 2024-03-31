import java.util.ArrayList;
import java.util.HashMap;
/**
 * Represents a chat which has a list of members and messages.
 */
public class Chat implements ChatInterface {
    private String id;
    private String name;
    private HashMap<String, User> members; //username, user
    private ArrayList<Message> messages;

    /**
     * Constructs a new Chat object.
     *
     * @param name  - the name of the chat
     * @param users - the list of users to be added to the chat
     */
    public Chat(String name, ArrayList<User> users) {
        this.name = name;
        members = new HashMap<>();
        messages = new ArrayList<>();
        for (User user : users) {
            members.put(user.getUsername(), user);
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
        if (!members.containsKey(sender.getUsername())) {
            return false;
        }
        for (User user : members.values()) {
            if (user.getUsername().equals(sender.getUsername())) {
                continue;
            }
            user.receiveMessage(message);
        }
        return true;
    }

    /**
     * Returns the list of messages in the chat.
     *
     * @return the list of messages
     */
    @Override
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * Returns the map of members in the chat.
     *
     * @return the map of members
     */
    @Override
    public HashMap<String, User> getMembers() {
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
        return members.containsKey(user.getUsername());
    }
}
