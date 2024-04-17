package GUI;

import Network.Client;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class Page {
    Client client;
    Frame frame;

    public Page(Client client) {
        this.client = client;
        SwingUtilities.invokeLater(() -> Content());
    }

    public void Content() {
        // This method will be overridden by subclasses
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
