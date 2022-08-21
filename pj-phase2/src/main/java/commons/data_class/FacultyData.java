package commons.data_class;

import java.util.ArrayList;
import java.util.UUID;

public class FacultyData {
    public UUID id;
    public ArrayList<UUID> professorIds;
    public String deputyEducationProfessorName;
    public UUID deputyEducationProfessorId;
    public String facultyHeadProfessorName;
    public UUID facultyHeadProfessorId;
    public String name;
    public ArrayList<String> majors;
    public ArrayList<String> minors;

    public FacultyData(UUID id, ArrayList<UUID> professorIds, String deputyEducationProfessorName,
                       UUID deputyEducationProfessorId, String facultyHeadProfessorName, UUID facultyHeadProfessorId,
                       String name, ArrayList<String> majors, ArrayList<String> minors) {
        this.id = id;
        this.professorIds = professorIds;
        this.deputyEducationProfessorName = deputyEducationProfessorName;
        this.deputyEducationProfessorId = deputyEducationProfessorId;
        this.facultyHeadProfessorName = facultyHeadProfessorName;
        this.facultyHeadProfessorId = facultyHeadProfessorId;
        this.name = name;
        this.majors = majors;
        this.minors = minors;
    }
}
