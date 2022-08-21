package front.services.model_handlers;

import front.commons.data_class.ScoreData;
import front.commons.data_class.StudentData;
import front.app.SceneControl;
import front.services.Client;

import java.util.ArrayList;

public class TemporaryScores {

    public static StudentData studentData;

    public static void showPage(StudentData studentData) {
        TemporaryScores.studentData = studentData;
        SceneControl.changeScene(SceneControl.SceneType.temporaryScoresStudentPage);

    }

    public static ArrayList<ScoreData> getNonFinalizedActiveScoresData() {
        return Client.getInstance().getStudentNonFinalizedActiveScores(studentData.id);
    }
}
