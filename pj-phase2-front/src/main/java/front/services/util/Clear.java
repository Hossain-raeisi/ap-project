package front.services.util;

public class Clear {
    public static void clear(){
        ServiceExemptionRequest.serviceExemptionResponse = "";
        Messenger.clear();
    }

    public static void stopAllThreads() {

    }
}
