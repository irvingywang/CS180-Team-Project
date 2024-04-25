import Database.Database;
import Network.*;
import Objects.User;
import org.junit.BeforeClass;
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

    @BeforeClass
    public static void setUp() {
        Server.start();
        client = new Client();
        client.connectToServer();

        database.setDataFile("test.ser");
        database.reset();
        database.initialize();


        database.addUser(
            new User("purduepete", "boilerup", "Purdue Pete", false));
    }

    @Test (timeout = 1000)
    public void login() {
        client.sendToServer(
                new NetworkMessage(ServerCommand.LOGIN, Identifier.CLIENT, "purduepete,boilerup"));
        NetworkMessage response = client.listenToServer();
        assertEquals(ClientCommand.LOGIN_SUCCESS, response.getCommand());

        client.sendToServer(
                new NetworkMessage(ServerCommand.LOGIN, Identifier.CLIENT, "purduepete,wrongpassword"));
        response = client.listenToServer();
        assertEquals(ClientCommand.LOGIN_FAILURE, response.getCommand());
    }

    @Test (timeout = 1000)
    public void createUser() {
        client.sendToServer(
                new NetworkMessage(ServerCommand.CREATE_USER, Identifier.CLIENT, "newuser,newpassword,New User"));
        NetworkMessage response = client.listenToServer();
        assertEquals(ClientCommand.CREATE_USER_SUCCESS, response.getCommand());

        client.sendToServer(
                new NetworkMessage(ServerCommand.CREATE_USER, Identifier.CLIENT, "purduepete,boilerup,Purdue Pete"));
        response = client.listenToServer();
        assertEquals(ClientCommand.CREATE_USER_FAILURE, response.getCommand());
    }

    @Test(timeout = 1000)
    public void testSearchUser() {
        client.sendToServer(new NetworkMessage(ServerCommand.SEARCH_USER, Identifier.CLIENT, "purduepete"));
        NetworkMessage response = client.listenToServer();
        assertEquals(ClientCommand.SEARCH_USER_RESULT, response.getCommand());
    }

    @Test(timeout = 1000)
    public void testSendMessage() {
        client.sendToServer(new NetworkMessage(ServerCommand.SEND_MESSAGE, Identifier.CLIENT, "recipientusername,messagecontent"));
        NetworkMessage response = client.listenToServer();
        assertEquals(ClientCommand.SEND_MESSAGE, response.getCommand());
    }

    @Test(timeout = 1000)
    public void testCreateChat() {
        client.sendToServer(new NetworkMessage(ServerCommand.CREATE_CHAT, Identifier.CLIENT, "participant1,participant2"));
        NetworkMessage response = client.listenToServer();
        assertEquals(ClientCommand.CREATE_CHAT_SUCCESS, response.getCommand());

        client.sendToServer(new NetworkMessage(ServerCommand.CREATE_CHAT, Identifier.CLIENT, "invalidparticipant1,invalidparticipant2"));
        response = client.listenToServer();
        assertEquals(ClientCommand.CREATE_CHAT_FAILURE, response.getCommand());
    }

    @Test(timeout = 1000)
    public void testShowMessage() {
        client.sendToServer(new NetworkMessage(ServerCommand.SHOW_MESSAGE, Identifier.CLIENT, "messagecontent"));
        NetworkMessage response = client.listenToServer();
        assertEquals(ClientCommand.MESSAGE_DISPLAY_SUCCESS, response.getCommand());
    }

    @Test(timeout = 1000)
    public void testGetUsers() {
        client.sendToServer(new NetworkMessage(ServerCommand.GET_USERS, Identifier.CLIENT, ""));
        NetworkMessage response = client.listenToServer();
        assertEquals(ClientCommand.GET_USERS_SUCCESS, response.getCommand());
    }

    @Test(timeout = 1000)
    public void testSaveProfile() {
        client.sendToServer(new NetworkMessage(ServerCommand.SAVE_PROFILE, Identifier.CLIENT, "username,newprofiledata"));
        NetworkMessage response = client.listenToServer();
        assertEquals(ClientCommand.SAVE_PROFILE_SUCCESS, response.getCommand());
    }
}
