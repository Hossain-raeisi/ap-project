package front.services.util;

import front.app.SceneControl;

public class Home {

    public static void home(){
        SceneControl.changeScene(SceneControl.SceneType.homePage);
    }
}
