package front.services.model_handlers;

import front.app.SceneControl;
import front.commons.data_class.AttachmentData;
import front.commons.data_class.EducationalContentData;
import front.services.Client;

import java.util.UUID;

public class EducationalContentItem {

    static UUID educationalContentId;

    static Boolean updating;
    static String changingItemText;
    static UUID changingMediaAttachmentId;

    public static void showNewItemPage(UUID educationalContentId) {
        updating = false;
        EducationalContentItem.educationalContentId = educationalContentId;
        SceneControl.changeScene(SceneControl.SceneType.educationalContentItem);
    }

    public static void showUpdateItemPage(UUID educationalContentId, String itemText) {
        updating = true;
        EducationalContentItem.educationalContentId = educationalContentId;
        changingItemText = itemText;
        SceneControl.changeScene(SceneControl.SceneType.educationalContentItem);
    }

    public static void showUpdateItemPage(UUID educationalContentId, UUID attachmentId) {
        updating = true;
        EducationalContentItem.educationalContentId = educationalContentId;
        changingMediaAttachmentId = attachmentId;
        SceneControl.changeScene(SceneControl.SceneType.educationalContentItem);
    }

    public static void updateEducationalContentItem(String text) {
        var client = Client.getInstance();
        var educationalContentData = client.getEducationalContentData(educationalContentId);

        if (!updating) {
            if (educationalContentData.attachmentsId.size() + educationalContentData.texts.size() > 4)
                return;

            educationalContentData.texts.add(text);
            client.updateEducationalContent(educationalContentData);
        }

        educationalContentData.texts.remove(changingItemText);
        educationalContentData.texts.add(text);

        client.updateEducationalContent(educationalContentData);

        CW.showPage();
    }

    public static void updateEducationalContentItem(AttachmentData attachmentData) {
        var client = Client.getInstance();
        var educationalContentData = client.getEducationalContentData(educationalContentId);

        if (!updating) {
            if (educationalContentData.attachmentsId.size() + educationalContentData.texts.size() > 4)
                return;

            educationalContentData.attachmentsId.add(attachmentData.id);
            client.updateEducationalContent(educationalContentData);
        }

        educationalContentData.attachmentsId.remove(changingMediaAttachmentId);
        educationalContentData.attachmentsId.add(attachmentData.id);

        client.updateEducationalContent(educationalContentData);

        CW.showPage();
    }

    public static EducationalContentData getCurrentEducationalContentData() {
        return Client.getInstance().getEducationalContentData(educationalContentId);
    }

    public static void deleteItem(UUID educationalContentId, String text) {
        var client = Client.getInstance();
        var educationalContentData = client.getEducationalContentData(educationalContentId);

        educationalContentData.texts.remove(text);
        client.updateEducationalContent(educationalContentData);
    }

    public static void deleteItem(UUID educationalContentId, UUID attachmentId) {
        var client = Client.getInstance();
        var educationalContentData = client.getEducationalContentData(educationalContentId);

        educationalContentData.attachmentsId.remove(attachmentId);
        client.updateEducationalContent(educationalContentData);
    }
}
