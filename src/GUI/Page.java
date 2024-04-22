package GUI;

import Network.Client;

import javax.swing.*;

public class Page implements PageInterface {
    public Client client;
    public Window window;
    public Panel panel;

    public Page(Client client) {
        this.client = client;
        window = Window.getInstance();
        panel = new Panel();
        SwingUtilities.invokeLater(() -> initContent());
    }

    public void initContent() {
        // Override this method
    }

    public void addComponents() {
        // Override this method
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public JPanel getPanel() {
        return panel;
    }
}
