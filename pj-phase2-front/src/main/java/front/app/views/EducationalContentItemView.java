package front.app.views;

import front.Config;
import front.commons.data_class.AttachmentData;
import front.services.util.EducationalContent;
import front.services.util.EducationalContentItem;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.UUID;

public class EducationalContentItemView extends Pane {
    UUID educationalContentId;
    UUID attachmentId;

    Label text  = new Label();
    Button deleteButton = new Button();
    Button changeButton = new Button();

    public EducationalContentItemView(UUID educationalContentId, AttachmentData attachmentData, front.services.util.EducationalContent.UserType userType){
        this.educationalContentId = educationalContentId;
        attachmentId = attachmentData.id;

        setPrefWidth(1000);
        setPrefHeight(50);

        Font f = new Font(18);

        text.setText("you can see the file @" + Config.MEDIA_BASE_PATH + attachmentData.fileName);
        text.setFont(f);
        getChildren().add(text);
        text.setLayoutX(5);
        text.setLayoutY(10);

        setButtonsVisibility(userType);
    }

    public EducationalContentItemView(UUID educationalContentId, String itemText, front.services.util.EducationalContent.UserType userType){
        setPrefWidth(1000);
        setPrefHeight(50);

        Font f = new Font(18);

        text.setText(itemText);
        text.setFont(f);
        getChildren().add(text);
        text.setLayoutX(5);
        text.setLayoutY(10);

        setButtonsVisibility(userType);
    }

    public void setButtonsVisibility(EducationalContent.UserType userType) {
        Font f = new Font(16);

        deleteButton.setText("delete");
        deleteButton.setFont(f);
        getChildren().add(deleteButton);
        deleteButton.setLayoutX(490);
        deleteButton.setLayoutY(10);

        changeButton.setText("change");
        changeButton.setFont(f);
        getChildren().add(changeButton);
        changeButton.setLayoutX(570);
        changeButton.setLayoutY(10);

        changeButton.setOnAction(this::changeButtonAction);
        deleteButton.setOnAction(this::deleteButtonAction);

        deleteButton.setVisible(userType == front.services.util.EducationalContent.UserType.professor);
        changeButton.setVisible(userType != front.services.util.EducationalContent.UserType.other);
    }

    public void changeButtonAction(ActionEvent actionEvent){
        if (attachmentId != null)
            EducationalContentItem.showUpdateItemPage(educationalContentId, attachmentId);
        EducationalContentItem.showUpdateItemPage(educationalContentId, text.getText());
    }

    public void deleteButtonAction(ActionEvent actionEvent){
        if (attachmentId != null)
            EducationalContentItem.deleteItem(educationalContentId, attachmentId);
        EducationalContentItem.deleteItem(educationalContentId, text.getText());
    }

}
