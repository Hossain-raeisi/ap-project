package front.services.model_handlers;

import front.commons.data_class.RequestData;
import front.commons.data_class.RequestFilter;
import front.commons.enums.RequestUserType;
import front.app.SceneControl;
import front.services.Client;

import java.util.ArrayList;

public class Requests {

    public static void showPage(){
        SceneControl.changeScene(SceneControl.SceneType.requestsPage);
    }

    public static ArrayList<RequestData> getUserAssignerRequestsData(){
        var client = Client.getInstance();
        return client.getFilteredRequests(new RequestFilter(
                client.getCurrentUserData().id.toString(),
                RequestUserType.assigner)
        );
    }

    public static ArrayList<RequestData> getUserAssigneeRequestsData(){
        var client = Client.getInstance();
        return client.getFilteredRequests(new RequestFilter(
                client.getCurrentUserData().id.toString(),
                RequestUserType.assignee));
    }
}
