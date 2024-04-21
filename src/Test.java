import Database.Database;
import GUI.Window;
import Network.Client;
import Pages.MainMenu;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Database database = Database.getInstance();
        database.initialize();

        Client client = new Client();
        client.setUser(database.getUser("purduepete"));
        Window window = Window.getInstance();
        window.switchPage(new MainMenu(client));
    }
}
