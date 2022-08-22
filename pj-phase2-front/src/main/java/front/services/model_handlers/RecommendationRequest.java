package front.services.model_handlers;

import front.app.SceneControl;
import front.app.Util;
import front.commons.data_class.FacultyData;
import front.commons.data_class.ProfessorData;
import front.commons.data_class.ProfessorFilter;
import front.services.Client;

import java.util.ArrayList;

public class RecommendationRequest {
    static ArrayList<ProfessorData> facultyProfessorsData;
    public static int currentSelectedProfessorIndex;

    public static void showPage(){
        SceneControl.changeScene(SceneControl.SceneType.recommendationRequestPage);
    }

    public static ArrayList<FacultyData> getFacultiesDadta() {
        return Client.getInstance().getAllFacultiesData();
    }

    public static ArrayList<String> getProfessors(String facultyName) {
        var client = Client.getInstance();
        ArrayList<String> professorNames = new ArrayList<>();
        facultyProfessorsData = client.getFilteredProfessorsData(new ProfessorFilter(
                null,
                null,
                client.getFacultyByName(facultyName).id.toString()));
        for (ProfessorData professorData: facultyProfessorsData){
            professorNames.add(Util.getUserFullName(professorData));
        }
        return professorNames;
    }

    public static void requestRecommendation() {
        Client.getInstance().requestRecommendation(facultyProfessorsData.get(currentSelectedProfessorIndex).id);
    }
}
