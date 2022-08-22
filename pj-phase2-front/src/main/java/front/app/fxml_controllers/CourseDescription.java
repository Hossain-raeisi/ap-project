package front.app.fxml_controllers;

import front.commons.data_class.CourseData;
import front.commons.data_class.CourseScoringData;
import front.commons.data_class.ScoreData;
import front.services.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.UUID;

public class CourseDescription {

    @FXML
    Label courseNameField;
    @FXML
    Label totalGradePointAverageLabel;
    @FXML
    ListView<String> studentsList;
    @FXML
    Label passedNumberLabel;
    @FXML
    Label failedNumberLabel;
    @FXML
    Label totalGradePointAverageWithoutFailedsLabel;

    @FXML
    public void initialize(){
        CourseData courseData = front.services.model_handlers.CourseDescription.getCourseData();

        courseNameField.setText(courseData.name);

        CourseScoringData courseScoringData = Client.getInstance().getCourseScoringData(courseData.id);

        totalGradePointAverageLabel.setText(String.valueOf(courseScoringData.totalGradePointAverage));
        passedNumberLabel.setText(String.valueOf(courseScoringData.passedNumber));
        failedNumberLabel.setText(String.valueOf(courseScoringData.failedNumber));
        totalGradePointAverageWithoutFailedsLabel.setText(String.valueOf(courseScoringData.totalGradePointAverageWithoutFails));

        for (UUID scoreId: courseData.scoresId) {
            ScoreData scoreData = Client.getInstance().getScoreData(scoreId);
            studentsList.getItems().add(scoreData.studentName + "   " + scoreData.finalScore);
        }
    }
}
