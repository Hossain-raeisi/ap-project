package commons.data_class;

import java.util.UUID;

public class ScoreData {
    public UUID id;
    public String courseName;
    public UUID courseId;
    public String studentName;
    public UUID studentId;
    public Float temporaryScore;
    public Float finalScore;
    public Boolean finalized;

    public ScoreData(UUID id, String courseName, UUID courseId, String studentName, UUID studentId,
                     Float temporaryScore, Float finalScore, Boolean finalized) {
        this.id = id;
        this.courseName = courseName;
        this.courseId = courseId;
        this.studentName = studentName;
        this.studentId = studentId;
        this.temporaryScore = temporaryScore;
        this.finalScore = finalScore;
        this.finalized = finalized;
    }
}
