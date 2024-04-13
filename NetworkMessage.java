import java.io.Serializable;

public class NetworkMessage implements Serializable {
    private Object object;
    private ServerCommand serverCommand;
    private Identifier identifier;
    private ClientCommand clientCommand;

    /**
     * Creates a NetworkMessage with a ServerCommand.
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
     * Creates a NetworkMessage with a ClientCommand.
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
     * Returns a string representation of the NetworkMessage.
     *
     * @return a string representation of the NetworkMessage
     */
    @Override
    public String toString() {
        return String.format("ServerCommand: %s, Identifier: %s, Message: %s", serverCommand, identifier, object);
    }
}
