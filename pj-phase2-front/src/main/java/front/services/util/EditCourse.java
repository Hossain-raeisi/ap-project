package front.services.util;

import front.commons.data_class.CourseData;
import front.app.SceneControl;

public class EditCourse {

    static CourseData courseData;

    public static void showPage(CourseData courseData) {
        EditCourse.courseData = courseData;
        SceneControl.changeScene(SceneControl.SceneType.editCoursePage);
    }

    public static CourseData getCurrentCourseData() {
        return courseData;
    }
}
