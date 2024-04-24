package Pages;

import GUI.*;
import Network.Client;
import Objects.User;

/**
 * Project05 -- ChatPage
 * <p>
 * Creates page that is shown after View Profile button
 * is clicked on the main menu.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
public class ViewProfilePage extends Page {
    // Declare components here
    Label displayNameLabel;
    Label usernameLabel;
    Button friendButton;
    Button blockButton;
    Button backButton;
    User user;

    public ViewProfilePage(Client client, User user) {
        super(client);
        this.user = user;
    }

    @Override
    public void initContent() {
        // Initialize components here
        if (user.isPublicProfile()) {
            displayNameLabel = new Label(user.getDisplayName(), 42);
        } else {
            displayNameLabel = new Label("Private Profile", 42);
        }
        usernameLabel = new Label("@" + user.getUsername(), 24);
        friendButton = new Button("Add Friend", () -> friendAction(), GUIConstants.SIZE_400_40);
        blockButton = new Button("Block User", () -> blockAction(), GUIConstants.SIZE_400_40, true);
        backButton = new Button("Back to Search", () -> window.switchPage(new SearchUsersPage(client)), GUIConstants.SIZE_400_40, true);

        addComponents();
    }

    @Override
    public void addComponents() {
        // Add components to panel here
        panel.add(new Spacer(200));
        panel.add(displayNameLabel);
        panel.add(new Spacer(40));
        panel.add(usernameLabel);
        panel.add(new Spacer(40));
        panel.add(friendButton);
        panel.add(new Spacer(10));
        panel.add(blockButton);
        panel.add(new Spacer(10));
        panel.add(backButton);

        panel.revalidate();
    }

    private void friendAction() {
        client.getUser().addFriend(user);
        showError("Friend added.");
    }

    private void blockAction() {
        client.getUser().blockUser(user);
        showError("User blocked.");
    }
}
