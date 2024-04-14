import java.util.ArrayList;

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
    private String id; //For now the name is the id
    private String name;
    private ArrayList<User> members; //username, user
    private ArrayList<Message> messages; //message

    /**
     * Constructs a new Chat object.
     *
     * @param name  - the name of the chat
     * @param members - the list of users to be added to the chat
     */
    public Chat(String name, ArrayList<User> members) {
        this.name = name;
        this.members = members;
        messages = new ArrayList<>();
    }

    /**
     * Adds a message to the chat.
     *
     * @param message - the message to be added
     */
    @Override
    public boolean addMessage(Message message) {
        if (this.isMember(message.getSender())) {
            messages.add(message);
            return true;
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
        return messages;
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
     * Returns the name of the chat.
     *
     * @return the name of the chat
     */
    @Override
    public String getName() {
        return name;
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

    @Override
    public String toString() {
        return String.format("Chat: %s, Member count: %d", name, members.size());
    }
}