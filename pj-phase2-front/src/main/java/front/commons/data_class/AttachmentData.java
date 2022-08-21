package front.commons.data_class;

import java.util.Objects;
import java.util.UUID;

public class AttachmentData {
    public UUID id;
    public byte[] data;
    public String fileName;

    public AttachmentData(UUID id, byte[] data, String fileName) {
        this.id = id;
        this.data = data;
        this.fileName = fileName;
    }

    public AttachmentData(byte[] data, String fileName) {
        this.data = data;
        this.fileName = fileName;
    }
}
