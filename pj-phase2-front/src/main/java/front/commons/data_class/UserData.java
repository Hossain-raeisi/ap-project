package front.commons.data_class;

import java.util.UUID;

public class UserData {
    public UUID id;
    public String facultyName;
    public UUID facultyId;
    public String nationalId;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public AttachmentData imageData;

    public UserData(UUID id, String facultyName, UUID facultyId, String nationalId, String firstName, String lastName,
                    String email, String phoneNumber, AttachmentData imageData) {
        this.id = id;
        this.facultyName = facultyName;
        this.facultyId = facultyId;
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imageData = imageData;
    }

    public UserData(String facultyName, UUID facultyId, String nationalId, String firstName, String lastName,
                    String email, String phoneNumber, AttachmentData imageData) {
        this.facultyName = facultyName;
        this.facultyId = facultyId;
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imageData = imageData;
    }
}
