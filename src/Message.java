public class Message {
    private User sender;
    private User recipient;
    private String message;
    private boolean isRead;
    private boolean isValid;

    public Message(String data) {
        try {
            String[] parts = data.split(",");
            this.sender = new User(parts[0]);
            this.recipient = new User(parts[1]);
            this.message = parts[2];
            this.isRead = Boolean.parseBoolean(parts[3]);
            this.isValid = true;
        } catch (Exception e) {
            this.isValid = false;
            Database.saveToLog("Failed to create message from data.");
        }
    }

    public Message(User sender, User recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.isRead = false;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void markAsRead() {
        isRead = true;
    }
}
