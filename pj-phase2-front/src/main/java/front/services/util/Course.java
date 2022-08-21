package front.services.util;

import front.app.SceneControl;
import front.commons.data_class.CourseData;
import front.services.Client;

import java.util.UUID;

public class Course {

    static CourseData courseData;

    public static void showPage(String courseId) {
        courseData = Client.getInstance().getCourseData(UUID.fromString(courseId));
        SceneControl.changeScene(SceneControl.SceneType.coursePage);
    }

    public static CourseData getCurrentCourseData() {
        return courseData;
    }
}
