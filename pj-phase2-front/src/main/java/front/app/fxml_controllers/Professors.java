package front.app.fxml_controllers;

import front.commons.data_class.ProfessorData;
import front.commons.data_class.ProfessorFilter;
import front.commons.enums.ProfessorRank;
import front.commons.enums.UserType;
import front.app.views.ProfessorView;
import front.services.Client;
import front.services.model_handlers.AddUser;
import front.services.model_handlers.EditUserProfessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.UUID;

public class Professors {

    @FXML
    Button deputyProfessorButton;
    @FXML
    TextField deputyProfessorIdField;
    @FXML
    Button editProfessorButton;
    @FXML
    Button addProfessorButton;
    @FXML
    Button delProfessorButton;
    @FXML
    TextField professorIdField;
    @FXML
    ListView<Pane> profs;
    @FXML
    TextField nameFilterField;
    @FXML
    TextField rankFilterField;
    @FXML
    TextField facultyFilterField;
    @FXML
    Button filterButton;

    @FXML
    public void initialize() {
        var client = Client.getInstance();
        var userData = client.getCurrentUserData();

        for (ProfessorData professorData : front.services.model_handlers.Professors.professorsData) {
            profs.getItems().add(new ProfessorView(professorData));
        }

        if (!(client.getUserType(userData.id) == UserType.facultyHeadProfessor)) {
            editProfessorButton.setVisible(false);
            addProfessorButton.setVisible(false);
            delProfessorButton.setVisible(false);
            professorIdField.setVisible(false);
            deputyProfessorButton.setVisible(false);
            deputyProfessorIdField.setVisible(false);
        } else {
            if (client.getFacultyDataById(userData.facultyId).deputyEducationProfessorId == null) {
                deputyProfessorButton.setOnAction(this::addDeputyEducation);
                deputyProfessorButton.setText("add deputy education");
            } else {
                deputyProfessorIdField.setVisible(false);
                deputyProfessorButton.setOnAction(this::removeDeputyEducation);
                deputyProfessorButton.setText("remove deputy education");
            }
        }
    }

    public void filterButtonAction(ActionEvent ignoredActionEvent) {
        String nameFilter = nameFilterField.getText();
        String rankFilterString = rankFilterField.getText();
        String facultyFilterString = facultyFilterField.getText();

        if (nameFilter.equals("") && rankFilterString.equals("") && facultyFilterString.equals("")) {
            front.services.model_handlers.Professors.showAllProfessors();
            return;
        }

        if (nameFilter.equals(""))
            nameFilter = null;

        ProfessorRank rankFilter = null;

        switch (rankFilterString) {
            case "assistant professor" -> rankFilter = ProfessorRank.assistantProfessor;
            case "associate professor" -> rankFilter = ProfessorRank.associateProfessor;
            case "full professor" -> rankFilter = ProfessorRank.fullProfessor;
        }

        if (facultyFilterString.equals(""))
            facultyFilterString = null;

        front.services.model_handlers.Professors.showFilteredProfessors(new ProfessorFilter(nameFilter,
                rankFilter,
                facultyFilterString));
    }

    public void editProfessorButtonAction() {
        var client = Client.getInstance();
        UUID professorId = UUID.fromString(professorIdField.getText());

        if (client.getProfessorData(professorId).facultyId
                .equals(client.getCurrentUserData().facultyId)) {
            EditUserProfessor.showPage(client.getProfessorData(professorId));
        }
    }

    public void addProfessorButtonAction() {
        AddUser.showPage("professor");
        front.services.model_handlers.Professors.showAllProfessors();
    }

    public void delProfessorButtonAction() {
        front.services.model_handlers.Professors.removeProfessor(professorIdField.getText());
        front.services.model_handlers.Professors.showAllProfessors();
    }

    public void addDeputyEducation(ActionEvent actionEvent) {
        var facultyId = Client.getInstance().getCurrentUserData().facultyId;
        var facultyData = Client.getInstance().getFacultyDataById(facultyId);

        facultyData.deputyEducationProfessorId = UUID.fromString(deputyProfessorIdField.getText());
        Client.getInstance().updateFaculty(facultyData);
        front.services.model_handlers.Professors.showAllProfessors();
    }

    public void removeDeputyEducation(ActionEvent actionEvent) {
        var facultyId = Client.getInstance().getCurrentUserData().facultyId;
        var facultyData = Client.getInstance().getFacultyDataById(facultyId);

        facultyData.deputyEducationProfessorId = null;
        facultyData.deputyEducationProfessorName = null;

        front.services.model_handlers.Professors.showAllProfessors();
    }
}
