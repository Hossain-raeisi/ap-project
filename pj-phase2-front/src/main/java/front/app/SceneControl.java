package front.app;

import front.commons.enums.UserType;
import front.services.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneControl {

    public enum SceneType {
        logInPage,
        homePage,
        professorsPage,
        coursesPage,
        examsPage,
        serviceExemptionRequestPage,
        minorRequestPage,
        requestsPage,
        recommendationRequestPage,
        withdrawalRequestPage,
        dormRequestPage,
        thesisDefenceRoundRequest,
        requestHandlingPage,
        requestResponsePage,
        educationalStatusStudentPage,
        educationalStatusDeputyEducationProfessorPage,
        temporaryScoresStudentPage,
        scoreObjectionRequestPage,
        addStudentPage,
        addProfessorPage,
        editProfessorPage,
        addCoursePage,
        editCoursePage,
        professorCoursesPage,
        courseScoresPage,
        profilePage,
        editProfilePage,
        weeklyPlanPage,
        deputyEducationTemporaryScoresPage,
        courseDescriptionPage,
        allObjectionRequestsPage,
        allProfessorScoresPage,
        changePasswordPage,
        myCoursesPage,
        coursePage,
        assignmentPage,
        educationalContentPage,
        messengerPage,
        newChatPage,
        courseSelectionPage,
        newExamPage,
        newEducationalContentPage,
        educationalContentItem,
        assignmentAnswerPage,
    }

    public static Map<SceneType, String> scenes = new HashMap<>();
    public static Stage primaryStage;

    public static void changeScene(SceneType scene) {
        try {
            primaryStage.setScene(new Scene(new FXMLLoader(SceneControl.class.getResource(scenes.get(scene))).load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.show();
    }

    public static void loadGui(Stage primaryStage) {
        SceneControl.primaryStage = primaryStage;
        scenes.put(SceneType.logInPage, "/fxmls/login.fxml");
        changeScene(SceneType.logInPage);

        // to move it to my other monitor
        var screens = Screen.getScreens();
        Rectangle2D bounds = screens.get(1).getVisualBounds();
        SceneControl.primaryStage.setX(bounds.getMinX() + ((bounds.getWidth() - 1920) / 2));
        SceneControl.primaryStage.setY(bounds.getMinY() + ((bounds.getHeight() - 1080) / 2));
        SceneControl.primaryStage.setResizable(false);

        SceneControl.primaryStage.show();
    }

    public static void loadScenes(UserType user) {
        Logger.Info("load fxmls");

        scenes.put(SceneType.professorsPage, "/fxmls/professors.fxml");
        scenes.put(SceneType.coursesPage, "/fxmls/courses.fxml");
        scenes.put(SceneType.examsPage, "/fxmls/exams.fxml");
        scenes.put(SceneType.educationalStatusStudentPage, "/fxmls/educationalStatusStudent.fxml");
        scenes.put(SceneType.educationalStatusDeputyEducationProfessorPage,
                "fxmls/educationalStatusDeputyEducationProfessor.fxml");
        scenes.put(SceneType.temporaryScoresStudentPage, "/fxmls/temporaryScoresStudent.fxml");
        scenes.put(SceneType.addStudentPage, "/fxmls/addUserStudent.fxml");
        scenes.put(SceneType.addProfessorPage, "/fxmls/addUserProfessor.fxml");
        scenes.put(SceneType.editProfessorPage, "/fxmls/editUserProfessor.fxml");
        scenes.put(SceneType.addCoursePage, "/fxmls/addCourse.fxml");
        scenes.put(SceneType.editCoursePage, "/fxmls/editCourse.fxml");
        scenes.put(SceneType.profilePage, "/fxmls/profile.fxml");
        scenes.put(SceneType.editProfilePage, "/fxmls/editProfile.fxml");
        scenes.put(SceneType.weeklyPlanPage, "/fxmls/weeklyPlan.fxml");
        scenes.put(SceneType.deputyEducationTemporaryScoresPage, "/fxmls/temporaryScoresDeputyEducation.fxml");
        scenes.put(SceneType.courseDescriptionPage, "/fxmls/courseDescription.fxml");
        scenes.put(SceneType.allProfessorScoresPage, "f/xmls/professorAllFinalizedScores.fxml");
        scenes.put(SceneType.changePasswordPage, "/fxmls/changePassword.fxml");
        scenes.put(SceneType.myCoursesPage, "/fxmls/myCourses.fxml");
        scenes.put(SceneType.coursePage, "/fxmls/course.fxml");
        scenes.put(SceneType.assignmentPage, "/fxmls/assignment.fxml");
        scenes.put(SceneType.educationalContentPage, "/fxmls/educationalContent.fxml");
        scenes.put(SceneType.messengerPage, "/fxmls/messenger.fxml");
        scenes.put(SceneType.newChatPage, "/fxmls/newChat.fxml");
        scenes.put(SceneType.courseSelectionPage, "/fxmls/courseSelection.fxml");
        scenes.put(SceneType.newExamPage, "/fxmls/newExam.fxml");
        scenes.put(SceneType.educationalContentItem, "/fxmls/educationalContentItem.fxml");
        scenes.put(SceneType.assignmentAnswerPage, "/fxmls/assignmentAnswer.fxml");

        //requests
        scenes.put(SceneType.serviceExemptionRequestPage, "/fxmls/requests/serviceExemptionRequest.fxml");
        scenes.put(SceneType.minorRequestPage, "/fxmls/requests/minorRequest.fxml");
        scenes.put(SceneType.requestsPage, "/fxmls/requests/requests.fxml");
        scenes.put(SceneType.scoreObjectionRequestPage, "/fxmls/requests/scoreObjectionRequest.fxml");
        scenes.put(SceneType.recommendationRequestPage, "/fxmls/requests/recommendationRequest.fxml");
        scenes.put(SceneType.withdrawalRequestPage, "/fxmls/requests/withdrawalRequest.fxml");
        scenes.put(SceneType.dormRequestPage, "/fxmls/requests/dormRequest.fxml");
        scenes.put(SceneType.thesisDefenceRoundRequest, "/fxmls/requests/thesisDefenceRoundRequest.fxml");
        scenes.put(SceneType.requestHandlingPage, "/fxmls/requests/requestHandling.fxml");
        scenes.put(SceneType.requestResponsePage, "/fxmls/requests/requestResponse.fxml");
        scenes.put(SceneType.allObjectionRequestsPage, "/fxmls/requests/allObjectionRequests.fxml");

        //user related
        switch (user) {
            case bachelorStudent, masterStudent, doctorateStudent ->
                    scenes.put(SceneType.homePage, "/fxmls/homePage.fxml");
            case normalProfessor, deputyEducationProfessor, facultyHeadProfessor -> {
                scenes.put(SceneType.homePage, "/fxmls/header.fxml");
                scenes.put(SceneType.professorCoursesPage, "/fxmls/professorCourses.fxml");
                scenes.put(SceneType.courseScoresPage, "/fxmls/courseScores.fxml");
            }
        }

    }
}
