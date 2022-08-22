package front.app.fxml_controllers;

import front.app.views.AssignmentAnswerView;
import front.commons.data_class.AssignmentData;
import front.commons.enums.AssignmentAcceptableTypes;
import front.services.Client;
import front.services.FileHandler;
import front.services.model_handlers.AssignmentAnswer;
import front.services.model_handlers.EducationalContent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.UUID;

public class Assignment {
    @FXML
    Label nameLabel;
    @FXML
    TextArea descriptionArea;
    @FXML
    Label startTimeLabel;
    @FXML
    Label fullScoreTimeLabel;
    @FXML
    Label endTimeLabel;
    @FXML
    ListView<AssignmentAnswerView> studentsAnswers;
    @FXML
    TextArea textAnswerField;
    @FXML
    TextField filePathField;
    @FXML
    Button sendAnswerButton;

    @FXML
    public void initialize() {
        var userType = EducationalContent.getCurrentUserType();

        var assignmentData = front.services.model_handlers.Assignment.getCurrentAssignmentData();

        nameLabel.setText(assignmentData.name);
        descriptionArea.setText(assignmentData.description);
        startTimeLabel.setText(assignmentData.startTime.toString());
        fullScoreTimeLabel.setText(assignmentData.fullScoreTime.toString());
        endTimeLabel.setText(assignmentData.endTime.toString());

        textAnswerField.setVisible(false);
        filePathField.setVisible(false);
        sendAnswerButton.setVisible(false);

        switch (userType) {
            case professor -> loadProfessor(assignmentData);
            case TA -> loadTA(assignmentData);
            case other -> loadStudent(assignmentData);
        }
    }

    public void loadStudent(AssignmentData assignmentData) {
        studentsAnswers.setVisible(false);

        sendAnswerButton.setVisible(true);

        if (assignmentData.answerType == AssignmentAcceptableTypes.text)
            textAnswerField.setVisible(true);
        else if (assignmentData.answerType == AssignmentAcceptableTypes.file)
            filePathField.setVisible(true);
    }

    public void loadTA(AssignmentData assignmentData) {
        var client = Client.getInstance();
        var assignmentAnswersData = client.getAssignmentAnswers(assignmentData.id);

        studentsAnswers.getItems().addAll(assignmentAnswersData.stream().map(
                assignmentAnswerData -> new AssignmentAnswerView(assignmentAnswerData.id, true)
        ).toList());

        studentsAnswers.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, assignmentAnswerView, t1) -> assignmentAnswerSelected(
                        studentsAnswers.getSelectionModel().getSelectedItem().getAssignmentAnswerId(),
                        EducationalContent.UserType.TA
                )
        );
    }

    public void loadProfessor(AssignmentData assignmentData) {
        var client = Client.getInstance();
        var assignmentAnswersData = client.getAssignmentAnswers(assignmentData.id);

        studentsAnswers.getItems().addAll(assignmentAnswersData.stream().map(
                assignmentAnswerData -> new AssignmentAnswerView(assignmentAnswerData.id, false)
        ).toList());

        studentsAnswers.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, assignmentAnswerView, t1) -> assignmentAnswerSelected(
                        studentsAnswers.getSelectionModel().getSelectedItem().getAssignmentAnswerId(),
                        EducationalContent.UserType.professor
                )
        );
    }

    public void assignmentAnswerSelected(UUID assignmentAnswerId, EducationalContent.UserType userType) {
        AssignmentAnswer.showPage(assignmentAnswerId, userType);
    }

    public void sendAnswerButtonAction(ActionEvent ignoredActionEvent) {
        var client = Client.getInstance();
        var assignmentData = front.services.model_handlers.Assignment.getCurrentAssignmentData();

        if (assignmentData.answerType == AssignmentAcceptableTypes.text)
            AssignmentAnswer.addAnswer(assignmentData.id, textAnswerField.getText());
        else if (assignmentData.answerType == AssignmentAcceptableTypes.file) {
            var attachmentData = FileHandler.getAttachmentDataFromPath(filePathField.getText());
            attachmentData.id = client.addAttachments(new ArrayList<>() {{ add(attachmentData); }}).get(0);
            AssignmentAnswer.addAnswer(assignmentData.id, attachmentData);
        }

        front.services.model_handlers.Assignment.showPage(assignmentData.id.toString());
    }
}
