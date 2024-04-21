package Pages;
import GUI.*;
import Network.Client;

public class BlankPage extends Page {
    // Declare components here

    public BlankPage(Client client) {
        super(client);
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
    }
}
