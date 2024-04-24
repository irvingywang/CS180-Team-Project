package Pages;
import GUI.*;
import Network.*;


/**
 * Project05 -- CreateUserPage
 * <p>
 * Creates page that is shown after Create Account button
 * is clicked on the welcome page.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
public class CreateUserPage extends Page {
    private Label titleLabel;
    private TextField usernameField;
    private TextField displayNameField;
    private TextField passwordField;
    private Button createButton;
    private Button backButton;


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
        backButton = new Button("Go back", () -> window.switchPage(new WelcomePage(client)), GUIConstants.SIZE_400_40, true);

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
        panel.add(backButton);

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
