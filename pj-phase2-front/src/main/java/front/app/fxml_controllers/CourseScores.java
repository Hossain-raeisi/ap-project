package front.app.fxml_controllers;

import front.app.views.StudentScoreView;
import front.commons.data_class.ScoreData;
import front.services.Client;
import front.services.Logger;
import front.services.model_handlers.ProfessorCourses;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.util.UUID;

public class CourseScores {

    @FXML
    ListView<Pane> studentListView;
    @FXML
    Button actionButton;

    @FXML
    public void initialize() {
        var client = Client.getInstance();

        for (UUID scoreId : front.services.model_handlers.CourseScores.getCurrentCourseData().scoresId) {
            studentListView.getItems().add(new StudentScoreView(client.getScoreData(scoreId)));
        }

        if (front.services.model_handlers.CourseScores.getCurrentCourseData().temporaryScoresSet) {
            actionButton.setText("set final scores");
            actionButton.setOnAction(this::setFinalScores);
        } else {
            actionButton.setText("set temporary scores");
            actionButton.setOnAction(this::setTemporaryScores);
        }
    }

    public void setTemporaryScores(ActionEvent actionEvent) {
        var client = Client.getInstance();

        try {
            for (Pane studentScoreView : studentListView.getItems()) {

                ScoreData scoreData = client.getScoreData(((StudentScoreView) studentScoreView).getScoreId());
                scoreData.temporaryScore = ((StudentScoreView) studentScoreView).getScoreFloat();
                if (scoreData.temporaryScore > 20 || scoreData.temporaryScore < 0) {
                    throw new Exception("score out of bound");
                }
                client.updateScore(scoreData);
            }

            front.services.model_handlers.CourseScores.getCurrentCourseData().temporaryScoresSet = true;
            client.updateCourse(front.services.model_handlers.CourseScores.getCurrentCourseData());
            ProfessorCourses.showPage();

        } catch (Exception e) {
            Logger.Error(e.toString());
        }
    }

    public void setFinalScores(ActionEvent actionEvent) {
        var client = Client.getInstance();

        try {
            for (Pane studentScoreView : studentListView.getItems()) {
                ScoreData scoreData = client.getScoreData(((StudentScoreView) studentScoreView).getScoreId());

                scoreData.finalScore = ((StudentScoreView) studentScoreView).getScoreFloat();
                if (scoreData.finalScore > 20 || scoreData.finalScore < 0) {
                    throw new Exception("score out of bound");
                }
                scoreData.finalized = true;
            }

            Client.getInstance().finalizeCourse(front.services.model_handlers.CourseScores.getCurrentCourseData().id);
            ProfessorCourses.showPage();
        } catch (Exception e) {
            Logger.Error(e.toString());
        }
    }
}
