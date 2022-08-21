package front.app.fxml_controllers;

import front.services.Client;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class WeeklyPlan {
    @FXML
    ListView<String> planListView;

    @FXML
    public void initialize() {
        for (String clas : Client.getInstance().getWeeklyPlan()) {
            planListView.getItems().add(clas);
        }
    }
}
