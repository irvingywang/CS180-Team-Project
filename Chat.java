import java.util.ArrayList;
import java.util.HashMap;

public class Chat implements ChatInterface {
    private String id;
    private String name;
    private HashMap<String, User> members; //username, user
    private ArrayList<Message> messages;

    public Chat(String name, ArrayList<User> users) {
        this.name = name;
        members = new HashMap<>();
        messages = new ArrayList<>();
        for (User user : users) {
            members.put(user.getUsername(), user);
        }
    }

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

    @Override
    public ArrayList<Message> getMessages() {
        return messages;
    }

    @Override
    public HashMap<String, User> getMembers() {
        return members;
    }

    @Override
    public boolean isMember(User user) {
        return members.containsKey(user.getUsername());
    }
}
