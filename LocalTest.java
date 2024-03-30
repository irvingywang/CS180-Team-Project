import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LocalTest {
    private Database database;

    private User user1;
    private User user2;

    /**
     * Setup for the Test of Database Class
     */
    @Before
    public void setUpDatabase() {
        database = Database.getInstance();
        // set the data file to use test.ser for testing
        database.setDataFile("test.ser");
        database.reset();
    }

    @Before
    public void setUpUsers() {
        user1 = new User("sender", "password1", "Sender");
        user2 = new User("receiver", "password2", "Recipient");
    }

    @Test
    public void testCreateUserAndRetrieve() {
        database.createUser("User", "password", "User");
        User retrievedUser = database.getUser("User");
        assertNotNull(retrievedUser);
        assertEquals("User", retrievedUser.getUsername());
        assertEquals("User", retrievedUser.getDisplayName());
    }

    @Test
    public void testGetUserNotFound() {
        User retrievedUser = database.getUser("User doesn't exist");
        assertEquals(new User(), retrievedUser); //should return a default user
    }

    @Test
    public void testMessageCreation() {
        User sender = new User("senderUser", "password", "Sender");
        User recipient = new User("recipientUser", "password", "Recipient");
        Message message = new Message(sender, recipient, "Test message");

        assertEquals(sender, message.getSender());
        assertEquals(recipient, message.getRecipient());
        assertEquals("Test message", message.getMessage());
        assertFalse(message.isRead());
    }

    @Test
    public void testMarkMessageAsRead() {
        User sender = new User("sender", "password", "Sender");
        User recipient = new User("recipient", "password", "Recipient");
        Message message = new Message(sender, recipient, "Hello, my name is ~");
        assertFalse("Not read", message.isRead());
        message.markAsRead();
        assertTrue("read", message.isRead());
    }

    @Test
    public void testSendMessage() {
        boolean result = user1.sendMessage(user2, "Hello, my name is ~");
        assertTrue(result);
        assertEquals(1, user2.getMessages().size());
        assertEquals("Hello, my name is ~", user2.getMessages().get(0).getMessage());
    }

    @Test
    public void testAddAndRemoveFriend() {
        assertTrue("user2 added user 1", user1.addFriend(user2));
        assertTrue("user2 and user1 is already friend", user1.isFriend(user2));
        assertTrue("user1 removed user2 from friend", user1.removeFriend(user2));
        assertFalse("user1 and user2 is not friend", user1.isFriend(user2));
    }

    @Test
    public void testBlockUser() {
        assertTrue("user1 blocked user2", user1.blockUser(user2));
        assertTrue("user1 blocked user2", user1.isBlocked(user2));
    }

    @Test
    public void testSendMessageToBlockedUser() {
        user1.blockUser(user2);
        assertFalse("user1 blocked user2", user1.sendMessage(user2, "Hello, my name is ~"));
        assertEquals(0, user2.getMessages().size());
    }

    @Test
    public void testUnblockUser() {
        user1.blockUser(user2);
        assertTrue("user1 blocked user2", user1.isBlocked(user2));
        assertTrue("user1 unblocked user2", user1.unblockUser(user2));
        assertFalse("user1 unblocked user2", user1.isBlocked(user2));
    }

    @Test
    public void testGetMessages() {
        user1.sendMessage(user2, "Hello, my name is ~");
        assertEquals(1, user2.getMessages().size());
        assertEquals("Hello, my name is ~", user2.getMessages().get(0).getMessage());
    }

    @Test
    public void testGetUsername() {
        assertEquals("sender", user1.getUsername());
        assertEquals("receiver", user2.getUsername());
    }
}
