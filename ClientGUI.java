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
    public void showError(String error) {
        JOptionPane.showMessageDialog(null, error);
    }

    @Override
    public void loginPage() { //TODO display everything together on one page
        String username = "";
        String password = "";
        while (username.isEmpty()) {
            username = JOptionPane.showInputDialog("Enter your username:");
            if (username == null || username.isEmpty()) {
                showError("Username cannot be empty.");
            }
        }
        while (password.isEmpty()) {
            password = JOptionPane.showInputDialog("Enter your password:");
            if (password == null || password.isEmpty()) {
                showError("Password cannot be empty.");
            }
        }
        client.sendToServer(String.format("LOGIN INFO: %s %s", username, password));
    }

    @Override
    public void createUser() {
        //TODO Functionality
    }

    @Override
    public void userSearch(String search) {
        String searchInput = JOptionPane.showInputDialog("Enter the name:");
        if (searchInput == null || searchInput.trim().isEmpty()) {
            showError("Search cannot be empty.");
            return;
        }
        client.sendToServer(String.format("SEARCH USER: %s", searchInput.trim()));
    }

    @Override
    public void homePage() {
        //TODO Functionality
    }

    @Override
    public void userInteraction(String choice, String info) {
        //TODO Functionality
    }
}
