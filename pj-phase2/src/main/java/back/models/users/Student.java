package back.models.users;

import back.database.DataBase;
import back.models.course.Course;
import back.models.course.Score;
import commons.enums.StudentEducationalStatus;
import commons.enums.StudentType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "student")
@DiscriminatorValue("student")
public class Student extends User {

    @ManyToOne
    @JoinColumn(name = "supervisor_professor_id")
    Professor supervisorProfessor;

    @Enumerated(EnumType.ORDINAL)
    StudentEducationalStatus educationalStatus;

    @Enumerated(EnumType.ORDINAL)
    StudentType type;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    List<Score> activeScores;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    List<Score> passedScores;

    @Column
    Double totalGradePointAverage;

    @Column
    Integer staringYear;

    @Column
    String major;

    @Column(name = "student_number")
    String studentNumber;

    @ManyToMany()
    List<Course> TACourses;

    protected Student() {
    }

    public Student(String username, String notHashedPassword, String email, StudentType type) {
        super(username, notHashedPassword, email);
        this.type = type;
    }

    public StudentEducationalStatus getEducationalStatus() {
        return educationalStatus;
    }

    public void setEducationalStatus(StudentEducationalStatus educationalStatus) {
        this.educationalStatus = educationalStatus;
    }

    public Professor getSupervisorProfessor() {
        return supervisorProfessor;
    }

    public void setSupervisorProfessor(Professor supervisorProfessor) {
        this.supervisorProfessor = supervisorProfessor;
    }

    public Double getTotalGradePointAverage() {
        return totalGradePointAverage;
    }

    public void setTotalGradePointAverage(Double totalGradePointAverage) {
        this.totalGradePointAverage = totalGradePointAverage;
    }

    public Integer getStaringYear() {
        return staringYear;
    }

    public void setStaringYear(Integer staringYear) {
        this.staringYear = staringYear;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void addActiveScore(Score score) {
        this.activeScores.add(score);
    }

    public List<Score> getActiveScores() {
        return activeScores;
    }

    public void setActiveScores(List<Score> activeScores) {
        this.activeScores = activeScores;
    }

    public void addPassedCourseScore(Score score) {
        this.passedScores.add(score);
    }

    public List<Score> getPassedScores() {
        return passedScores;
    }

    public void setPassedScores(List<Score> passedScores) {
        this.passedScores = passedScores;
    }

    public StudentType getType() {
        return type;
    }

    public void setType(StudentType type) {
        this.type = type;
    }

    @Override
    public ArrayList<Course> getActiveCourses() {
        ArrayList<Course> result = new ArrayList<>();

        for (Score score : activeScores) {
            result.add(score.getCourse());
        }

        result.addAll(TACourses);

        return result;
    }

    public List<Course> getTACourses() {
        return TACourses;
    }

    public void setTACourses(List<Course> TACourses) {
        this.TACourses = TACourses;
    }

    @Override
    public List<User> getContacts() {
        var result = new ArrayList<User>();

        result.addAll(
                (List<Student>) DataBase.entityManager.
                        createNativeQuery(String.format("SELECT * FROM edu_use WHERE user_type='student' AND staringyear=%s", staringYear), Student.class)
                        .getResultList()
        );

        result.addAll(
                (List<Student>) DataBase.entityManager.
                createNativeQuery(String.format("SELECT * FROM edu_use WHERE user_type='student' AND major='%s' AND staringyear!=%s",major, staringYear), Student.class)
                .getResultList()
        );

        result.add(supervisorProfessor);

        return result;
    }

    public void updateTotalGradePointAverage() {
        Integer passedCoursesTotalSize = 0;
        Double scoresTotalWeightedSum = 0.0;

        for (var score: passedScores) {
            var course = score.getCourse();

            passedCoursesTotalSize += course.getSize();
            scoresTotalWeightedSum += course.getSize() * score.getFinalScore();
        }

        totalGradePointAverage = scoresTotalWeightedSum / passedCoursesTotalSize;
    }
}
