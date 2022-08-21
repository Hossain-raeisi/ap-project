package front.services.security;

import front.commons.data_class.CaptchaData;
import front.app.SceneControl;
import front.app.Util;
import front.services.Client;
import javafx.scene.image.Image;

import java.time.LocalDateTime;

public class LogIn {
    static CaptchaData captchaData;
    static int passwordChangeTimeDeltaInSeconds = 60 * 60 * 24 * 10;

    public static void showPage(){
        SceneControl.changeScene(SceneControl.SceneType.logInPage);
    }

    public static void newCaptcha(){
        captchaData = Client.getInstance().getCaptcha();
    }

    public static void logIn(String idNumber, String password, String captcha) {
        var client = Client.getInstance();

//        if (!captcha.equals(LogIn.captchaData.text)){
//            LogIn.showPage();
//            return;
//        }

        if (client.logIn(idNumber, password)){
            SceneControl.loadScenes(client.getUserType(client.getCurrentUserData().id));
            var userLogIns = client.getUserLogIns(client.getCurrentUserData().id);
//            if (userLogIns.get(userLogIns.size() - 2).isBefore(LocalDateTime.now().minusSeconds(passwordChangeTimeDeltaInSeconds))) {
//                SceneControl.changeScene(SceneControl.SceneType.changePasswordPage);
//            } else {
                SceneControl.changeScene(SceneControl.SceneType.homePage);
//            }
        }
    }

    public static Image getCaptchaImage() {
        return Util.getImageFromAttachmentData(captchaData.attachmentData);
    }
}
