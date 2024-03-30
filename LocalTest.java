import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database database;

    /**
     * Setup for the Test of Database Class
     */
    @BeforeEach
    void setUp() {
        database = Database.getInstance();
        database.clearDatabase();
    }
    @Test
    void testCreateUserAndRetrieve() {
        database.createUser("User", "password", "User");
        User retrievedUser = database.getUser("User");
        assertAll(
                () -> assertNotNull(retrievedUser),
                () -> assertEquals("User", retrievedUser.getUsername()),
                () -> assertEquals("User", retrievedUser.getDisplayName())
        );
    }
    @Test
    void testGetUserNotFound() {
        User retrievedUser = database.getUser("User doesn't exist");
        assertNull(retrievedUser, "User should not find anyone");
    }
}

class MessageTest {

    /**
     * To test the message class
     */
    @Test
    void testMessageCreation() {
        User sender = new User("senderUser", "password", "Sender");
        User recipient = new User("recipientUser", "password", "Recipient");
        Message message = new Message(sender, recipient, "Test message");

        assertAll(
                () -> assertEquals(sender, message.getSender()),
                () -> assertEquals(recipient, message.getRecipient()),
                () -> assertEquals("Test message", message.getMessage()),
                () -> assertFalse(message.isRead())
        );
    }
    @Test
    void testMarkMessageAsRead() {
        User sender = new User("sender", "password", "Sender");
        User recipient = new User("recipient", "password", "Recipient");
        Message message = new Message(sender, recipient, "Hello, my name is ~");
        assertFalse(message.isRead(), "Not read");
        message.markAsRead();
        assertTrue(message.isRead(), "read");
    }
}

class UserTest {
    /**
     * Create Users(sender and recipient)
     */
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User("sender", "password1", "Sender");
        user2 = new User("receiver", "password2", "Recipient");
    }

    @Test
    void testSendMessage() {
        boolean result = user1.sendMessage(user2, "Hello, my name is ~");
        assertTrue(result);
        assertEquals(1, user2.getMessages().size());
        assertEquals("Hello, my name is ~", user2.getMessages().get(0).getMessage());
    }
    @Test
    void testAddAndRemoveFriend() {
        assertTrue(user1.addFriend(user2), "user2 added user 1");
        assertTrue(user1.isFriend(user2), "user2 and user1 is already friend");
        assertTrue(user1.removeFriend(user2), "user1 removed user2 from friend");
        assertFalse(user1.isFriend(user2), "user1 and user2 is not friend");
    }
}
