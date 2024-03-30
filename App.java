public class App {
    public static void main(String[] args) {
        System.out.println("Hello World");
        Database database = Database.getInstance();

        //database.reset();

        database.initialize();

        database.createUser("purduepete", "boilerup", "Purdue Pete");
        database.createUser("john123", "password", "John Doe");
        database.createUser("hoosier123", "password", "IU student");

        User purduepete = database.getUser("purduepete");
        User john123 = database.getUser("john123");
        User hoosier123 = database.getUser("hoosier123");

        purduepete.addFriend(john123);
        purduepete.sendMessage(john123, "hello");

        purduepete.blockUser(hoosier123);
        hoosier123.sendMessage(database.getUser("purduepete"), "IU is better than Purdue");

        for (Message message : database.getUser("purduepete").getMessages()) {
            System.out.printf("Message from %s to %s: %s\n",
                    message.getSender().getUsername(), message.getRecipient().getUsername(), message.getMessage());
        }

        System.out.println("User1 username: " + database.getUser("purduepete").getUsername());
        System.out.println("User2 username: " + database.getUser("john123").getUsername());

        database.close();
    }
}