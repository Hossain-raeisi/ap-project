package front.services.util;

import front.app.SceneControl;
import front.commons.data_class.AttachmentData;
import front.commons.data_class.EducationalContentData;
import front.services.Client;

import java.util.ArrayList;
import java.util.UUID;

public class EducationalContent {

    public enum UserType {
        professor,
        TA,
        other,
    }

    static EducationalContentData educationalContentData;
    static UUID courseId;

    public static void showPage(String educationalContentId, UUID courseId) {
        educationalContentData = Client.getInstance().getEducationalContentData(UUID.fromString(educationalContentId));
        EducationalContent.courseId = courseId;
        SceneControl.changeScene(SceneControl.SceneType.educationalContentPage);
    }

    public static void showNewEducationalContentPage(String courseId) {
        educationalContentTexts.clear();
        educationalContentAttachmentsData.clear();

        Exams.courseId = UUID.fromString(courseId);
        SceneControl.changeScene(SceneControl.SceneType.newEducationalContentPage);
    }

    public static EducationalContentData getEducationalContentData() {
        return educationalContentData;
    }

    public static void removeEducationalContent(UUID educationalContentId) {
        Client.getInstance().removeEducationalData(educationalContentId);
    }

    public static UserType getCurrentUserType() {
        var client = Client.getInstance();
        var courseData = client.getCourseData(courseId);
        var userData = client.getCurrentUserData();

        if (courseData.professorId.equals(userData.id))
            return UserType.professor;

        if (courseData.TAsId.contains(userData.id))
            return UserType.TA;

        return UserType.other;
    }


    static ArrayList<String> educationalContentTexts;
    static ArrayList<AttachmentData> educationalContentAttachmentsData;

    public static void addNewEducationalContentText(String text) {
        educationalContentTexts.add(text);
    }

    public static void addNewEducationalAttachmentData(AttachmentData attachmentData) {
        educationalContentAttachmentsData.add(attachmentData);
    }

    public static void addEducationalContent(String educationalContentName) {
        EducationalContentData newEducationalContentData = new EducationalContentData(
                educationalContentName,
                new ArrayList<>(educationalContentTexts),
                new ArrayList<>(educationalContentAttachmentsData.stream().map(educationalContentData -> educationalContentData.id).toList()),
                courseId
        );

        educationalContentTexts.clear();
        educationalContentAttachmentsData.clear();

        Client.getInstance().addEducationalContent(newEducationalContentData);
        Course.showPage(courseId.toString());
    }


}
