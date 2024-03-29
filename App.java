public class App {
    public static void main(String[] args) {
        System.out.println("Hello World");
        Database database = Database.getInstance();

        database.initialize();

        //database.clearDatabase();

        database.createUser("purduepete", "boilerup", "Purdue Pete");
        database.createUser("john123", "password", "John Doe");


        database.getUser("purduepete").sendMessage(database.getUser("john123"), "hello");

        database.createUser("hoosier123", "password", "IU student");
        database.getUser("purduepete").addFriend(database.getUser("john123"));
        database.getUser("purduepete").blockUser(database.getUser("hoosier123"));

        database.getUser("hoosier123")
                .sendMessage(database.getUser("purduepete"), "IU is better than Purdue");

        for (Message message : database.getUser("purduepete").getMessages()) {
            System.out.printf("Message from %s to %s: %s\n",
                    message.getSender().getUsername(), message.getRecipient().getUsername(), message.getMessage());
        }

        System.out.println("User1 username: " + database.getUser("purduepete").getUsername());
        System.out.println("User2 username: " + database.getUser("john123").getUsername());

        database.close();
    }
}