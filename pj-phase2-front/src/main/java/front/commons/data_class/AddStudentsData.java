package front.commons.data_class;

import java.util.ArrayList;
import java.util.UUID;

public class AddStudentsData {
    public UUID courseId;
    public ArrayList<UUID> studentIds;

    public AddStudentsData(UUID courseId, ArrayList<UUID> studentIds) {
        this.courseId = courseId;
        this.studentIds = studentIds;
    }
}
