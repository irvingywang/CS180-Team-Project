import java.io.Serializable;

public class NetworkMessage implements Serializable {
    private Object message;
    private ServerCommand serverCommand;
    private Identifier identifier;
    private ClientCommand clientCommand;

    public NetworkMessage(ServerCommand serverCommand, Identifier identifier, Object message) {
        this.message = message;
        this.serverCommand = serverCommand;
        this.identifier = identifier;
    }

    public NetworkMessage(ClientCommand clientCommand, Identifier identifier, Object message) {
        this.message = message;
        this.identifier = identifier;
        this.clientCommand = clientCommand;
    }

    public Object getMessage() {
        return message;
    }

    public Enum getCommand() {
        return serverCommand != null ? serverCommand : clientCommand;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return String.format("ServerCommand: %s, Identifier: %s, Message: %s", serverCommand, identifier, message);
    }
}
