package Pages;

import GUI.*;
import Network.Client;
import java.awt.FlowLayout;

/**
 * A page to display search results to the user and allow user management actions.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
public class SearchResultsPage extends Page {
    private String[] results;

    public SearchResultsPage(Client client, String[] results) {
        super(client);
        this.results = results;
    }

    @Override
    public void initContent() {
        // Initialize components here
        Label titleLabel = new Label("User Search Results", 42);
        panel.add(new Spacer(20));
        panel.add(titleLabel);
        panel.add(new Spacer(20));

        if (results != null && results.length > 0) {
            for (String username : results) {
                addResultEntry(username);
            }
        } else {
            panel.add(new Label("No results found.", 24));
        }

        Button backButton = new Button("Back", () -> window.switchPage(new SearchUsersPage(client)), GUIConstants.SIZE_400_40, true);
        panel.add(new Spacer(20));
        panel.add(backButton);

        panel.revalidate();
    }

    private void addResultEntry(String username) {
        Panel userPanel = new Panel(new FlowLayout());
        Label userLabel = new Label(username, 24);
        userPanel.add(userLabel);

        Button addButton = new Button("Add", () -> addUser(username), GUIConstants.SIZE_200_40);
        Button deleteButton = new Button("Delete", () -> deleteUser(username), GUIConstants.SIZE_200_40);

        userPanel.add(addButton);
        userPanel.add(deleteButton);

        panel.add(userPanel);
        panel.add(new Spacer(10));
    }

    private void addUser(String username) {
        System.out.println("Adding user: " + username);
    }

    private void deleteUser(String username) {
        System.out.println("Deleting user: " + username);
    }
}
