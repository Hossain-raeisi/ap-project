package front.app.fxml_controllers;

import front.commons.data_class.UserData;
import front.app.Util;
import front.services.Client;
import front.services.security.LogOut;
import front.services.model_handlers.CourseSelection;
import front.services.model_handlers.Messenger;
import front.services.model_handlers.ProfessorCourses;
import front.services.model_handlers.Profile;
import front.services.model_handlers.WeeklyPlan;
import front.services.model_handlers.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Header {

    enum Theme {
        grayBlue,
        cyber
    }

    static Theme theme = Theme.grayBlue;

    @FXML
    Button courseSelection;
    @FXML
    AnchorPane basePane;
    @FXML
    Pane headerPane;
    @FXML
    ButtonBar navbarButtonBar;
    @FXML
    Label lastLogInTimeLabel;
    @FXML
    Button requests;
    @FXML
    Button cw;
    @FXML
    Button messenger;
    @FXML
    Button logOut;
    @FXML
    Button home;
    @FXML
    Button userProfile;
    @FXML
    MenuButton register;
    @FXML
    MenuButton reportCard;
    @FXML
    MenuButton educationalServices;
    @FXML
    Label userFullName;
    @FXML
    Label userEmail;
    @FXML
    ImageView userImage;
    @FXML
    Menu requestsSubMenu;

    public static void changeTheme() {
        switch (theme) {
            case grayBlue -> theme = Theme.cyber;
            case cyber -> theme = Theme.grayBlue;
        }
    }

    @FXML
    public void initialize() {
        var client = Client.getInstance();
        UserData userData = client.getCurrentUserData();

        userFullName.setText(Util.getUserFullName(userData));
        userEmail.setText(userData.email);

        lastLogInTimeLabel.setText(client.getUserLastLogIn(userData.id).toString());

        if (userData.imageData != null)
            userImage.setImage(Util.getImageFromAttachmentData(userData.imageData));

        switch (client.getUserType(userData.id)) {
            case bachelorStudent -> loadBachelorStudent();
            case masterStudent -> loadMasterStudent();
            case doctorateStudent -> loadDoctorateStudent();
            case normalProfessor -> loadNormalProfessor();
            case deputyEducationProfessor -> loadDeputyEducationProfessor();
            case facultyHeadProfessor -> loadFacultyHeadProfessor();
            case mohseni -> loadMohseni();
        }

        if (theme == Theme.cyber) {
            basePane.setStyle("-fx-background-color: #370065");
            headerPane.setStyle("-fx-background-color: #f5398a");
        }
    }

    private void loadMohseni() {

    }

    public void loadBachelorStudent() {
        var client = Client.getInstance();

        MenuItem educationalStatusReportCard = new MenuItem();
        educationalStatusReportCard.setText("educationalStatus");
        educationalStatusReportCard.setOnAction(this::educationalStatusMenuItemAction);
        reportCard.getItems().add(educationalStatusReportCard);

        MenuItem recommendationRequest = new MenuItem("recommendation");
        recommendationRequest.setOnAction(this::recommendationRequestMenuItemAction);
        MenuItem serviceExemptionRequest = new MenuItem("military service exemption");
        serviceExemptionRequest.setOnAction(this::serviceExemptionRequestMenuItemAction);
        MenuItem minorRequest = new MenuItem("minor");
        minorRequest.setOnAction(this::minorRequestMenuItemAction);
        MenuItem withdrawalRequest = new MenuItem("withdrawal");
        withdrawalRequest.setOnAction(this::withdrawalRequestMenuItemAction);

        requestsSubMenu.getItems().addAll(recommendationRequest, serviceExemptionRequest, minorRequest, withdrawalRequest);

        courseSelection.setVisible(client.isStudentCourseSelectionTime());
    }

    public void loadMasterStudent() {
        var client = Client.getInstance();

        MenuItem educationalStatusReportCard = new MenuItem();
        educationalStatusReportCard.setText("educationalStatus");
        educationalStatusReportCard.setOnAction(this::educationalStatusMenuItemAction);
        reportCard.getItems().add(educationalStatusReportCard);

        MenuItem recommendationRequest = new MenuItem("recommendation");
        recommendationRequest.setOnAction(this::recommendationRequestMenuItemAction);
        MenuItem serviceExemptionRequest = new MenuItem("military service exemption");
        serviceExemptionRequest.setOnAction(this::serviceExemptionRequestMenuItemAction);
        MenuItem dormRequest = new MenuItem("dorm");
        dormRequest.setOnAction(this::dormRequestMenuItemAction);
        MenuItem withdrawalRequest = new MenuItem("withdrawal");
        withdrawalRequest.setOnAction(this::withdrawalRequestMenuItemAction);

        requestsSubMenu.getItems().addAll(recommendationRequest, serviceExemptionRequest, dormRequest, withdrawalRequest);

        courseSelection.setVisible(client.isStudentCourseSelectionTime());
    }

    public void loadDoctorateStudent() {
        var client = Client.getInstance();

        MenuItem educationalStatusReportCard = new MenuItem();
        educationalStatusReportCard.setText("educationalStatus");
        educationalStatusReportCard.setOnAction(this::educationalStatusMenuItemAction);
        reportCard.getItems().add(educationalStatusReportCard);

        MenuItem serviceExemptionRequest = new MenuItem("military service exemption");
        serviceExemptionRequest.setOnAction(this::serviceExemptionRequestMenuItemAction);
        MenuItem withdrawalRequest = new MenuItem("withdrawal");
        withdrawalRequest.setOnAction(this::withdrawalRequestMenuItemAction);
        MenuItem thesisDefenceRoundRequest = new MenuItem(" thesis defence round");
        thesisDefenceRoundRequest.setOnAction(this::thesisDefenceRoundRequestMenuItemAction);

        requestsSubMenu.getItems().addAll(serviceExemptionRequest, withdrawalRequest, thesisDefenceRoundRequest);

        courseSelection.setVisible(client.isStudentCourseSelectionTime());
    }

    public void loadNormalProfessor() {
        educationalServices.getItems().remove(2);
        courseSelection.setVisible(false);
    }

    public void loadDeputyEducationProfessor() {
        educationalServices.getItems().remove(2);

        MenuItem educationalStatusReportCard = new MenuItem();
        educationalStatusReportCard.setText("educationalStatus");
        educationalStatusReportCard.setOnAction(this::educationalStatusMenuItemAction);
        reportCard.getItems().add(educationalStatusReportCard);

        MenuButton addUserMenuButton = new MenuButton();
        addUserMenuButton.setText("add user");
        addUserMenuButton.setStyle("-fx-background-color: #3299bb;");
        navbarButtonBar.getButtons().add(addUserMenuButton);

        MenuItem addStudent = new MenuItem();
        addStudent.setText("student");
        addStudent.setOnAction(this::addStudentMenuItemAction);
        addUserMenuButton.getItems().add(addStudent);

        MenuItem addProfessor = new MenuItem();
        addProfessor.setText("professor");
        addProfessor.setOnAction(this::addProfessorMenuItemAction);
        addUserMenuButton.getItems().add(addProfessor);

        MenuItem DeputyEducationTemporaryScores = new MenuItem();
        DeputyEducationTemporaryScores.setText("special");
        DeputyEducationTemporaryScores.setOnAction(this::deputyEducationEducationalStatus);
        reportCard.getItems().add(DeputyEducationTemporaryScores);

        courseSelection.setVisible(false);
    }

    public void loadFacultyHeadProfessor() {
        educationalServices.getItems().remove(2);

        courseSelection.setVisible(false);
    }

    public void professorsMenuItemAction(ActionEvent ignoredActionEvent) {
        front.services.model_handlers.Professors.showAllProfessors();
    }

    public void coursesMenuItemAction(ActionEvent ignoredActionEvent) {
        front.services.model_handlers.Courses.showAllCourses();
    }

    public void weeklyPlanMenuItemAction(ActionEvent ignoredActionEvent) {
        WeeklyPlan.showPage();
    }

    public void examsMenuItemAction(ActionEvent ignoredActionEvent) {
        front.services.model_handlers.Exams.showUserExams();
    }

    public void recommendationRequestMenuItemAction(ActionEvent actionEvent) {
        RecommendationRequest.showPage();
    }

    public void serviceExemptionRequestMenuItemAction(ActionEvent actionEvent) {
        ServiceExemptionRequest.showPage();
    }

    public void minorRequestMenuItemAction(ActionEvent actionEvent) {
        MinorRequest.showPage();
    }

    public void withdrawalRequestMenuItemAction(ActionEvent actionEvent) {
        WithdrawalRequest.showPage();
    }

    public void dormRequestMenuItemAction(ActionEvent actionEvent) {
        DormRequest.showPage();
    }

    public void thesisDefenceRoundRequestMenuItemAction(ActionEvent actionEvent) {
        ThesisDefenceRoundRequest.showPage();
    }

    public void temporaryScoresMenuItemAction(ActionEvent ignoredActionEvent) {
        // TODO
        var client = Client.getInstance();
        switch (client.getUserType(client.getCurrentUserData().id)) {
            case bachelorStudent,
                    masterStudent,
                    doctorateStudent ->
                    TemporaryScores.showPage((client.getStudentData(client.getCurrentUserData().id)));
            case normalProfessor,
                    deputyEducationProfessor,
                    facultyHeadProfessor -> ProfessorCourses.showPage();
        }
    }

    public void educationalStatusMenuItemAction(ActionEvent actionEvent) {
        EducationalStatus.showPage();
    }

    public void userProfileButtonAction(ActionEvent ignoredActionEvent) {
        Profile.showPage();
    }

    public void addStudentMenuItemAction(ActionEvent actionEvent) {
        AddUser.showPage("student");
    }

    public void addProfessorMenuItemAction(ActionEvent actionEvent) {
        AddUser.showPage("professor");
    }

    public void requestsButtonAction(ActionEvent ignoredActionEvent) {
        Requests.showPage();
    }

    public void cwButtonAction(ActionEvent actionEvent) {
        CW.showPage();
    }

    public void messengerButtonAction(ActionEvent actionEvent) {
        Messenger.showPage();
    }

    public void deputyEducationEducationalStatus(ActionEvent actionEvent) {
        DeputyEducationEducationalStatus.showPage();
    }

    public void homeButtonAction(ActionEvent ignoredActionEvent) {
        Home.home();
    }

    public void logOutButtonAction(ActionEvent ignoredActionEvent) {
        LogOut.logOut();
    }

    public void courseSelectionButtonAction(ActionEvent actionEvent) {
        CourseSelection.showPage();
    }
}
