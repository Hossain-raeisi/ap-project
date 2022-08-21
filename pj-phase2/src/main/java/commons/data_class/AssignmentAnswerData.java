package commons.data_class;

import java.util.UUID;

public class AssignmentAnswerData {

    public UUID id;
    public String assignmentName;
    public UUID assignmentId;
    public String studentName;
    public UUID studentId;
    public UUID attachmentId;
    public String text;

    public AssignmentAnswerData(UUID id, String assignmentName, UUID assignmentId, String studentName, UUID studentId,
                                UUID attachmentId, String text) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.assignmentId = assignmentId;
        this.studentName = studentName;
        this.studentId = studentId;
        this.attachmentId = attachmentId;
        this.text = text;
    }
}
