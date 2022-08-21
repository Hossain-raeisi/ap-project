package front.app.fxml_controllers;

import front.app.Util;
import front.commons.data_class.PasswordChangeData;
import front.commons.data_class.ProfessorData;
import front.commons.enums.ProfessorRank;
import front.commons.enums.ProfessorType;
import front.services.Client;
import front.services.security.Utils;
import front.services.util.AddUser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.security.NoSuchAlgorithmException;


public class AddUserProfessor {

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
    TextField rankField;
    @FXML
    TextField roomNumberField;

    public void addUserButtonAction() {
        var client = Client.getInstance();

        String facultyName = facultyNameField.getText();
        ProfessorRank professorRank = ProfessorRank.assistantProfessor;
        switch (rankField.getText()) {
            case "assistant professor" -> professorRank = ProfessorRank.assistantProfessor;
            case "associate professor" -> professorRank = ProfessorRank.associateProfessor;
            case "full professor" -> professorRank = ProfessorRank.fullProfessor;
        }
        String nationalId = nationalIdField.getText();


        // TODO
        String imageAddress = imageAddressField.getText();

        ProfessorData professorData = new ProfessorData(
                facultyName,
                client.getFacultyByName(facultyName).id,
                nationalIdField.getText(),
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                phoneNumberField.getText(),
                Util.getAttachmentDataFromFilePath(imageAddress),
                Integer.parseInt(roomNumberField.getText()),
                ProfessorType.normal,
                professorRank
        );

        AddUser.addProfessor(professorData);

        var userData = client.getUserDataByNationalId(nationalId);

        try {
            PasswordChangeData passwordChangeData = new PasswordChangeData(
                    userData.id,
                    Utils.hashPassword(passwordField.getText()));

            Client.getInstance().updateUserPassword(passwordChangeData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        AddUser.showPage("professor");
    }
}
