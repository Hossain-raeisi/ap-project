package front.app.views;

import front.commons.data_class.CourseData;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.UUID;

public class CourseView extends Pane {

    Label name = new Label();
    Label professor = new Label();
    Label faculty = new Label();
    Label size = new Label();
    Label level = new Label();
    Label id = new Label();
    UUID courseId;


    public CourseView(CourseData courseData) {
        courseId = courseData.id;

        setPrefWidth(1300);
        setPrefHeight(50);

        Font f = new Font(18);

        name.setText(courseData.name);
        name.setFont(f);
        getChildren().add(name);
        name.setLayoutX(5);
        name.setLayoutY(10);

        professor.setText(courseData.professorName);
        professor.setFont(f);
        getChildren().add(professor);
        professor.setLayoutX(255);
        professor.setLayoutY(10);

        faculty.setText(courseData.facultyName);
        faculty.setFont(f);
        getChildren().add(faculty);
        faculty.setLayoutX(455);
        faculty.setLayoutY(10);

        size.setText(String.valueOf(courseData.size));
        size.setFont(f);
        getChildren().add(size);
        size.setLayoutX(705);
        size.setLayoutY(10);

        level.setText(String.valueOf(courseData.level));
        level.setFont(f);
        getChildren().add(level);
        level.setLayoutX(855);
        level.setLayoutY(10);

        id.setText(String.valueOf(courseData.id));
        id.setFont(f);
        getChildren().add(id);
        id.setLayoutX(1055);
        id.setLayoutY(10);
    }
}
