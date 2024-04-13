import java.io.Serializable;

public class NetworkMessage implements Serializable {
    private Object object;
    private ServerCommand serverCommand;
    private Identifier identifier;
    private ClientCommand clientCommand;

    public NetworkMessage(ServerCommand serverCommand, Identifier identifier, Object object) {
        this.object = object;
        this.serverCommand = serverCommand;
        this.identifier = identifier;
    }

    public NetworkMessage(ClientCommand clientCommand, Identifier identifier, Object object) {
        this.object = object;
        this.identifier = identifier;
        this.clientCommand = clientCommand;
    }

    public Object getObject() {
        return object;
    }

    public Enum getCommand() {
        return serverCommand != null ? serverCommand : clientCommand;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return String.format("ServerCommand: %s, Identifier: %s, Message: %s", serverCommand, identifier, object);
    }
}
