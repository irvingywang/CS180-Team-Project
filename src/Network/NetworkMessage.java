package Network;

import java.io.Serializable;

/**
 * Project05 -- objects.NetworkMessage
 *
 * Allows for the creation of a message that is to be sent between
 * the client and server.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 *
 * @version April 14, 2024
 *
 */
public class NetworkMessage implements Serializable {
    private Object object;
    private ServerCommand serverCommand;
    private Identifier identifier;
    private ClientCommand clientCommand;

    /**
     * Creates a objects.NetworkMessage with a Network.ServerCommand.
     *
     * @param serverCommand - the command from the server
     * @param identifier    - the identifier for the message
     * @param object        - the object associated with the message
     */
    public NetworkMessage(ServerCommand serverCommand, Identifier identifier, Object object) {
        this.object = object;
        this.serverCommand = serverCommand;
        this.identifier = identifier;
    }

    /**
     * Creates a objects.NetworkMessage with a Client.ClientCommand.
     *
     * @param clientCommand - the command from the client
     * @param identifier    - the identifier for the message
     * @param object        - the object associated with the message
     */
    public NetworkMessage(ClientCommand clientCommand, Identifier identifier, Object object) {
        this.object = object;
        this.identifier = identifier;
        this.clientCommand = clientCommand;
    }

    /**
     * Returns the object associated with the message.
     *
     * @return the object associated with the message
     */
    public Object getObject() {
        return object;
    }

    /**
     * Returns the command associated with the message.
     *
     * @return the command associated with the message
     */
    public Enum getCommand() {
        return serverCommand != null ? serverCommand : clientCommand;
    }

    /**
     * Returns the identifier for the message.
     *
     * @return the identifier for the message
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    /**
     * Returns a string representation of the objects.NetworkMessage.
     *
     * @return a string representation of the objects.NetworkMessage
     */
    @Override
    public String toString() {
        return String.format("Network.ServerCommand: %s, Network.Identifier: %s, objects.Message: %s", serverCommand, identifier, object);
    }
}
