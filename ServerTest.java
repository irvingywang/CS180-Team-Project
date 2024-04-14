import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.net.Socket;

public class ServerTest {
    private static final int PORT = 1234; // Example port number for testing
    private static final String SERVER_ADDRESS = "localhost"; // Example server address for testing

    private Server server;
    private Socket socket;
    private ObjectOutputStream objOS;
    private ObjectInputStream objIS;

    @Before
    public void setUp() throws IOException {
        server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();

        socket = new Socket(SERVER_ADDRESS, PORT);
        objOS = new ObjectOutputStream(socket.getOutputStream());
        objOS.flush();
        objIS = new ObjectInputStream(socket.getInputStream());

        objOS.close();
        objIS.close();
        socket.close();
    }

    @Test
    public void testLogin() {
        assertTrue(server.login("username", "password"));
        assertFalse(server.login("nonexistent_user", "wrong_password"));
    }

    @Test
    public void testCreateUser() {
        assertTrue(server.createUser("new_user", "password", "New User", true));
        assertFalse(server.createUser("existing_user", "password", "Existing User", true));
    }
}