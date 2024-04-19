package Pages;
import GUI.Page;
import Network.Client;

public class BlankPage extends Page {
    // Declare components here

    public BlankPage(Client client) {
        super(client);
        initContent();
    }

    @Override
    public void initContent() {
        // Initialize components here
        addComponents();
    }

    @Override
    public void addComponents() {
        // Add components to panel here

        panel.revalidate();
        panel.repaint();
    }
}
