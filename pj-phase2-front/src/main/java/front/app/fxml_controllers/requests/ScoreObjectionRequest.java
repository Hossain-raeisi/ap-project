package front.app.fxml_controllers.requests;

import front.commons.data_class.ScoreData;
import front.services.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ScoreObjectionRequest {

    @FXML
    Label courseNameLabel;
    @FXML
    Label courseScoreLabel;
    @FXML
    TextArea objectionTextArea;
    @FXML
    Button objectButton;

    @FXML
    public void initialize() {
        ScoreData scoreData = front.services.model_handlers.ScoreObjectionRequest.getScoreData();
        courseNameLabel.setText(scoreData.courseName);
        courseScoreLabel.setText(String.valueOf(scoreData.finalScore));
    }

    public void objectButtonAction(ActionEvent ignoredActionEvent) {
        var client = Client.getInstance();

        front.services.model_handlers.ScoreObjectionRequest.object(objectionTextArea.getText());
        front.services.model_handlers.TemporaryScores.showPage(client.getStudentData(client.getCurrentUserData().id));
    }

}
