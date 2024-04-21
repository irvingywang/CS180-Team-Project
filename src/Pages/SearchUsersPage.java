package Pages;

import GUI.*;
import Network.Client;

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
        /*
        put search results into String[]
        String[] results = new String[]{"User 1", "User 2", "User 3"};

        if (results not empty)
        window.switchPage(new SearchResultsPage(client, results);
        else
        show error no results found
        */
    }
}
