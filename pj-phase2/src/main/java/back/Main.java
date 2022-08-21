package back;

import back.database.DataBase;
import back.models.course.*;
import back.models.faculty.Faculty;
import back.models.messenger.ChatFeed;
import back.models.users.Professor;
import back.models.users.Student;
import back.models.users.User;
import back.server.Server;
import commons.enums.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Main {

    @PersistenceContext
    static EntityManager entityManager;

    public static void main(String[] args) {
//        Config.loadConfig();
        DataBase.run();
        var server = Server.getInstance();
        server.start(5555);
    }

    public static void loadMockData() {

        try{
            DataBase.entityManager.getTransaction().begin();

            Faculty mathFaculty = new Faculty("math");
            mathFaculty.setMajors(new ArrayList<>(){
                {
                    add("math");
                    add("cs");
                }
            });

            Professor facultyHead = new Professor("professor1",
                    "12345",
                    "test@test.com",
                    ProfessorType.facultyHead);
            facultyHead.setFirstName("f1");
            facultyHead.setLastName("l1");
            facultyHead.setRank(ProfessorRank.fullProfessor);
            facultyHead.setRoomNumber(101);
            facultyHead.setPhoneNumber("09999999999");
            facultyHead.setFaculty(mathFaculty);
            mathFaculty.setFacultyHeadProfessor(facultyHead);

            Professor deputyEducation = new Professor("professor2",
                    "12345",
                    "test@test.com",
                    ProfessorType.deputyEducation);
            facultyHead.setFirstName("f2");
            facultyHead.setLastName("l2");
            facultyHead.setRank(ProfessorRank.fullProfessor);
            facultyHead.setRoomNumber(102);
            facultyHead.setPhoneNumber("09999999999");
            facultyHead.setFaculty(mathFaculty);
            mathFaculty.setDeputyEducationProfessor(deputyEducation);

            DataBase.entityManager.persist(mathFaculty);
            DataBase.entityManager.persist(facultyHead);
            DataBase.entityManager.persist(deputyEducation);

//            DataBase.em.merge(mathFaculty);
//            DataBase.em.merge(facultyHead);
//            DataBase.em.merge(deputyEducation);

            DataBase.entityManager.getTransaction().commit();
        }catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public static void loadMockStudents() {
        try{
            DataBase.entityManager.getTransaction().begin();

            Faculty mathFaculty = DataBase.entityManager.find(Faculty.class, UUID.fromString("a6c3756c-4e20-4f0a-885e-0e0503f73da6"));

            Student student = new Student(
                    "student1",
                    "12345",
                    "taet@test.com",
                    StudentType.bachelor
            );
            student.setFaculty(mathFaculty);
            student.setFirstName("f3");
            student.setLastName("l3");
            student.setStudentNumber("10000");
            student.setMajor("cs");
            student.setStaringYear(1400);
            student.setEducationalStatus(StudentEducationalStatus.underGraduate);
            student.setSupervisorProfessor(DataBase.entityManager.find(Professor.class, UUID.fromString("b7e5a843-20d2-40a8-afe0-3fac497c473d")));

            DataBase.entityManager.persist(student);
            DataBase.entityManager.getTransaction().commit();
        }catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public static void loadMuckCourse(){
        try{
            DataBase.entityManager.getTransaction().begin();

            Faculty mathFaculty = DataBase.entityManager.find(Faculty.class, UUID.fromString("a6c3756c-4e20-4f0a-885e-0e0503f73da6"));
            Professor professor = DataBase.entityManager.find(Professor.class, UUID.fromString("b7e5a843-20d2-40a8-afe0-3fac497c473d"));
            Student student = DataBase.entityManager.find(Student.class, UUID.fromString("3cdc7881-eabc-4cca-8ff4-dc645f5a4700"));

            Course course = new Course(
                    "ap",
                    mathFaculty,
                    professor,
                    4,
                    CourseLevel.bachelor
            );
            course.setTime("9:00 - 11:00");

            DataBase.entityManager.persist(course);

            Score score = new Score(
                    course,
                    student
            );

            DataBase.entityManager.persist(score);
            DataBase.entityManager.getTransaction().commit();
        }catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public static void loadMuckEduContentAndAssignmentAndExam(){
        try{
            DataBase.entityManager.getTransaction().begin();

            Course course = DataBase.entityManager.find(Course.class, UUID.fromString("81784b33-c834-4523-9d5a-64d825c367ad"));

            Assignment assignment = new Assignment(
                    course,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(21),
                    LocalDateTime.now().plusMonths(1),
                    "first assignment",
                    "description",
                    null,
                    AssignmentAcceptableTypes.text
            );

            DataBase.entityManager.persist(assignment);

            Exam exam = new Exam(
                    "first exam",
                    LocalDateTime.now().plusWeeks(2),
                    course
            );

            DataBase.entityManager.persist(exam);

            EducationalContent educationalContent = new EducationalContent(
                    "first educational content",
                    new ArrayList<>() {{add("text 1"); add("text 2"); add("text 3");}},
                    new ArrayList<>(),
                    course
            );

            DataBase.entityManager.persist(educationalContent);

            DataBase.entityManager.getTransaction().commit();
        }catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public static void loadMuckChat() {
        try{
            DataBase.entityManager.getTransaction().begin();

            var user1 = DataBase.entityManager.find(User.class, UUID.fromString("b7e5a843-20d2-40a8-afe0-3fac497c473d"));
            var user2 = DataBase.entityManager.find(User.class, UUID.fromString("3cdc7881-eabc-4cca-8ff4-dc645f5a4700"));

            ChatFeed chatFeed = new ChatFeed(
                    new ArrayList<>() {{
                        add(user1);
                        add(user2);
                    }}
            );

            user1.getChatFeeds().add(chatFeed);
            user2.getChatFeeds().add(chatFeed);

            DataBase.entityManager.persist(chatFeed);
            DataBase.entityManager.getTransaction().commit();
        }catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public static void test(){
        DataBase.entityManager.getTransaction().begin();
        Course course = DataBase.entityManager.find(Course.class, UUID.fromString("81784b33-c834-4523-9d5a-64d825c367ad"));
        course.setWeekDays(new ArrayList<>() {{
            add("saturday");
            add("sunday");
        }});
        DataBase.entityManager.getTransaction().commit();
    }
}
