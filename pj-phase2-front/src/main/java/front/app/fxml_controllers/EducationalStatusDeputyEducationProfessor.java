package front.app.fxml_controllers;

import front.commons.data_class.StudentData;
import front.app.views.StudentEducationalStatusView;
import front.services.Client;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.UUID;


public class EducationalStatusDeputyEducationProfessor {

    @FXML
    ListView<StudentEducationalStatusView> studentsList;
    @FXML
    TextField nameFilterField;
    @FXML
    TextField studentNumberFilterField;
    @FXML
    Button filterButton;

    @FXML
    public void initialize() {
        for (StudentData studentData : front.services.util.EducationalStatus.getStudentsData()) {
            studentsList.getItems().add(new StudentEducationalStatusView(studentData));
        }

        studentsList.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Pane>) (observableValue, pane, t1)
                -> {
            System.out.println("fuck you");
            showStudentEducationalStatus(UUID.fromString(studentsList.getSelectionModel().getSelectedItem()
                    .getChildren().get(4).toString()));
        });
    }

    public void filterButtonAction(ActionEvent ignoredActionEvent) {
        String nameFilter = nameFilterField.getText();
        String studentNumberFilter = studentNumberFilterField.getText();

        if (nameFilter.equals("") && studentNumberFilter.equals("")) {
            front.services.util.EducationalStatus.showPage();
            return;
        }

        if (nameFilter.equals(""))
            nameFilter = null;
        if (studentNumberFilter.equals(""))
            studentNumberFilter = null;

        showFilteredStudents(front.services.util.EducationalStatus.getFilteredStudents(nameFilter, studentNumberFilter));
    }

    public void showFilteredStudents(ArrayList<StudentData> studentsData) {
        studentsList.getItems().clear();

        for (StudentData studentData : studentsData) {
            studentsList.getItems().add(new StudentEducationalStatusView(studentData));
        }

        studentsList.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Pane>)
                (observableValue, pane, t1) -> showStudentEducationalStatus(UUID.fromString(studentsList.getSelectionModel().getSelectedItem().getChildren().get(4).toString())));
    }

    public void showStudentEducationalStatus(UUID studentId) {
        front.services.util.EducationalStatus.showPageStudent(Client.getInstance().getStudentData(studentId));
    }
}
