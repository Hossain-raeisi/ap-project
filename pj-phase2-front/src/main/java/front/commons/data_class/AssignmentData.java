package front.commons.data_class;

import front.commons.enums.AssignmentAcceptableTypes;

import java.time.LocalDateTime;
import java.util.UUID;

public class AssignmentData {

    public UUID id;
    public String courseName;
    public UUID courseId;
    public LocalDateTime startTime;
    public LocalDateTime fullScoreTime;
    public LocalDateTime endTime;
    public String name;
    public String description;
    public UUID attachmentId;
    public AssignmentAcceptableTypes answerType;

    public AssignmentData(UUID id, String courseName, UUID courseId, LocalDateTime startTime,
                          LocalDateTime fullScoreTime, LocalDateTime endTime, String name, String description,
                          UUID attachmentId, AssignmentAcceptableTypes answerType) {
        this.id = id;
        this.courseName = courseName;
        this.courseId = courseId;
        this.startTime = startTime;
        this.fullScoreTime = fullScoreTime;
        this.endTime = endTime;
        this.name = name;
        this.description = description;
        this.attachmentId = attachmentId;
        this.answerType = answerType;
    }
}
