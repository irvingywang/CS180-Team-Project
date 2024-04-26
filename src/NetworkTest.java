import Database.Database;
import Network.*;
import Objects.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Project05 - NetworkTest
 *
 * Tests all the network methods created.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 *
 * @version April 1, 2024
 *
 */
public class NetworkTest {
    private static Client client;
    private static Database database = Database.getInstance();

    @Before
    public void setUp() {
        Server.start();

        database.setDataFile("test.ser");
        database.reset();
        database.initialize();

        client = new Client();
        client.connectToServer();

        database.addUser(
            new User("purduepete", "boilerup", "Purdue Pete", false));

        database.addUser(
                new User("john", "123", "Boiler Up", false));
    }

    @After
    public void tearDown() {
        client.disconnectFromServer();
    }

    @Test (timeout = 5000)
    public synchronized void createUser() {
        client.sendToServer(
                new NetworkMessage(ServerCommand.CREATE_USER, Identifier.CLIENT, "newuser,newpassword,New User"));
        NetworkMessage response = client.listenToServer();
        assertEquals(ClientCommand.CREATE_USER_SUCCESS, response.getCommand());

        client.sendToServer(
                new NetworkMessage(ServerCommand.CREATE_USER, Identifier.CLIENT, "purduepete,boilerup,Purdue Pete"));
        response = client.listenToServer();
        assertEquals(ClientCommand.CREATE_USER_FAILURE, response.getCommand());
    }

    @Test (timeout = 5000)
    public synchronized void login() {
        client.sendToServer(
                new NetworkMessage(ServerCommand.LOGIN, Identifier.CLIENT, "purduepete,boilerup"));
        NetworkMessage response = client.listenToServer();
        assertEquals(ClientCommand.LOGIN_SUCCESS, response.getCommand());

        client.sendToServer(
                new NetworkMessage(ServerCommand.LOGIN, Identifier.CLIENT, "purduepete,wrongpassword"));
        response = client.listenToServer();
        assertEquals(ClientCommand.LOGIN_FAILURE, response.getCommand());
    }

    @Test(timeout = 5000)
    public synchronized void testSearchUser() {
        client.sendToServer(new NetworkMessage(ServerCommand.SEARCH_USER, Identifier.CLIENT, "purduepete"));
        NetworkMessage response = client.listenToServer();
        assertEquals(ClientCommand.SEARCH_USER_RESULT, response.getCommand());
    }

    @Test(timeout = 5000)
    public synchronized void testCreateChat() {
        client.sendToServer(new NetworkMessage(ServerCommand.CREATE_CHAT, Identifier.CLIENT, new String[]{"test","purduepete,john"}));
        NetworkMessage response = client.listenToServer();
        assertEquals(ClientCommand.CREATE_CHAT_SUCCESS, response.getCommand());

        client.sendToServer(new NetworkMessage(ServerCommand.CREATE_CHAT, Identifier.CLIENT, new String[]{"test","invalidparticipant1,invalidparticipant2"}));
        response = client.listenToServer();
        assertEquals(ClientCommand.CREATE_CHAT_FAILURE, response.getCommand());
    }

    @Test(timeout = 5000)
    public synchronized void testSaveProfile() {
        client.sendToServer(new NetworkMessage(ServerCommand.SAVE_PROFILE, Identifier.CLIENT, "badinfo:badinfo"));
        NetworkMessage response = client.listenToServer();
        assertEquals(ClientCommand.SAVE_PROFILE_FAILURE, response.getCommand());
    }
//
//    @Test(timeout = 1000)
//    public synchronized void testAddFriend() {
//        client.sendToServer(new NetworkMessage(ServerCommand.ADD_FRIEND, Identifier.CLIENT, "newuser"));
//        NetworkMessage response = client.listenToServer();
//        assertEquals(ClientCommand.ADD_FRIEND_SUCCESS, response.getCommand());
//
//        client.sendToServer(new NetworkMessage(ServerCommand.ADD_FRIEND, Identifier.CLIENT, "invaliduser"));
//        response = client.listenToServer();
//        assertEquals(ClientCommand.ADD_FRIEND_FAILURE, response.getCommand());
//    }
//
//    @Test(timeout = 1000)
//    public synchronized void testBlockUser() {
//        client.sendToServer(new NetworkMessage(ServerCommand.BLOCK_USER, Identifier.CLIENT, "newuser"));
//        NetworkMessage response = client.listenToServer();
//        assertEquals(ClientCommand.BLOCK_USER_SUCCESS, response.getCommand());
//
//        client.sendToServer(new NetworkMessage(ServerCommand.BLOCK_USER, Identifier.CLIENT, "invaliduser"));
//        response = client.listenToServer();
//        assertEquals(ClientCommand.BLOCK_USER_FAILURE, response.getCommand());
//    }

}
