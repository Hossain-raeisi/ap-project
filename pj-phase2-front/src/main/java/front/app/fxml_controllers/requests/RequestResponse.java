package front.app.fxml_controllers.requests;

import front.commons.data_class.RequestData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RequestResponse {

    @FXML
    Label requestTitleLabel;
    @FXML
    Label requestDescriptionLabel;
    @FXML
    Label requestResponseLabel;

    @FXML
    public void initialize(){
        RequestData requestData = front.services.util.RequestResponse.getCurrentRequestData();

        requestDescriptionLabel.setText(requestData.description);
        requestTitleLabel.setText(requestData.title);
        if (requestData.response != null)
            requestResponseLabel.setText(requestData.response);
    }
}
