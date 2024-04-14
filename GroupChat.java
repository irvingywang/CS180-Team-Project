import java.lang.reflect.Member;
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
 * @author Karan Vankwani, L08
 *
 * @version April 1, 2024
 *
 */
public class GroupChat extends Chat implements GroupChatInterface {

    private User admin;
    /**
     * Constructs a new GroupChat object.
     *
     * @param name  - the name of the chat
     * @param users - the list of users to be added to the chat
     */
    public GroupChat(String name, ArrayList<User> users, User admin) throws InvalidChatException {
        super(name, users);
        if (!users.contains(admin)) {
            throw new InvalidChatException("Admin must be a member of the chat");
        }
        if (users.size() < 3) {
            throw new InvalidChatException("Group chat must have at least 3 members");
        }
        this.admin = admin;
    }


    /**
     * Adds a member to the chat.
     *
     * @param user - the user to be added
     * @return true if the user was added, false otherwise
     */
    public boolean addMember(User user) {
        if (super.isMember(user)) {
            return false;
        }
        super.getMembers().add(user);
        return true;
    }

    /**
     * Removes a member from the chat.
     *
     * @param user - the user to be removed
     * @return true if the user was removed, false otherwise
     */
    public boolean removeMember(User user) {
        if (!super.isMember(user)) {
            return false;
        }
        super.getMembers().remove(user);
        return true;
    }
}