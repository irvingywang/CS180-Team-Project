public class Message {
    private User sender = new User();
    private User recipient = new User();
    private String message = "invalid";
    private boolean isRead = false;
    private boolean isValid = false;

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

    public boolean isValid() {
        return isValid;
    }

    public void markAsRead() {
        isRead = true;
    }

    @Override
    public String toString() {
        return String.format("%s%s%s%s%s", sender.getUsername(), Database.getDelimiter(),
                recipient.getUsername(), Database.getDelimiter(), message);
    }
}
