import GUI.LoginPage;
import Network.Client;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        LoginPage loginPage = new LoginPage(new Client());
    }
}
