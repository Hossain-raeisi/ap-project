package front.commons.data_class;

import java.time.LocalDateTime;
import java.util.UUID;

public class ExamData {
    public UUID id;
    public String courseName;
    public UUID courseId;
    public LocalDateTime time;
    public String name;

    public ExamData(UUID id, String courseName, UUID courseId, LocalDateTime time, String name) {
        this.id = id;
        this.courseName = courseName;
        this.courseId = courseId;
        this.time = time;
        this.name = name;
    }

    public ExamData(UUID courseId, LocalDateTime time, String name) {
        this.courseId = courseId;
        this.time = time;
        this.name = name;
    }
}
