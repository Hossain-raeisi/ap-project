package front.app.fxml_controllers;

import front.commons.data_class.ScoreData;
import front.services.model_handlers.ScoreObjectionRequest;
import front.services.model_handlers.TemporaryScores;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class TemporaryScoresStudent {

    @FXML
    ListView<String> scoresList;

    @FXML
    public void initialize() {
        ArrayList<ScoreData> scoresData = TemporaryScores.getNonFinalizedActiveScoresData();
        for (ScoreData scoreData : scoresData) {
            scoresList.getItems().add(scoreData.courseName + " : " + scoreData.temporaryScore);

        }

        scoresList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, s, t1) -> ScoreObjectionRequest.showPage(scoresData.get(scoresList.getSelectionModel().getSelectedIndex()))
        );
    }
}
