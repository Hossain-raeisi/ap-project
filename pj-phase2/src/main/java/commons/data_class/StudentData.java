package commons.data_class;

import commons.enums.StudentEducationalStatus;
import commons.enums.StudentType;

import java.util.ArrayList;
import java.util.UUID;

public class StudentData extends UserData{
    public String supervisorProfessorName;
    public UUID supervisorProfessorId;
    public StudentEducationalStatus educationalStatus;
    public StudentType type;
    public ArrayList<UUID> activeScoresId;
    public ArrayList<UUID> passedScoresId;
    public Float totalGradePointAverage;
    public int startingYear;
    public String major;
    public String studentNumber;

    public StudentData(UUID id, String facultyName, UUID facultyId, String nationalId, String firstName,
                       String lastName, String email, String phoneNumber, AttachmentData imageData,
                       String supervisorProfessorName, UUID supervisorProfessorId,
                       StudentEducationalStatus educationalStatus, StudentType type, ArrayList<UUID> activeScoresId,
                       ArrayList<UUID> passedScoresId, Float totalGradePointAverage, int startingYear, String major,
                       String studentNumber) {
        super(id, facultyName, facultyId, nationalId, firstName, lastName, email, phoneNumber, imageData);
        this.supervisorProfessorName = supervisorProfessorName;
        this.supervisorProfessorId = supervisorProfessorId;
        this.educationalStatus = educationalStatus;
        this.type = type;
        this.activeScoresId = activeScoresId;
        this.passedScoresId = passedScoresId;
        this.totalGradePointAverage = totalGradePointAverage;
        this.startingYear = startingYear;
        this.major = major;
        this.studentNumber = studentNumber;
    }

    public StudentData(String facultyName, UUID facultyId, String nationalId, String firstName, String lastName,
                       String email, String phoneNumber, AttachmentData imageData, UUID supervisorProfessorId,
                       StudentEducationalStatus educationalStatus, StudentType type, int startingYear,
                       String major, String studentNumber) {
        super(facultyName, facultyId, nationalId, firstName, lastName, email, phoneNumber, imageData);
        this.supervisorProfessorId = supervisorProfessorId;
        this.educationalStatus = educationalStatus;
        this.type = type;
        this.startingYear = startingYear;
        this.major = major;
        this.studentNumber = studentNumber;
    }
}
