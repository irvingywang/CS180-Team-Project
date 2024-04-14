import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.net.Socket;

public class ClientTest {
    private static final int PORT = 9876; // Example port number for testing
    private static final String SERVER_ADDRESS = "localhost"; // Example server address for testing
    private Client client;
    private ObjectOutputStream objOS;

    @Before
    public void setUp() throws IOException {
        Socket socket = new Socket(SERVER_ADDRESS, PORT);
        objOS = new ObjectOutputStream(socket.getOutputStream());
        objOS.flush();
        ObjectInputStream objIS = new ObjectInputStream(socket.getInputStream());

        client = new Client();

        objIS.close();
        objOS.close();
        socket.close();
    }

    @Test
    public void testConnectToServer() {
        assertTrue(client.connectToServer());
    }

    @Test
    public void testSendToServer() {
        NetworkMessage message = new NetworkMessage(ServerCommand.LOGIN,
                Client.IDENTIFIER, "PurduePete, PurduePete123");
        assertTrue(client.connectToServer());
        assertTrue(client.sendToServer(message));
    }

//    @Test
//    public void testReadMessage() {
//        NetworkMessage expectedMessage = new NetworkMessage(ServerCommand.LOGIN,
//                Client.IDENTIFIER, "PurduePete, PurduePete123");
//        try {
//            objOS.writeObject(expectedMessage);
//            objOS.flush();
//            NetworkMessage receivedMessage = client.readMessage();
//            assertNotNull(receivedMessage);
//            assertEquals(expectedMessage, receivedMessage);
//        } catch (IOException e) {
//            fail("IOException occurred: " + e.getMessage());
//        }
//    }
}