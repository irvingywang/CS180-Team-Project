package Pages;
import GUI.*;
import Network.Client;


/**
 * Project05 -- ChatPage
 * <p>
 * Creates page that is shown after user logs in or creates
 * a new account.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
public class MainMenu extends Page {
    // Declare components here
    Label titleLabel;
    Button viewChatsButton;
    Button createChatButton;
    Button searchUsersButton;
    Button editProfileButton;
    Button logoutButton;

    public MainMenu(Client client) {
        super(client);
    }

    @Override
    public void initContent() {
        // Initialize components here
        titleLabel = new Label("Welcome " + client.getUsername(), 42);

        viewChatsButton = new Button("View Chats", () -> window.switchPage(new AllChatsPage(client)), GUIConstants.SIZE_400_40, true);
        createChatButton = new Button("Create Chat", () -> window.switchPage(new CreateChatPage(client)), GUIConstants.SIZE_400_40, true);
        searchUsersButton = new Button("Search Users", () -> window.switchPage(new SearchUsersPage(client)), GUIConstants.SIZE_400_40, true);
        editProfileButton = new Button("Edit Profile", () -> window.switchPage(new EditProfilePage(client)), GUIConstants.SIZE_400_40, true);
        logoutButton = new Button("Logout", () -> window.switchPage(new WelcomePage(client)), GUIConstants.SIZE_400_40, true);

        addComponents();
    }

    @Override
    public void addComponents() {
        // Add components to panel here
        panel.add(new Spacer(200));
        panel.add(titleLabel);
        panel.add(new Spacer(40));
        panel.add(viewChatsButton);
        panel.add(new Spacer(10));
        panel.add(createChatButton);
        panel.add(new Spacer(10));
        panel.add(searchUsersButton);
        panel.add(new Spacer(10));
        panel.add(editProfileButton);
        panel.add(new Spacer(10));
        panel.add(logoutButton);

        panel.revalidate();
    }
}
