package front.services.util;

import front.commons.data_class.FacultyData;
import front.commons.data_class.ProfessorData;
import front.commons.data_class.StudentData;
import front.app.SceneControl;
import front.services.Client;

import java.util.UUID;

public class AddUser {

    public static void showPage(String type) {
        switch (type){
            case "student" -> SceneControl.changeScene(SceneControl.SceneType.addStudentPage);
            case "professor" -> SceneControl.changeScene(SceneControl.SceneType.addProfessorPage);
        }
    }

    public static FacultyData getFacultyData(String facultyName) {
        return Client.getInstance().getFacultyByName(facultyName);
    }

    public static void addProfessor(ProfessorData professorData) {
        Client.getInstance().addProfessor(professorData);
    }

    public static ProfessorData getProfessorData(String professorId) {
        return Client.getInstance().getProfessorData(UUID.fromString(professorId));
    }

    public static void addStudent(StudentData studentData) {
        Client.getInstance().addStudent(studentData);
    }
}
