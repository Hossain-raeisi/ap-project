package back.models;

import back.models.users.Student;
import commons.enums.StudentType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "course_selection_filter")
public class CourseSelectionFilter {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "starting_year")
    int startingYear;

    @Column(name = "student_type")
    @Enumerated(EnumType.ORDINAL)
    StudentType studentType;

    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime startTime;

    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime endTime;

    public CourseSelectionFilter() {

    }

    public CourseSelectionFilter(int startingYear, StudentType studentType, LocalDateTime startTime,
                                 LocalDateTime endTime) {
        this.startingYear = startingYear;
        this.studentType = studentType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UUID getId() {
        return id;
    }

    public int getStartingYear() {
        return startingYear;
    }

    public void setStartingYear(int startingYear) {
        this.startingYear = startingYear;
    }

    public StudentType getStudentType() {
        return studentType;
    }

    public void setStudentType(StudentType studentType) {
        this.studentType = studentType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean doesStudentApplies(Student student) {
        return student.getStaringYear() == startingYear && student.getType() == studentType;
    }
}
