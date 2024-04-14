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

        database.serializeDatabase();
        for (User user : database.getUsers()) {
            System.out.println(user);
        }

        //Start the server
        Server.main(args);

        //FIXME join the server thread instead of sleep
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        //Start the client
        Client.main(args);
    }
}

