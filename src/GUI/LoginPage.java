package GUI;

import Network.Client;
import Network.ClientCommand;
import Network.NetworkMessage;
import Network.ServerCommand;
import Objects.User;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends Page {

    public LoginPage(Client client) {
        super(client);
        frame = new Frame("Login");
    }

    @Override
    public void Content() {
        Panel panel = new Panel(new GridLayout(3, 1));

        TextField usernameField = new TextField("Username");
        TextField passwordField = new TextField("Password");

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

        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(loginButton);

        frame.addComponent(panel);
    }
}
