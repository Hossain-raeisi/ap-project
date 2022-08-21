package front.services.model_handlers;

import front.app.SceneControl;
import front.commons.data_class.CourseData;
import front.services.Client;

import java.util.ArrayList;

public class CW {
    static ArrayList<CourseData> coursesData;

    public static void showPage(){
        coursesData = Client.getInstance().getUserActiveCourses();
        SceneControl.changeScene(SceneControl.SceneType.myCoursesPage);
    }

    public static ArrayList<CourseData> getCoursesData() {
        return coursesData;
    }
}
