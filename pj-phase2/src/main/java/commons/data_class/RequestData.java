package commons.data_class;

import commons.enums.RequestStatus;
import commons.enums.RequestType;

import java.util.ArrayList;
import java.util.UUID;

public class RequestData {

    public UUID id;
    public RequestType type;
    public String title;
    public String description;
    public RequestStatus status;
    public String response;
    public String assignerName;
    public UUID assignerId;
    public ArrayList<UUID> assigneeIds;

    public RequestData(UUID id, RequestType type, String title, String description, RequestStatus status,
                       String response, String assignerName, UUID assignerId, ArrayList<UUID> assigneeIds) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.status = status;
        this.response = response;
        this.assignerName = assignerName;
        this.assignerId = assignerId;
        this.assigneeIds = assigneeIds;
    }
}
