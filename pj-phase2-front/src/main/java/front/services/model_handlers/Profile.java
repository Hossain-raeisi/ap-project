package front.services.model_handlers;

import front.app.SceneControl;

public class Profile {
    public static void showPage() {
        SceneControl.changeScene(SceneControl.SceneType.profilePage);
    }
}
