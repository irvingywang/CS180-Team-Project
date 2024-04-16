import java.util.Date;

/**
 * Project05 -- MessageInterface
 *
 * Creates an interface for the message class.
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
public interface MessageInterface {
    User getSender();
    Chat getChat();
    String getMessage();
    String getImagePath();
    Date getTimestamp();
    String toString();
}
