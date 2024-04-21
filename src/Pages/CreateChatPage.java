package Pages;

import GUI.*;
import Network.Client;

public class CreateChatPage extends Page {
    // Declare components here
    Label titleLabel;
    TextField chatNameField;
    TextField membersField;
    Button createChatButton;
    Button backButton;

    public CreateChatPage(Client client) {
        super(client);
    }

    @Override
    public void initContent() {
        // Initialize components here
        titleLabel = new Label("Create Chat", 42);
        chatNameField = new TextField("Enter Chat Name", GUIConstants.SIZE_400_40);
        membersField = new TextField("Enter Members (comma separated)", GUIConstants.SIZE_400_40);
        createChatButton = new Button("Create Chat", () -> createChatAction(), GUIConstants.SIZE_400_40);
        backButton = new Button("Back to Menu", () -> window.switchPage(new MainMenu(client)), GUIConstants.SIZE_400_40, true);

        addComponents();
    }

    @Override
    public void addComponents() {
        // Add components to panel here
        panel.add(new Spacer(200));
        panel.add(titleLabel);
        panel.add(new Spacer(40));
        panel.add(chatNameField);
        panel.add(new Spacer(10));
        panel.add(membersField);
        panel.add(new Spacer(40));
        panel.add(createChatButton);
        panel.add(new Spacer(10));
        panel.add(backButton);

        panel.revalidate();
    }

    private void createChatAction() {
        //TODO create chat
        String chatName = chatNameField.getText();
        String members = membersField.getText();
        System.out.println("Creating chat: " + chatName + " with members: " + members);
    }
}
