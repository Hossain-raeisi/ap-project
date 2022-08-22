package front.app;

import front.commons.data_class.AttachmentData;
import front.commons.data_class.UserData;
import front.services.FileHandler;
import javafx.scene.image.Image;

import java.util.List;

public class Util {

    public static String getUserFullName(UserData userData) {
        return userData.firstName + " " + userData.lastName;
    }

    public static Image getImageFromAttachmentData(AttachmentData attachmentData) {
        var filePath = FileHandler.writeFile(attachmentData.data, attachmentData.fileName);
        return new Image("file:media/" + attachmentData.fileName);
    }

    public static AttachmentData getAttachmentDataFromFilePath(String filePath) {
        return new AttachmentData(
                FileHandler.readFile(filePath),
                filePath
        );
    }

    public static void stopWorkerThreads(List<Thread> threads) {
        for (var thread : threads) {
            thread.interrupt();
        }
    }
}
