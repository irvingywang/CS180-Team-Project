import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a user which can send and receive messages.
 * A user has a unique username, a password, a display name,
 * a list of friends, a list of blocked users, and a list of messages.
 */
public class User implements UserInterface, Serializable {
    private String username = "invalid";
    private String password = "invalid";
    private String displayName = "invalid";
    private ArrayList<User> friends = new ArrayList<User>();
    private ArrayList<User> blocked = new ArrayList<User>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    private boolean isValid = false;

    /**
     * Constructs a User with username, password, and displayName.
     *
     * @param username    - the username of the user
     * @param password    - the password of the user
     * @param displayName - the display name of the user
     */
    public User(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.friends = new ArrayList<User>();
        this.blocked = new ArrayList<User>();
    }

    /**
     * Default constructor for User.
     */
    public User() {
    }

    /**
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the display name of the user
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the list of friends of the user
     */
    public ArrayList<User> getFriends() {
        return friends;
    }

    /**
     * @return the list of blocked users by the user
     */
    public ArrayList<User> getBlocked() {
        return blocked;
    }

    /**
     * Sets the list of friends for the user.
     * This method is meant to be used when loading friends from the database.
     *
     * @param friends - the list of friends
     */
    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    /**
     * Sets the list of blocked users for the user.
     * This method is meant to be used when loading blocked users from the database.
     *
     * @param blocked - the list of blocked users
     */
    public void setBlocked(ArrayList<User> blocked) {
        this.blocked = blocked;
    }

    /**
     * @return true if the user is valid, false otherwise
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Sets the list of messages for the user.
     *
     * @param messages - the list of messages
     */
    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    /**
     * Adds a friend to the user's friend list.
     *
     * @param friend - the friend to be added
     * @return true if the friend is added, false otherwise
     */
    public boolean addFriend(User friend) {
        if (friends.contains(friend)) {
            return false;
        } else {
            friends.add(friend);
            return true;
        }
    }

    /**
     * Removes a friend from the user's friend list.
     *
     * @param friend - the friend to be removed
     * @return true if the friend is removed, false otherwise
     */
    public boolean removeFriend(User friend) {
        if (friends.contains(friend)) {
            friends.remove(friend);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a user to the blocked list.
     *
     * @param user - the user to be blocked
     * @return true if the user is blocked, false otherwise
     */
    public boolean blockUser(User user) {
        if (blocked.contains(user)) {
            return false;
        } else {
            blocked.add(user);
            return true;
        }
    }

    /**
     * Removes a user from the blocked list.
     *
     * @param user - the user to be unblocked
     * @return true if the user is unblocked, false otherwise
     */
    public boolean unblockUser(User user) {
        if (blocked.contains(user)) {
            blocked.remove(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a user is a friend.
     *
     * @param user - the user to be checked
     * @return true if the user is a friend, false otherwise
     */
    public boolean isFriend(User user) {
        return friends.contains(user);
    }

    /**
     * Checks if a user is blocked.
     *
     * @param user - the user to be checked
     * @return true if the user is blocked, false otherwise
     */
    public boolean isBlocked(User user) {
        return blocked.contains(user);
    }

    /**
     * @return the list of messages of the user
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * Sends a message to a recipient.
     * The message is only sent if the recipient is not blocked.
     *
     * @param recipient - the recipient of the message
     * @param message   - the message to be sent
     * @return true if the message is sent, false otherwise
     */
    public boolean sendMessage(User recipient, String message) {
        if (blocked.contains(recipient)) {
            Database.writeLog(String.format("Message from %s to %s failed: recipient is blocked.",
                    this.username, recipient.getUsername()));
            return false;
        } else {
            Message newMessage = new Message(this, recipient, message);
            this.messages.add(newMessage);
            if (recipient.receiveMessage(newMessage)) { // message received
                Database.writeLog(String.format("Message from %s to %s successfully sent and received.",
                        this.username, recipient.getUsername()));
                return true;
            } else { // message not received
                this.messages.remove(newMessage);
                return false;
            }
        }
    }

    /**
     * Receives a message.
     * The message is only received if the sender is not blocked.
     *
     * @param message - the message to be received
     * @return true if the message is received, false otherwise
     */
    public boolean receiveMessage(Message message) {
        if (!blocked.contains(message.getSender())) {
            this.messages.add(message);
            return true;
        } else {
            Database.writeLog(String.format("Message from %s to %s was blocked.",
                    message.getSender().getUsername(), this.username));
            return false;
        }
    }

    /**
     * @return a string representation of the User
     */
    @Override
    public String toString() {
        return String.format("%s,%s,%s", username, password, displayName);
    }

    /**
     * Checks if this User is equal to another object.
     *
     * @param obj - the object to compare this User to
     * @return true if the object is a User and has the same username,
     * password, and displayName as this User, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            return this.username.equals(user.username) &&
                    this.password.equals(user.password) &&
                    this.displayName.equals(user.displayName);
        }
        return false;
    }
}
