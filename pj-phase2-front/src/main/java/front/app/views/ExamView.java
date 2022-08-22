package front.app.views;

import front.commons.data_class.ExamData;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.UUID;

public class ExamView extends Pane {

    Label name = new Label();
    Label courseName = new Label();
    Label dateTime = new Label();
    UUID examId;


    public ExamView(ExamData examData) {
        examId = examData.id;

        setPrefWidth(1300);
        setPrefHeight(50);

        Font f = new Font(18);

        name.setText(examData.name);
        name.setFont(f);
        getChildren().add(name);
        name.setLayoutX(5);
        name.setLayoutY(10);

        courseName.setText(examData.courseName);
        courseName.setFont(f);
        getChildren().add(courseName);
        courseName.setLayoutX(205);
        courseName.setLayoutY(10);

        dateTime.setText(examData.time.toString());
        dateTime.setFont(f);
        getChildren().add(dateTime);
        dateTime.setLayoutX(305);
        dateTime.setLayoutY(10);
    }
}


