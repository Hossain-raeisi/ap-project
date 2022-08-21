package front.app.fxml_controllers;

import front.commons.data_class.ExamData;
import front.services.Client;
import front.services.util.Exams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class NewExam {

    @FXML
    TextField examTimeField;
    @FXML
    DatePicker examDateField;
    @FXML
    TextField examNameField;
    @FXML
    Button registerButton;
    @FXML
    Label courseName;

    @FXML
    public void initialize() {
        courseName.setText(Exams.getCurrentCourseData().name);
    }

    public void registerButtonAction(ActionEvent actionEvent) {
        var examDate = examDateField.getValue();
        var examTime = LocalTime.parse(examTimeField.getText());

        var examDateTime = LocalDateTime.of(examDate, examTime);
        var courseData = Exams.getCurrentCourseData();

        ExamData examData = new ExamData(
                courseData.id,
                examDateTime,
                examNameField.getText()
        );

        Client.getInstance().addExam(examData);
    }
}
