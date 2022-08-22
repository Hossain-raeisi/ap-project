package commons.data_class;

import commons.enums.ProfessorRank;
import commons.enums.ProfessorType;

import java.util.UUID;

public class ProfessorData extends UserData {
    public Integer roomNumber;
    public String professorNumber;
    public ProfessorType type;
    public ProfessorRank rank;

    public ProfessorData(UUID id, String facultyName, UUID facultyId, String nationalId, String firstName,
                         String lastName, String email, String phoneNumber, AttachmentData imageData, int roomNumber,
                         String professorNumber, ProfessorType type, ProfessorRank rank) {
        super(id, facultyName, facultyId, nationalId, firstName, lastName, email, phoneNumber, imageData);
        this.roomNumber = roomNumber;
        this.professorNumber = professorNumber;
        this.type = type;
        this.rank = rank;
    }

    public ProfessorData(String facultyName, UUID facultyId, String nationalId, String firstName, String lastName,
                         String email, String phoneNumber, AttachmentData imageData, int roomNumber, ProfessorType type,
                         ProfessorRank rank) {
        super(facultyName, facultyId, nationalId, firstName, lastName, email, phoneNumber, imageData);
        this.roomNumber = roomNumber;
        this.type = type;
        this.rank = rank;
    }
}
