import java.util.ArrayList;

/**
 * Project05 -- GroupChat
 *
 * Represents a group chat with a variable amount of members.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 *
 * @version April 1, 2024
 *
 */
public class GroupChat extends Chat implements GroupChatInterface {

    /**
     * Constructs a new GroupChat object.
     *
     * @param name  - the name of the chat
     * @param users - the list of users to be added to the chat
     */
    public GroupChat(String name, ArrayList<User> users) {
        super(name, users);
    }

    /**
     * Adds a member to the chat.
     *
     * @param user - the user to be added
     * @return true if the user was added, false otherwise
     */
    public boolean addMember(User user) {
        if (super.getMembers().containsKey(user.getUsername())) {
            return false;
        }
        super.getMembers().put(user.getUsername(), user);
        return true;
    }

    /**
     * Removes a member from the chat.
     *
     * @param user - the user to be removed
     * @return true if the user was removed, false otherwise
     */
    public boolean removeMember(User user) {
        if (!super.getMembers().containsKey(user.getUsername())) {
            return false;
        }
        super.getMembers().remove(user.getUsername());
        return true;
    }
}
