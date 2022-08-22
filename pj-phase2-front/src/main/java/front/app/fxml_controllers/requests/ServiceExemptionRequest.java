package front.app.fxml_controllers.requests;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ServiceExemptionRequest {

    @FXML
    Button requestButton;
    @FXML
    Label responseLabel;

    public void requestButtonAction(ActionEvent ignoredActionEvent) {
        front.services.model_handlers.ServiceExemptionRequest.requestServiceExemption();
        changeLabelText(front.services.model_handlers.ServiceExemptionRequest.serviceExemptionResponse);
    }

    public void changeLabelText(String text) {
        responseLabel.setText(text);
    }
}
