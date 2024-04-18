package Pages;

import GUI.*;
import Network.*;
import Objects.User;

import javax.swing.Box;
import java.awt.Dimension;

public class LoginPage extends Page {

    public LoginPage(Client client) {
        super(client);
        frame = new Frame("Login");
    }

    @Override
    public void Content() {
        Panel panel = new Panel();
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

        Button goBackButton = new Button("Go Back", () -> {
            frame.switchPage(new WelcomePage(client));
        });

        //Sizing
        usernameField.setMaximumSize(new Dimension(400, 40));
        passwordField.setMaximumSize(new Dimension(400, 40));
        loginButton.setMaximumSize(new Dimension(400, 40));
        goBackButton.setMaximumSize(new Dimension(400, 40));


        //Spacing
        panel.add(Box.createVerticalStrut(200));
        panel.add(title);
        panel.add(Box.createVerticalStrut(60));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(30));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(goBackButton);

        frame.addComponent(panel);
    }
}
