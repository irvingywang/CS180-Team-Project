package Pages;

import GUI.*;
import Network.*;
import Objects.User;

import javax.swing.*;
import java.awt.Dimension;

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

        titleLabel = new Label("Login to your account", 35);
        usernameField = new TextField("Enter Username:");
        passwordField = new TextField("Enter Password:");
        loginButton = new Button("Login", () -> performLogin());
        goBackButton = new Button("Go back", () -> window.switchPage(new WelcomePage(client)));

        setupComponents();
    }

    @Override
    public void setupComponents() {
        usernameField.setMaximumSize(new Dimension(400, 40));
        passwordField.setMaximumSize(new Dimension(400, 40));
        loginButton.setMaximumSize(new Dimension(400, 40));
        goBackButton.setMaximumSize(new Dimension(400, 40));

        panel.add(Box.createVerticalStrut(200));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(60));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(30));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(goBackButton);

        panel.revalidate();
        panel.repaint();
    }

    private void performLogin() {
        String username = usernameField.getText().equals(usernameField.getPlaceholder()) ? "" : usernameField.getText();
        String password = passwordField.getText().equals(usernameField.getPlaceholder()) ? "" : passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password cannot be empty.");
            return;
        }

        client.sendToServer(
                new NetworkMessage(ServerCommand.LOGIN, client.IDENTIFIER, String.format("%s,%s", username, password))
        );

        NetworkMessage message = client.listenToServer();
        switch ((ClientCommand) message.getCommand()) {
            case LOGIN_SUCCESS -> {
                client.setUser((User) message.getObject());
                showError("Login successful.");
            }
            case LOGIN_FAILURE -> {
                showError("Login failed.");
            }
        }
    }
}
