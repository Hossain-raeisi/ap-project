package front.services.model_handlers;

import front.commons.data_class.RequestData;
import front.commons.enums.RequestStatus;
import front.app.SceneControl;
import front.services.Client;

public class RequestHandling {
    public static String currentRequestId;

    public static void showPage(String requestId) {
        currentRequestId = requestId;
        SceneControl.changeScene(SceneControl.SceneType.requestHandlingPage);
    }

    public static RequestData getCurrentRequestData() {
        return Client.getInstance().getRequest(currentRequestId);
    }

    public static void approveCurrentRequest(String responseText) {
        var client = Client.getInstance();
        var requestData = client.getRequest(currentRequestId);

        requestData.status = RequestStatus.approved;
        requestData.response = responseText;
        client.finalizeRequest(requestData);

        SceneControl.changeScene(SceneControl.SceneType.requestsPage);
    }

    public static void disapproveCurrentRequest(String responseText) {
        var client = Client.getInstance();
        var requestData = client.getRequest(currentRequestId);

        requestData.status = RequestStatus.disapproved;
        requestData.response = responseText;
        client.finalizeRequest(requestData);

        SceneControl.changeScene(SceneControl.SceneType.requestsPage);
    }
}
