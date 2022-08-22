package front.app.fxml_controllers;

import front.commons.data_class.CourseData;
import front.commons.enums.CourseLevel;
import front.services.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.UUID;

public class AddCourse {

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

    public void addCourseButtonAction(ActionEvent ignoredActionEvent) {
        Client client = Client.getInstance();

        String name = nameField.getText();
        UUID facultyId = UUID.fromString(facultyField.getText());

        if (!(client.getCurrentUserData().facultyId.equals(facultyId)))
            return;


        UUID professorId = UUID.fromString(professorIdField.getText());
        int size = Integer.parseInt(sizeField.getText());

        CourseLevel level;
        switch (levelField.getText()) {
            case "bachelor" -> level = CourseLevel.bachelor;
            case "master" -> level = CourseLevel.master;
            case "doctorate" -> level = CourseLevel.doctorate;
            default -> throw new IllegalStateException("Unexpected value: " + levelField.getText());
        }

        CourseData newCourseData = new CourseData(
                facultyId,
                professorId,
                level,
                name,
                size);

        newCourseData.weekDays.addAll(Arrays.asList(daysField.getText().split(",")));

        newCourseData.time = timeField.getText();


        client.addCourse(newCourseData);

        front.services.model_handlers.AddCourse.showPage();
    }
}
