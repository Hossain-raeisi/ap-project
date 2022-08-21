package front.services.util;

import front.commons.data_class.CourseData;
import front.app.SceneControl;
import front.services.Client;

import java.util.ArrayList;

public class ProfessorCourses {

    public static void showPage() {
        SceneControl.changeScene(SceneControl.SceneType.professorCoursesPage);
    }

    public static ArrayList<CourseData> getProfessorCoursesData(){
        return Client.getInstance().getUserActiveCourses();
    }
}
