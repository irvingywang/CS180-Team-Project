import Database.Database;
import Network.*;
import Objects.User;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

}
