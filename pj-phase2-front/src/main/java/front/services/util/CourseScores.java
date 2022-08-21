package front.services.util;

import front.commons.data_class.CourseData;
import front.app.SceneControl;
import front.services.Client;

import java.util.UUID;

public class CourseScores {

    static CourseData courseData;

    public static void showPage(String courseId){
        courseData = Client.getInstance().getCourseData(UUID.fromString(courseId));
        SceneControl.changeScene(SceneControl.SceneType.courseScoresPage);
    }

    public static CourseData getCurrentCourseData() {
        return courseData;
    }
}
