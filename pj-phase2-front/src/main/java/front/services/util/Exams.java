package front.services.util;
import front.commons.data_class.CourseData;
import front.commons.data_class.ExamData;
import front.app.SceneControl;
import front.services.Client;

import java.util.ArrayList;
import java.util.UUID;

public class Exams {
    public static ArrayList<ExamData> userExamsData;
    static UUID courseId;

    public static void showUserExams(){
        userExamsData = Client.getInstance().getUserActiveExams();
        SceneControl.changeScene(SceneControl.SceneType.examsPage);
    }

    public static void showNewExamPage(String courseId) {
        Exams.courseId = UUID.fromString(courseId);
        SceneControl.changeScene(SceneControl.SceneType.newExamPage);
    }

    public static CourseData getCurrentCourseData() {
        return Client.getInstance().getCourseData(courseId);
    }

}
