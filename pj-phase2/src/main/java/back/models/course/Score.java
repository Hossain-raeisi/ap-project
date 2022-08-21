package back.models.course;

import back.models.users.Student;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(
        indexes = {
                @Index(columnList = "course_id"),
                @Index(columnList = "student_id"),
                @Index(columnList = "finalized"),
        }
)
public class Score {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    @Column
    Float temporaryScore;

    @Column
    Float finalScore;

    @Column
    Boolean finalized;

    protected Score() {
    }

    public Score(Course course, Student student) {
        this.course = course;
        this.student = student;
    }

    public Float getTemporaryScore() {
        return temporaryScore;
    }

    public void setTemporaryScore(Float temporaryScore) {
        this.temporaryScore = temporaryScore;
    }

    public Float getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Float finalScore) {
        this.finalScore = finalScore;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Boolean isFinalized() {
        return finalized;
    }

    public void setFinalized(Boolean finalized) {
        this.finalized = finalized;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
