package commons.data_class;

import commons.enums.RequestUserType;

public class RequestFilter {
    public String userId;
    public RequestUserType type;

    public RequestFilter(String userId, RequestUserType type) {
        this.userId = userId;
        this.type = type;
    }
}
