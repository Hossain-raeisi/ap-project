package front.app.fxml_controllers.requests;

import front.commons.data_class.FacultyData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Objects;

public class RecommendationRequest {
    @FXML
    Label facultyListLabel;
    @FXML
    ListView<String> facultyList;
    @FXML
    ListView<String> professorList;
    @FXML
    Label professorListLabel;
    @FXML
    Button requestButton;
    @FXML
    Label responseLabel;

    @FXML
    public void initialize(){
        for (FacultyData facultyData: front.services.model_handlers.RecommendationRequest.getFacultiesDadta()){
            facultyList.getItems().add(facultyData.name);
        }

        facultyList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, s, t1) -> facultySelected(facultyList.getSelectionModel().getSelectedItem())
        );

        requestButton.setDisable(true);
        requestButton.setVisible(false);
        responseLabel.setVisible(false);
    }

    public void facultySelected(String facultyName){
        professorList.getItems().clear();
        professorList.getItems().addAll(Objects.requireNonNull(front.services.model_handlers.RecommendationRequest.getProfessors(facultyName)));

        professorList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            front.services.model_handlers.RecommendationRequest.currentSelectedProfessorIndex = professorList.getSelectionModel().getSelectedIndex();
            requestButton.setDisable(false);
            requestButton.setVisible(true);
        });
    }

    public void requestButtonAction(ActionEvent ignoredActionEvent){
        // todo : what?

        String facultyName = facultyList.getSelectionModel().getSelectedItem();
        String professorName = professorList.getSelectionModel().getSelectedItem();

        front.services.model_handlers.RecommendationRequest.requestRecommendation();
        responseLabel.setVisible(true);
    }

}
