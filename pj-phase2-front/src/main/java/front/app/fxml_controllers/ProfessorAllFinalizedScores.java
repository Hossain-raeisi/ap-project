package front.app.fxml_controllers;

import front.commons.data_class.ScoreData;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ProfessorAllFinalizedScores {

    @FXML
    ListView<String> scoresListView;

    @FXML
    public void initialize() {
        for (ScoreData scoreData : front.services.model_handlers.ProfessorAllFinalizedScores.getProfessorAllFinalizedScoreData()) {
            scoresListView.getItems().add(scoreData.courseName + "     " +
                    scoreData.studentName + "     " + scoreData.finalScore);
        }
    }
}
