package front.app.views;

import front.app.Util;
import front.commons.data_class.ChatFeedData;
import front.services.Client;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.UUID;

public class ChatFeedView extends Pane {

    UUID chatFeedId;
    Label members = new Label();
    Label lastMessage = new Label();

    public ChatFeedView(ChatFeedData chatFeedData) {
        var client = Client.getInstance();
        chatFeedId = chatFeedData.id;

        setPrefWidth(430);
        setPrefHeight(100);

        Font f = new Font(18);

        String membersText = "";
        for (UUID userId: chatFeedData.membersId) {
            if (userId != client.getCurrentUserData().id) {
                membersText += Util.getUserFullName(client.getUserData(userId));
                break;
            }
        }
        if (chatFeedData.membersId.size() > 2)
            membersText += ", ...";

        members.setText(membersText);
        members.setFont(f);
        getChildren().add(members);
        members.setLayoutX(5);
        members.setLayoutY(5);

        if (chatFeedData.messagesId == null)
            return;

        var lastMessageData = client.getMessageData(chatFeedData.messagesId.get(0));
        String lastMessageString = lastMessageData.text.substring(0, Math.min(100, lastMessageData.text.length()));

        lastMessage.setText(lastMessageString);
        lastMessage.setFont(f);
        getChildren().add(lastMessage);
        lastMessage.setLayoutX(5);
        lastMessage.setLayoutY(25);

    }

    public UUID getChatFeedId() {
        return chatFeedId;
    }
}
