package front.app.fxml_controllers;

import front.commons.data_class.AuthCheckData;
import front.commons.data_class.PasswordChangeData;
import front.app.SceneControl;
import front.services.Client;
import front.services.security.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ChangePassword {

    @FXML
    TextField oldPasswordField;
    @FXML
    Button changePasswordButton;
    @FXML
    TextField password1Field;
    @FXML
    TextField password2Field;

    public void changePasswordButtonAction(ActionEvent ignoredActionEvent) {
        Client client = Client.getInstance();

        String oldPassword = oldPasswordField.getText();
        String password1 = password1Field.getText();
        String password2 = password2Field.getText();

        try {
            AuthCheckData authCheckData = new AuthCheckData(
                    client.getCurrentUserData().id,
                    Utils.hashPassword(oldPassword)
            );
            client.checkAuth(authCheckData);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (!password1.equals(password2)) {
            return;
        }

        try {
            PasswordChangeData passwordChangeData = new PasswordChangeData(
                    client.getCurrentUserData().id,
                    Utils.hashPassword(password1));
            client.updateUserPassword(passwordChangeData);
            SceneControl.changeScene(SceneControl.SceneType.homePage);
        } catch (Exception ignored){}

    }
}
