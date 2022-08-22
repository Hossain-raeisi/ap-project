package front.app.fxml_controllers.requests;

import front.commons.data_class.FacultyData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Objects;

public class MinorRequest {

    @FXML
    Label facultyListLabel;
    @FXML
    ListView<String> facultyList;
    @FXML
    Label minorListLabel;
    @FXML
    ListView<String> minorList;
    @FXML
    Button registerButton;
    @FXML
    Label responseLabel;

    @FXML
    public void initialize() {
        for (FacultyData facultyData : front.services.model_handlers.MinorRequest.getFaculties()) {
            facultyList.getItems().add(facultyData.name);
        }

        facultyList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, s, t1) -> facultySelected(facultyList.getSelectionModel().getSelectedItem())
        );

        registerButton.setDisable(true);
        registerButton.setVisible(false);
        responseLabel.setVisible(false);
    }

    public void facultySelected(String facultyName) {
        minorList.getItems().clear();
        minorList.getItems().addAll(Objects.requireNonNull(front.services.model_handlers.MinorRequest.getFacultyMinors(facultyName)));

        minorList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            registerButton.setDisable(false);
            registerButton.setVisible(true);
        });
    }

    public void registerButtonAction(ActionEvent ignoredActionEvent) {
        String facultyName = facultyList.getSelectionModel().getSelectedItem();
        String minorName = minorList.getSelectionModel().getSelectedItem();

        front.services.model_handlers.MinorRequest.requestMinor(facultyName, minorName);
        responseLabel.setVisible(true);
    }
}
