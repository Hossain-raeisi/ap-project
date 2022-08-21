package front.app.fxml_controllers;

import front.commons.data_class.ScoreData;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ProfessorAllFinalizedScores {

    @FXML
    ListView<String> scoresListView;

    @FXML
    public void initialize() {
        for (ScoreData scoreData : front.services.util.ProfessorAllFinalizedScores.getProfessorAllFinalizedScoreData()) {
            scoresListView.getItems().add(scoreData.courseName + "     " +
                    scoreData.studentName + "     " + scoreData.finalScore);
        }
    }
}
