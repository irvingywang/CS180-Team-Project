public interface MessageInterface {
    User getSender();
    User getRecipient();
    String getMessage();
    boolean isRead();
    void markAsRead();
    String toString();
}
