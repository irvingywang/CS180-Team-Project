package Pages;

import GUI.*;
import Network.Client;
import Objects.User;

public class ViewProfilePage extends Page {
    // Declare components here
    private Label displayNameLabel;
    private Label usernameLabel;
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
