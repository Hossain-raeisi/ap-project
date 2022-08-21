package back.models.request;

import back.models.users.User;
import commons.enums.RequestStatus;
import commons.enums.RequestType;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        indexes = {
                @Index(columnList = "type"),
                @Index(columnList = "status"),
                @Index(columnList = "assigner_id"),
        }
)
public class Request {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @Enumerated(EnumType.ORDINAL)
    RequestType type;

    @Column
    String title;

    @Column
    String description;

    @Column
    RequestStatus status;

    @Column
    String response;

    @ManyToOne
    @JoinColumn(name = "assigner_id", nullable = false)
    User assigner;

    @ManyToMany(mappedBy = "assigneeRequests")
    List<User> assignees;

    protected Request() {
    }

    public Request(User assigner, List<User> assignees, String title, String description, RequestType type) {
        this.assigner = assigner;
        this.assignees = assignees;
        this.title = title;
        this.description = description;
        this.status = RequestStatus.pending;
        this.type = type;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public User getAssigner() {
        return assigner;
    }

    public void setAssigner(User assigner) {
        this.assigner = assigner;
    }

    public List<User> getAssignees() {
        return assignees;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void changeStatus(RequestStatus status) {
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RequestType getType() {
        return type;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
