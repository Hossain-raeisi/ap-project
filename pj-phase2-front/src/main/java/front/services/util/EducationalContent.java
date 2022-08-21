package front.services.util;

import front.app.SceneControl;
import front.commons.data_class.EducationalContentData;
import front.services.Client;

import java.util.UUID;

public class EducationalContent {

    static EducationalContentData educationalContentData;


    public static void showPage(String educationalContentId) {
        educationalContentData = Client.getInstance().getEducationalContentData(UUID.fromString(educationalContentId));
        SceneControl.changeScene(SceneControl.SceneType.educationalContentPage);
    }

    public static void showNewEducationalContentPage(String courseId) {

    }

    public static EducationalContentData getEducationalContentData() {
        return educationalContentData;
    }

    public static void removeEducationalContent(UUID educationalContentId) {
        Client.getInstance().removeEducationalData(educationalContentId);
    }
}
