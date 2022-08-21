package front.services;

import front.Config;
import front.commons.data_class.AttachmentData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler {

    public static byte[] readFile(String filePath) {
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String writeFile(byte[] data, String fileName) {
        String filePath = Config.MEDIA_BASE_PATH + fileName;
        try {
            Files.write(
                    Paths.get(filePath),
                    data
            );
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AttachmentData getAttachmentDataFromPath(String filePath) {
        var fileData = readFile(filePath);
        var fileName = filePath.split("/")[filePath.split("/").length];
        return new AttachmentData(
                fileData,
                fileName
        );
    }
}
