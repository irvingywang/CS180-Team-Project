import java.io.Serializable;

public class NetworkMessage implements Serializable {
    private String message;
    private ServerCommand serverCommand;
    private String identifier;

    public NetworkMessage(ServerCommand serverCommand, String identifier, String message) {
        this.message = message;
        this.serverCommand = serverCommand;
        this.identifier = identifier;
    }

    public String getMessage() {
        return message;
    }

    public ServerCommand getServerCommand() {
        return serverCommand;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return String.format("ServerCommand: %s, Identifier: %s, Message: %s", serverCommand, identifier, message);
    }
}
