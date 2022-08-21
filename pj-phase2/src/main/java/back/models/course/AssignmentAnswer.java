package back.models.course;

import back.models.Attachment;
import back.models.users.Student;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
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

}
