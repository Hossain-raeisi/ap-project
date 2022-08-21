package front.services.model_handlers;

import front.app.SceneControl;

public class Home {

    public static void home(){
        SceneControl.changeScene(SceneControl.SceneType.homePage);
    }
}
