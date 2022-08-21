package commons.data_class;

public class LogInData {
    public String nationalId;
    public String hashedPassword;

    public LogInData(String nationalId, String hashedPassword) {
        this.nationalId = nationalId;
        this.hashedPassword = hashedPassword;
    }
}
