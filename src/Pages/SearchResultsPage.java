package Pages;

import GUI.*;
import Network.Client;
import Objects.User;

public class SearchResultsPage extends Page {
    // Declare components here
    private Label titleLabel;
    private Dropdown searchResultsDropdown;
    private Button viewProfileButton;
    private Button backButton;
    private User[] results;

    public SearchResultsPage(Client client, User[] results) {
        super(client);
        this.results = results;
    }

    @Override
    public void initContent() {
        // Initialize components here
        String[] resultUsernames = new String[results.length];
        for (int i = 0; i < results.length; i++) {
            //resultUsernames[i] = String.format("%s - @%s", results[i].getDisplayName(), results[i].getUsername());
            resultUsernames[i] = "@" + results[i].getUsername();
        }
        titleLabel = new Label("Search Results", 42);
        searchResultsDropdown = new Dropdown(resultUsernames, GUIConstants.SIZE_400_40);
        viewProfileButton = new Button("View Profile", () -> viewProfileAction(), GUIConstants.SIZE_400_40);
        backButton = new Button("Back to Search", () -> window.switchPage(new SearchUsersPage(client)), GUIConstants.SIZE_400_40, true);

        addComponents();
    }

    @Override
    public void addComponents() {
        // Add components to panel here
        panel.add(new Spacer(200));
        panel.add(titleLabel);
        panel.add(new Spacer(40));
        panel.add(searchResultsDropdown);
        panel.add(new Spacer(10));
        panel.add(viewProfileButton);
        panel.add(new Spacer(10));
        panel.add(backButton);

        panel.revalidate();
    }

    private void viewProfileAction() {
        int selectedIndex = searchResultsDropdown.getSelectedIndex();
        User selectedUser = results[selectedIndex];
        window.switchPage(new ViewProfilePage(client, selectedUser));
    }
}
