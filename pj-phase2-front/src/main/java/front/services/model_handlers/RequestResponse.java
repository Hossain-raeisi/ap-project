package front.services.model_handlers;

import front.commons.data_class.RequestData;
import front.app.SceneControl;
import front.services.Client;

public class RequestResponse {
    public static String currentRequestId;

    public static void showPage(String requestID) {
        currentRequestId = requestID;
        SceneControl.changeScene(SceneControl.SceneType.requestResponsePage);
    }

    public static RequestData getCurrentRequestData() {
        return Client.getInstance().getRequest(currentRequestId);
    }
}
