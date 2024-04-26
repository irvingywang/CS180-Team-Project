package GUI;

import Network.Client;

import javax.swing.*;

/**
 * Project05 -- Page
 * <p>
 * Creates a framework for a GUI page.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
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
