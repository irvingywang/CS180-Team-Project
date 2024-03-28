public class App {
    public static void main(String[] args) {
        System.out.println("Hello World");

        Database.initialize();

//        Database.clearDatabase();
//
//        Database.createUser("purduepete", "boilerup", "Purdue Pete");
//        Database.createUser("john123", "password", "John Doe");
//
//        Database.getUser("purduepete").sendMessage(
//                Database.getUser("john123"), "hello");

        System.out.println("User1 username: " + Database.getUser("purduepete").getUsername());
        System.out.println("User2 username: " + Database.getUser("john123").getUsername());

        Database.close();
    }
}