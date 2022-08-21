package back.models.course;

import back.models.Attachment;
import commons.enums.AssignmentAcceptableTypes;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Assignment {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime startTime;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime fullScoreTime;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime endTime;

    @Column
    String name;

    @Column
    String description;

    @OneToOne
    Attachment attachment;

    @Enumerated(EnumType.ORDINAL)
    AssignmentAcceptableTypes answerType;


    private Assignment() {
    }

    public Assignment(Course course, LocalDateTime startTime, LocalDateTime fullScoreTime, LocalDateTime endTime,
                      String name, String description, Attachment attachment, AssignmentAcceptableTypes answerType) {
        this.course = course;
        this.startTime = startTime;
        this.fullScoreTime = fullScoreTime;
        this.endTime = endTime;
        this.name = name;
        this.description = description;
        this.attachment = attachment;
        this.answerType = answerType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFullScoreTime() {
        return fullScoreTime;
    }

    public void setFullScoreTime(LocalDateTime fullScoreTime) {
        this.fullScoreTime = fullScoreTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public AssignmentAcceptableTypes getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AssignmentAcceptableTypes answerType) {
        this.answerType = answerType;
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
