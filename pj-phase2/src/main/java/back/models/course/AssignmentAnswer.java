package back.models.course;

import back.models.Attachment;
import back.models.users.Student;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        indexes = {
                @Index(columnList = "student_id"),
                @Index(columnList = "assignment_id"),
                @Index(columnList = "sent_time"),
        }
)
public class AssignmentAnswer {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    @OneToOne
    Attachment attachment;

    @Column
    String text;

    @Column(name = "sent_time")
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime sentTime;

    @Column
    Float score;

    private AssignmentAnswer() {
    }

    public AssignmentAnswer(Assignment assignment, Student student, Attachment attachment, String text) {
        this.assignment = assignment;
        this.student = student;
        this.attachment = attachment;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
