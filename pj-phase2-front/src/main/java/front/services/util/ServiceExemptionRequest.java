package front.services.util;

import front.app.SceneControl;
import front.services.Client;

public class ServiceExemptionRequest {
    public static String serviceExemptionResponse = "";

    public static void requestServiceExemption(){
        serviceExemptionResponse = Client.getInstance().requestServiceExemption();
    }

    public static void showPage(){
        SceneControl.changeScene(SceneControl.SceneType.serviceExemptionRequestPage);
    }

}
