package Network;

/**
 * Project05 - ClientCommand
 *
 * Stores the success/failure of commands from the Network.ServerCommand class.
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
public enum ClientCommand {
    LOGIN_SUCCESS, LOGIN_FAILURE,
    CREATE_USER_SUCCESS, CREATE_USER_FAILURE,
    SEARCH_USER_RESULT,
    SAVE_PROFILE_SUCCESS, SAVE_PROFILE_FAILURE,
    SEND_MESSAGE
}
