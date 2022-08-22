package back.services;

import back.database.DataBase;
import back.models.course.Course;
import back.models.course.Score;
import back.models.users.Student;
import commons.data_class.AddStudentsData;

import java.util.UUID;

public class CourseHandler {

    public static void finalizeCourse(Course course) {
        try {
            DataBase.entityManager.getTransaction().begin();

            for (Score score : course.getScores()) {
                Student student = score.getStudent();

                student.getActiveScores().remove(score);
                student.getPassedScores().add(score);

                student.updateTotalGradePointAverage();
            }

            course.setArchived(true);

            DataBase.entityManager.getTransaction().commit();

            Logger.Info("Finalize and archive course: " + course.getId());
        } catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
            Logger.Error("Couldn't finalize course");
        }
    }

    public static void addTAsToCourse(AddStudentsData addTAsData) {
        try {
            DataBase.entityManager.getTransaction().begin();

            var course = DataBase.entityManager.find(Course.class, addTAsData.courseId);

            for (var studentId : addTAsData.studentIds) {
                course.getTAs().add(DataBase.entityManager.find(Student.class, studentId));
            }

            DataBase.entityManager.getTransaction().commit();

            Logger.Info("Added TAs to course: " + course.getId());
        } catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
            Logger.Error("Couldn't add TA to course");
        }
    }

    public static void addStudentsToCourse(AddStudentsData addStudentsData) {
        try {
            DataBase.entityManager.getTransaction().begin();

            var course = DataBase.entityManager.find(Course.class, addStudentsData.courseId);

            for (var studentId : addStudentsData.studentIds) {
                var score = new Score(
                        course,
                        DataBase.entityManager.find(Student.class, studentId)
                );
                DataBase.entityManager.persist(score);
            }

            DataBase.entityManager.getTransaction().commit();

            Logger.Info("Added TAs to course: " + course.getId());
        } catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
            Logger.Error("Couldn't add TA to course");
        }
    }
}
