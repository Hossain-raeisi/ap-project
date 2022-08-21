package back.models.users;

import back.database.DataBase;
import back.services.Logger;
import back.services.RequestHandler;
import back.models.course.Course;
import back.models.request.Request;
import commons.enums.ProfessorRank;
import commons.enums.ProfessorType;
import commons.enums.RequestType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "professor")
@DiscriminatorValue("professor")
public class Professor extends User {

    @Enumerated(EnumType.ORDINAL)
    ProfessorType type;

    @Enumerated(EnumType.ORDINAL)
    ProfessorRank rank;

    @OneToMany(mappedBy = "professor")
    List<Course> activeCourses;

    @Column
    int roomNumber;

    @Column
    String professorNumber;

    protected Professor() {
    }

    public Professor(String nationalId, String notHashedPassword, String email, ProfessorType type) {
        super(nationalId, notHashedPassword, email);
        this.type = type;
    }

    public ProfessorRank getRank() {
        return rank;
    }

    public void setRank(ProfessorRank rank) {
        this.rank = rank;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getProfessorNumber() {
        return professorNumber;
    }

    public void setProfessorNumber(String professorNumber) {
        this.professorNumber = professorNumber;
    }

    public ProfessorType getType() {
        return type;
    }

    public void setType(ProfessorType type) {
        this.type = type;
    }

    @Override
    public List<Course> getActiveCourses() {
        return activeCourses;
    }

    public void setActiveCourses(ArrayList<Course> activeCourses) {
        this.activeCourses = activeCourses;
    }

    public void disapproveAllDeputyRequests() {
        if (!type.equals(ProfessorType.deputyEducation))
            return;

        Request[] assigneeRequestsCopy = getAssigneeRequests().toArray(new Request[0]);
        for (Request request : assigneeRequestsCopy) {
            if (request.getType() == RequestType.minor) {
                RequestHandler.disApproveRequest(request, "Deputy Education was removed");
                Logger.Warn("Remove dpEducation while having active assignee requests");
            }
        }
    }

    @Override
    public List<User> getContacts() {
        if (type == ProfessorType.normal)
            return getNormalProfessorContacts();
        return getPrivilegedProfessorContacts();
    }

    private List<User> getNormalProfessorContacts() {
        var result = new ArrayList<User>();

        result.addAll(
                (List<Student>) DataBase.entityManager.
                        createNativeQuery(String.format("SELECT * FROM edu_use WHERE user_type='student' AND supervisor_professor_id=%s", getId()), Student.class)
                        .getResultList()
        );

        return result;
    }

    private List<User> getPrivilegedProfessorContacts() {
        var result = new ArrayList<User>();

        result.addAll(
                (List<Student>) DataBase.entityManager.
                        createNativeQuery(String.format("SELECT * FROM edu_user WHERE user_type='student' AND faculty_id='%s'", faculty.getId()), Student.class)
                        .getResultList()
        );

        return result;
    }
}
