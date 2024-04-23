package Pages;

import GUI.*;
import Network.Client;
import Objects.Chat;

public class ChatPage extends Page {
    // Declare components here
    Label titleLabel;
    //TODO chat message panel
    TextField chatField;
    Button sendButton;
    Button backButton;
    Chat chat;

    public ChatPage(Client client, Chat chat) {
        super(client);
        this.chat = chat;
    }

    @Override
    public void initContent() {
        // Initialize components here
        titleLabel = new Label(chat.getName(), 42);
        //TODO chat message panel
        chatField = new TextField("Enter Message", GUIConstants.SIZE_400_40);
        sendButton = new Button("Send", () -> sendMessageAction(), GUIConstants.SIZE_400_40);
        backButton = new Button("Back to Chats", () -> window.switchPage(new AllChatsPage(client)), GUIConstants.SIZE_400_40, true);

        addComponents();
    }

    @Override
    public void addComponents() {
        // Add components to panel here
        panel.add(new Spacer(220));
        panel.add(titleLabel);
        panel.add(new Spacer(40));
        panel.add(chatField);
        panel.add(new Spacer(10));
        panel.add(sendButton);
        panel.add(new Spacer(10));
        panel.add(backButton);

        panel.revalidate();
    }

    private void sendMessageAction() {
        //TODO send message
        String message = chatField.getText();
    }
}
