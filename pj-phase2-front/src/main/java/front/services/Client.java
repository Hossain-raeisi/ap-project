package front.services;

import com.google.gson.Gson;
import front.Config;
import front.commons.data_class.*;
import front.commons.enums.APIs;
import front.commons.enums.UserType;
import front.commons.gson.CustomGson;
import front.services.security.Control;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {
    private static final String baseAddress = Config.SERVER_ADDRESS;
    private static final HashMap<APIs, String> urls = new HashMap<>();
    static volatile boolean ONLINE;
    private static Client client;
    private static Gson gson;
    HttpClient httpClient;

    private Client() {
        httpClient = HttpClient.newHttpClient();
        mapUrls();
        gson = new CustomGson().gson;
    }

    public static Client getInstance() {
        if (client == null) {
            client = new Client();
            client.checkOnlineStatus();
        }

        return client;
    }

    public void checkOnlineStatus() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            try {
                checkServerStatus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void mapUrls() {
        urls.put(APIs.serverStatus, baseAddress + "/status");

        urls.put(APIs.add_student, baseAddress + "/add/student");
        urls.put(APIs.add_professor, baseAddress + "/add/professor");
        urls.put(APIs.add_message, baseAddress + "/add/message");
        urls.put(APIs.add_attachments, baseAddress + "/add/attachments");

        urls.put(APIs.add_course, baseAddress + "/add/course");
        urls.put(APIs.add_assignment, baseAddress + "/add/assignment");
        urls.put(APIs.add_assignmentAnswer, baseAddress + "/add/assignmentAnswer");
        urls.put(APIs.add_educationalContent, baseAddress + "/add/educationalContent");
        urls.put(APIs.add_exam, baseAddress + "/add/exam");

        urls.put(APIs.add_request_serviceExemption, baseAddress + "/add/request/serviceExemption");
        urls.put(APIs.add_request_recommendation, baseAddress + "/add/request/recommendation");
        urls.put(APIs.add_request_minor, baseAddress + "/add/request/minor");
        urls.put(APIs.add_request_dorm, baseAddress + "/add/request/dorm");
        urls.put(APIs.add_request_withdrawal, baseAddress + "/add/request/withdrawal");
        urls.put(APIs.add_request_objection, baseAddress + "/add/request/objection");
        urls.put(APIs.add_request_contact, baseAddress + "/add/request/contact");

        urls.put(APIs.remove_user, baseAddress + "/remove/user");
        urls.put(APIs.remove_course, baseAddress + "/remove/course");
        urls.put(APIs.remove_educationalContent, baseAddress + "/remove/educationalContent");

        urls.put(APIs.update_course_finalize, baseAddress + "/update/course/finalize");
        urls.put(APIs.update_course_details, baseAddress + "/update/course/details");
        urls.put(APIs.update_course_addTAs, baseAddress + "/update/course/addTAs");
        urls.put(APIs.update_course_addStudents, baseAddress + "/update/course/addStudents");
        urls.put(APIs.update_educationalContent_details, baseAddress + "/update/educationalContent/details");
        urls.put(APIs.update_course_assignmentAnswer, baseAddress + "/update/course/assignmentAnswer");


        urls.put(APIs.update_request_finalize, baseAddress + "/update/request/finalize");
        urls.put(APIs.update_professor_details, baseAddress + "/update/professor/details");
        urls.put(APIs.update_faculty_details, baseAddress + "/update/faculty/details");
        urls.put(APIs.update_user_details, baseAddress + "/update/user/details");
        urls.put(APIs.update_user_password, baseAddress + "/update/user/password");
        urls.put(APIs.update_score_details, baseAddress + "/update/score/details");

        urls.put(APIs.data_user, baseAddress + "/data/user");
        urls.put(APIs.data_userType, baseAddress + "/data/userType");
        urls.put(APIs.data_userWithNationalId, baseAddress + "/data/userWithNationalId");
        urls.put(APIs.data_userLastLogin, baseAddress + "/data/userLastLogin");
        urls.put(APIs.data_userActiveCourses, baseAddress + "/data/userActiveCourses");
        urls.put(APIs.data_userContacts, baseAddress + "/data/userContacts");

        urls.put(APIs.data_attachment, baseAddress + "/data/attachment");

        urls.put(APIs.data_student, baseAddress + "/data/student");
        urls.put(APIs.data_students, baseAddress + "/data/students");
        urls.put(APIs.data_isStudentCourseSelectionTime, baseAddress + "/data/isStudentCourseSelectionTime");

        urls.put(APIs.data_professor, baseAddress + "/data/professor");
        urls.put(APIs.data_professors, baseAddress + "/data/professors");
        urls.put(APIs.data_professorAllFinalizedScore, baseAddress + "/data/professorAllFinalizedScore");

        urls.put(APIs.data_course, baseAddress + "/data/course");
        urls.put(APIs.data_courses, baseAddress + "/data/courses");
        urls.put(APIs.data_courseScoring, baseAddress + "/data/courseScoring");
        urls.put(APIs.data_courseAssignment, baseAddress + "/data/courseAssignment");
        urls.put(APIs.data_assignment, baseAddress + "/data/getAssignment");
        urls.put(APIs.data_assignmentAnswer, baseAddress + "/data/getAssignmentAnswer");
        urls.put(APIs.data_assignmentAnswers, baseAddress + "/data/getAssignmentAnswers");
        urls.put(APIs.data_educationalContent, baseAddress + "/data/educationalContent");
        urls.put(APIs.data_exam, baseAddress + "/data/exam");

        urls.put(APIs.data_faculty, baseAddress + "/data/faculty");
        urls.put(APIs.data_facultyByName, baseAddress + "/data/facultyByName");
        urls.put(APIs.data_faculties, baseAddress + "/data/faculties");

        urls.put(APIs.data_request, baseAddress + "/data/request");
        urls.put(APIs.data_objectionRequests, baseAddress + "/data/objectionRequests");
        urls.put(APIs.data_weekly_plan, baseAddress + "/data/weeklyPlan");
        urls.put(APIs.data_score, baseAddress + "/data/score");

        urls.put(APIs.data_userChatFeeds, baseAddress + "/data/userChatFeeds");
        urls.put(APIs.data_chatFeed, baseAddress + "/data/chatFeed");
        urls.put(APIs.data_message, baseAddress + "/data/message");
        urls.put(APIs.data_userChatFeedsWithUsers, baseAddress + "/data/userChatFeedsWithUsers");

        urls.put(APIs.data_filtered_students, baseAddress + "/data/filtered/students");
        urls.put(APIs.data_filtered_professors, baseAddress + "/data/filtered/professors");
        urls.put(APIs.data_filtered_courses, baseAddress + "/data/filtered/courses");
        urls.put(APIs.data_filtered_userExams, baseAddress + "/data/filtered/userExams");
        urls.put(APIs.data_filtered_scores, baseAddress + "/data/filtered/scores");
        urls.put(APIs.data_filtered_facultyMinors, baseAddress + "/data/filtered/facultyMinors");
        urls.put(APIs.data_filtered_requests, baseAddress + "/data/filtered/requests");

        urls.put(APIs.auth_getCaptcha, baseAddress + "/auth/getCaptcha");
        urls.put(APIs.auth_login, baseAddress + "/auth/login");
        urls.put(APIs.auth_logout, baseAddress + "/auth/logout");
        urls.put(APIs.auth_check, baseAddress + "/auth/check");
    }

    public HttpResponse<String> sendRequest(APIs api, HttpRequest.Builder requestBuilder) {
        try {
            Logger.Info("calling server on: " + api.toString());
            return httpClient.send(requestBuilder.uri(URI.create(urls.get(api))).build(),
                    HttpResponse.BodyHandlers.ofString());
        } catch (ConnectException connectException) {
            ONLINE = false;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.Error(e.getMessage());
        }
        return null;
    }

    public HttpResponse<String> sendSecureRequest(APIs api, HttpRequest.Builder requestBuilder) {
        try {
            Logger.Info("calling server on: " + api.toString());
            return httpClient.send(Control.addSecurityHeaders(
                            requestBuilder.uri(URI.create(urls.get(api)))
                    ).build(),
                    HttpResponse.BodyHandlers.ofString());
        } catch (ConnectException connectException) {
            ONLINE = false;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.Error(e.getMessage());
        }
        return null;
    }

    public boolean logIn(String idNumber, String password) {

        try {
            String hashedPassword = front.services.security.Utils.hashPassword(password);
            var logInData = new LogInData(idNumber, hashedPassword);
            var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.toJson(logInData)));
            var response = sendRequest(APIs.auth_login, request);

            Control.authToken = String.valueOf(response.headers().firstValue("auth-token").stream().toArray()[0]);
            Control.userId = UUID.fromString(String.valueOf(response.headers().firstValue("user-id").stream().toArray()[0]));

            return Control.authToken != null && !Control.authToken.equals("");
        } catch (Exception ignored) {
        }

        return false;
    }

    public void logOut() {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(""));
        sendSecureRequest(APIs.auth_logout, request);
    }

    public ArrayList<ProfessorData> getAllProfessorsData() {
        var request = HttpRequest.newBuilder().GET();
        var response = sendSecureRequest(APIs.data_professors, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), ProfessorData[].class)));
    }

    public ArrayList<ProfessorData> getFilteredProfessorsData(ProfessorFilter professorFilter) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(professorFilter))
        );
        var response = sendSecureRequest(APIs.data_filtered_professors, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), ProfessorData[].class)));
    }

    public ArrayList<CourseData> getAllCoursesData() {
        var request = HttpRequest.newBuilder().GET();
        var response = sendSecureRequest(APIs.data_courses, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), CourseData[].class)));
    }

    public ArrayList<CourseData> getFilteredCoursesData(CourseFilter courseFilter) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(courseFilter))
        );
        var response = sendSecureRequest(APIs.data_filtered_courses, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), CourseData[].class)));
    }

    public ArrayList<ExamData> getUserActiveExams() {
        var request = HttpRequest.newBuilder().GET();

        HttpResponse<String> response;
        if (ONLINE) {
            response = sendSecureRequest(APIs.data_filtered_userExams, request);
            Cache.addSingletonResponseToCache(APIs.data_filtered_userExams, response);
        } else {
            response = Cache.getCachedSingletonResponse(APIs.data_filtered_userExams);
        }

        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), ExamData[].class)));
    }

    public String requestServiceExemption() {
        var request = HttpRequest.newBuilder().GET();
        var response = sendSecureRequest(APIs.add_request_serviceExemption, request);
        return response.body();
    }

    public ArrayList<FacultyData> getAllFacultiesData() {
        var request = HttpRequest.newBuilder().GET();
        var response = sendSecureRequest(APIs.data_faculties, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), FacultyData[].class)));
    }

    public void requestRecommendation(UUID professorId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(professorId.toString()));
        var response = sendSecureRequest(APIs.add_request_recommendation, request);
        response.body();
    }

    public ArrayList<String> getFacultyMinors(String facultyName) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(facultyName));
        var response = sendSecureRequest(APIs.data_filtered_facultyMinors, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), String[].class)));
    }

    public void requestMinor(MinorData minorData) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.toJson(minorData)));
        var response = sendSecureRequest(APIs.add_request_minor, request);
        response.body();
    }

    public void requestContact(UUID userId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.toJson(userId)));
        var response = sendSecureRequest(APIs.add_request_contact, request);
        response.body();
    }

    public ArrayList<RequestData> getFilteredRequests(RequestFilter requestFilter) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(requestFilter))
        );
        var response = sendSecureRequest(APIs.data_filtered_requests, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), RequestData[].class)));
    }

    public RequestData getRequest(String requestId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(requestId));
        var response = sendSecureRequest(APIs.data_request, request);
        return gson.fromJson(response.body(), RequestData.class);
    }

    public void finalizeRequest(RequestData requestData) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(requestData))
        );
        var response = sendSecureRequest(APIs.update_request_finalize, request);
        response.body();
    }

    public void requestWithdrawal() {
        var request = HttpRequest.newBuilder().GET();
        var response = sendSecureRequest(APIs.add_request_withdrawal, request);
        response.body();
    }

    public void requestDorm() {
        var request = HttpRequest.newBuilder().GET();
        var response = sendSecureRequest(APIs.add_request_dorm, request);
        response.body();
    }

    public StudentData getStudentData(UUID studentID) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(studentID.toString()));

        HttpResponse<String> response = null;
        if (ONLINE) {
            response = sendSecureRequest(APIs.data_student, request);
            if (studentID.equals(Control.userId))
                Cache.addSingletonResponseToCache(APIs.data_student, response);

        } else {
            if (studentID.equals(Control.userId))
                response = Cache.getCachedSingletonResponse(APIs.data_student);
        }
        return gson.fromJson(response.body(), StudentData.class);
    }

    public ArrayList<StudentData> getFilteredStudentsData(StudentFilter studentFilter) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(studentFilter))
        );
        var response = sendSecureRequest(APIs.data_filtered_students, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), StudentData[].class)));
    }

    public ArrayList<StudentData> getAllStudentsData() {
        var request = HttpRequest.newBuilder().GET();
        var response = sendSecureRequest(APIs.data_students, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), StudentData[].class)));
    }

    public ArrayList<ScoreData> getFilteredScores(ScoreFilter scoreFilter) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(scoreFilter))
        );
        var response = sendSecureRequest(APIs.data_filtered_scores, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), ScoreData[].class)));
    }

    public CaptchaData getCaptcha() {
        var request = HttpRequest.newBuilder().GET();
        var response = sendRequest(APIs.auth_getCaptcha, request);
        return gson.fromJson(response.body(), CaptchaData.class);
    }

    public void addProfessor(ProfessorData professorData) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(professorData))
        );
        var response = sendSecureRequest(APIs.add_professor, request);
        response.body();
    }

    public FacultyData getFacultyByName(String facultyName) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(facultyName));
        var response = sendSecureRequest(APIs.data_facultyByName, request);
        return gson.fromJson(response.body(), FacultyData.class);
    }

    public ProfessorData getProfessorData(UUID professorId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(professorId.toString()));

        HttpResponse<String> response = null;
        if (ONLINE) {
            response = sendSecureRequest(APIs.data_professor, request);
            if (professorId.equals(Control.userId))
                Cache.addSingletonResponseToCache(APIs.data_professor, response);

        } else {
            if (professorId.equals(Control.userId))
                response = Cache.getCachedSingletonResponse(APIs.data_professor);
        }
        return gson.fromJson(response.body(), ProfessorData.class);
    }

    public FacultyData getFacultyDataById(UUID facultyId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(facultyId.toString()));
        var response = sendSecureRequest(APIs.data_faculty, request);
        return gson.fromJson(response.body(), FacultyData.class);
    }

    public void addStudent(StudentData studentData) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(studentData))
        );
        var response = sendSecureRequest(APIs.add_student, request);
        response.body();
    }

    public void removeUser(String userId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(userId));
        var response = sendSecureRequest(APIs.remove_user, request);
        response.body();
    }

    public void addCourse(CourseData courseData) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(courseData))
        );
        sendSecureRequest(APIs.add_course, request);
    }

    public void removeCourse(String courseId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(courseId));
        var response = sendSecureRequest(APIs.remove_course, request);
        response.body();
    }

    public CourseData getCourseData(UUID courseId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(courseId.toString()));
        var response = sendSecureRequest(APIs.data_course, request);
        return gson.fromJson(response.body(), CourseData.class);
    }

    public void finalizeCourse(UUID courseId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(courseId.toString()));
        var response = sendSecureRequest(APIs.update_course_finalize, request);
        response.body();
    }

    public ArrayList<String> getWeeklyPlan() {
        var request = HttpRequest.newBuilder().GET();

        HttpResponse<String> response;
        if (ONLINE) {
            response = sendSecureRequest(APIs.data_weekly_plan, request);
            Cache.addSingletonResponseToCache(APIs.data_weekly_plan, response);
        } else {
            response = Cache.getCachedSingletonResponse(APIs.data_weekly_plan);
        }

        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), String[].class)));
    }

    public void updateCourse(CourseData courseData) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.toJson(courseData)));
        sendSecureRequest(APIs.update_course_details, request);
    }

    public void updateProfessor(ProfessorData professorData) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.toJson(professorData)));
        sendSecureRequest(APIs.update_professor_details, request);
    }

    public void updateUser(UserData userData) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.toJson(userData)));
        sendSecureRequest(APIs.update_user_details, request);
    }

    public UserData getCurrentUserData() {
        return getUserData(Control.userId);
    }

    public ScoreData getScoreData(UUID scoreId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(scoreId.toString()));
        var response = sendSecureRequest(APIs.data_score, request);
        return gson.fromJson(response.body(), ScoreData.class);
    }

    public UserType getUserType(UUID userId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(userId.toString()));

        HttpResponse<String> response = null;
        if (ONLINE) {
            response = sendSecureRequest(APIs.data_userType, request);
            if (userId.equals(Control.userId))
                Cache.addSingletonResponseToCache(APIs.data_userType, response);

        } else {
            if (userId.equals(Control.userId))
                response = Cache.getCachedSingletonResponse(APIs.data_userType);
        }

        return gson.fromJson(response.body(), UserType.class);
    }

    public void updateFaculty(FacultyData facultyData) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.toJson(facultyData)));
        sendSecureRequest(APIs.update_faculty_details, request);
    }

    public void updateUserPassword(PasswordChangeData passwordChangeData) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.toJson(passwordChangeData)));
        sendSecureRequest(APIs.update_user_password, request);
    }

    public void checkAuth(AuthCheckData authCheckData) throws Exception {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.toJson(authCheckData)));
        var response = sendSecureRequest(APIs.auth_check, request);
        if (response.statusCode() != 200)
            throw new Exception("auth failed");
    }

    public UserData getUserDataByNationalId(String nationalId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(nationalId));
        var response = sendSecureRequest(APIs.data_userWithNationalId, request);
        return gson.fromJson(response.body(), UserData.class);
    }

    public void updateScore(ScoreData scoreData) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.toJson(scoreData)));
        sendSecureRequest(APIs.update_score_details, request);
    }

    public CourseScoringData getCourseScoringData(UUID courseId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(courseId.toString()));
        var response = sendSecureRequest(APIs.data_courseScoring, request);
        return gson.fromJson(response.body(), CourseScoringData.class);
    }

    public ArrayList<RequestData> getAllObjectionRequests() {
        var request = HttpRequest.newBuilder().GET();
        var response = sendSecureRequest(APIs.data_objectionRequests, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), RequestData[].class)));
    }

    public UserData getUserData(UUID userId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(userId.toString()));

        HttpResponse<String> response = null;
        if (ONLINE) {
            response = sendSecureRequest(APIs.data_user, request);
            if (userId.equals(Control.userId))
                Cache.addSingletonResponseToCache(APIs.data_user, response);

        } else {
            if (userId.equals(Control.userId))
                response = Cache.getCachedSingletonResponse(APIs.data_user);
        }

        return gson.fromJson(response.body(), UserData.class);
    }

    public ArrayList<ScoreData> getProfessorAllFinalizedScore(UUID professorId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(professorId.toString()));
        var response = sendSecureRequest(APIs.data_professorAllFinalizedScore, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), ScoreData[].class)));
    }

    public ArrayList<CourseData> getUserActiveCourses() {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(Control.userId.toString()));
        var response = sendSecureRequest(APIs.data_userActiveCourses, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), CourseData[].class)));
    }

    public ArrayList<ScoreData> getStudentNonFinalizedActiveScores(UUID studentId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(studentId.toString()));
        var response = sendSecureRequest(APIs.data_studentNonFinalizedActiveScores, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), ScoreData[].class)));
    }

    public void requestObjection(ObjectionRequestData objectionRequestData) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.toJson(objectionRequestData)));
        sendSecureRequest(APIs.add_request_objection, request);
    }

    public LocalDateTime getUserLastLogIn(UUID id) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(id.toString())
        );
        var response = sendSecureRequest(APIs.data_userLastLogin, request);
        return gson.fromJson(response.body(), LocalDateTime.class);
    }

    public void addStudentToCourse(AddStudentsData addStudentsData) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(addStudentsData))
        );
        sendSecureRequest(APIs.update_course_addStudents, request);
    }

    public void addTAToCourse(AddStudentsData addTAsData) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(addTAsData))
        );
        sendSecureRequest(APIs.update_course_addTAs, request);
    }

    public AssignmentData getAssignmentData(UUID assignmentId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(assignmentId.toString()));
        var response = sendSecureRequest(APIs.data_assignment, request);
        return gson.fromJson(response.body(), AssignmentData.class);
    }

    public EducationalContentData getEducationalContentData(UUID educationalContentId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(educationalContentId.toString()));
        var response = sendSecureRequest(APIs.data_educationalContent, request);
        return gson.fromJson(response.body(), EducationalContentData.class);
    }

    public ExamData getExamData(UUID examId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(examId.toString()));
        var response = sendSecureRequest(APIs.data_exam, request);
        return gson.fromJson(response.body(), ExamData.class);
    }

    public ArrayList<AssignmentData> getCourseAssignments(UUID courseId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(courseId.toString()));
        var response = sendSecureRequest(APIs.data_courseAssignment, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), AssignmentData[].class)));
    }

    public AttachmentData getAttachmentData(UUID attachmentId) {
        var cachedAttachmentData = Cache.getAttachment(attachmentId);
        if (cachedAttachmentData != null)
            return cachedAttachmentData;

        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(attachmentId.toString()));
        var response = sendSecureRequest(APIs.data_attachment, request);

        var attachmentData = gson.fromJson(response.body(), AttachmentData.class);
        Cache.addAttachmentToCache(attachmentData);
        return attachmentData;
    }

    public void removeEducationalData(UUID educationalContentId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(educationalContentId.toString()));
        sendSecureRequest(APIs.remove_educationalContent, request);
    }

    public MessageData getMessageData(UUID messageId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(messageId.toString()));
        var response = sendSecureRequest(APIs.data_message, request);
        return gson.fromJson(response.body(), MessageData.class);
    }

    public ChatFeedData getChatFeedData(UUID chatFeedId) {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(chatFeedId.toString()));

        HttpResponse<String> response;
        if (ONLINE) {
            response = sendSecureRequest(APIs.data_chatFeed, request);
            Cache.addGetChatFeedDataResponse(chatFeedId, response);
        } else {
            response = Cache.getGetChatFeedDataResponse(chatFeedId);
        }

        return gson.fromJson(response.body(), ChatFeedData.class);
    }

    public ArrayList<ChatFeedData> getCurrentUserChatFeeds() {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(Control.userId.toString()));

        HttpResponse<String> response;
        if (ONLINE) {
            response = sendSecureRequest(APIs.data_userChatFeeds, request);
            Cache.addSingletonResponseToCache(APIs.data_userChatFeeds, response);
        } else {
            response = Cache.getCachedSingletonResponse(APIs.data_userChatFeeds);
        }

        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), ChatFeedData[].class)));
    }

    public ArrayList<UUID> addAttachments(ArrayList<AttachmentData> attachmentsData) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(attachmentsData))
        );
        var response = sendSecureRequest(APIs.add_attachments, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), UUID[].class)));
    }

    public void addMessage(MessageData messageData) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(messageData))
        );
        sendSecureRequest(APIs.add_message, request);
    }

    public ArrayList<UUID> getCurrentUserContacts() {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(Control.userId.toString())
        );
        var response = sendSecureRequest(APIs.data_userContacts, request);
        System.out.println(response.body());
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), UUID[].class)));
    }

    public ArrayList<ChatFeedData> getCurrentUserChatFeedsWithUsers(ArrayList<UUID> userIds) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(userIds))
        );
        var response = sendSecureRequest(APIs.data_userContacts, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), ChatFeedData[].class)));
    }

    public void checkServerStatus() {
        var request = HttpRequest.newBuilder().GET();
        var response = sendRequest(APIs.serverStatus, request);
        try {
            ONLINE = response.body().equals("online");
        } catch (Exception ignored) {
            ONLINE = false;
        }
    }

    public Boolean isStudentCourseSelectionTime() {
        var request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(""));
        var response = sendSecureRequest(APIs.data_isStudentCourseSelectionTime, request);
        return gson.fromJson(response.body(), Boolean.class);
    }

    public void addExam(ExamData examData) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(examData))
        );
        sendSecureRequest(APIs.add_exam, request);
    }

    public void addEducationalContent(EducationalContentData educationalContentData) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(educationalContentData))
        );
        sendSecureRequest(APIs.add_educationalContent, request);
    }

    public void updateEducationalContent(EducationalContentData educationalContentData) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(educationalContentData))
        );
        sendSecureRequest(APIs.update_educationalContent_details, request);
    }

    public ArrayList<AssignmentAnswerData> getAssignmentAnswers(UUID assignmentId) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(assignmentId.toString())
        );
        var response = sendSecureRequest(APIs.data_assignmentAnswers, request);
        return new ArrayList<>(Arrays.asList(gson.fromJson(response.body(), AssignmentAnswerData[].class)));
    }

    public void addAssignmentAnswer(AssignmentAnswerData assignmentAnswerData) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(assignmentAnswerData))
        );
        sendSecureRequest(APIs.add_assignmentAnswer, request);
    }

    public AssignmentAnswerData getAssignmentAnswerData(UUID assignmentAnswerId) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(assignmentAnswerId.toString())
        );
        var response = sendSecureRequest(APIs.data_assignmentAnswer, request);
        return gson.fromJson(response.body(), AssignmentAnswerData.class);
    }

    public void updateAssignmentAnswer(AssignmentAnswerData assignmentAnswerData) {
        var request = HttpRequest.newBuilder().POST(
                HttpRequest.BodyPublishers.ofString(gson.toJson(assignmentAnswerData))
        );
        sendSecureRequest(APIs.update_course_assignmentAnswer, request);
    }
}
