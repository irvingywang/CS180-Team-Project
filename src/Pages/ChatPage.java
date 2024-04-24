package Pages;

import GUI.*;
import Network.Client;

/**
 * Project05 -- ChatPage
 * <p>
 * Creates page that is shown after a new chat is created.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
public class ChatPage extends Page {
    // Declare components here
    Label titleLabel;
    TextField messageField;
    Button sendButton;
    Button backButton;

    private String chatName;

    public ChatPage(Client client, String chatName) {
        super(client);
        this.chatName = chatName;
        initContent();
    }

    @Override
    public void initContent() {
        // Initialize components here
        titleLabel = new Label("Chat: " + chatName, 42);
        messageField = new TextField("Enter message", GUIConstants.SIZE_400_40);
        sendButton = new Button("Send", () -> sendMessageAction(), GUIConstants.SIZE_400_40);
        backButton = new Button("Back to Chats", () -> window.switchPage(new CreateChatPage(client)), GUIConstants.SIZE_400_40, true);

        addComponents();
    }

    @Override
    public void addComponents() {
        // Add components to panel here
        panel.add(new Spacer(200));
        panel.add(titleLabel);
        panel.add(new Spacer(40));
        panel.add(messageField);
        panel.add(new Spacer(10));
        panel.add(sendButton);
        panel.add(new Spacer(10));
        panel.add(backButton);

        panel.revalidate();
    }

    private void sendMessageAction() {
        String message = messageField.getText();
        System.out.println(message);

        if (message.isEmpty()) {
            showError("Message cannot be empty.");
            return;
        }

        client.sendMessage(chatName, message);
        messageField.setText("");
    }
}