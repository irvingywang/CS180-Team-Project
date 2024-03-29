/**
 * Represents a message between two users.
 * A message has a sender, a recipient, a message string, and a read status.
 */
public class Message implements MessageInterface {
    private User sender = new User();
    private User recipient = new User();
    private String message = "invalid";
    private boolean isRead = false;
    private boolean isValid = false;

    /**
     * Constructs a Message object from a string.
     * Meant to be used when loading messages from the database.
     *
     * @param data - the data string
     */
    public Message(String data) {
        try {
            String[] parts = data.split(Database.getDelimiter());
            this.sender = new User(parts[0]);
            this.recipient = new User(parts[1]);
            this.message = parts[2];
            this.isRead = Boolean.parseBoolean(parts[3]);
            this.isValid = true;
        } catch (Exception e) {
            Database.saveToLog("Failed to create message from data.");
        }
    }

    /**
     * Constructs a Message object using a sender, recipient, and message string.
     *
     * @param sender    - the sender User
     * @param recipient - the recipient User
     * @param message   - the message string
     */
    public Message(User sender, User recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.isRead = false;
    }

    /**
     * @return the sender User
     */
    public User getSender() {
        return sender;
    }

    /**
     * @return the recipient User
     */
    public User getRecipient() {
        return recipient;
    }

    /**
     * @return the message string
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return true if the message is read, false otherwise
     */
    public boolean isRead() {
        return isRead;
    }

    /**
     * @return true if the message is valid, false otherwise
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Marks the message as read.
     */
    public void markAsRead() {
        isRead = true;
    }

    /**
     * Returns a string representation of the Message.
     * This method is meant to be used when saving the Message to the database.
     * @return string representation of the Message
     */
    @Override
    public String toString() {
        return String.format("%s%s%s%s%s", sender.getUsername(), Database.getDelimiter(),
                recipient.getUsername(), Database.getDelimiter(), message);
    }
}
