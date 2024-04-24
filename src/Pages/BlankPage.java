package Pages;
import GUI.*;
import Network.Client;

/**
 * Project05 -- ChatPage
 * <p>
 * Creates a blank GUI page.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
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
