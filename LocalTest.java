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
        database.createUser("testUser", "password", "Test User");
        User retrievedUser = database.getUser("testUser");
        assertAll(
                () -> assertNotNull(retrievedUser),
                () -> assertEquals("testUser", retrievedUser.getUsername()),
                () -> assertEquals("Test User", retrievedUser.getDisplayName())
        );
    }
}
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
}
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    /**
     * Create Users(sender and recipient)
     */
    private User sender;
    private User recipient;

    @BeforeEach
    void setUp() {
        sender = new User("sender", "pass1", "Sender");
        recipient = new User("recipient", "pass2", "Recipient");
    }

    @Test
    void testSendMessage() {
        boolean result = sender.sendMessage(recipient, "Hello!");
        assertTrue(result);
        assertEquals(1, recipient.getMessages().size());
        assertEquals("Hello!", recipient.getMessages().get(0).getMessage());
    }
}
