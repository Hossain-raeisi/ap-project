package front.services.util;

import front.app.SceneControl;
import front.commons.data_class.AssignmentData;
import front.services.Client;

import java.util.UUID;

public class Assignment {
    static AssignmentData assignmentData;

    public static void showPage(String assignmentId) {
        assignmentData = Client.getInstance().getAssignmentData(UUID.fromString(assignmentId));
        SceneControl.changeScene(SceneControl.SceneType.assignmentPage);
    }

    public static void showNewAssignmentPage(String courseId) {

    }
}
