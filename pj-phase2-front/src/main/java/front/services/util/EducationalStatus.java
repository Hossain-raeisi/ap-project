package front.services.util;

import front.commons.data_class.StudentData;
import front.commons.data_class.StudentFilter;
import front.app.SceneControl;
import front.services.Client;

import java.util.ArrayList;
import java.util.UUID;

public class EducationalStatus {
    static UUID studentId;

    public static void showPage() {
        var client = Client.getInstance();

        switch (client.getUserType(client.getCurrentUserData().id)) {
            case bachelorStudent,
                    masterStudent,
                    doctorateStudent -> SceneControl.changeScene(SceneControl.SceneType.educationalStatusStudentPage);
            case deputyEducationProfessor -> SceneControl.changeScene(SceneControl.SceneType.educationalStatusDeputyEducationProfessorPage);
        }
    }

    public static void showPageStudent(StudentData studentData) {
        EducationalStatus.studentId = studentData.id;
        SceneControl.changeScene(SceneControl.SceneType.educationalStatusStudentPage);
    }

    public static ArrayList<StudentData> getStudentsData() {
        return Client.getInstance().getAllStudentsData();
    }

    public static ArrayList<StudentData> getFilteredStudents(String nameFilter, String studentNumberFilter) {
        return Client.getInstance().getFilteredStudentsData( new StudentFilter(nameFilter, studentNumberFilter));
    }

    public static StudentData getStudentData() {
        return Client.getInstance().getStudentData(studentId);
    }
}
