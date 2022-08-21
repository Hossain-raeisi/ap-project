package front.app.fxml_controllers;

import front.commons.data_class.ExamData;
import front.app.views.ExamView;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class Exams {
    @FXML
    ListView<Pane> examsListView;

    @FXML
    public void initialize() {
        if (front.services.util.Exams.userExamsData != null) {
            for (ExamData examData : front.services.util.Exams.userExamsData) {
                examsListView.getItems().add(new ExamView(examData));
            }
        }
    }
}
