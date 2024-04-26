package Pages;

import GUI.*;
import Network.Client;
import Objects.Chat;

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
    private Label titleLabel;
    //TODO chat message panel
    private TextField chatField;
    private Button sendButton;
    private Button backButton;
    private Chat chat;

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
