import javax.swing.*;

public class ClientGUI implements GUIInterface {
    private Client client;

    public ClientGUI(Client client) {
        this.client = client;
    }

    @Override
    public void welcomePage() {
        JOptionPane.showMessageDialog(null, "Welcome to App!");
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public void loginPage() { //TODO display everything together on one page
        String username = "";
        String password = "";
        while (username.isEmpty()) {
            username = JOptionPane.showInputDialog("Enter your username:");
            if (username == null || username.isEmpty()) {
                showError("Username cannot be empty.");
                return;
            }
        }
        while (password.isEmpty()) {
            password = JOptionPane.showInputDialog("Enter your password:");
            if (password == null || password.isEmpty()) {
                showError("Password cannot be empty.");
                return;
            }
        }
        client.sendToServer(
                new NetworkMessage(ServerCommand.LOGIN, Client.IDENTIFIER, String.format("%s,%s", username, password)));
        NetworkMessage message = client.listenToServer();
        switch ((ClientCommand) message.getCommand()) {
            case LOGIN_SUCCESS -> {
                client.setUser((User) message.getMessage());
                showError("Login successful.");
            }
            case LOGIN_FAILURE -> {
                showError("Login failed.");
            }
        }
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