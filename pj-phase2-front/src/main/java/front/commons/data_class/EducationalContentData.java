package front.commons.data_class;

import java.util.ArrayList;
import java.util.UUID;

public class EducationalContentData {
    public UUID id;
    public String name;
    public ArrayList<String> texts;
    public ArrayList<UUID> attachmentsId;
    public UUID courseId;

    public EducationalContentData(UUID id, String name, ArrayList<String> texts, ArrayList<UUID> attachmentsId,
                                  UUID courseId) {
        this.id = id;
        this.name = name;
        this.texts = texts;
        this.attachmentsId = attachmentsId;
        this.courseId = courseId;
    }

    public EducationalContentData(String name, ArrayList<String> texts, ArrayList<UUID> attachmentsId, UUID courseId) {
        this.name = name;
        this.texts = texts;
        this.attachmentsId = attachmentsId;
        this.courseId = courseId;
    }
}
