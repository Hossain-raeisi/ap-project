package front.app.views;

import front.commons.data_class.EducationalContentData;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.UUID;

public class EducationalContentView extends Pane {
    UUID educationalContentId;
    Label name = new Label();

    public EducationalContentView(EducationalContentData educationalContentData) {
        educationalContentId = educationalContentData.id;

        setPrefWidth(500);
        setPrefHeight(60);

        Font f = new Font(20);

        name.setText(educationalContentData.name);
        name.setFont(f);
        getChildren().add(name);
        name.setLayoutX(10);
        name.setLayoutY(10);
    }

    public UUID getEducationalContentId() {
        return educationalContentId;
    }
}
