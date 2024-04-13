import javax.swing.*;
import java.awt.*;

public class ClientGUI implements GUIInterface {
    private Client client;

    public ClientGUI(Client client) {
        this.client = client;
    }

    //TODO create standard window

    @Override
    public void welcomePage() {
        JOptionPane.showMessageDialog(null, "Welcome to App!");
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public void loginPage() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new GridLayout(3, 1, 10, 10));

            TextField usernameField = new TextField("Username");
            TextField passwordField = new TextField("Password");

            GUI.Button loginButton = new GUI.Button("Login", () -> {
                String username = usernameField.getText();
                String password = passwordField.getText();
                if (username.isEmpty() || password.isEmpty()) {
                    showError("Username and password cannot be empty.");
                    return;
                }
                client.sendToServer(
                        new NetworkMessage(ServerCommand.LOGIN, Client.IDENTIFIER, String.format("%s,%s", username, password)));

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
            frame.add(usernameField);
            frame.add(passwordField);
            frame.add(loginButton);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    @Override
    public void createUser() { //TODO Creates the user
        String username = JOptionPane.showInputDialog("Enter username:");
        if (username == null || username.trim().isEmpty()) {
            showError("Username cannot be empty.");
            return;
        }
        String password = JOptionPane.showInputDialog("Enter new password:");
        if (password == null || password.trim().isEmpty()) {
            showError("Password cannot be empty.");
            return;
        }
        client.sendToServer(
                new NetworkMessage(ServerCommand.CREATE_USER, Client.IDENTIFIER, String.format("%s,%s", username, password)));
        JOptionPane.showMessageDialog(null, "Successful!");
    }

    @Override
    public void userSearch(String search) { //TODO Finds user with search function
        String searchInput = JOptionPane.showInputDialog("Enter the name:");
        if (searchInput == null || searchInput.trim().isEmpty()) {
            showError("Search cannot be empty.");
            return;
        }
        client.sendToServer(
                new NetworkMessage(ServerCommand.SEARCH_USER, Client.IDENTIFIER, searchInput));
    }

    @Override
    public void homePage() { //TODO Main page of the app
        String[] options = {"User Search", "Create User", "Log Out"};
        int choice = JOptionPane.showOptionDialog(null, "Choose option", "Yap", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            userSearch("");
        } else if (choice == 1) {
            createUser();
        } else if (choice == 2) {
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(null, "Nothing selected");
        }
    }
    @Override
    public void userInteraction(String choice, String info) { //TODO Functionality

    }
}