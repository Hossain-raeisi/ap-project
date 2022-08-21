package commons.data_class;

import commons.enums.ProfessorRank;

public class ProfessorFilter {
    public String name;
    public ProfessorRank rank;
    public String facultyId;

    public ProfessorFilter(String name, ProfessorRank rank, String facultyId) {
        this.name = name;
        this.rank = rank;
        this.facultyId = facultyId;
    }
}
