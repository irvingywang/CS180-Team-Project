package Network;

import javax.swing.*;

/**
 * Project05 - ClientGUI.java
 *
 * Stores the success/failure of commands from the ServerCommand class.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 *
 * @version April 14, 2024
 *
 */
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
    public void createUser() {
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
    public void userSearch(String search) {
        String searchInput = JOptionPane.showInputDialog("Enter the name:");
        if (searchInput == null || searchInput.trim().isEmpty()) {
            showError("Search cannot be empty.");
            return;
        }
        client.sendToServer(
                new NetworkMessage(ServerCommand.SEARCH_USER, Client.IDENTIFIER, searchInput));
    }

    @Override
    public void homePage() {
        String[] options = {"Objects.User Search", "Create Objects.User", "Log Out"};
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

    public void sendMessage()
    {
        String info = JOptionPane.showInputDialog("Enter name of the chat: ");
        String text = JOptionPane.showInputDialog("Enter message: ");

        userSearch(info);

        client.sendToServer(
            new NetworkMessage(ServerCommand.SEND_MESSAGE, Client.IDENTIFIER, text));
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}