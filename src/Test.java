import GUI.Window;
import Network.Client;
import Pages.WelcomePage;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        Client client = new Client();
        Window window = Window.getInstance();
        window.switchPage(new WelcomePage(client));
    }
}
