package commons.data_class;

import java.util.ArrayList;
import java.util.UUID;

public class ChatFeedData {

    public UUID id;
    public ArrayList<UUID> membersId;
    public ArrayList<UUID> messagesId;

    public ChatFeedData(UUID id, ArrayList<UUID> membersId, ArrayList<UUID> messagesId) {
        this.id = id;
        this.membersId = membersId;
        this.messagesId = messagesId;
    }
}
