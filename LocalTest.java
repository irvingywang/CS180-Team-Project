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
    private Chat chat;

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
    }

    @Test
    public void testUserCreationAndMessaging() {
        User purduepete = database.getUser("purduepete");
        User john123 = database.getUser("john123");
        User hoosier123 = database.getUser("hoosier123");

        // check user creation
        assertNotNull(purduepete);
        assertNotNull(john123);
        assertNotNull(hoosier123);
        assertEquals("Purdue Pete", purduepete.getDisplayName());
        assertEquals("John Doe", john123.getDisplayName());
        assertEquals("IU student", hoosier123.getDisplayName());

        // test friend system
        purduepete.addFriend(john123);
        john123.addFriend(purduepete);
        assertTrue(purduepete.isFriend(john123));
        assertTrue(john123.isFriend(purduepete));

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
    public void testUnblockUser() {
        user1.blockUser(user2);
        assertTrue("user1 blocked user2", user1.isBlocked(user2));
        assertTrue("user1 unblocked user2", user1.unblockUser(user2));
        assertFalse("user1 unblocked user2", user1.isBlocked(user2));
    }

    @Test
    public void testGetUsername() {
        assertEquals("sender", user1.getUsername());
        assertEquals("receiver", user2.getUsername());
    }

    @Test
    public void testAddMember() {
        User newUser = new User("newUser", "password", "New User", true);
        try {
            ArrayList<User> users = new ArrayList<>();
            users.add(user1);
            users.add(user2);
            chat = new Chat("chat", database.getUsers());
        } catch (InvalidChatException e) {
            e.printStackTrace();
        }
        assertTrue(chat.addMember(newUser));
        assertTrue(chat.isMember(newUser));
    }

    @Test
    public void testRemoveMember() {
        try {
            ArrayList<User> users = new ArrayList<>();
            users.add(user1);
            users.add(user2);
            chat = new Chat("chat", database.getUsers());
        } catch (InvalidChatException e) {
            e.printStackTrace();
        }
        assertFalse(chat.removeMember(user1));
        assertFalse(chat.isMember(user1));
    }

    @Test
    public void testChat() {
        User purduepete = database.getUser("purduepete");
        User john123 = database.getUser("john123");
        User hoosier123 = database.getUser("hoosier123");

        ArrayList<User> users = new ArrayList<>();
        users.add(purduepete);

        assertThrows(InvalidChatException.class, () -> {
            Chat chat = new Chat("chat", users);
        });

        users.add(john123);
        users.add(hoosier123);

        try {
            Chat chat = new Chat("chat", users);
        } catch (InvalidChatException e) {
            e.printStackTrace();
        }
    }

}