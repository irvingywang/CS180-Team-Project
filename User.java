import java.io.Serializable;
import java.util.ArrayList;

/**
 * Project05 -- User
 *
 * Represents a user which can send and receive messages.
 * A user has a unique username, a password, a display name,
 * a list of friends, a list of blocked users, and a list of messages.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 *
 * @version April 1, 2024
 *
 */
public class User implements UserInterface, Serializable {
    private String username;
    private String password;
    private String displayName;
    private Boolean publicProfile;
    private ArrayList<User> friends;
    private ArrayList<User> blocked;
    private ArrayList<Message> messages;
    private String identifier;

    /**
     * Constructs a new User object
     *
     * @param username    - the username of the user
     * @param password    - the password of the user
     * @param displayName - the display name of the user
     * @param publicProfile    - the public status of the user
     */
    public User(String username, String password, String displayName, Boolean publicProfile) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.publicProfile = publicProfile;
        this.friends = new ArrayList<User>();
        this.blocked = new ArrayList<User>();
        this.messages = new ArrayList<Message>();
        this.identifier = "USER " + username;
    }

    /**
     * @return the username of the user
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * @return the password of the user
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * @return the display name of the user
     */
    @Override
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return true if the user profile is public, false otherwise
     */
    @Override
    public boolean isPublicProfile() {
        return publicProfile;
    }

    /**
     * Checks if a user is a friend.
     *
     * @param user - the user to be checked
     * @return true if the user is a friend, false otherwise
     */
    @Override
    public boolean isFriend(User user) {
        return friends.contains(user);
    }

    /**
     * Checks if a user is blocked.
     *
     * @param user - the user to be checked
     * @return true if the user is blocked, false otherwise
     */
    @Override
    public boolean isBlocked(User user) {
        return blocked.contains(user);
    }

    /**
     * Adds a friend to the user's friend list.
     *
     * @param friend - the friend to be added
     * @return true if the friend is added, false otherwise
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
    public boolean unblockUser(User user) {
        if (blocked.contains(user)) {
            blocked.remove(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the list of messages of the user
     */
    @Override
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
    @Override
    public boolean sendMessage(User recipient, String message) {
        if (blocked.contains(recipient)) {
            Database.writeLog(LogType.WARNING, identifier,
                    String.format("Message from %s to %s failed: recipient is blocked.", this.username, recipient.getUsername()));
            return false;
        }
        if (!recipient.isPublicProfile() && !recipient.isFriend(this)) {
            Database.writeLog(LogType.WARNING, identifier,
                    String.format("Message from %s to %s failed: recipient is not a friend.",
                            this.username, recipient.getUsername()));
            return false;
        }

        Message newMessage = new Message(this, recipient, message);
        this.messages.add(newMessage);
        if (recipient.receiveMessage(newMessage)) { // message received
            Database.writeLog(LogType.INFO, username,
                    String.format("Message from %s to %s successfully sent and received.",
                            this.username, recipient.getUsername()));
            return true;
        } else { // message not received
            this.messages.remove(newMessage);
            return false;
        }

    }

    /**
     * Receives a message.
     * The message is only received if the sender is not blocked.
     *
     * @param message - the message to be received
     * @return true if the message is received, false otherwise
     */
    @Override
    public boolean receiveMessage(Message message) {
        if (blocked.contains(message.getSender())) {
            Database.writeLog(LogType.WARNING, identifier,
                    String.format("Message from %s to %s was blocked.",
                    message.getSender().getUsername(), this.username));
            return false;
        }
        if (!this.publicProfile && !this.isFriend(message.getSender())) {
            Database.writeLog(LogType.WARNING, identifier,
                    String.format("Message from %s to %s failed: sender is not a friend.",
                    message.getSender().getUsername(), this.username));
            return false;
        }
        this.messages.add(message);
        return true;
    }

    /**
     * @return a string representation of the User
     */
    @Override
    public String toString() {
        return String.format("User %s,\"%s\"", username, displayName);
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
