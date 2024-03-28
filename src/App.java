public class App {
    public static void main(String[] args) {
        System.out.println("Hello World");

        Database.start();

        System.out.println("User1 username: " + Database.getUsers().get(0).getUsername());
        System.out.println("User2 username: " + Database.getUsers().get(1).getUsername());
    }
}