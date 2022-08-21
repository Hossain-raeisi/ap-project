package commons.data_class;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class MessageData {
    public UUID id;
    public String senderName;
    public UUID senderId;
    public String text;
    public ArrayList<UUID> attachmentsId;
    public UUID chatFeedId;
    public LocalDateTime registerTime;

    public MessageData(UUID id, String senderName, UUID senderId, String text, ArrayList<UUID> attachmentsId,
                       UUID chatFeedId, LocalDateTime registerTime) {
        this.id = id;
        this.senderName = senderName;
        this.senderId = senderId;
        this.text = text;
        this.attachmentsId = attachmentsId;
        this.chatFeedId = chatFeedId;
        this.registerTime = registerTime;
    }
}
