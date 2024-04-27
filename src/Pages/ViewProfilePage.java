package Pages;

import GUI.*;
import Network.Client;
import Objects.User;

/**
 * Project05 -- ViewProfilePage
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
    private Label displayNameLabel;
    private Label usernameLabel;
    private Label statusLabel;
    private Label invalidLabel;
    private Button friendButton;
    private Button blockButton;
    private Button backButton;
    private User user;

    public ViewProfilePage(Client client, User user) {
        super(client);
        this.user = user;
    }

    @Override
    public void initContent() {

        backButton = new Button("Back to Search", () -> window.switchPage(new SearchUsersPage(client)), GUIConstants.SIZE_400_40, true);

        if (user.isBlocked(client.getUser())) {
            invalidLabel = new Label("User not found", 20);
        } else {
            // Initialize components here
            displayNameLabel = new Label(user.getDisplayName(), 42);
            usernameLabel = new Label("@" + user.getUsername(), 24);
            if (user.isPublicProfile()) {
                statusLabel = new Label(user.getStatus(), 20);
            } else {
                if (client.getUser().isFriend(user))
                    statusLabel = new Label(user.getStatus(), 20);
                else
                    statusLabel = new Label("Add them as a friend to see their status!", 20);
            }
            friendButton = new Button("Add Friend", () -> friendAction(), GUIConstants.SIZE_400_40);
            blockButton = new Button("Block User", () -> blockAction(), GUIConstants.SIZE_400_40, true);
        }
        addComponents();
    }

    @Override
    public void addComponents() {
        // Add components to panel here


        if (user.isBlocked(client.getUser())) {
            panel.add(new Spacer(300));
            panel.add(invalidLabel);
            panel.add(new Spacer(10));
            panel.add(backButton);
        } else {
            panel.add(new Spacer(180));
            panel.add(displayNameLabel);
            panel.add(new Spacer(20));
            panel.add(usernameLabel);
            panel.add(new Spacer(40));
            panel.add(statusLabel);
            panel.add(new Spacer(60));
            panel.add(friendButton);
            panel.add(new Spacer(10));
            panel.add(blockButton);
            panel.add(new Spacer(10));
            panel.add(backButton);
        }

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