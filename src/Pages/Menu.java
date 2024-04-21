package Pages;
import GUI.*;
import Network.Client;

public class Menu extends Page {
    // Declare components here
    Label titleLabel;
    Button viewChatsButton;
    Button createChatButton;
    Button searchUsersButton;
    Button editProfileButton;
    Button logoutButton;
    Dropdown chatDropdown;


    public Menu(Client client) {
        super(client);
        initContent();
    }

    @Override
    public void initContent() {
        // Initialize components here
        titleLabel = new Label("Welcome " + client.getUsername(), 42);

        viewChatsButton = new Button("View Chats", () -> {}, GUIConstants.SIZE_400_40, true);
        createChatButton = new Button("Create Chat", () -> {}, GUIConstants.SIZE_400_40, true);
        searchUsersButton = new Button("Search Users", () -> {}, GUIConstants.SIZE_400_40, true);
        editProfileButton = new Button("Edit Profile", () -> {}, GUIConstants.SIZE_400_40, true);
        logoutButton = new Button("Logout", () -> {}, GUIConstants.SIZE_400_40, true);

        chatDropdown = new Dropdown(new String[]{"Chat 1", "Chat 2", "Chat 3"}, GUIConstants.SIZE_400_40);

        addComponents();
    }

    @Override
    public void addComponents() {
        panel.removeAll();
        // Add components to panel here
        panel.add(new Spacer(100));
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
        panel.add(new Spacer(10));
        panel.add(chatDropdown);

        panel.revalidate();
    }
}
