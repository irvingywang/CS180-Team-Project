package Pages;

import GUI.*;
import Network.*;
import Objects.Chat;
import Objects.User;

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
        membersField = new TextField("Enter Other Member Usernames (comma separated)", GUIConstants.SIZE_400_40);
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
        if (chatName.isEmpty() || members.isEmpty()) {
            showError("Chat name and members cannot be empty.");
            return;
        }
        if (!members.contains(client.getUsername())) {
            members += "," + client.getUsername();
        }
        String[] membersArray = members.split(",");
        if (membersArray.length < 2) {
            showError("Chat must have at least 2 members including yourself.");
            return;
        }

        client.sendToServer(new NetworkMessage(ServerCommand.CREATE_CHAT, client.IDENTIFIER, new String[] {chatName, members}));

        NetworkMessage response = client.listenToServer();
        switch ((ClientCommand) response.getCommand()) {
            case CREATE_CHAT_SUCCESS -> {
                showError("Chat created successfully.");
                Chat chat = (Chat) response.getObject();
                //TODO switch to chat page
                System.out.println("Chat created: " + chat.getName());
                for (User member : chat.getMembers()) {
                    System.out.println(member.getUsername() + " " + member.getDisplayName());
                }
            }
            case CREATE_CHAT_FAILURE -> {
                showError((String) response.getObject());
            }
        }

        
        System.out.println("Creating chat: " + chatName + " with members: " + members);
    }
}
