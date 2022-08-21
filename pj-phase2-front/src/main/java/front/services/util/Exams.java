package front.services.util;
import front.commons.data_class.ExamData;
import front.app.SceneControl;
import front.services.Client;

import java.util.ArrayList;

public class Exams {
    public static ArrayList<ExamData> userExamsData;

    public static void showUserExams(){
        userExamsData = Client.getInstance().getUserActiveExams();
        SceneControl.changeScene(SceneControl.SceneType.examsPage);
    }

    public static void showNewExamPage(String courseId) {

    }

}
