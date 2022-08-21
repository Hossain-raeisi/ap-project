package front.services.model_handlers;

import front.commons.data_class.FacultyData;
import front.commons.data_class.MinorData;
import front.app.SceneControl;
import front.services.Client;

import java.util.ArrayList;

public class MinorRequest {

    public static ArrayList<FacultyData> facultiesData;

    public static void showPage() {
        SceneControl.changeScene(SceneControl.SceneType.minorRequestPage);
    }
    
    public static ArrayList<FacultyData> getFaculties(){
        return Client.getInstance().getAllFacultiesData();
    }

    public static ArrayList<String> getFacultyMinors(String facultyName) {
        return Client.getInstance().getFacultyMinors(facultyName);
    }

    public static void requestMinor(String facultyName, String minorName){
        Client.getInstance().requestMinor(new MinorData(minorName, facultyName));
    }
}
