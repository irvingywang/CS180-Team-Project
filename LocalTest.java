import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Project05 -- LocalTest
 *
 * Tests all the methods created.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 *
 * @version April 1, 2024
 *
 */
public class LocalTest {
    private Database database;
    private User user1;
    private User user2;
    private GroupChat groupChat;

    /**
     * Setup for the Test of Database Class
     */
    @Before
    public void setUp() {
        database = Database.getInstance();
        database.setDataFile("test.ser");
        database.reset();
        database.initialize();

        database.addUser(new User("purduepete", "boilerup", "Purdue Pete", false));

        database.addUser(new User("john123", "password", "John Doe", false));

        database.addUser(new User("hoosier123", "password", "IU student", true));

        user1 = new User("sender", "password1", "Sender", true);
        user2 = new User("receiver", "password2", "Recipient", true);

        ArrayList<User> groupUsers = new ArrayList<>();
        groupUsers.add(user1);
        groupUsers.add(user2);
        groupChat = new GroupChat("Group Chat", groupUsers , user1);
    }

    @Test
    public void testUserCreationAndMessaging() {
        User purduepete = database.getUser("purduepete");
        User john123 = database.getUser("john123");
        User hoosier123 = database.getUser("hoosier123");

        assertNotNull(purduepete);
        assertNotNull(john123);
        assertNotNull(hoosier123);

        // check user creation
        assertEquals("Purdue Pete", purduepete.getDisplayName());
        assertEquals("John Doe", john123.getDisplayName());
        assertEquals("IU student", hoosier123.getDisplayName());

        // test messaging and friend system
        assertTrue(purduepete.addFriend(john123));
        assertTrue(john123.addFriend(purduepete));
        assertTrue(purduepete.sendMessage(john123, "hello"));
        assertEquals(1, john123.getMessages().size());
        assertEquals("hello", john123.getMessages().get(0).getMessage());

        // test blocking
        purduepete.blockUser(hoosier123);
        assertFalse(hoosier123.sendMessage(purduepete, "IU is better than Purdue"));
        assertTrue(purduepete.getMessages().size() <= 1); // there should only be one message

        database.close();
    }

    @Test
    public void testCreateUserAndRetrieve() {
        User user = new User("User", "password", "User", true);
        database.addUser(user);
        User retrievedUser = database.getUser("User");
        assertNotNull(retrievedUser);
        assertEquals("User", retrievedUser.getUsername());
        assertEquals("User", retrievedUser.getDisplayName());
    }

    @Test
    public void testGetUserNotFound() {
        User retrievedUser = database.getUser("User doesn't exist");
        assertNull(retrievedUser); //should return null
    }

    @Test
    public void testMessageCreation() {
        User sender = new User("senderUser", "password", "Sender", true);
        User recipient = new User("recipientUser", "password", "Recipient", true);
        Message message = new Message(sender, recipient, "Test message");

        assertEquals(sender, message.getSender());
        assertEquals(recipient, message.getRecipient());
        assertEquals("Test message", message.getMessage());
        assertFalse(message.isRead());
    }

    @Test
    public void testMarkMessageAsRead() {
        User sender = new User("sender", "password", "Sender", true);
        User recipient = new User("recipient", "password", "Recipient",true);
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

    @Test
    public void testAddMember() {
        User newUser = new User("newUser", "password", "New User", true);
        assertTrue(groupChat.addMember(newUser));
        assertTrue(groupChat.isMember(newUser));
    }

    @Test
    public void testRemoveMember() {
        assertTrue(groupChat.removeMember(user1));
        assertFalse(groupChat.isMember(user1));
    }

}