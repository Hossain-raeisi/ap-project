package front.app.views;

import front.Config;
import front.app.fxml_controllers.EducationalContent;
import front.commons.data_class.AttachmentData;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class EducationalContentItemView extends Pane {
    Label text  = new Label();
//    Button deleteButton = new Button();
//    Button changeButton = new Button();

    public EducationalContentItemView(AttachmentData attachmentData){
        setPrefWidth(1000);
        setPrefHeight(50);

        Font f = new Font(18);

        text.setText("you can see the file @" + Config.MEDIA_BASE_PATH + attachmentData.fileName);
        text.setFont(f);
        getChildren().add(text);
        text.setLayoutX(5);
        text.setLayoutY(10);

//        setButtonsVisibility(userType);
    }

    public EducationalContentItemView(String itemText){
        setPrefWidth(1000);
        setPrefHeight(50);

        Font f = new Font(18);

        text.setText(itemText);
        text.setFont(f);
        getChildren().add(text);
        text.setLayoutX(5);
        text.setLayoutY(10);
    }

//    public void setButtonsVisibility(EducationalContent.UserType userType) {
//        getChildren().add(changeButton);
//        changeButton.setLayoutX(700);
//        changeButton.setLayoutX(10);
//
//        getChildren().add(deleteButton);
//        changeButton.setLayoutX(800);
//        changeButton.setLayoutX(10);
//
//        deleteButton.setVisible(userType == EducationalContent.UserType.professor);
//        changeButton.setVisible(userType != EducationalContent.UserType.other);
//    }

}
