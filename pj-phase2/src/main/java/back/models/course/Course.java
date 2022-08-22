package back.models.course;

import back.models.faculty.Faculty;
import back.models.users.Professor;
import back.models.users.Student;
import commons.enums.CourseLevel;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        indexes = {
                @Index(columnList = "faculty_id"),
                @Index(columnList = "professor_id"),
                @Index(columnList = "level"),
                @Index(columnList = "archived"),
        }
)
public class Course {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    Professor professor;

    @Enumerated(EnumType.ORDINAL)
    CourseLevel level;

    @Column
    String name;

    @Column
    int size;

    @Column
    String time;

    @Column
    Boolean temporaryScoresSet;

    @Column
    Boolean archived;

    @OneToMany(mappedBy = "course")
    List<Exam> exams;
    @OneToMany(mappedBy = "course")
    List<Score> scores;

    @Column
    List<String> weekDays;

    @ManyToMany()
    @JoinTable(
            name = "course_tas",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "edu_user_id")}
    )
    List<Student> TAs;

    @OneToMany(cascade = CascadeType.ALL)
    List<EducationalContent> educationalContents;

    protected Course() {
    }

    public Course(String name, Faculty faculty, Professor professor, int size, CourseLevel level) {
        this.name = name;
        this.faculty = faculty;
        this.professor = professor;
        this.size = size;
        this.level = level;
        this.archived = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public CourseLevel getLevel() {
        return level;
    }

    public void setLevel(CourseLevel level) {
        this.level = level;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void addExam(Exam exam) {
        this.exams.add(exam);
    }

    public void removeExam(Exam exam) {
        exams.remove(exam);
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public void addScore(Score score) {
        scores.add(score);
    }

    public void removeScore(Score score) {
        scores.remove(score);
    }

    public List<Student> getRegisteredStudents() {
        ArrayList<Student> result = new ArrayList<>();
        for (Score score : scores) {
            result.add(score.student);
        }
        return result;
    }

    public void addDay(String weekday) {
        weekDays.add(weekday);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(ArrayList<String> weekDays) {
        this.weekDays = weekDays;
    }

    public Boolean isTemporaryScoresSet() {
        return temporaryScoresSet;
    }

    public void setTemporaryScoresSet(Boolean temporaryScoresSet) {
        this.temporaryScoresSet = temporaryScoresSet;
    }

    public Boolean isArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }

    public Double getTotalGradePointAverage() {
        Double sum = 0.0;

        try {
            for (Score score : scores) {
                sum += score.getFinalScore();
            }

            return sum / scores.size();

        } catch (Exception ignored) {
            return 0.0;
        }
    }

    public Integer getPassedNumber() {
        Integer passed = 0;

        try {
            for (Score score: scores) {
                if (score.getFinalScore() > 10)
                    passed++;
            }

        } catch (Exception ignored) {}

        return passed;
    }

    public Integer getFailedNumber() {
        Integer failed = 0;

        try {
            for (Score score : scores) {
                if (score.getFinalScore() < 10)
                    failed++;
            }

        } catch (Exception ignored) {
        }

        return failed;
    }

    public Double getTotalGradePointAverageWithoutFails() {
        Double sum = 0.0;

        try {
            for (Score score: scores) {
                if (score.getFinalScore() > 10)
                    sum += score.getFinalScore();
            }

            return sum / getPassedNumber();

        } catch (Exception ignored) {
            return 0.0;
        }
    }

    public Boolean getTemporaryScoresSet() {
        return temporaryScoresSet;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public void setWeekDays(List<String> weekDays) {
        this.weekDays = weekDays;
    }

    public List<Student> getTAs() {
        return TAs;
    }

    public void setTAs(List<Student> TAs) {
        this.TAs = TAs;
    }

    public List<EducationalContent> getEducationalContents() {
        return educationalContents;
    }

    public void setEducationalContents(List<EducationalContent> educationalContents) {
        this.educationalContents = educationalContents;
    }
}
