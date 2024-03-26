import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String displayName;
    private ArrayList<User> friends;
    private ArrayList<User> blocked;
    private static ArrayList<Message> messages = new ArrayList<Message>();

    public User(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.friends = new ArrayList<User>();
        this.blocked = new ArrayList<User>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean addFriend(User friend) {
        if (friends.contains(friend)) {
            return false;
        } else {
            friends.add(friend);
            return true;
        }
    }

    public boolean removeFriend(User friend) {
        if (friends.contains(friend)) {
            friends.remove(friend);
            return true;
        } else {
            return false;
        }
    }

    public boolean blockUser(User user) {
        if (blocked.contains(user)) {
            return false;
        } else {
            blocked.add(user);
            return true;
        }
    }

    public boolean unblockUser(User user) {
        if (blocked.contains(user)) {
            blocked.remove(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean isFriend(User user) {
        return friends.contains(user);
    }

    public boolean isBlocked(User user) {
        return blocked.contains(user);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public boolean sendMessage(User recipient, String message) {
        if (blocked.contains(recipient)) {
            return false;
        } else {
            Message newMessage = new Message(this, recipient, message);
            messages.add(newMessage);
            recipient.receiveMessage(newMessage);
            Database.writeMessages();
            return true;
        }
    }

    public boolean receiveMessage(Message message) {
        if (blocked.contains(message.getSender())) {
            return false;
        } else {
            messages.add(message);
            Database.writeMessages();
            return true;
        }
    }

//    @Override
//    public String toString() {
//        return String.format("<User: %s, %s, %s>", username, password, displayName);
//    }
}
