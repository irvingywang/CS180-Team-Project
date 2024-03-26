public class App {
    public static void main(String[] args) {
        System.out.println("Hello World");

        Database.readUsers();

        User user1 = new User("purduepete", "password", "Purdue Pete");
        User user2 = new User("john123", "password", "John Doe");
        Database.createUser(user1);
        Database.createUser(user2);
        Database.writeUsers();

        user1.sendMessage(user2, "Hello");

        System.out.println(("Username: " + user1.getUsername()));
    }
}