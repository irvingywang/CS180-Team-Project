public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        Database database = new Database();
        User testUser = new User("testUser", "password", "Test User");
        Database.createUser(testUser);

        System.out.println(("Username: " + testUser.getUsername()));
    }
}