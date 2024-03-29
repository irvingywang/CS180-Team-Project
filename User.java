import java.util.ArrayList;

public class User implements UserInterface {
    private String username = "invalid";
    private String password = "invalid";
    private String displayName = "invalid";
    private ArrayList<User> friends = new ArrayList<User>();
    private ArrayList<User> blocked = new ArrayList<User>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    private boolean isValid = false;

    public User(String data) {
        try {
            String[] parts = data.split(Database.getDelimiter());
            this.username = parts[0];
            this.password = parts[1];
            this.displayName = parts[2];
            this.isValid = true;
        } catch (Exception e) {
            Database.saveToLog("Failed to create user from data.");
        }
    }

    public User(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.friends = new ArrayList<User>();
        this.blocked = new ArrayList<User>();
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public ArrayList<User> getBlocked() {
        return blocked;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public void setBlocked(ArrayList<User> blocked) {
        this.blocked = blocked;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
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
            Database.saveToLog(String.format("Message from %s to %s failed: recipient is blocked.",
                    this.username, recipient.getUsername()));
            return false;
        } else {
            Message newMessage = new Message(this, recipient, message);
            this.messages.add(newMessage);
            if (recipient.receiveMessage(newMessage)) { // message received
                Database.saveToLog(String.format("Message from %s to %s successfully sent and received.",
                        this.username, recipient.getUsername()));
                return true;
            } else { // message not received
                this.messages.remove(newMessage);
                return false;
            }
        }
    }

    public boolean receiveMessage(Message message) {
        if (!blocked.contains(message.getSender())) {
            this.messages.add(message);
            return true;
        } else {
            Database.saveToLog(String.format("Message from %s to %s was blocked.",
                    message.getSender().getUsername(), this.username));
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("%s%s%s%s%s", username, Database.getDelimiter(),
                password, Database.getDelimiter(), displayName);
    }
}
