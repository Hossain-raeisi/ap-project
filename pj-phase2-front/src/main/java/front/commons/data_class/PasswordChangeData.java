package front.commons.data_class;

import java.util.UUID;

public class PasswordChangeData {
    UUID userId;
    String hashedPassword;

    public PasswordChangeData(UUID userId, String hashedPassword) {
        this.userId = userId;
        this.hashedPassword = hashedPassword;
    }
}