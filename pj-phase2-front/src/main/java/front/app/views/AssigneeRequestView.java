package front.app.views;

import front.commons.data_class.RequestData;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.UUID;

public class AssigneeRequestView extends Pane {

    Label title = new Label();
    Label status = new Label();
    Label assigner = new Label();
    Label id = new Label();
    UUID requestId;


    public AssigneeRequestView(RequestData requestData){
        requestId = requestData.id;

        setPrefWidth(1500);
        setPrefHeight(40);

        Font f = new Font(18);

        title.setText(requestData.title);
        title.setFont(f);
        getChildren().add(title);
        title.setLayoutX(5);
        title.setLayoutY(5);

        status.setText(requestData.status.toString());
        status.setFont(f);
        getChildren().add(status);
        status.setLayoutX(500);
        status.setLayoutY(5);

        assigner.setText(requestData.assignerName);
        assigner.setFont(f);
        getChildren().add(assigner);
        assigner.setLayoutX(1000);
        assigner.setLayoutY(5);

        id.setText(String.valueOf(requestData.id));
        id.setFont(f);
        getChildren().add(id);
        id.setLayoutX(1300);
        id.setLayoutY(5);
    }


}
