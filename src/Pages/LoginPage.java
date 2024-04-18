package Pages;

import GUI.*;
import Network.*;
import Objects.User;

import javax.swing.*;
import java.awt.Dimension;

public class LoginPage extends Page {
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
        titleLabel = new Label("Login to your account", 35);
        usernameField = new TextField("Enter Username:");
        passwordField = new TextField("Enter Password:");

        loginButton = new Button("Login", () -> {
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
                    showError("Login successful.");
                }
                case LOGIN_FAILURE -> {
                    showError("Login failed.");
                }
            }
        });

        goBackButton = new Button("Go Back", () -> {
            window.switchPage(new WelcomePage(client));
        });

        // Sizing
        usernameField.setMaximumSize(new Dimension(400, 40));
        passwordField.setMaximumSize(new Dimension(400, 40));
        loginButton.setMaximumSize(new Dimension(400, 40));
        goBackButton.setMaximumSize(new Dimension(400, 40));

        // Spacing
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
    }
}
