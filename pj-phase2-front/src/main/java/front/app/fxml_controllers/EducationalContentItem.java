package front.app.fxml_controllers;

import front.services.Client;
import front.services.FileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class EducationalContentItem {

    @FXML
    TextField filePathField;
    @FXML
    TextArea textField;
    @FXML
    Button setTextButton;
    @FXML
    Button setMediaButton;

    public void setTextButtonAction(ActionEvent actionEvent) {
        front.services.model_handlers.EducationalContentItem.updateEducationalContentItem(textField.getText());
    }

    public void setMediaButtonAction(ActionEvent actionEvent) {
        var attachmentData = FileHandler.getAttachmentDataFromPath(filePathField.getText());
        attachmentData.id = Client.getInstance().addAttachments(new ArrayList<>() {{add(attachmentData);}}).get(0);
        front.services.model_handlers.EducationalContentItem.updateEducationalContentItem(attachmentData);
    }
}
