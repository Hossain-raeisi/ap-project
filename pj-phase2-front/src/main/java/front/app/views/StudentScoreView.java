package front.app.views;

import front.commons.data_class.ScoreData;
import front.services.Client;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.UUID;

public class StudentScoreView extends Pane {

    Label studentName = new Label();
    Label studentAlreadySetScore = new Label();
    TextField studentScore = new TextField();
    UUID scoreId;

    public StudentScoreView(ScoreData scoreData) {
        this.scoreId = scoreData.id;

        setPrefWidth(1300);
        setPrefHeight(50);

        Font f = new Font(18);

        studentName.setText(scoreData.studentName);
        studentName.setFont(f);
        getChildren().add(studentName);
        studentName.setLayoutX(10);
        studentName.setLayoutY(10);

        if (scoreData.temporaryScore != null) {
            studentAlreadySetScore.setText(String.valueOf(scoreData.temporaryScore));
            studentAlreadySetScore.setFont(f);
            getChildren().add(studentAlreadySetScore);
            studentAlreadySetScore.setLayoutX(500);
            studentAlreadySetScore.setLayoutY(10);
        }

        getChildren().add(studentScore);
        studentScore.setLayoutX(1000);
        studentScore.setLayoutY(10);
    }

    public UUID getScoreId() {
        return scoreId;
    }

    public Float getScoreFloat() {
        ScoreData scoreData = Client.getInstance().getScoreData(scoreId);
        String scoreFieldText = studentScore.getText();

        if (scoreFieldText.equals("")) {
            return scoreData.temporaryScore;
        }

        return Float.parseFloat(scoreFieldText);
    }
}
