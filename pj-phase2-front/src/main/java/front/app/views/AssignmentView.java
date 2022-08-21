package front.app.views;

import front.commons.data_class.AssignmentData;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.UUID;

public class AssignmentView extends Pane {
    UUID assignmentId;
    Label name = new Label();
    Label endTime = new Label();
    
    public AssignmentView(AssignmentData assignmentData) {
        assignmentId = assignmentData.id;

        setPrefWidth(480);
        setPrefHeight(50);

        Font f = new Font(18);

        name.setText(assignmentData.name);
        name.setFont(f);
        getChildren().add(name);
        name.setLayoutX(5);
        name.setLayoutY(10);

        endTime.setText(assignmentData.endTime.toString());
        endTime.setFont(f);
        getChildren().add(endTime);
        endTime.setLayoutX(205);
        endTime.setLayoutY(10);

    }

    public UUID getAssignmentId() {
        return assignmentId;
    }
}
