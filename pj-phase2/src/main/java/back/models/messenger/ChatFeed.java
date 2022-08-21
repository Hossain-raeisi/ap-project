package back.models.messenger;

import back.models.users.User;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class ChatFeed {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToMany(mappedBy = "chatFeeds")
    List<User> users;

    @OneToMany(mappedBy = "chatFeed")
    List<Message> messages;

    private ChatFeed() {

    }

    public ChatFeed(List<User> users, List<Message> messages) {
        this.users = users;
        this.messages = messages;
    }

    public ChatFeed(ArrayList<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
