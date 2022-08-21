package commons.data_class;

public class FileAddress {
    public String bucketName;
    public String objectName;
    public String fileName;

    public FileAddress(String bucketName, String objectName, String fileName) {
        this.bucketName = bucketName;
        this.objectName = objectName;
        this.fileName = fileName;
    }
}
