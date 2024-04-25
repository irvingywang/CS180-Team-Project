package Pages;

import GUI.*;
import Network.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Project05 -- SearchUsersPage
 * <p>
 * Creates page that is shown after Search Users button
 * is clicked on the Main Menu page.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
public class SearchUsersPage extends Page {
    // Declare components here
    Label titleLabel;
    TextField searchField;
    Button searchButton;
    Button backButton;

    public SearchUsersPage(Client client) {
        super(client);
    }

    @Override
    public void initContent() {
        // Initialize components here
        titleLabel = new Label("Search Users", 42);
        searchField = new TextField("Enter Username", GUIConstants.SIZE_400_40);
        searchButton = new Button("Search", () -> searchAction(), GUIConstants.SIZE_400_40);
        backButton = new Button("Back to Menu", () -> window.switchPage(new MainMenu(client)), GUIConstants.SIZE_400_40, true);

        addComponents();
    }

    @Override
    public void addComponents() {
        // Add components to panel here
        panel.add(new Spacer(220));
        panel.add(titleLabel);
        panel.add(new Spacer(40));
        panel.add(searchField);
        panel.add(new Spacer(10));
        panel.add(searchButton);
        panel.add(new Spacer(10));
        panel.add(backButton);

        panel.revalidate();
    }

    private void searchAction() {
        //TODO search users
        String search = searchField.getText();
        System.out.println("Searching for users: " + search);

//        String[] users = ????
//        List<String> searchList = new ArrayList<>();
//
//        for (String user : users) {
//            if (user.toLowerCase().contains(search.toLowerCase())) {
//                searchList.add(user);
//            }
//        }
//
//        String[] searchResults = searchList.toArray(new String[0]);
//
//        if (searchResults.length > 0) {
//            window.switchPage(new SearchResultsPage(client, searchResults));
//        } else {
//            showError("No results found for: " + search);
//        }
    }
}
