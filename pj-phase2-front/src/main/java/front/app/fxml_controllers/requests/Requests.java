package front.app.fxml_controllers.requests;

import front.app.views.AssigneeRequestView;
import front.app.views.AssignerRequestView;
import front.commons.data_class.RequestData;
import front.services.model_handlers.RequestHandling;
import front.services.model_handlers.RequestResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class Requests {

    @FXML
    ListView<Pane> assigneeRequests;
    @FXML
    ListView<Pane> assignerRequests;

    @FXML
    public void initialize() {
        for (RequestData requestData : front.services.model_handlers.Requests.getUserAssignerRequestsData()) {
            assignerRequests.getItems().add(new AssignerRequestView(requestData));
        }

        assignerRequests.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, pane, t1) -> assignerRequestSelected(((Label) assignerRequests.getSelectionModel().getSelectedItem().getChildren().get(3)).getText())
        );

        for (RequestData requestData : front.services.model_handlers.Requests.getUserAssigneeRequestsData()) {
            assigneeRequests.getItems().add(new AssigneeRequestView(requestData));
        }

        assigneeRequests.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, pane, t1) -> assigneeRequestSelected(((Label) assigneeRequests.getSelectionModel().getSelectedItem().getChildren().get(3)).getText())
        );
    }

    public void assigneeRequestSelected(String requestID) {
        RequestHandling.showPage(requestID);
    }

    public void assignerRequestSelected(String requestID) {
        RequestResponse.showPage(requestID);
    }
}
