package front.services.util;

import front.commons.data_class.CourseData;
import front.app.SceneControl;
import front.services.Client;

import java.util.UUID;

public class CourseDescription {

    public static CourseData courseData;

    public static void showPage(String courseId) {
        courseData = Client.getInstance().getCourseData(UUID.fromString(courseId));
        SceneControl.changeScene(SceneControl.SceneType.courseDescriptionPage);
    }

    public static CourseData getCourseData() {
        return courseData;
    }
}
