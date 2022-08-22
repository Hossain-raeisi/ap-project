package front.app.fxml_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AssignmentAnswer {
    @FXML
    Label assignmentAnswerPath;
    @FXML
    TextField scoreField;
    @FXML
    TextArea assignmentAnswerTextField;
    @FXML
    Button scoreRegisterButton;

    @FXML
    public void initialize() {
        var assignmentAnswerData = front.services.model_handlers.AssignmentAnswer.getCurrentAssignmentAnswerData();

        if (assignmentAnswerData.attachmentId != null) {
            // TODO
        } else if (assignmentAnswerData.text != null) {
            assignmentAnswerTextField.setText(assignmentAnswerData.text);
        }
    }

    public void scoreRegisterButtonAction(ActionEvent ignoredActionEvent) {
        front.services.model_handlers.AssignmentAnswer.setScore(Float.parseFloat(scoreField.getText()));
    }
}
