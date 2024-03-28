public class App {
    public static void main(String[] args) {
        System.out.println("Hello World");

        Database.initialize();

//        Database.clearDatabase();
//
//        Database.createUser("purduepete", "boilerup", "Purdue Pete");
//        Database.createUser("john123", "password", "John Doe");
//
        Database.getUser("purduepete").sendMessage(Database.getUser("john123"), "hello");

        Database.createUser("hoosier123", "password", "IU student");
        Database.getUser("purduepete").addFriend(Database.getUser("john123"));
        Database.getUser("purduepete").blockUser(Database.getUser("hoosier123"));

        Database.getUser("hoosier123")
                .sendMessage(Database.getUser("purduepete"), "IU is better than Purdue");

        System.out.println("User1 username: " + Database.getUser("purduepete").getUsername());
        System.out.println("User2 username: " + Database.getUser("john123").getUsername());

        Database.close();
    }
}