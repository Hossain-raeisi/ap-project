package front.services.util;


import front.commons.data_class.CourseData;
import front.commons.data_class.CourseFilter;
import front.commons.enums.CourseLevel;
import front.app.SceneControl;
import front.services.Client;

import java.util.ArrayList;
import java.util.UUID;

public class Courses {

    public static ArrayList<CourseData> coursesData;
    public static Client client = Client.getInstance();

    public static void showAllCourses(){
        coursesData = client.getAllCoursesData();
        SceneControl.changeScene(SceneControl.SceneType.coursesPage);
    }

    public static void showFilteredCourses(String nameFilter, Integer sizeFilter, String facultyFilterName,
                                           String idFilter, CourseLevel levelFilter){
        UUID facultyIdFilter = null;
        if (facultyFilterName != null) {
            facultyIdFilter = client.getFacultyByName(facultyFilterName).id;
        }
        CourseFilter courseFilter = new CourseFilter(
                nameFilter,
                sizeFilter,
                facultyIdFilter.toString(),
                idFilter,
                levelFilter
        );
        coursesData = client.getFilteredCoursesData(courseFilter);
        SceneControl.changeScene(SceneControl.SceneType.coursesPage);
    }

    public static void delCourse(String courseID) {
        client.removeCourse(courseID);
        Courses.showAllCourses();
    }
}
