import Database.Database;
import GUI.Window;
import Network.Client;
import Objects.User;
import Pages.MainMenu;
import Pages.ViewProfilePage;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Database database = Database.getInstance();
        database.initialize();

        Client client = new Client();
        User user = database.getUser("purduepete");
        user.setDisplayName("Purdue Pete");
        user.setPublicProfile(true);
        client.setUser(user);
        Window window = Window.getInstance();
        window.switchPage(new MainMenu(client));
    }
}
