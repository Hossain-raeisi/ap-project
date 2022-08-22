package front.app.fxml_controllers;

import front.app.Util;
import front.commons.data_class.PasswordChangeData;
import front.commons.data_class.StudentData;
import front.commons.enums.StudentEducationalStatus;
import front.commons.enums.StudentType;
import front.services.Client;
import front.services.security.Utils;
import front.services.model_handlers.AddUser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class AddUserStudent {


    @FXML
    Button addUserButton;
    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    TextField facultyNameField;
    @FXML
    TextField emailField;
    @FXML
    TextField nationalIdField;
    @FXML
    TextField phoneNumberField;
    @FXML
    TextField imageAddressField;
    @FXML
    TextField passwordField;
    @FXML
    TextField educationalStatusField;
    @FXML
    TextField startingYearField;
    @FXML
    TextField majorField;
    @FXML
    TextField studentNumberField;
    @FXML
    TextField superVisorProfessorIdField;
    @FXML
    TextField levelField;

    public void addUserButtonAction(){
        var client = Client.getInstance();

        StudentType studentType = StudentType.bachelor;
        switch (levelField.getText()) {
            case "bachelor" -> studentType = StudentType.bachelor;
            case "master" -> studentType = StudentType.master;
            case "doctorate" -> studentType = StudentType.doctorate;
            default -> throw new IllegalStateException("Unexpected value");
        }
        StudentEducationalStatus educationalStatus = StudentEducationalStatus.underGraduate;
        switch (educationalStatusField.getText()) {
            case "under graduate" -> educationalStatus = StudentEducationalStatus.underGraduate;
            case "graduate" -> educationalStatus = StudentEducationalStatus.graduate;
            case "withdrawal" -> educationalStatus = StudentEducationalStatus.withdrawal;
        }
        String facultyName = facultyNameField.getText();
        UUID superVisorProfessorId = UUID.fromString(superVisorProfessorIdField.getText());
        String nationalId = nationalIdField.getText();

        // TODO
        String imageAddress = imageAddressField.getText();


        StudentData studentData = new StudentData(
                facultyName,
                client.getFacultyByName(facultyName).id,
                nationalId,
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                phoneNumberField.getText(),
                Util.getAttachmentDataFromFilePath(imageAddress),
                superVisorProfessorId,
                educationalStatus,
                studentType,
                Integer.parseInt(startingYearField.getText()),
                majorField.getText(),
                studentNumberField.getText()
        );

        AddUser.addStudent(studentData);

        var userData = client.getUserDataByNationalId(nationalId);

        try {
            PasswordChangeData passwordChangeData = new PasswordChangeData(
                    userData.id,
                    Utils.hashPassword(passwordField.getText()));

            Client.getInstance().updateUserPassword(passwordChangeData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        AddUser.showPage("student");
    }
}
