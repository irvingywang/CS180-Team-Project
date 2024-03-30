public class App {
    public static void main(String[] args) {
        Database database = Database.getInstance();

        database.reset();
        database.initialize();

        database.createUser("purduepete", "boilerup", "Purdue Pete", false);
        database.createUser("john123", "password", "John Doe", false);
        database.createUser("hoosier123", "password", "IU student", true);

        User purduepete = database.getUser("purduepete");
        User john123 = database.getUser("john123");
        User hoosier123 = database.getUser("hoosier123");

        for (User user : database.getUsers()) {
            System.out.println(user.toString());
        }

        purduepete.addFriend(john123);
        purduepete.sendMessage(john123, "hello");
        john123.addFriend(purduepete);
        purduepete.sendMessage(john123, "hello");


        purduepete.blockUser(hoosier123);
        hoosier123.sendMessage(database.getUser("purduepete"), "IU is better than Purdue");

        for (Message message : database.getUser("purduepete").getMessages()) {
            System.out.println(message.toString());
        }

        database.close();
    }
}