package front.app.views;

import front.commons.data_class.StudentData;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.UUID;

import static front.app.Util.getUserFullName;


public class StudentEducationalStatusView extends Pane {

    Label name = new Label();
    Label studentNumber = new Label();
    Label major = new Label();
    Label totalGradePointAverage = new Label();
    UUID studentId;


    public StudentEducationalStatusView(StudentData studentData) {
        studentId = studentData.id;

        setPrefWidth(1300);
        setPrefHeight(50);

        Font f = new Font(18);

        name.setText(getUserFullName(studentData));
        name.setFont(f);
        studentNumber.setText(studentData.studentNumber);
        studentNumber.setFont(f);
        major.setText(studentData.major);
        major.setFont(f);
        totalGradePointAverage.setText(String.valueOf(studentData.totalGradePointAverage));
        totalGradePointAverage.setFont(f);

        getChildren().add(name);
        name.setLayoutX(5);
        name.setLayoutY(10);

        getChildren().add(studentNumber);
        studentNumber.setLayoutX(305);
        studentNumber.setLayoutY(10);

        getChildren().add(major);
        major.setLayoutX(605);
        major.setLayoutY(10);

        getChildren().add(totalGradePointAverage);
        totalGradePointAverage.setLayoutX(905);
        totalGradePointAverage.setLayoutY(10);
    }
}
