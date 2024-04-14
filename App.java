import java.util.Scanner;

/**
 * Project05 -- App
 * <p>
 * Creates a framework for the messaging app by processing user input.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @version April 1, 2024
 */
public class App {
    public static void main(String[] args) {
        Database database = Database.getInstance();

        database.reset();
        database.addUser(new User("purduepete", "123", "Purdue Pete", true));
        database.addUser(new User("john", "123", "Boilermaker", true));

        Chat chat = new Chat("Purdue Chat", database.getUsers());
        database.addChat(chat);

        database.serializeDatabase();
        for (User user : database.getUsers()) {
            System.out.println(user);
        }

        for (Chat c : database.getChats()) {
            System.out.println(c);
        }
    }
}

