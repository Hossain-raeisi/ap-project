package front.app.fxml_controllers;

import front.services.FileHandler;
import front.services.model_handlers.EducationalContent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewEducationalContent {
    @FXML
    TextField filePathField;
    @FXML
    TextField educationalContentNameField;
    @FXML
    Button registerButton;
    @FXML
    Label courseName;
    @FXML
    TextArea textField;
    @FXML
    Button addTextButton;
    @FXML
    Button addMediaButton;

    @FXML
    public void initialize() {

    }

    public void registerButtonAction(ActionEvent actionEvent) {
        EducationalContent.addEducationalContent(educationalContentNameField.getText());
    }

    public void addTextButtonAction(ActionEvent actionEvent) {
        EducationalContent.addNewEducationalContentText(textField.getText());
    }

    public void addMediaButtonAction(ActionEvent actionEvent) {
        var attachmentData = FileHandler.getAttachmentDataFromPath(filePathField.getText());
        EducationalContent.addNewEducationalAttachmentData(attachmentData);
    }
}
