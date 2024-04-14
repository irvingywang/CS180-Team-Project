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

    @Override
    public boolean sendMessage(Message message) {
        return message.getChat().addMessage(message);
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public void setPublicProfile(Boolean publicProfile) {
        this.publicProfile = publicProfile;
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
