package front.services.util;

import front.app.SceneControl;

public class CourseSelection {
    public static void showPage() {
        SceneControl.changeScene(SceneControl.SceneType.courseSelectionPage);
    }
}
