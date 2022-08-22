package front.app.fxml_controllers.requests;

import front.services.Client;
import front.services.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDateTime;

public class ThesisDefenceRoundRequest {
    @FXML
    Button thesisDefenceRoundRequestButton;
    @FXML
    Label buttonExplanationLabel;

    public void thesisDefenceRoundRequestButtonAction(ActionEvent ignoredActionEvent) {
        buttonExplanationLabel.setText("your request has been recorded. checkout requests tab for more information");
        buttonExplanationLabel.setLayoutX(150);
        thesisDefenceRoundRequestButton.setVisible(false);

        Logger.Info("new thesis defence round for user" + Client.getInstance().getCurrentUserData().nationalId);

        buttonExplanationLabel.setText("your round: " + LocalDateTime.now().plusDays(90).toString());
        front.services.model_handlers.ThesisDefenceRoundRequest.requestThesisDefenceRound();
    }
}
