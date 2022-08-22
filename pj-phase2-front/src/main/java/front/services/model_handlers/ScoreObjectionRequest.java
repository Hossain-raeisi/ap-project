package front.services.model_handlers;

import front.app.SceneControl;
import front.commons.data_class.ObjectionRequestData;
import front.commons.data_class.ScoreData;
import front.services.Client;

public class ScoreObjectionRequest {
    static ScoreData scoreData;

    public static void showPage(ScoreData scoreData) {
        ScoreObjectionRequest.scoreData = scoreData;
        SceneControl.changeScene(SceneControl.SceneType.scoreObjectionRequestPage);
    }

    public static ScoreData getScoreData() {
        return scoreData;
    }

    public static void object(String text) {
        Client.getInstance().requestObjection(new ObjectionRequestData(scoreData.id, text));
    }
}
