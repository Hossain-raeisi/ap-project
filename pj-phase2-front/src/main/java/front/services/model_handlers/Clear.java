package front.services.model_handlers;

public class Clear {
    public static void clear(){
        ServiceExemptionRequest.serviceExemptionResponse = "";
        Messenger.clear();
    }

    public static void stopAllThreads() {

    }
}
