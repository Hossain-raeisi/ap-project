package back.database;

import back.models.CourseSelectionFilter;
import back.models.course.Course;
import back.models.course.EducationalContent;
import back.models.course.Score;
import back.models.faculty.Faculty;
import back.models.messenger.ChatFeed;
import back.models.request.Request;
import back.models.users.Professor;
import back.models.users.Student;
import back.models.users.User;
import back.services.Logger;
import commons.data_class.*;
import commons.enums.RequestType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static spark.Spark.halt;

public class DataBase {

    public static EntityManager entityManager;

    public static void run() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        entityManager = emf.createEntityManager();
    }

    public static User getUserWithNationalId(String nationalId) {
        String queryString = String.format("SELECT * FROM edu_user WHERE nationalid='%s'", nationalId);
        return (User) entityManager.createNativeQuery(queryString, User.class).getResultList().get(0);
    }

    public static List<Professor> getFilteredProfessors(ProfessorFilter professorFilter) {
        String queryString = "SELECT * FROM edu_user WHERE user_type='professor' ";
        String addedConditions = null;

        if (professorFilter.name != null) {
            addedConditions = "AND ";
            addedConditions += String.format("last_name LIKE '%s' ", professorFilter.name);
        }
        if (professorFilter.rank != null) {
            if (addedConditions == null) {
                addedConditions = "AND ";
            } else {
                addedConditions += "AND ";
            }
            addedConditions += String.format("rank=%s ", Util.getProfessorRankInt(professorFilter.rank));
        }
        if (professorFilter.facultyId != null) {
            if (addedConditions == null) {
                addedConditions = "AND ";
            } else {
                addedConditions += "AND ";
            }
            addedConditions += String.format("faculty_id='%s' ", professorFilter.facultyId);
        }

        if (addedConditions != null) {
            queryString += addedConditions + " )";
        }

        return (List<Professor>) entityManager.createNativeQuery(queryString, Professor.class).getResultList();
    }

    public static List<Course> getFilteredCourses(CourseFilter courseFilter) {
        String queryString = "SELECT * FROM course ";
        String addedConditions = null;

        if (courseFilter.size != null) {
            addedConditions = "WHERE ";
            addedConditions += String.format("size='%s' ", courseFilter.size);
        }
        if (courseFilter.facultyId != null) {
            if (addedConditions == null) {
                addedConditions = "WHERE ";
            } else {
                addedConditions += "AND ";
            }
            addedConditions += String.format("faculty_id='%s' ", courseFilter.facultyId);
        }
        if (courseFilter.id != null) {
            if (addedConditions == null) {
                addedConditions = "WHERE ";
            } else {
                addedConditions += "AND ";
            }
            addedConditions += String.format("id='%s' ", courseFilter.id);
        }
        if (courseFilter.level != null) {
            if (addedConditions == null) {
                addedConditions = "WHERE ";
            } else {
                addedConditions += "AND ";
            }
            addedConditions += String.format("level=%s ", Util.getCourseLevelInt(courseFilter.level));
        }
        if (courseFilter.name != null) {
            if (addedConditions == null) {
                addedConditions = "WHERE ";
            } else {
                addedConditions += "AND ";
            }
            addedConditions += String.format("name LIKE '%s' ", courseFilter.name);
        }

        if (addedConditions != null) {
            queryString += addedConditions;
        }

        return (List<Course>) entityManager.createNativeQuery(queryString, Course.class).getResultList();
    }

    public static List<Student> getFilteredStudents(StudentFilter studentFilter) {
        String queryString = "SELECT * FROM edu_user WHERE user_type='professor' ";
        String addedConditions = null;

        if (studentFilter.name != null) {
            addedConditions = "WHERE ";
            addedConditions += String.format("last_name LIKE '%s' ", studentFilter.name);
        }
        if (studentFilter.studentNumber != null) {
            if (addedConditions == null) {
                addedConditions = "WHERE ";
            } else {
                addedConditions += "AND ";
            }
            addedConditions += String.format("studentnumber='%s' ", studentFilter.studentNumber);
        }

        return (List<Student>) entityManager.createNativeQuery(queryString, Student.class).getResultList();
    }

    public static Student getStudentByStudentNumber(String studentNumber) {
        return (Student) entityManager.createNativeQuery(
                String.format("SELECT * FROM edu_user WHERE user_type='student' AND studentnumber='%s'", studentNumber),
                Student.class
        ).getResultList().get(0);
    }

    public static ArrayList<Score> getNonFinalizedActiveScores(Student student) {
        ArrayList<Score> result = new ArrayList<>();

        for (Score score : student.getActiveScores()) {
            if (!score.isFinalized()) {
                result.add(score);
            }
        }

        return result;
    }

    public static Faculty getFacultyByName(String facultyName) {
        return (Faculty) entityManager.createNativeQuery(
                String.format("SELECT * FROM faculty WHERE name='%s'", facultyName), Faculty.class
        ).getResultList().get(0);
    }

    public static List<Request> getAllObjectionRequests() {
        String queryString = String.format("SELECT * FROM request WHERE type=%s",
                Util.getRequestTypeInt(RequestType.objectionRequest));

        return (List<Request>) entityManager.createNativeQuery(queryString, Request.class).getResultList();
    }

    public static List<Score> getAllFinalizedScoreProfessor(String professorId) {
        ArrayList<Score> result = new ArrayList<>();

        for (Score score : (List<Score>) entityManager.
                createNativeQuery("SELECT * FROM score WHERE finalized=false", Score.class).getResultList()) {
            if (score.getCourse().getProfessor().getNationalId().equals(professorId)) {
                result.add(score);
            }
        }

        return result;
    }

    public static void removeCourse(String courseId) {
        try {
            entityManager.getTransaction().begin();
            Course course = entityManager.find(Course.class, UUID.fromString(courseId));
            entityManager.remove(course);
            entityManager.getTransaction().commit();
            Logger.Info("Remove course: " + courseId);
        } catch (Exception exception) {
            entityManager.getTransaction().rollback();
            exception.printStackTrace();
            Logger.Error("Couldn't remove course: " + courseId);
        }
    }

    public static void removeUser(String userId) {
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, UUID.fromString(userId));
            entityManager.remove(user);
            entityManager.getTransaction().commit();
            Logger.Info("Remove user: " + userId);
        } catch (Exception exception) {
            entityManager.getTransaction().rollback();
            exception.printStackTrace();
            Logger.Error("Couldn't remove user: " + userId);
        }
    }

    public static ArrayList<Request> getFilteredRequests(User user, RequestFilter requestFilter) {
        switch (requestFilter.type) {
            case assignee -> {
                return new ArrayList<>(user.getAssigneeRequests());
            }
            case assigner -> {
                return new ArrayList<>(user.getAssignerRequests());
            }
        }

        return null;
    }

    public static List<EducationalContent> getEducationalContents(Course course) {
        return (List<EducationalContent>) DataBase.entityManager.createNativeQuery(
                String.format("SELEct * FROM educationalcontent WHERE course_id='%s'", course.getId()),
                EducationalContent.class
        ).getResultList();
    }

    public static void removeEducationalContent(String educationalContentId) {
        try {
            entityManager.getTransaction().begin();
            var educationalContent = entityManager.find(EducationalContent.class, UUID.fromString(educationalContentId));
            entityManager.remove(educationalContent);
            entityManager.getTransaction().commit();
            Logger.Info("Remove EducationalContent: " + educationalContentId);
        } catch (Exception exception) {
            entityManager.getTransaction().rollback();
            exception.printStackTrace();
            Logger.Error("Couldn't remove EducationalContent: " + educationalContentId);
        }
    }

    public static List<Student> getStudentsWithStudentNumberStart(String studentNumberStart) {
        return (List<Student>) DataBase.entityManager.
                createNativeQuery(
                        String.format(
                                "SELECT * FROM edu_use WHERE user_type='student' AND studentnumber LIKE '%s%'",
                                studentNumberStart
                        ),
                        Student.class
                ).getResultList();
    }

    public static List<ChatFeed> getUserChatFeedsWithUsers(User user, ArrayList<UUID> targetUsersId) {
        return user.getChatFeeds().stream().filter(
                chatFeed -> chatFeed.getUsers().size() == 2
        ).filter(
                chatFeed -> !Collections.disjoint(targetUsersId, chatFeed.getUsers().stream().map(User::getId).toList())
        ).toList();
    }

    public static Boolean isStudentCourseSelectionTime(Student student) {
        var courseSelectionFilters = (List<CourseSelectionFilter>) DataBase.entityManager.createNativeQuery(
                "SELECT * from course_selection_filter", CourseSelectionFilter.class
        ).getResultList();

        for (var courseSelectionFilter : courseSelectionFilters) {
            if (courseSelectionFilter.doesStudentApplies(student))
                if (LocalDateTime.now().isBefore(courseSelectionFilter.getEndTime()) &&
                        LocalDateTime.now().isAfter(courseSelectionFilter.getStartTime()))
                    return true;
        }

        return false;
    }

    public static void addNewUserChatFeeds(UUID userId) {
        try {
            DataBase.entityManager.getTransaction().begin();

            var user = DataBase.entityManager.find(User.class, userId);

            var contacts = user.getContacts();

            for (var contact: contacts) {
                var chatFeed = new ChatFeed(
                        new ArrayList<>() {{
                            add(user);
                            add(contact);
                        }},
                        new ArrayList<>()
                );
                DataBase.entityManager.persist(chatFeed);
            }

            DataBase.entityManager.getTransaction().commit();

            Logger.Info("Updated user password");

        } catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
            Logger.Error("Failed to update user password");
            halt(500);
        }

    }
}
