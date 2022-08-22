package front.app.fxml_controllers.requests;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WithdrawalRequest {
    @FXML
    Label buttonExplanationLabel;
    @FXML
    Button withdrawRequestButton;

    public void withdrawRequestButtonAction(ActionEvent ignoredActionEvent) {
        buttonExplanationLabel.setText("your request has been recorded. checkout requests tab for more information");
        buttonExplanationLabel.setLayoutX(150);
        withdrawRequestButton.setVisible(false);
        front.services.model_handlers.WithdrawalRequest.requestWithdrawal();
    }
}
