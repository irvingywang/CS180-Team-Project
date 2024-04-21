package Pages;

import GUI.*;
import Network.*;
import Objects.User;

public class LoginPage extends Page implements PageInterface {
    private Label titleLabel;
    private TextField usernameField;
    private TextField passwordField;
    private Button loginButton;
    private Button goBackButton;

    public LoginPage(Client client) {
        super(client);
        initContent();
    }

    @Override
    public void initContent() {
        panel.removeAll();

        titleLabel = new Label("Login to your account", 42);
        usernameField = new TextField("Enter Username", GUIConstants.SIZE_400_40);
        passwordField = new TextField("Enter Password", GUIConstants.SIZE_400_40);
        loginButton = new Button("Login", () -> performLogin(), GUIConstants.SIZE_400_40);
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
        panel.add(passwordField);
        panel.add(new Spacer(40));
        panel.add(loginButton);
        panel.add(new Spacer(10));
        panel.add(goBackButton);

        panel.revalidate();
        panel.repaint();
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password cannot be empty.");
            return;
        }

        client.sendToServer(
                new NetworkMessage(ServerCommand.LOGIN, client.IDENTIFIER, String.format("%s,%s", username, password)));

        NetworkMessage message = client.listenToServer();
        switch ((ClientCommand) message.getCommand()) {
            case LOGIN_SUCCESS -> {
                client.setUser((User) message.getObject());
                window.switchPage(new Menu(client));
            }
            case LOGIN_FAILURE -> {
                showError("Login failed.");
            }
        }
    }
}
