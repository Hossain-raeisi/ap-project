package back.models.course;

import back.models.Attachment;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        indexes = {
                @Index(columnList = "course_id"),
        }
)
public class EducationalContent {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column
    String name;

    @Column
    List<String> texts;

    @OneToMany(cascade = CascadeType.ALL)
    List<Attachment> medias;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    private EducationalContent() {
    }

    public EducationalContent(String name, ArrayList<String> texts, ArrayList<Attachment> medias, Course course) {
        this.name = name;
        this.texts = texts;
        this.medias = medias;
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public List<Attachment> getMedias() {
        return medias;
    }

    public void setMedias(List<Attachment> medias) {
        this.medias = medias;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
