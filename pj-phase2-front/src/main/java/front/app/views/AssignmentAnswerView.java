package front.app.views;

import front.services.Client;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.UUID;

public class AssignmentAnswerView extends Pane {

    UUID assignmentAnswerId;

    Label studentName = new Label();
    Label studentNumber = new Label();
    Label time = new Label();

    public AssignmentAnswerView(UUID assignmentAnswerId, Boolean maskNames) {
        this.assignmentAnswerId = assignmentAnswerId;
        var client = Client.getInstance();

        var assignmentAnswerData = client.getAssignmentAnswerData(assignmentAnswerId);

        setPrefWidth(600);
        setPrefHeight(50);

        Font f = new Font(18);

        time.setText(assignmentAnswerData.sentTime.toString());
        time.setFont(f);
        getChildren().add(time);
        time.setLayoutX(5);
        time.setLayoutY(10);

        studentName.setText("***");
        studentName.setFont(f);
        getChildren().add(studentName);
        studentName.setLayoutX(5);
        studentName.setLayoutY(10);

        studentNumber.setText("***");
        studentNumber.setFont(f);
        getChildren().add(studentNumber);
        studentNumber.setLayoutX(5);
        studentNumber.setLayoutY(10);

        if (!maskNames) {
            studentName.setText(assignmentAnswerData.studentName);
            studentNumber.setText(client.getStudentData(assignmentAnswerData.studentId).studentNumber);
        }
    }

    public UUID getAssignmentAnswerId() {
        return assignmentAnswerId;
    }
}
