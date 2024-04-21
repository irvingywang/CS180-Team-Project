package Pages;

import GUI.*;
import Network.Client;

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
        displayNameField = new TextField("Enter Display Name", GUIConstants.SIZE_400_40);
        usernameField = new TextField("Enter Username", GUIConstants.SIZE_400_40);
        passwordField = new TextField("Enter Password", GUIConstants.SIZE_400_40);
        publicDropdown = new Dropdown(new String[]{"Public", "Private"}, GUIConstants.SIZE_400_40);
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
        String username = usernameField.getText();
        String password = passwordField.getText();
        String privacy = (String) publicDropdown.getSelectedItem();

        System.out.println("Saving profile: " + displayName + " " + username + " " + password + " " + privacy);
    }
}
