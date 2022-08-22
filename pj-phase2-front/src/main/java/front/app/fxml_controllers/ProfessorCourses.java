package front.app.fxml_controllers;

import front.app.views.CourseView;
import front.commons.data_class.CourseData;
import front.services.model_handlers.CourseScores;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class ProfessorCourses {

    @FXML
    ListView<Pane> coursesListView;

    public void initialize() {
        for (CourseData courseData : front.services.model_handlers.ProfessorCourses.getProfessorCoursesData()) {
            coursesListView.getItems().add((new CourseView(courseData)));
        }

        coursesListView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, pane, t1) -> showCoursePage(((Label) coursesListView.getSelectionModel().getSelectedItem().getChildren().get(5)).getText())
        );
    }

    public void showCoursePage(String courseId) {
        CourseScores.showPage(courseId);
    }
}
