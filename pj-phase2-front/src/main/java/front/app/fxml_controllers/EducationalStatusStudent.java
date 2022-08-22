package front.app.fxml_controllers;

import front.commons.data_class.StudentData;
import front.services.Client;
import front.services.model_handlers.EducationalStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.UUID;

public class EducationalStatusStudent {

    @FXML
    Label passedCoursesTotalSizeLabel;
    @FXML
    Label totalGradePointAverageLabel;
    @FXML
    ListView<String> passedCoursesList;

    @FXML
    public void initialize() {
        var client = Client.getInstance();

        StudentData studentData = EducationalStatus.getStudentData();

        totalGradePointAverageLabel.setText(String.valueOf(studentData.totalGradePointAverage));

        int passedCoursesTotalSizeInt = 0;
        for (UUID scoreId : studentData.passedScoresId) {
            passedCoursesTotalSizeInt += client.getCourseData(client.getScoreData(scoreId).courseId).size;
        }
        passedCoursesTotalSizeLabel.setText(String.valueOf(passedCoursesTotalSizeInt));

        for (UUID scoreId : studentData.passedScoresId) {
            var scoreData = client.getScoreData(scoreId);
            passedCoursesList.getItems().add(scoreData.courseName + " : " + scoreData.finalScore);
        }
    }
}
