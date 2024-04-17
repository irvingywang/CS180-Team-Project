package GUI;

import Network.Client;
import Network.ClientCommand;
import Network.NetworkMessage;
import Network.ServerCommand;
import Objects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.TextField;

public class LoginPage extends Page {

    public LoginPage(Client client) {
        super(client);

        SwingUtilities.invokeLater(() -> {
            GUI.Frame frame = new GUI.Frame("Login");
            JPanel panel = new JPanel(new GridLayout(3, 1, 20, 20));

            java.awt.TextField usernameField = new java.awt.TextField("Username");
            java.awt.TextField passwordField = new TextField("Password");

            GUI.Button loginButton = new GUI.Button("Login", () -> {
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
        });
    }
}
