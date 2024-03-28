public interface MessageInterface {
    public User getSender();
    public User getRecipient();
    public String getMessage();
    public boolean isRead();
}
