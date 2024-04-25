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
    SEARCH_USER_RESULT, GET_USERS_SUCCESS,
    SAVE_PROFILE_SUCCESS, SAVE_PROFILE_FAILURE,
    CREATE_CHAT_SUCCESS, CREATE_CHAT_FAILURE,
    SEND_MESSAGE,
    GET_CHATS_RESULT, MESSAGE_DISPLAY_SUCCESS
}
