package front.app.fxml_controllers;

import front.commons.data_class.ProfessorData;
import front.commons.data_class.StudentData;
import front.commons.data_class.UserData;
import front.app.Util;
import front.services.Client;
import front.services.util.EditProfile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Profile {

    @FXML
    ImageView pictureImage;
    @FXML
    Label nameLabel;
    @FXML
    Label emailLabel;
    @FXML
    Label phoneNumberLabel;
    @FXML
    Label idNumberLabel;
    @FXML
    Label s_fNumberLabel;
    @FXML
    Label facultyLabel;
    @FXML
    Label levelLabel;
    @FXML
    Label ey_rnLabel;
    @FXML
    Label avgGradeStudentLabel;
    @FXML
    Label educationalStatusStudentLabel;
    @FXML
    Label supervisorProfessorStudentLabel;
    @FXML
    Button changeThemeButton;
    @FXML
    Button editButton;

    @FXML
    public void initialize() {
        var client = Client.getInstance();

        UserData userData = client.getCurrentUserData();

        // TODO
//        pictureImage.setImage(Util.getImageFromAttachmentData(userData.imageData));
        nameLabel.setText(Util.getUserFullName(userData));
        emailLabel.setText(userData.email);
        phoneNumberLabel.setText(userData.phoneNumber);
        idNumberLabel.setText(userData.nationalId);
        facultyLabel.setText(userData.facultyName);

        switch (client.getUserType(userData.id)) {
            case bachelorStudent,
                    masterStudent,
                    doctorateStudent -> loadStudentSpecific(client.getStudentData(userData.id));
            case normalProfessor,
                    deputyEducationProfessor,
                    facultyHeadProfessor -> loadProfessorSpecific(client.getProfessorData(userData.id));

        }
    }

    public void loadStudentSpecific(StudentData studentData) {
        s_fNumberLabel.setText(studentData.studentNumber);
        levelLabel.setText(studentData.type.toString());
        ey_rnLabel.setText(String.valueOf(studentData.startingYear));
        avgGradeStudentLabel.setText(String.valueOf(studentData.totalGradePointAverage));
        educationalStatusStudentLabel.setText(studentData.educationalStatus.toString());
        supervisorProfessorStudentLabel.setText(studentData.supervisorProfessorName);
    }

    public void loadProfessorSpecific(ProfessorData professorData) {
        avgGradeStudentLabel.setVisible(false);
        educationalStatusStudentLabel.setVisible(false);
        supervisorProfessorStudentLabel.setVisible(false);

        s_fNumberLabel.setText(professorData.professorNumber);
        levelLabel.setText(professorData.rank.toString());
        ey_rnLabel.setText(String.valueOf(professorData.roomNumber));
    }

    public void changeThemeButtonAction(ActionEvent ignoredActionEvent) {
        Header.changeTheme();
        front.services.util.Profile.showPage();
    }

    public void editButtonAction(ActionEvent ignoredActionEvent) {
        EditProfile.showPage();
    }
}
