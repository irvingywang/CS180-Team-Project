import java.util.ArrayList;

public class GroupChat extends Chat {

    public GroupChat(String name, ArrayList<User> users) {
        super(name, users);
    }

    public boolean addMember(User user) {
        if (super.getMembers().containsKey(user.getUsername())) {
            return false;
        }
        super.getMembers().put(user.getUsername(), user);
        return true;
    }

    public boolean removeMember(User user) {
        if (!super.getMembers().containsKey(user.getUsername())) {
            return false;
        }
        super.getMembers().remove(user.getUsername());
        return true;
    }
}
