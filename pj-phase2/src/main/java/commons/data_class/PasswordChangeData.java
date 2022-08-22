package commons.data_class;

import java.util.UUID;

public class PasswordChangeData {
    public UUID userId;
    public String hashedPassword;

    public PasswordChangeData(UUID userId, String hashedPassword) {
        this.userId = userId;
        this.hashedPassword = hashedPassword;
    }
}
