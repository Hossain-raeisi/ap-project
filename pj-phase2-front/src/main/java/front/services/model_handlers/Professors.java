package front.services.model_handlers;

import front.commons.data_class.ProfessorData;
import front.commons.data_class.ProfessorFilter;
import front.app.SceneControl;
import front.services.Client;

import java.util.ArrayList;

public class Professors {

    public static ArrayList<ProfessorData> professorsData;

    public static void showAllProfessors(){
        professorsData = Client.getInstance().getAllProfessorsData();
        SceneControl.changeScene(SceneControl.SceneType.professorsPage);
    }

    public static void showFilteredProfessors(ProfessorFilter professorFilter){
        professorsData = Client.getInstance().getFilteredProfessorsData(professorFilter);
        SceneControl.changeScene(SceneControl.SceneType.professorsPage);
    }

    public static void removeProfessor(String professorId) {
        Client.getInstance().removeUser(professorId);
    }

}
