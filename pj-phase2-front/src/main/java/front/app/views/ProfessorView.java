package front.app.views;

import front.app.Util;
import front.commons.data_class.ProfessorData;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.UUID;


public class ProfessorView extends Pane {

    ImageView iv = new ImageView();
    Label name = new Label();
    Label email = new Label();
    Label faculty = new Label();
    Label roomNumber = new Label();
    UUID professorId;

    public ProfessorView(ProfessorData professorData) {
        professorId = professorData.id;

        setPrefWidth(1300);
        setPrefHeight(50);

        Font f = new Font(18);

        name.setText(Util.getUserFullName(professorData));
        name.setFont(f);
        iv.setImage(Util.getImageFromAttachmentData(professorData.imageData));
        iv.setFitHeight(40);
        iv.setFitWidth(40);
        email.setText(professorData.email);
        email.setFont(f);
        faculty.setText(professorData.facultyName);
        faculty.setFont(f);
        roomNumber.setText(String.valueOf(professorData.roomNumber));
        roomNumber.setFont(f);

        getChildren().add(iv);
        iv.setX(5);
        iv.setY(5);

        getChildren().add(name);
        name.setLayoutX(200);
        name.setLayoutY(10);

        getChildren().add(faculty);
        faculty.setLayoutX(500);
        faculty.setLayoutY(10);

        getChildren().add(email);
        email.setLayoutX(900);
        email.setLayoutY(10);

        getChildren().add(roomNumber);
        roomNumber.setLayoutX(1200);
        roomNumber.setLayoutY(10);

    }

    public Label getFaculty() {
        return faculty;
    }

    public void setFaculty(Label faculty) {
        this.faculty = faculty;
    }
}
