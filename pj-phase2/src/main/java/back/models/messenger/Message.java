package back.models.messenger;

import back.models.Attachment;
import back.models.users.User;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Message {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    User sender;

    @Column
    String text;

    @OneToMany()
    List<Attachment> attachments;

    @ManyToOne
    @JoinColumn(name = "chatfeed_id")
    ChatFeed chatFeed;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime registerTime;

    private Message() {
    }

    public Message(User sender, String text, List<Attachment> attachments, ChatFeed chatFeed) {
        this.sender = sender;
        this.text = text;
        this.attachments = attachments;
        this.chatFeed = chatFeed;

        registerTime = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public ChatFeed getChatFeed() {
        return chatFeed;
    }

    public void setChatFeed(ChatFeed chatFeed) {
        this.chatFeed = chatFeed;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }
}
