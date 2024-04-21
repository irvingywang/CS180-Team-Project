package Pages;

import GUI.*;
import Network.Client;

public class AllChatsPage extends Page {
    // Declare components here
    Label titleLabel;
    Dropdown chatDropdown;
    Button viewChatButton;
    Button backButton;

    public AllChatsPage(Client client) {
        super(client);
    }

    @Override
    public void initContent() {
        // Initialize components here
        titleLabel = new Label("All Chats", 42);
        chatDropdown = new Dropdown(new String[]{"Chat 1", "Chat 2", "Chat 3"}, GUIConstants.SIZE_400_40);
        viewChatButton = new Button("View Chat", () -> viewChatAction(), GUIConstants.SIZE_400_40);
        backButton = new Button("Back to Menu", () -> window.switchPage(new MainMenu(client)), GUIConstants.SIZE_400_40, true);

        addComponents();
    }

    @Override
    public void addComponents() {
        // Add components to panel here
        panel.add(new Spacer(220));
        panel.add(titleLabel);
        panel.add(new Spacer(40));
        panel.add(chatDropdown);
        panel.add(new Spacer(10));
        panel.add(viewChatButton);
        panel.add(new Spacer(10));
        panel.add(backButton);

        panel.revalidate();
    }

    public void viewChatAction() {
        //TODO switch window to chat view
        String selectedChat = (String) chatDropdown.getSelectedItem();
        System.out.println("Viewing chat: " + selectedChat);
    }
}
