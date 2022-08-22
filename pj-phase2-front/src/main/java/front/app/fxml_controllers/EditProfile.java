package front.app.fxml_controllers;

import front.app.Util;
import front.commons.data_class.UserData;
import front.services.Client;
import front.services.Logger;
import front.services.model_handlers.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EditProfile {

    @FXML
    TextField pictureAddressField;
    @FXML
    TextField emailField;
    @FXML
    TextField phoneNumberField;
    @FXML
    Button EditButton;

    public void EditButtonAction(ActionEvent ignoredActionEvent) {
        UserData userData = Client.getInstance().getCurrentUserData();// tODO

        String email = emailField.getText();
        String pictureAddress = pictureAddressField.getText();
        String phoneNumber = phoneNumberField.getText();

        if (email.equals("") && pictureAddress.equals("") && phoneNumber.equals(""))
            return;

        if (!email.equals("")) {
            userData.email = email;
        }

        if (!phoneNumber.equals("")) {
            userData.phoneNumber = phoneNumber;
        }

        if (!pictureAddress.equals("")) {
            userData.imageData = Util.getAttachmentDataFromFilePath(pictureAddress);
        }

        Client.getInstance().updateUser(userData);

        Logger.Info("Edit user: " + userData.id);

        Profile.showPage();
    }
}
