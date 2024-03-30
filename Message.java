import java.io.Serializable;

/**
 * Represents a message between two users.
 * A message has a sender, a recipient, a message string, and a read status.
 */
public class Message implements MessageInterface, Serializable {
    private User sender = new User();
    private User recipient = new User();
    private String message = "invalid";
    private boolean isRead = false;
    private boolean isValid = false;

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
        return String.format("%s,%s,%s", sender.getUsername(), recipient.getUsername(), message);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message) {
            Message other = (Message) obj;
            return this.sender.equals(other.sender) && this.recipient.equals(other.recipient)
                    && this.message.equals(other.message) && this.isRead == other.isRead;
        }
        return false;
    }
}
