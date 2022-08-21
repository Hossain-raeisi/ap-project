package front.services.model_handlers;

import front.commons.data_class.ProfessorData;
import front.app.SceneControl;
import front.services.Client;

import java.util.UUID;

public class EditUserProfessor {

    static UUID professorId;

    public static ProfessorData getCurrentProfessorData() {
        return Client.getInstance().getProfessorData(professorId);
    }

    public static void showPage(ProfessorData professorData) {
        EditUserProfessor.professorId = professorData.id;
        SceneControl.changeScene(SceneControl.SceneType.editProfessorPage);
    }
}
