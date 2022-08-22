package front.app.fxml_controllers;

import front.app.views.EducationalContentItemView;
import front.services.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.UUID;

public class EducationalContent {

    @FXML
    Label name;
    @FXML
    ListView<EducationalContentItemView> itemsList;
    @FXML
    Button deleteButton;

    UUID educationalContentId;

    @FXML
    public void initialize() {
        var client = Client.getInstance();
        var educationalContentData = front.services.model_handlers.EducationalContent.getEducationalContentData();
        this.educationalContentId = educationalContentData.id;
        var userType = front.services.model_handlers.EducationalContent.getCurrentUserType();

        name.setText(educationalContentData.name);

        if (!Client.getInstance().getCurrentUserData().id.equals(client.getCourseData(educationalContentData.courseId).professorId))
            deleteButton.setVisible(false);

        itemsList.getItems().addAll(educationalContentData.texts.stream().map(text -> new EducationalContentItemView(educationalContentId, text, userType)).toList());
        itemsList.getItems().addAll(educationalContentData.attachmentsId.stream().map(client::getAttachmentData).map(attachmentData -> new EducationalContentItemView(educationalContentId, attachmentData, userType)).toList());
    }


    public void deleteButtonAction(ActionEvent actionEvent) {
        front.services.model_handlers.EducationalContent.removeEducationalContent(educationalContentId);
    }
}
