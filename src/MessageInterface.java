public interface MessageInterface {
    User getSender();
    User getRecipient();
    String getMessage();
    boolean isRead();
    boolean isValid();
    void markAsRead();
    String toString();
}
