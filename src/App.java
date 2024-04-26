import Network.Client;
import Database.Database;
import Objects.Chat;
import Network.Server;
import Objects.InvalidChatException;
import Objects.User;

/**
 * Project05 - App
 * <p>
 * Creates a framework for the messaging app by processing user input.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
public class App {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        database.initialize();

        System.out.println("Current users: ");
        for (User user : database.getUsers()) {
            System.out.println(user);
        }

        System.out.println("Current chats: ");
        for (Chat chat : database.getChats()) {
            System.out.println(chat);
        }

        //Start the server
        Server.start();

        //Start the client
        Client.start();

    }
}

