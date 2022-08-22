package back.server;

import back.database.DataBase;
import back.models.Attachment;
import back.models.course.*;
import back.models.faculty.Faculty;
import back.models.messenger.ChatFeed;
import back.models.messenger.Message;
import back.models.request.Request;
import back.models.security.Captcha;
import back.models.users.*;
import back.security.Control;
import back.security.Util;
import back.services.CourseHandler;
import back.services.Logger;
import back.services.RawDataHandler;
import back.services.RequestHandler;
import com.google.gson.Gson;
import commons.data_class.*;
import commons.enums.RequestStatus;
import commons.enums.UserType;
import commons.gson.CustomGson;
import spark.Response;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class Server {

    public static Gson gson = new CustomGson().gson;
    public static HashMap<String, UUID> activeAuthTokenToUserId = new HashMap<>();

    public static Server server;

    public static Server getInstance() {
        if (server == null) {
            server = new Server();
        }

        return server;
    }

    public void start(int server_port) {
        port(server_port);
        mapUrls();
    }

    public void mapUrls() {
        path("/api", () -> {
            before("/*", (request, response) -> Control.validateRequest(request));

            get("/status", Server::checkStatus);

            path("/add", () -> {
                post("/student", Server::addStudent);
                post("/professor", Server::addProfessor);
                post("/message", Server::sendMessage);
                post("/attachments", Server::addAttachments);
                post("/course", Server::addCourse);
                post("/assignment", Server::addAssignment);
                post("/assignmentAnswer", Server::addAssignmentAnswer);
                post("/educationalContent", Server::addEducationalContent);
                post("/exam", Server::addExam);

                path("/request", () -> {
                    get("/serviceExemption", Server::requestServiceExemption);
                    post("/recommendation", Server::requestRecommendation);
                    post("/minor", Server::requestMinor);
                    post("/dorm", Server::requestDorm);
                    get("/withdrawal", Server::requestWithdrawal);
                    post("/objection", Server::requestObjection);
                    post("/contact", Server::requestContact);
                });
            });

            path("/remove", () -> {
                post("/user", Server::removeUser);
                post("/course", Server::removeCourse);
                post("/educationalContent", Server::removeEducationalContent);
            });

            path("/update", () -> {
                path("/course", () -> {
                    post("/finalize", Server::finalizeCourse);
                    post("/details", Server::updateCourse);
                    post("/addTAs", Server::addTAsToCourse);
                    post("/addStudents", Server::addStudentsToCourse);
                });
                path("/request", () -> {
                    post("/finalize", Server::finalizeRequest);
                });
                path("/professor", () -> {
                    post("/details", Server::updateProfessor);
                });
                path("/user", () -> {
                    post("/details", Server::updateUser);
                    post("/password", Server::updatePassword);
                });
                path("/score", () -> {
                    post("/details", Server::updateScore);
                });
                path("/faculty", () -> {
                    post("details", Server::updateFaculty);
                });
                path("/educationalContent", () -> {
                    post("details", Server::updateEducationalContent);
                });
            });

            path("/data", () -> {
                post("/user", Server::getUser);
                post("/userType", Server::getUserType);
                post("/userWithNationalId", Server::getUserWithNationalId);
                post("/userLastLogin", Server::getUserLastLogin);
                post("/userActiveCourses", Server::getUserActiveCourses);
                post("/userContacts", Server::getUserContacts);

                post("/student", Server::getStudent);
                get("/students", Server::getAllStudents);
                post("/studentNonFinalizedActiveScores", Server::getStudentNonFinalizedActiveScores);
                post("/studentsWithStudentNumberStart", Server::getStudentWithStudentNumberStart);
                post("/isStudentCourseSelectionTime", Server::isStudentCourseSelectionTime);

                post("/professor", Server::getProfessor);
                get("/professors", Server::getAllProfessors);
                post("professorAllFinalizedScore", Server::getProfessorAllFinalizedScore);

                post("/course", Server::getCourse);
                get("/courses", Server::getAllCourses);
                post("/courseScoring", Server::getCourseScoringData);
                post("/courseAssignment", Server::getCourseAssignments);
                post("/getAssignment", Server::getAssignment);
                post("/getAssignmentAnswers", Server::getAssignmentsAnswers);  // return an assignment's answers
                post("/educationalContent", Server::getEducationalContent);
                post("/exam", Server::getExam);
                post("/currentSemesterCourses", Server::getCurrentSemesterCourses);

                post("/faculty", Server::getFacultyData);
                post("/facultyByName", Server::getFacultyByName);
                get("/faculties", Server::getAllFaculties);

                post("/request", Server::getRequest);
                get("/objectionRequests", Server::getAllObjectionRequests);

                get("/weeklyPlan", Server::getWeeklyPlan);
                post("/score", Server::getScoreData);
                post("/attachment", Server::getAttachment);

                post("/userChatFeeds", Server::getUserChatFeeds);
                post("/chatFeed", Server::getChatFeed);
                post("/message", Server::getMessage);
                post("/userChatFeedsWithUsers", Server::getUserChatFeedsWithUsers);

                path("/filtered", () -> {
                    post("/students", Server::getFilteredStudents);
                    post("/professors", Server::getFilteredProfessors);
                    post("/courses", Server::getFilteredCourses);
                    get("/userExams", Server::getUserActiveExams);
                    post("/facultyMinors", Server::getFacultyMinors);
                    post("/requests", Server::getFilteredRequests);
                });
            });

            path("/auth", () -> {
                get("/getCaptcha", Server::getCaptcha);
                post("/login", Server::logIn);
                post("/logout", Server::logOut);
                post("/check", Server::checkAuth);
            });

        });
    }

    /**
     * takes examData in body
     */
    private static String addExam(spark.Request request, Response response) {
        return null;
    }

    private static String getCurrentSemesterCourses(spark.Request request, Response response) {
        var activeCourses = (List<Course>) DataBase.entityManager.createNativeQuery(
                "SELECT * FROM course WHERE archived='f'", Course.class
        ).getResultList();
        response.body(gson.toJson(activeCourses.stream().map(RawDataHandler::getCourseData).toList()));
        return response.body();
    }

    /**
     * takes [userId] in Body
     */
    private static String getUserChatFeedsWithUsers(spark.Request request, Response response) {
        var user = RawDataHandler.getUserFromRequest(request);
        response.body(
                gson.toJson(DataBase.getUserChatFeedsWithUsers(
                        user,
                        new ArrayList<>(Arrays.asList(gson.fromJson(request.body(), UUID[].class)))
                ))
        );
        return response.body();
    }

    private static String requestContact(spark.Request request, Response response) {
        var user = RawDataHandler.getUserFromRequest(request);
        RequestHandler.chatRequest(
                user,
                DataBase.entityManager.find(User.class, UUID.fromString(request.body()))
        );
        response.body("request created successfully");
        return response.body();
    }

    private static String getStudentWithStudentNumberStart(spark.Request request, Response response) {
        response.body(
                gson.toJson(DataBase.getStudentsWithStudentNumberStart(request.body()))
        );
        return response.body();
    }

    /**
     * takes userId in body
     */
    private static String getUserContacts(spark.Request request, Response response) {
        var user = DataBase.entityManager.find(User.class, UUID.fromString(request.body()));
        response.body(gson.toJson(user.getContacts().stream().map(User::getId)));
        return response.body();
    }

    private static String checkStatus(spark.Request request, Response response) {
        response.body("online");
        return response.body();
    }

    /**
     * takes EducationalContentData in body
     */
    private static String updateEducationalContent(spark.Request request, Response response) {
        RawDataHandler.updateEducationalContent(gson.fromJson(request.body(), EducationalContentData.class));
        response.body("educational data updated successfully");
        return response.body();
    }

    /**
     * takes educationalContentId in body
     */
    private static String removeEducationalContent(spark.Request request, Response response) {
        DataBase.removeEducationalContent(request.body());
        response.body("educational content removed successfully");
        return response.body();
    }

    /**
     * takes AttachmentData in body
     */
    private static String getAttachment(spark.Request request, Response response) {
        var attachment = DataBase.entityManager.find(Attachment.class, UUID.fromString(request.body()));
        response.body(gson.toJson(RawDataHandler.getAttachmentData(attachment)));
        return response.body();
    }

    /**
     * takes examId in body
     */
    private static String getExam(spark.Request request, Response response) {
        var exam = DataBase.entityManager.find(Exam.class, UUID.fromString(request.body()));
        response.body(gson.toJson(RawDataHandler.getExamData(exam)));
        return response.body();
    }

    /**
     * takes addStudentsData in body
     */
    private static String addStudentsToCourse(spark.Request request, Response response) {
        var studentsData = gson.fromJson(request.body(), AddStudentsData.class);
        CourseHandler.addStudentsToCourse(studentsData);
        response.body("students added successfully");
        return response.body();
    }

    /**
     * takes EducationalContentId in body
     */
    private static String getEducationalContent(spark.Request request, Response response) {
        var educationalContent = DataBase.entityManager.find(
                EducationalContent.class, UUID.fromString(request.body())
        );
        response.body(gson.toJson(RawDataHandler.getEducationalContentData(educationalContent)));
        return response.body();
    }

    /**
     * takes educationalContentData in body
     */
    private static String addEducationalContent(spark.Request request, Response response) {
        var educationalContentData = gson.fromJson(request.body(), EducationalContentData.class);
        RawDataHandler.createEducationalContent(educationalContentData);
        response.body("educational content created");
        return response.body();
    }

    /**
     * takes addStudentsData in body
     */
    private static String addTAsToCourse(spark.Request request, Response response) {
        var TAsData = gson.fromJson(request.body(), AddStudentsData.class);
        CourseHandler.addTAsToCourse(TAsData);
        response.body("Tas added successfully");
        return response.body();
    }

    /**
     * takes assignmentAnswerData in body
     */
    private static String addAssignmentAnswer(spark.Request request, Response response) {
        var assignmentAnswerData = gson.fromJson(request.body(), AssignmentAnswerData.class);
        RawDataHandler.createAssignmentAnswer(assignmentAnswerData);
        response.body("assignment answer submitted successfully");
        return response.body();
    }

    /**
     * takes assignmentData in body
     */
    private static String addAssignment(spark.Request request, Response response) {
        var assignmentData = gson.fromJson(request.body(), AssignmentData.class);
        RawDataHandler.createAssignment(assignmentData);
        response.body("assignment added successfully");
        return response.body();
    }

    /**
     * takes assignmentId in body
     */
    private static String getAssignmentsAnswers(spark.Request request, Response response) {
        var assignmentAnswers = (List<AssignmentAnswer>) DataBase.entityManager.createNativeQuery(
                String.format("SELECT * FROM assignmentanswer WHERE assignment_id='%s'", request.body()), AssignmentAnswer.class
        ).getResultList();
        response.body(gson.toJson(assignmentAnswers.stream().map(RawDataHandler::getAssignmentAnswerData).collect(Collectors.toList())));
        return response.body();
    }

    /**
     * takes assignmentId in body
     */
    private static String getAssignment(spark.Request request, Response response) {
        var assignment = DataBase.entityManager.find(Assignment.class, UUID.fromString(request.body()));
        response.body(gson.toJson(RawDataHandler.getAssignmentData(assignment)));
        return response.body();
    }

    /**
     * takes courseId in body
     */
    private static String getCourseAssignments(spark.Request request, Response response) {
        var assignments = (List<Assignment>) DataBase.entityManager.createNativeQuery(
                String.format("SELECT * FROM assignment WHERE course_id='%s'", request.body()), Assignment.class
        ).getResultList();
        response.body(gson.toJson(assignments.stream().map(RawDataHandler::getAssignmentData).collect(Collectors.toList())));
        return response.body();
    }

    /**
     * takes [attachmentData] in body
     * returns [attachmentsIds] in body
     */
    private static String addAttachments(spark.Request request, Response response) {
        var attachmentsData = gson.fromJson(request.body(), AttachmentData[].class);
        var attachmentIds = new ArrayList<UUID>();
        for (AttachmentData attachmentData : attachmentsData) {
            attachmentIds.add(RawDataHandler.createAttachment(attachmentData).getId());
        }
        response.body(gson.toJson(attachmentIds));
        return response.body();
    }

    /**
     * takes userId in body
     */
    private static String getUserChatFeeds(spark.Request request, Response response) {
        var user = DataBase.entityManager.find(User.class, UUID.fromString(request.body()));
        response.body(gson.toJson(user.getChatFeeds().stream().map(RawDataHandler::getChatFeedData).toList()));
        return response.body();
    }

    /**
     * takes messageId in body
     */
    private static String getMessage(spark.Request request, Response response) {
        var message = DataBase.entityManager.find(Message.class, UUID.fromString(request.body()));
        response.body(gson.toJson(RawDataHandler.getMessageData(message)));
        return response.body();
    }

    /**
     * takes chatFeedId in body
     */
    private static String getChatFeed(spark.Request request, Response response) {
        var chatFeed = DataBase.entityManager.find(ChatFeed.class, UUID.fromString(request.body()));
        response.body(gson.toJson(RawDataHandler.getChatFeedData(chatFeed)));
        return response.body();
    }

    /**
     * takes messageData in body
     */
    private static String sendMessage(spark.Request request, Response response) {
        var messageData = gson.fromJson(request.body(), MessageData.class);
        RawDataHandler.createMessage(messageData);
        response.body("message sent successfully");
        return response.body();
    }

    public static String logIn(spark.Request request, Response response) {
        var logInData = gson.fromJson(request.body(), LogInData.class);

        String authToken = Control.generateAuthToken();
        var user = ((List<User>) DataBase.entityManager.createNativeQuery(
                String.format("SELECT * FROM edu_user WHERE nationalid='%s'", logInData.nationalId), User.class
        ).getResultList()).get(0);


        if (user.getPassword().equals(logInData.hashedPassword)) {
            Logger.Info("successful LogIn attempt for user:" + user.getId());

            DataBase.entityManager.getTransaction().begin();
            user.setSecondLatLogin(user.getLastLogin());
            user.setLastLogin(LocalDateTime.now());
            DataBase.entityManager.getTransaction().commit();

            activeAuthTokenToUserId.put(authToken, user.getId());

            response.header("auth-token", authToken);
            response.header("user-id", user.getId().toString());

            response.status(200);

            Logger.Info("user successful login. user id: " + user.getId() + "response status: " + response.status());
            return response.body();
        } else {
            Logger.Info("failed LogIn attempt for user: " + user.getId());
            halt(401);
        }

        halt(400);
        return null;
    }

    public static Response logOut(spark.Request request, Response response) {
        var authToken = request.headers("auth-token");
        activeAuthTokenToUserId.remove(authToken);
        return null;
    }

    public static String getUser(spark.Request request, Response response) {
        User user = RawDataHandler.getUserFromRequest(request);
        UserData userData = RawDataHandler.getUserData(user);

        response.body(gson.toJson(userData));
        return response.body();
    }

    public static String getAllProfessors(spark.Request request, Response response) {
        List<Professor> professors = (List<Professor>) DataBase.entityManager.
                createNativeQuery("SELECT * FROM edu_user WHERE user_type='professor'", Professor.class).getResultList();

        List<ProfessorData> professorsData = professors.stream().map(RawDataHandler::getProfessorData).
                collect(Collectors.toList());

        response.body(gson.toJson(professorsData));
        return response.body();
    }

    public static String getFilteredProfessors(spark.Request request, Response response) {
        ProfessorFilter professorFilter = gson.fromJson(request.body(), ProfessorFilter.class);
        List<Professor> filteredProfessors = DataBase.getFilteredProfessors(professorFilter);
        List<ProfessorData> filteredProfessorsData = filteredProfessors.stream().map(RawDataHandler::getProfessorData).
                collect(Collectors.toList());

        response.body(gson.toJson(filteredProfessorsData));
        return response.body();
    }

    public static String getAllCourses(spark.Request request, Response response) {
        List<Course> courses = (List<Course>) DataBase.entityManager.
                createNativeQuery("SELECT * FROM course", Course.class).getResultList();

        List<CourseData> coursesData = courses.stream().map(RawDataHandler::getCourseData).collect(Collectors.toList());

        response.body(gson.toJson(coursesData));
        return response.body();
    }

    public static String getFilteredCourses(spark.Request request, Response response) {
        CourseFilter courseFilter = gson.fromJson(request.body(), CourseFilter.class);
        List<Course> filteredCourses = DataBase.getFilteredCourses(courseFilter);
        List<CourseData> filteredCoursesData = filteredCourses.stream().map(RawDataHandler::getCourseData).
                collect(Collectors.toList());

        response.body(gson.toJson(filteredCoursesData));
        return response.body();
    }

    public static String getUserActiveExams(spark.Request request, Response response) {
        User user = RawDataHandler.getUserFromRequest(request);
        List<Exam> userExams = user.getActiveExams();
        List<ExamData> userExamsData = userExams.stream().map(RawDataHandler::getExamData).collect(Collectors.toList());

        response.body(gson.toJson(userExamsData));
        return response.body();
    }

    public static String getFilteredStudents(spark.Request request, Response response) {
        StudentFilter studentFilter = gson.fromJson(request.body(), StudentFilter.class);
        List<Student> filteredStudents = DataBase.getFilteredStudents(studentFilter);
        List<StudentData> filteredStudentsData = filteredStudents.stream().map(RawDataHandler::getStudentData).
                collect(Collectors.toList());

        response.body(gson.toJson(filteredStudentsData));
        return response.body();
    }

    /**
     * takes studentNumber
     */
    public static String getStudent(spark.Request request, Response response) {
        Student student = (Student) DataBase.entityManager.createNativeQuery(
                String.format("SELECT * FROM edu_user WHERE nationalid='%s'", request.body()
                ), Student.class).getResultList().get(0);

        response.body(gson.toJson(RawDataHandler.getStudentData(student)));
        return response.body();
    }

    public static String getAllStudents(spark.Request request, Response response) {
        List<Student> students = (List<Student>) DataBase.entityManager.
                createNativeQuery("SELECT * FROM edu_use WHERE user_type='student'", Student.class).getResultList();
        List<StudentData> studentsData = students.stream().map(RawDataHandler::getStudentData).
                collect(Collectors.toList());

        response.body(gson.toJson(studentsData));
        return response.body();
    }

    public static String getAllFaculties(spark.Request request, Response response) {
        List<Faculty> faculties = (List<Faculty>) DataBase.entityManager.
                createNativeQuery("SELECT * FROM faculty", Faculty.class).getResultList();
        List<FacultyData> facultiesData = faculties.stream().map(RawDataHandler::getFacultyData).collect(Collectors.toList());

        response.body(gson.toJson(facultiesData));
        return response.body();
    }

    public static String getFilteredRequests(spark.Request request, Response response) {
        RequestFilter requestFilter = gson.fromJson(request.body(), RequestFilter.class);
        List<Request> requests = DataBase.getFilteredRequests(RawDataHandler.getUserFromRequest(request), requestFilter);
        List<RequestData> requestsData = requests.stream().map(RawDataHandler::getRequestData).collect(Collectors.toList());

        response.body(gson.toJson(requestsData));
        return response.body();
    }

    /**
     * takes requestId in body
     */
    public static String getRequest(spark.Request webRequest, Response response) {
        Request request = DataBase.entityManager.find(Request.class, UUID.fromString(webRequest.body()));
        RequestData requestData = RawDataHandler.getRequestData(request);

        response.body(gson.toJson(requestData));
        return response.body();
    }

    /**
     * takes facultyName in body
     */
    public static String getFacultyMinors(spark.Request request, Response response) {
        Faculty faculty = DataBase.getFacultyByName(request.body());
        response.body(faculty.getMinors().toString());
        return response.body();
    }

    /**
     * @return captcha number and url to it's picture in body
     */
    public static String getCaptcha(spark.Request request, Response response) {
        Captcha captcha = Util.getACaptcha();
        CaptchaData captchaData = RawDataHandler.getCaptchaData(captcha);
        response.body(gson.toJson(captchaData));
        return response.body();
    }

    /**
     * takes facultyId in body
     */
    private static String getFacultyData(spark.Request request, Response response) {
        Faculty faculty = DataBase.entityManager.find(Faculty.class, UUID.fromString(request.body()));
        FacultyData facultyData = RawDataHandler.getFacultyData(faculty);

        response.body(gson.toJson(facultyData));
        return response.body();
    }

    /**
     * takes facultyName in body
     */
    public static String getFacultyByName(spark.Request request, Response response) {
        Faculty faculty = DataBase.getFacultyByName(request.body());
        FacultyData facultyData = RawDataHandler.getFacultyData(faculty);

        response.body(gson.toJson(facultyData));
        return response.body();
    }

    /**
     * takes professorData in body
     */
    public static String addProfessor(spark.Request request, Response response) {
        ProfessorData professorData = gson.fromJson(request.body(), ProfessorData.class);
        RawDataHandler.createProfessor(professorData);
        response.body("Professor registered successfully");
        return response.body();
    }

    /**
     * takes professorId in body
     */
    public static String getProfessor(spark.Request request, Response response) {
        Professor professor = DataBase.entityManager.find(Professor.class, UUID.fromString(request.body()));
        ProfessorData professorData = RawDataHandler.getProfessorData(professor);

        response.body(gson.toJson(professorData));
        return response.body();
    }

    public static String addCourse(spark.Request request, Response response) {
        CourseData courseData = gson.fromJson(request.body(), CourseData.class);
        RawDataHandler.createCourse(courseData);
        response.body("course registered successfully");
        return response.body();
    }

    /**
     * takes courseId in body
     */
    public static String getCourse(spark.Request request, Response response) {
        Course course = DataBase.entityManager.find(Course.class, UUID.fromString(request.body()));
        CourseData courseData = RawDataHandler.getCourseData(course);

        response.body(gson.toJson(courseData));
        return response.body();
    }

    public static String getWeeklyPlan(spark.Request request, Response response) {
        User user = RawDataHandler.getUserFromRequest(request);
        response.body(gson.toJson(user.getWeeklyPlan()));
        return response.body();
    }

    /**
     * takes studentData in body
     */
    public static String addStudent(spark.Request request, Response response) {
        StudentData studentData = gson.fromJson(request.body(), StudentData.class);
        RawDataHandler.createStudent(studentData);
        response.body("student registered successfully");
        return response.body();
    }

    /**
     * takes user id in body
     */
    public static String removeUser(spark.Request request, Response response) {
        DataBase.removeUser(request.body());
        response.body("User deleted successfully");
        return response.body();
    }

    /**
     * takes courseId in body
     */
    public static String removeCourse(spark.Request request, Response response) {
        DataBase.removeCourse(request.body());
        response.body("Course deleted successfully");
        return response.body();
    }

    /**
     * returns service exemption request's result text in body
     */
    public static String requestServiceExemption(spark.Request request, Response response) {
        Student student = (Student) RawDataHandler.getUserFromRequest(request);
        response.body(RequestHandler.requestServiceExemption(student));
        return response.body();
    }

    /**
     * takes professorId in body
     */
    public static String requestRecommendation(spark.Request request, Response response) {
        Student student = (Student) RawDataHandler.getUserFromRequest(request);
        RequestHandler.recommendationRequest(student,
                DataBase.entityManager.find(Professor.class, UUID.fromString(request.body())));

        response.body("request created successfully");
        return response.body();
    }

    /**
     * takes courseId in body
     */
    public static String finalizeCourse(spark.Request request, Response response) {
        CourseHandler.finalizeCourse(DataBase.entityManager.find(Course.class, UUID.fromString(request.body())));
        response.body("Course finalized successfully");
        return response.body();
    }

    public static String requestMinor(spark.Request request, Response response) {
        Student student = (Student) RawDataHandler.getUserFromRequest(request);
        MinorData minorData = gson.fromJson(request.body(), MinorData.class);

        RequestHandler.minorRequest(student,
                DataBase.getFacultyByName(minorData.facultyName),
                minorData.name);

        response.body("request created successfully");
        return response.body();
    }

    public static String requestWithdrawal(spark.Request request, Response response) {
        Student student = (Student) RawDataHandler.getUserFromRequest(request);
        RequestHandler.withdrawalRequest(student);

        response.body("request created successfully");
        return response.body();
    }

    public static String requestDorm(spark.Request request, Response response) {
        Student student = (Student) RawDataHandler.getUserFromRequest(request);
        RequestHandler.dormRequest((student));

        response.body("request created successfully");
        return response.body();
    }

    public static String finalizeRequest(spark.Request request, Response response) {
        User user = RawDataHandler.getUserFromRequest(request);
        RequestData requestData = gson.fromJson(request.body(), RequestData.class);

        if (!requestData.assigneeIds.contains(user.getId())) {
            halt(403);
        }

        if (requestData.status == RequestStatus.approved) {
            RequestHandler.approveRequest(
                    user,
                    DataBase.entityManager.find(Request.class, requestData.id),
                    requestData.response);
            response.body("Request with id:" + requestData.id + " approved");
        } else {
            RequestHandler.disApproveRequest(
                    DataBase.entityManager.find(Request.class, requestData.id),
                    requestData.response);
            response.body("Request with id:" + requestData.id + " disapproved");
        }

        return response.body();
    }

    public static String getAllObjectionRequests(spark.Request request, Response response) {
        List<Request> objectionRequests = DataBase.getAllObjectionRequests();
        List<RequestData> objectionRequestsData = objectionRequests.stream().map(RawDataHandler::getRequestData).
                collect(Collectors.toList());

        response.body(gson.toJson(objectionRequestsData));
        return response.body();
    }

    /**
     * takes ObjectionRequestData in body
     */
    private static String requestObjection(spark.Request request, Response response) {
        var objectionRequestData = gson.fromJson(request.body(), ObjectionRequestData.class);
        RequestHandler.objectionRequest(
                DataBase.entityManager.find(Score.class, objectionRequestData.scoreId),
                objectionRequestData.text
        );

        response.body("request created successfully");
        return response.body();
    }

    /**
     * takes userId in body
     */
    private static String getUserActiveCourses(spark.Request request, Response response) {
        var user = DataBase.entityManager.find(User.class, UUID.fromString(request.body()));
        response.body(gson.toJson(user.getActiveCourses().stream().map(RawDataHandler::getCourseData).toList()));
        return response.body();
    }

    /**
     * takes professorId in body
     */
    private static String getProfessorAllFinalizedScore(spark.Request request, Response response) {
        var professor = DataBase.entityManager.find(Professor.class, UUID.fromString(request.body()));
        ArrayList<Score> resultScores = new ArrayList<>();

        for (Course course : professor.getActiveCourses()) {
            resultScores.addAll(course.getScores().stream().filter(Score::isFinalized).toList());
        }

        response.body(gson.toJson(resultScores));
        return response.body();
    }

    /**
     * takes studentId in body
     */
    private static String getStudentNonFinalizedActiveScores(spark.Request request, Response response) {
        var student = DataBase.entityManager.find(Student.class, UUID.fromString(request.body()));
        List<Score> results = student.getActiveScores().stream().filter(score -> !score.isFinalized()).toList();
        response.body(gson.toJson(results));
        return response.body();
    }

    /**
     * takes userId in body
     */
    private static String getUserLastLogin(spark.Request request, Response response) {
        var user = DataBase.entityManager.find(User.class, UUID.fromString(request.body()));
        response.body(gson.toJson(user.getSecondLatLogin()));
        return response.body();
    }

    /**
     * takes scoreId in body
     */
    private static String getCourseScoringData(spark.Request request, Response response) {
        var course = DataBase.entityManager.find(Course.class, UUID.fromString(request.body()));
        response.body(gson.toJson(RawDataHandler.getCourseScoringData(course)));
        return response.body();
    }

    /**
     * takes nationalId in body
     */
    private static String getUserWithNationalId(spark.Request request, Response response) {
        var user = ((List<User>) DataBase.entityManager.createNativeQuery(
                String.format("SELECT * FROM edu_user WHERE nationalid='%s'", request.body()), User.class
        ).getResultList()).get(0);
        response.body(gson.toJson(RawDataHandler.getUserData(user)));
        return response.body();
    }

    private static String getUserType(spark.Request request, Response response) {
        var user = DataBase.entityManager.find(User.class, UUID.fromString(request.body()));
        if (user instanceof Student) {
            switch (((Student) user).getType()) {
                case bachelor -> response.body(gson.toJson(UserType.bachelorStudent));
                case master -> response.body(gson.toJson(UserType.masterStudent));
                case doctorate -> response.body(gson.toJson(UserType.doctorateStudent));
            }
        } else if (user instanceof Professor) {
            switch (((Professor) user).getType()) {
                case normal -> response.body(gson.toJson(UserType.normalProfessor));
                case deputyEducation -> response.body(gson.toJson(UserType.deputyEducationProfessor));
                case facultyHead -> response.body(gson.toJson(UserType.facultyHeadProfessor));
            }
        } else if (user instanceof Mohseni) {
            response.body(gson.toJson(UserType.mohseni));
        } else if (user instanceof Admin) {
            response.body(gson.toJson(UserType.admin));
        }
        return response.body();
    }

    /**
     * takes authCheckData in body
     */
    private static String checkAuth(spark.Request request, Response response) {
        var authCheckData = gson.fromJson(request.body(), AuthCheckData.class);
        var user = DataBase.entityManager.find(User.class, authCheckData.userId);
        if (!user.getPassword().equals(authCheckData.userHashedPassword))
            halt(400);
        response.body("auth ok");
        return response.body();
    }

    /**
     * takes scoreId in body
     */
    private static String getScoreData(spark.Request request, Response response) {
        var score = DataBase.entityManager.find(Score.class, UUID.fromString(request.body()));
        response.body(gson.toJson(RawDataHandler.getScoreData(score)));
        return response.body();
    }

    private static String updateFaculty(spark.Request request, Response response) {
        RawDataHandler.updateFaculty(gson.fromJson(request.body(), FacultyData.class));
        response.body("faculty updated successfully");
        return response.body();
    }

    private static String updateScore(spark.Request request, Response response) {
        RawDataHandler.updateScore(gson.fromJson(request.body(), ScoreData.class));
        response.body("score updated successfully");
        return response.body();
    }

    private static String updatePassword(spark.Request request, Response response) {
        RawDataHandler.updatePassword(gson.fromJson(request.body(), PasswordChangeData.class));
        response.body("password updated successfully");
        return response.body();
    }

    private static String updateUser(spark.Request request, Response response) {
        RawDataHandler.updateUser(gson.fromJson(request.body(), UserData.class));
        response.body("user updated successfully");
        return response.body();
    }

    private static String updateProfessor(spark.Request request, Response response) {
        RawDataHandler.updateProfessor(gson.fromJson(request.body(), ProfessorData.class));
        response.body("professor updated successfully");
        return response.body();
    }

    private static String updateCourse(spark.Request request, Response response) {
        RawDataHandler.updateCourse(gson.fromJson(request.body(), CourseData.class));
        response.body("course updated successfully");
        return response.body();
    }

    private static String isStudentCourseSelectionTime(spark.Request request, Response response) {
        var student = (Student) RawDataHandler.getUserFromRequest(request);
        response.body(
                gson.toJson(DataBase.isStudentCourseSelectionTime(student))
        );
        return response.body();
    }
}
