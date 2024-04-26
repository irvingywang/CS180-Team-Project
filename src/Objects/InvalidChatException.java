package Objects;

/**
 * Project05 - InvalidChatException
 *
 * Custom Java exception for handling invalid chat conditions
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 **/

public class InvalidChatException extends Exception {
    /**
     * Creates a new problem with a message explaining what's wrong.
     *
     * @param message Details about what's wrong with the chat.
     */
    public InvalidChatException(String message) {
        super(message);
    }
}
