/**
 * Project05 -- MessageInterface
 *
 * Creates an interface for the message class.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 *
 * @version April 1, 2024
 *
 */
public interface MessageInterface {
    User getSender();
    User getRecipient();
    String getMessage();
    boolean isRead();
    void markAsRead();
    String toString();
}
