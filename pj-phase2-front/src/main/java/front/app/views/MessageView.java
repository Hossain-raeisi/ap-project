package front.app.views;

import front.commons.data_class.MessageData;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.UUID;

public class MessageView extends Pane {

    UUID messageId;
    Label senderName = new Label();
    Label text = new Label();
    Label sentTime = new Label();

    // TODO: add attachments

    public MessageView(MessageData messageData) {
        messageId = messageData.id;

        setPrefWidth(900);
        setPrefHeight(70);

        Font f = new Font(18);

        senderName.setText(messageData.senderName);
        senderName.setFont(f);
        getChildren().add(senderName);
        senderName.setLayoutX(5);
        senderName.setLayoutY(5);

        text.setText(messageData.text);
        text.setFont(f);
        getChildren().add(text);
        text.setLayoutX(5);
        text.setLayoutY(25);

        sentTime.setText(messageData.registerTime.toString());
        sentTime.setFont(f);
        getChildren().add(sentTime);
        sentTime.setLayoutX(700);
        sentTime.setLayoutY(60);

    }
}
