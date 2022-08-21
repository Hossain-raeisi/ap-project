package front.commons.data_class;

import java.util.UUID;

public class AuthCheckData {
    UUID userId;
    String userHashedPassword;

    public AuthCheckData(UUID userId, String userHashedPassword) {
        this.userId = userId;
        this.userHashedPassword = userHashedPassword;
    }
}
