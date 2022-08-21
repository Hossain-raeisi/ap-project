package front.services.security;

import front.app.SceneControl;
import front.services.Client;
import front.services.util.Clear;

public class LogOut {

    public static void logOut(){
        Client.getInstance().logOut();
        Clear.clear();
        SceneControl.changeScene(SceneControl.SceneType.logInPage);
    }
}
