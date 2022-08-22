package front.app.fxml_controllers;

import front.app.Util;
import front.commons.data_class.PasswordChangeData;
import front.commons.data_class.ProfessorData;
import front.commons.enums.ProfessorRank;
import front.services.Client;
import front.services.Logger;
import front.services.security.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.security.NoSuchAlgorithmException;

public class EditUserProfessor {

    @FXML
    Button editProfessorButton;
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

    public void editProfessorButtonAction() {
        ProfessorData professorData = front.services.model_handlers.EditUserProfessor.getCurrentProfessorData();

        String firstName = firstNameField.getText();
        if (!firstName.equals("")){
            professorData.firstName = firstName;
        }

        String lastName = lastNameField.getText();
        if (!lastName.equals("")){
            professorData.lastName = lastName;
        }

        String facultyName = facultyNameField.getText();
        if (!facultyName.equals("")){
            professorData.facultyId = Client.getInstance().getFacultyByName(facultyName).id;
        }

        String email = emailField.getText();
        if (!email.equals("")){
            professorData.email = email;
        }

        String nationalId = nationalIdField.getText();
        if (!nationalId.equals("")){
            professorData.nationalId = nationalId;
        }

        String phoneNumber = phoneNumberField.getText();
        if (!phoneNumber.equals("")){
            professorData.phoneNumber = phoneNumber;
        }

        String imageAddress = imageAddressField.getText();
        if (!imageAddress.equals("")){
            professorData.imageData = Util.getAttachmentDataFromFilePath(imageAddress);
        }

        String password = passwordField.getText();
        if (!password.equals("")){
            try {
                PasswordChangeData passwordChangeData = new PasswordChangeData(
                        professorData.id,
                        Utils.hashPassword(password));

                Client.getInstance().updateUserPassword(passwordChangeData);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        String rank = rankField.getText();
        switch (rank) {
            case "assistant professor" -> professorData.rank = ProfessorRank.assistantProfessor;
            case "associate professor" -> professorData.rank = ProfessorRank.associateProfessor;
            case "full professor" -> professorData.rank = ProfessorRank.fullProfessor;
        }

        String roomNumber = roomNumberField.getText();
        if (!roomNumber.equals("")){
            professorData.roomNumber = Integer.parseInt(roomNumber);
        }

        Client.getInstance().updateProfessor(professorData);

        Logger.Info("Edit user: " + professorData.id);
    }
}
