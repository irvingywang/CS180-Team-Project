package Pages;

import GUI.*;
import GUI.Label;
import GUI.TextField;
import GUI.Button;
import Network.Client;
import Network.NetworkMessage;
import Network.ServerCommand;
import Objects.Chat;
import Objects.Message;

import javax.swing.*;
import java.awt.*;

/**
 * Project05 - ChatPage
 *
 * Creates page that is shown after a new chat is created.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 *
 * @version April 14, 2024
 *
 */

public class ChatPage extends Page {
    private Label titleLabel;
    private JTextArea chatArea;
    private TextField messageField;
    private Button sendButton;
    private Button backButton;
    private JScrollPane scrollPane;

    public ChatPage(Client client, Chat chatName) {
        super(client);
    }

    @Override
    public void initContent() {
        titleLabel = new Label("Chat", 42);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setWrapStyleWord(true);
        chatArea.setLineWrap(true);

        scrollPane = new JScrollPane(chatArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 200));

        messageField = new TextField("Type your message here...", new Dimension(400, 40));
        sendButton = new Button("Send", this::sendMessage, new Dimension(400, 40));
        backButton = new Button("Back to Menu", () -> window.switchPage(new MainMenu(client)), new Dimension(400, 40), true);

        addComponents();
    }

    @Override
    public void addComponents() {
        panel.add(new Spacer(200));
        panel.add(titleLabel);
        panel.add(new Spacer(40));
        panel.add(scrollPane);
        panel.add(new Spacer(10));
        panel.add(messageField);
        panel.add(new Spacer(10));
        panel.add(sendButton);
        panel.add(new Spacer(40));
        panel.add(backButton);

        panel.revalidate();
    }

    private void sendMessage() {
        String text = messageField.getText();
        if (!text.trim().isEmpty()) {
            client.sendToServer(new NetworkMessage(ServerCommand.SEND_MESSAGE, Client.IDENTIFIER, text));
            messageField.setText("");
            appendMessage("Me: " + text);
        }
    }
    public void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }

    public void displayReceivedMessage(Message message) {
        SwingUtilities.invokeLater(() -> {
            appendMessage(message.getSender().getDisplayName() + ": " + message.getMessage());
        });
    }
}