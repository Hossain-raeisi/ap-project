package front.commons.data_class;

public class CaptchaData {
    public String text;
    public AttachmentData attachmentData;

    public CaptchaData(String text, AttachmentData attachmentData) {
        this.text = text;
        this.attachmentData = attachmentData;
    }
}
