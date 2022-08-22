package front.app.fxml_controllers.requests;

import front.commons.data_class.RequestData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class RequestHandling {

    @FXML
    Label requestDescriptionLabel;
    @FXML
    Label requestTitleLabel;
    @FXML
    TextArea responseTextArea;
    @FXML
    Button approveButton;
    @FXML
    Button disapproveButton;

    @FXML
    public void initialize() {
        RequestData requestData = front.services.model_handlers.RequestHandling.getCurrentRequestData();

        requestDescriptionLabel.setText(requestData.description);
        requestTitleLabel.setText(requestData.title);
    }

    public void approveButtonAction() {
        String responseText = responseTextArea.getText();
        front.services.model_handlers.RequestHandling.approveCurrentRequest(responseText);
    }

    public void disapproveButtonAction() {
        String responseText = responseTextArea.getText();
        front.services.model_handlers.RequestHandling.disapproveCurrentRequest(responseText);
    }
}
