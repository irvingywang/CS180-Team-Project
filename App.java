import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        String menu = "1. Login\n2. Create User\n3. Remove User\n4. Exit";
        String loggedInMenu = "1. Send Message\n2. Read Messages\n3. Exit";
        Scanner scanner = new Scanner(System.in);

        database.setDataFile("data.ser");

        try {
            System.out.println("Reset database? (yes/no/exit)");
            String initialChoice = scanner.nextLine();

            switch (initialChoice) {
                case "yes":
                    database.reset();
                    System.out.println("Database reset.");
                    break;
                case "no":
                    System.out.println("Database not reset.");
                    break;
                default:
                    return;
            }
            database.initialize();

            boolean loggedIn = false;
            String username = "";
            String choice;

            do {
                if (!loggedIn) {
                    System.out.println(menu);
                    System.out.print("Enter a number: ");
                    choice = scanner.nextLine();

                    switch (choice) {
                        case "1":
                            System.out.print("Enter username: ");
                            username = scanner.nextLine();
                            System.out.print("Enter password: ");
                            String password = scanner.nextLine();
                            if (database.login(username, password)) {
                                System.out.println("Login successful.");
                                loggedIn = true;
                            } else {
                                System.out.println("Login failed.");
                            }
                            break;
                        case "2":
                            System.out.print("Enter username: ");
                            username = scanner.nextLine();
                            System.out.print("Enter password: ");
                            password = scanner.nextLine();
                            System.out.print("Enter display name: ");
                            String displayName = scanner.nextLine();
                            System.out.print("Public profile? (true/false): ");
                            boolean publicProfile = scanner.nextBoolean();
                            scanner.nextLine();
                            database.createUser(username, password, displayName, publicProfile);
                            System.out.println("User created.");
                            break;
                        case "3":
                            System.out.print("Enter username: ");
                            username = scanner.nextLine();
                            database.removeUser(username);
                            System.out.println("User removed.");
                            break;
                        case "4":
                            System.out.println("Exiting...");
                            return;
                    }
                } else {
                    System.out.println(loggedInMenu);
                    System.out.print("Enter a number: ");
                    choice = scanner.nextLine();

                    switch (choice) {
                        case "1":
                            System.out.print("Enter recipient: ");
                            String recipient = scanner.nextLine();
                            System.out.print("Enter message: ");
                            String message = scanner.nextLine();
                            User sender = database.getUser(username);
                            sender.sendMessage(database.getUser(recipient), message);
                            System.out.println("Message sent.");
                            break;
                        case "2":
                            User user = database.getUser(username);
                            for (Message m : user.getMessages()) {
                                System.out.println(m);
                            }
                            break;
                        case "3":
                            System.out.println("Exiting...");
                            database.close();
                            return;
                    }
                }
            } while (!"5".equals(choice) || !loggedIn);
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }
}

