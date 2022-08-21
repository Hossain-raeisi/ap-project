package front.services.model_handlers;

import front.commons.data_class.ScoreData;
import front.app.SceneControl;
import front.services.Client;

import java.util.ArrayList;
import java.util.UUID;

public class ProfessorAllFinalizedScores {

    static String professorId;

    public static void showPage(String professorId){
        ProfessorAllFinalizedScores.professorId = professorId;
        SceneControl.changeScene(SceneControl.SceneType.allProfessorScoresPage);
    }

    public static ArrayList<ScoreData> getProfessorAllFinalizedScoreData() {
        return Client.getInstance().getProfessorAllFinalizedScore(UUID.fromString(professorId));
    }
}
