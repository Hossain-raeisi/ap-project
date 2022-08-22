package front.services.model_handlers;

import front.app.SceneControl;
import front.services.Client;

public class WithdrawalRequest {

    public static void showPage() {
        SceneControl.changeScene(SceneControl.SceneType.withdrawalRequestPage);
    }

    public static void requestWithdrawal() {
        Client.getInstance().requestWithdrawal();
    }
}
