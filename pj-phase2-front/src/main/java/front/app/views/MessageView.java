package front.app.views;

import front.commons.data_class.MessageData;
import front.services.Client;
import front.services.FileHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.UUID;

public class MessageView extends Pane {

    UUID messageId;
    Label senderName = new Label();
    Label text = new Label();
    Label sentTime = new Label();

    ListView<String> attachmentsPath = new ListView<>();

    public MessageView(MessageData messageData) {
        var client = Client.getInstance();
        messageId = messageData.id;

        setPrefWidth(900);
        setPrefHeight(100);

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
        sentTime.setLayoutY(90);

        attachmentsPath.getItems().addAll(messageData.attachmentsId.stream().map(client::getAttachmentData).map(attachmentData -> FileHandler.writeFile(attachmentData.data, attachmentData.fileName)).toList());
        getChildren().add(attachmentsPath);
        sentTime.setLayoutX(5);
        sentTime.setLayoutY(50);
        sentTime.setPrefHeight(40);
        sentTime.setPrefWidth(300);
    }
}
