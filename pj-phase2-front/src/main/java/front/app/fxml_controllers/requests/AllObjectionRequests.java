package front.app.fxml_controllers.requests;

import front.commons.data_class.RequestData;
import front.app.views.RequestView;
import front.services.util.RequestResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class AllObjectionRequests {

    @FXML
    ListView<Pane> objectionsListView;

    @FXML
    public void initialize(){
        for (RequestData requestData: front.services.util.AllObjectionRequests.getAllObjectionRequests()) {
            objectionsListView.getItems().add(new RequestView(requestData));
        }

        objectionsListView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, pane, t1) -> requestsSelected(((Label)objectionsListView.getSelectionModel().getSelectedItem().getChildren().get(4)).getText())
        );
    }

    public void requestsSelected(String requestId){
        RequestResponse.showPage(requestId);
    }
}
