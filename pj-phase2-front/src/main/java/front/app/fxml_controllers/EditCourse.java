package front.app.fxml_controllers;

import front.commons.data_class.CourseData;
import front.commons.enums.CourseLevel;
import front.services.Client;
import front.services.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.UUID;

public class EditCourse {

    @FXML
    TextField nameField;
    @FXML
    TextField facultyField;
    @FXML
    TextField professorIdField;
    @FXML
    TextField levelField;
    @FXML
    TextField sizeField;
    @FXML
    TextField timeField;
    @FXML
    TextField daysField;


    public void editCourseButtonAction(ActionEvent ignoredActionEvent) {
        CourseData courseData = front.services.model_handlers.EditCourse.getCurrentCourseData();
        Client client = Client.getInstance();

        String name = nameField.getText();
        if (!name.equals("")) {
            courseData.name = name;
        }

        String faculty = facultyField.getText();
        if (!faculty.equals("")) {
            courseData.facultyId = UUID.fromString(faculty);
        }

        String professorID = professorIdField.getText();
        if (!professorID.equals("")) {
            courseData.professorId = UUID.fromString(professorID);
        }

        String size = sizeField.getText();
        if (!size.equals("")) {
            courseData.size = Integer.parseInt(size);
        }

        switch (levelField.getText()) {
            case "bachelor" -> courseData.level = CourseLevel.bachelor;
            case "master" -> courseData.level = CourseLevel.master;
            case "doctorate" -> courseData.level = CourseLevel.doctorate;
        }

        String days = daysField.getText();
        if (!days.equals("")) {
            courseData.weekDays.clear();
            courseData.weekDays.addAll(Arrays.asList(days.split(",")));
        }

        String time = timeField.getText();
        if (!time.equals("")) {
            courseData.time = time;
        }

        Logger.Info("Edit course: " + courseData.id);

        client.updateCourse(courseData);

        front.services.model_handlers.Courses.showAllCourses();
    }
}
