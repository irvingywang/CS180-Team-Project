public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        User testUser = new User("testUser", "password", "Test User");

        System.out.println(("Username: " + testUser.getUsername()));
    }
}