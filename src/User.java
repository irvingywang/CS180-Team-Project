import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String displayName;
    private ArrayList<User> friends;

    public User(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.friends = new ArrayList<User>();
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

    public boolean addFriend() {
        // Add a friend to the user's friend list
        return false;
    }
}
