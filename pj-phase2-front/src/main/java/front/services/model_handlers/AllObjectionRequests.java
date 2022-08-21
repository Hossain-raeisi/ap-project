package front.services.model_handlers;

import front.commons.data_class.RequestData;
import front.app.SceneControl;
import front.services.Client;

import java.util.ArrayList;

public class AllObjectionRequests {

    public static void showPage() {
        SceneControl.changeScene(SceneControl.SceneType.allObjectionRequestsPage);
    }

    public static ArrayList<RequestData> getAllObjectionRequests() {
        return Client.getInstance().getAllObjectionRequests();
    }
}
