package front.app.fxml_controllers.requests;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DormRequest {

    @FXML
    Button dormRequestButton;
    @FXML
    Label buttonExplanationLabel;

    public void dormRequestButtonAction(ActionEvent ignoredActionEvent) {
        buttonExplanationLabel.setText("your request has been recorded. checkout requests tab for more information");
        buttonExplanationLabel.setLayoutX(150);
        dormRequestButton.setVisible(false);
        front.services.util.DormRequest.requestDorm();
    }
}
