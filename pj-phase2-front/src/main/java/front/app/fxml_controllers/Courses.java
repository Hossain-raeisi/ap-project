package front.app.fxml_controllers;

import front.commons.data_class.CourseData;
import front.commons.enums.CourseLevel;
import front.commons.enums.UserType;
import front.app.views.CourseView;
import front.services.Client;
import front.services.util.AddCourse;
import front.services.util.EditCourse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.UUID;


public class Courses {

    @FXML
    Button editCourseButton;
    @FXML
    Button addCourseButton;
    @FXML
    Button delCourseButton;
    @FXML
    TextField courseIdField;
    @FXML
    Button filterButton;
    @FXML
    AnchorPane base;
    @FXML
    ListView<Pane> coursesListView;
    @FXML
    TextField nameFilterField;
    @FXML
    TextField sizeFilterField;
    @FXML
    TextField facultyFilterField;
    @FXML
    TextField idFilterField;
    @FXML
    TextField levelFilterField;

    @FXML
    public void initialize() {
        var client = Client.getInstance();

        for (CourseData courseData : front.services.util.Courses.coursesData) {
            coursesListView.getItems().add((new CourseView(courseData)));
        }

        if (!(client.getUserType(client.getCurrentUserData().id) == UserType.deputyEducationProfessor)) {
            editCourseButton.setVisible(false);
            addCourseButton.setVisible(false);
            delCourseButton.setVisible(false);
            courseIdField.setVisible(false);
        }
    }

    public void filterButtonAction(ActionEvent ignoredActionEvent) {
        String nameFilter = nameFilterField.getText();
        String sizeFilterString = sizeFilterField.getText();
        String facultyFilterString = facultyFilterField.getText();
        String idFilterString = idFilterField.getText();
        String levelFilterString = levelFilterField.getText();

        if (nameFilter.equals("") && sizeFilterString.equals("") && facultyFilterString.equals("") && idFilterString.equals("") && levelFilterString.equals("")) {
            front.services.util.Courses.showAllCourses();
            return;
        }

        if (nameFilter.equals(""))
            nameFilter = null;

        Integer sizeFilter = null;
        if (!sizeFilterString.equals("")) {
            sizeFilter = Integer.parseInt(sizeFilterString);
        }

        String idFilter = null;
        if (!idFilterString.equals("")) {
            idFilter = idFilterString;
        }

        CourseLevel levelFilter = null;
        switch (levelFilterString) {
            case "bachelor" -> levelFilter = CourseLevel.bachelor;
            case "master" -> levelFilter = CourseLevel.master;
            case "doctorate" -> levelFilter = CourseLevel.doctorate;
        }

        front.services.util.Courses.showFilteredCourses(nameFilter, sizeFilter, facultyFilterString, idFilter, levelFilter);
    }

    public void editCourseButtonAction() {
        var client = Client.getInstance();
        UUID courseId = UUID.fromString(courseIdField.getText());
        if (client.getCourseData(courseId).facultyId.equals(client.getCurrentUserData().facultyId)) {
            EditCourse.showPage(client.getCourseData(courseId));
        }
    }

    public void addCourseButtonAction() {
        AddCourse.showPage();
    }

    public void delCourseButtonAction() {
        front.services.util.Courses.delCourse(courseIdField.getText());
        front.services.util.Courses.showAllCourses();
    }
}
