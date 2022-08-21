package front.services.model_handlers;

import front.app.SceneControl;
import front.commons.data_class.AssignmentAnswerData;
import front.commons.data_class.AttachmentData;
import front.services.Client;

import java.util.UUID;

public class AssignmentAnswer {
    static UUID assignmentAnswerId;
    static EducationalContent.UserType userType;

    public static void showPage(UUID assignmentAnswerId, EducationalContent.UserType userType) {
        AssignmentAnswer.assignmentAnswerId = assignmentAnswerId;
        AssignmentAnswer.userType = userType;
        SceneControl.changeScene(SceneControl.SceneType.assignmentAnswerPage);
    }

    public static void addAnswer(UUID assignmentId, String answerText) {
        var client = Client.getInstance();
        var assignmentData = client.getAssignmentData(assignmentId);
        var assignmentAnswerData = new AssignmentAnswerData(
                assignmentData.id,
                client.getCurrentUserData().id,
                null,
                answerText
        );
        client.addAssignmentAnswer(assignmentAnswerData);
    }

    public static void addAnswer(UUID assignmentId, AttachmentData attachmentData) {
        var client = Client.getInstance();
        var assignmentData = client.getAssignmentData(assignmentId);
        var assignmentAnswerData = new AssignmentAnswerData(
                assignmentData.id,
                client.getCurrentUserData().id,
                attachmentData.id,
                null
        );
        client.addAssignmentAnswer(assignmentAnswerData);
    }
}
