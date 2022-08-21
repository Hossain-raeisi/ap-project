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

    public enum UserType {
        professor,
        TA,
        other,
    }

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
        var educationalContentData = front.services.util.EducationalContent.getEducationalContentData();
        this.educationalContentId = educationalContentData.id;

        name.setText(educationalContentData.name);

        if (!Client.getInstance().getCurrentUserData().id.equals(client.getCourseData(educationalContentData.courseId).professorId))
            deleteButton.setVisible(false);

        itemsList.getItems().addAll(educationalContentData.texts.stream().map(EducationalContentItemView::new).toList());
        itemsList.getItems().addAll(educationalContentData.attachmentsId.stream().map(client::getAttachmentData).map(EducationalContentItemView::new).toList());
    }


    public void deleteButtonAction(ActionEvent actionEvent) {
        front.services.util.EducationalContent.removeEducationalContent(educationalContentId);
    }
}
