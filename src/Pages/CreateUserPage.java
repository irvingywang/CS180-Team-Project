package Pages;
import GUI.*;
import Network.*;

public class CreateUserPage extends Page {
    private Label titleLabel;
    private TextField usernameField;
    private TextField displayNameField;
    private TextField passwordField;
    private Button createButton;
    private Button goBackButton;


    public CreateUserPage(Client client) {
        super(client);
        initContent();
    }

    @Override
    public void initContent() {
        panel.removeAll();

        titleLabel = new Label("Create a new account", 42);
        usernameField = new TextField("Username", GUIConstants.SIZE_400_40);
        displayNameField = new TextField("Display name", GUIConstants.SIZE_400_40);
        passwordField = new TextField("Enter password", GUIConstants.SIZE_400_40);
        createButton = new Button("Create", () -> performCreate(), GUIConstants.SIZE_400_40);
        goBackButton = new Button("Go back", () -> window.switchPage(new WelcomePage(client)), GUIConstants.SIZE_400_40, true);

        addComponents();
    }

    @Override
    public void addComponents() {
        panel.add(new Spacer(200));
        panel.add(titleLabel);
        panel.add(new Spacer(40));
        panel.add(usernameField);
        panel.add(new Spacer(10));
        panel.add(displayNameField);
        panel.add(new Spacer(10));
        panel.add(passwordField);
        panel.add(new Spacer(40));
        panel.add(createButton);
        panel.add(new Spacer(10));
        panel.add(goBackButton);

        panel.revalidate();
        panel.repaint();
    }

    public void performCreate() {
        String username = usernameField.getText();
        String displayName = displayNameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password cannot be empty.");
            return;
        }

        if (username.contains(" ")) {
            showError("Username cannot contain spaces.");
            return;
        }

        client.sendToServer(
                new NetworkMessage(ServerCommand.CREATE_USER, Client.IDENTIFIER, String.format("%s,%s,%s", username, password, displayName)));

        NetworkMessage message = client.listenToServer();
        switch ((ClientCommand) message.getCommand()) {
            case CREATE_USER_SUCCESS -> {
                showError("Account created. You may now log in.");
                window.switchPage(new WelcomePage(client));
            }
            case CREATE_USER_FAILURE -> {
                showError("Username already exists. Please try again.");
            }
        }
    }
}
