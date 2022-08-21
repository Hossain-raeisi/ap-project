package back.models.faculty;

//import back.database.DataBase;
import back.models.users.Professor;
import commons.enums.ProfessorType;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(
        indexes = {
                @Index(columnList = "name"),
        }
)
public class Faculty {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    List<Professor> professors;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deputy_education_professor_id")
    Professor deputyEducationProfessor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "faculty_head_professor_id")
    Professor facultyHeadProfessor;

    @Column(unique = true)
    String name;

    @Column
    List<String> majors;

    @Column
    List<String> minors;

    protected Faculty() {
    }

    public Faculty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMajor(String majorName) {
        majors.add(majorName);
    }

    public List<String> getMajors() {
        return majors;
    }

    public void setMajors(ArrayList<String> majors) {
        this.majors = majors;
    }

    public void addProfessor(Professor professor) {
        professors.add(professor);
    }

    public void removeProfessor(Professor professor) {
        professors.remove(professor);
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public List<String> getMinors() {
        return getMajors();
    }

    public Professor getDeputyEducationProfessor() {
        return deputyEducationProfessor;
    }

    public void setDeputyEducationProfessor(Professor deputyEducationProfessor) {
        this.deputyEducationProfessor = deputyEducationProfessor;
    }

    public Professor getFacultyHeadProfessor() {
        return facultyHeadProfessor;
    }

    public void setFacultyHeadProfessor(Professor facultyHeadProfessor) {
        this.facultyHeadProfessor = facultyHeadProfessor;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void removeDeputyEducation() {
        Professor oldDP = getDeputyEducationProfessor();
        oldDP.setType(ProfessorType.normal);
        oldDP.disapproveAllDeputyRequests();

        deputyEducationProfessor = null;
    }

    public void addDeputyEducation(Professor professor) {
        professor.setType(ProfessorType.deputyEducation);

        deputyEducationProfessor = professor;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }

    public void setMajors(List<String> majors) {
        this.majors = majors;
    }

    public void setMinors(List<String> minors) {
        this.minors = minors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return name.equals(faculty.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
