package front.app.fxml_controllers;

import front.services.security.LogIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class Login {

    @FXML
    ImageView captchaImage;
    @FXML
    TextField idNumberField;
    @FXML
    TextField passwordField;
    @FXML
    TextField captchaField;
    @FXML
    Button logInButton;

    @FXML
    public void initialize() {
        LogIn.newCaptcha();
        captchaImage.setImage(LogIn.getCaptchaImage());
    }

    public void loginButtonAction(ActionEvent actionEvent) {
        String idNumber = idNumberField.getText();
        String password = passwordField.getText();
        String captcha = captchaField.getText();

        LogIn.logIn(idNumber, password, captcha);
    }
}
