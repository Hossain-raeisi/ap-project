package front.app.fxml_controllers;

import front.app.views.CourseView;
import front.commons.data_class.CourseData;
import front.services.model_handlers.Course;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class MyCourses {

    @FXML
    ListView<CourseView> courses;

    public void initialize() {
        for (CourseData courseData : front.services.model_handlers.CW.getCoursesData()) {
            courses.getItems().add((new CourseView(courseData)));
        }

        courses.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, courseView, t1) ->
                        showCoursePage(((Label) courses.getSelectionModel().getSelectedItem().getChildren().get(5)).getText())
        );
    }

    public void showCoursePage(String courseId) {
        Course.showPage(courseId);
    }
}
