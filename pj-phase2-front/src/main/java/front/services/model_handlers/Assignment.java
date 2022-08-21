package front.services.model_handlers;

import front.app.SceneControl;
import front.commons.data_class.AssignmentData;
import front.services.Client;

import java.util.UUID;

public class Assignment {
    static AssignmentData assignmentData;
    static UUID assignmentId;

    public static void showPage(String assignmentId) {
        Assignment.assignmentId = UUID.fromString(assignmentId);
        SceneControl.changeScene(SceneControl.SceneType.assignmentPage);
    }

    public static void showNewAssignmentPage(String courseId) {

    }

    public static AssignmentData getCurrentAssignmentData() {
        return Client.getInstance().getAssignmentData(assignmentData.id);
    }
}
