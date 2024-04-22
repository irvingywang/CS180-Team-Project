package Pages;

import GUI.*;
import Network.*;
import Objects.User;

public class EditProfilePage extends Page {
    // Declare components here
    Label titleLabel;
    TextField displayNameField;
    TextField usernameField;
    TextField passwordField;
    Dropdown publicDropdown;
    Button saveButton;
    Button backButton;


    public EditProfilePage(Client client) {
        super(client);
    }

    @Override
    public void initContent() {
        // Initialize components here
        titleLabel = new Label("Edit Profile", 42);
        User user = client.getUser();
        String displayName = client.getUser().getDisplayName();
        displayName = displayName.isEmpty() ? "Enter Display Name" : displayName;
        displayNameField = new TextField(displayName, GUIConstants.SIZE_400_40);
        usernameField = new TextField(user.getUsername(), GUIConstants.SIZE_400_40);
        passwordField = new TextField(user.getPassword(), GUIConstants.SIZE_400_40);
        String[] privacy = user.isPublicProfile() ? new String[]{"Public", "Private"} : new String[]{"Private", "Public"};
        publicDropdown = new Dropdown(privacy, GUIConstants.SIZE_400_40);
        saveButton = new Button("Save", () -> saveAction(), GUIConstants.SIZE_400_40);
        backButton = new Button("Back to menu", () -> window.switchPage(new MainMenu(client)), GUIConstants.SIZE_400_40, true);

        addComponents();
    }

    @Override
    public void addComponents() {
        // Add components to panel here
        panel.add(new Spacer(180));
        panel.add(titleLabel);
        panel.add(new Spacer(40));
        panel.add(displayNameField);
        panel.add(new Spacer(10));
        panel.add(usernameField);
        panel.add(new Spacer(10));
        panel.add(passwordField);
        panel.add(new Spacer(10));
        panel.add(publicDropdown);
        panel.add(new Spacer(40));
        panel.add(saveButton);
        panel.add(new Spacer(10));
        panel.add(backButton);

        panel.revalidate();
    }

    private void saveAction() {
        String displayName = displayNameField.getText();
        displayName = displayName.isEmpty() ? client.getUser().getDisplayName() : displayName;
        String username = usernameField.getText();
        username = username.isEmpty() ? client.getUser().getUsername() : username;
        String password = passwordField.getText();
        password = password.isEmpty() ? client.getUser().getPassword() : password;
        String privacy = (String) publicDropdown.getSelectedItem();

        client.sendToServer(
                new NetworkMessage(ServerCommand.SAVE_PROFILE, client.IDENTIFIER,
                        String.format("%s,%s,%s,%s", displayName, username, password, privacy)));

        NetworkMessage response = client.listenToServer();
        switch ((ClientCommand) response.getCommand()) {
            case SAVE_PROFILE_SUCCESS -> {
                client.setUser((User) response.getObject());
                showError("Profile saved.");
            }
            case SAVE_PROFILE_FAILURE -> {
                showError("Error. User already exists.");
            }
        }

        //FIXME bug where new info is not updated
    }
}
