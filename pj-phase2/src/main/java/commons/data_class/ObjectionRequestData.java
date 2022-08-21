package commons.data_class;

import java.util.UUID;

public class ObjectionRequestData {
    public UUID scoreId;
    public String text;

    public ObjectionRequestData(UUID scoreId, String text) {
        this.scoreId = scoreId;
        this.text = text;
    }
}
