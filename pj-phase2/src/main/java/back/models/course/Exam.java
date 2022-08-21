package back.models.course;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        indexes = {
                @Index(columnList = "course_id"),
                @Index(columnList = "time"),
        }
)
public class Exam {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime time;

    @Column
    String name;

    protected Exam() {
    }

    public Exam(String name, LocalDateTime time, Course course) {
        this.name = name;
        this.time = time;
        this.course = course;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
