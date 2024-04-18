package Pages;

import GUI.*;
import Network.*;
import Objects.User;

import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Component;
import java.awt.Dimension;

public class LoginPage extends Page {

    public LoginPage(Client client) {
        super(client);
        frame = new Frame("Login");
    }

    @Override
    public void Content() {
        Panel panel = new Panel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Label title = new Label("Login to your account", 35);

        TextField usernameField = new TextField("Enter Username:");
        TextField passwordField = new TextField("Enter Password:");

        Button loginButton = new Button("Login", () -> {
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

        //Sizing
        usernameField.setMaximumSize(new Dimension(400, 40));
        passwordField.setMaximumSize(new Dimension(400, 40));
        loginButton.setMaximumSize(new Dimension(400, 40));

        //Alignment
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Spacing
        panel.add(Box.createVerticalStrut(250));
        panel.add(title);
        panel.add(Box.createVerticalStrut(60));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(15));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(30));
        panel.add(loginButton);

        frame.addComponent(panel);
    }
}
