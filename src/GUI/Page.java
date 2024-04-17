package GUI;

import javax.swing.*;
import Network.Client;

public class Page {
    Client client;

    public Page(Client client) {
        this.client = client;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
