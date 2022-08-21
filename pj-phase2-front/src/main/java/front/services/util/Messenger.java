package front.services.util;

import front.app.SceneControl;
import front.commons.data_class.AttachmentData;
import front.commons.data_class.ChatFeedData;
import front.commons.data_class.MessageData;
import front.services.Client;
import front.services.FileHandler;

import java.util.ArrayList;
import java.util.UUID;

public class Messenger {
    public static volatile UUID currentChatFeedId;
    public static ArrayList<AttachmentData> attachmentsData = new ArrayList<>();

    public static void showPage() {
        SceneControl.changeScene(SceneControl.SceneType.messengerPage);
    }

    public static ChatFeedData getCurrentChatFeedData() {
        if (currentChatFeedId == null)
            return null;
        return Client.getInstance().getChatFeedData(currentChatFeedId);
    }

    public static ArrayList<ChatFeedData> getUserChatFeeds() {
        return Client.getInstance().getCurrentUserChatFeeds();
    }

    public static void chatSelected(UUID chatFeedId) {
        currentChatFeedId = chatFeedId;
        showPage();
    }

    public static void sendMessage(String text) {
        var client = Client.getInstance();
        var attachmentsId = client.addAttachments(attachmentsData);
        attachmentsData.clear();
        var messageData = new MessageData(
                client.getCurrentUserData().id,
                text,
                attachmentsId,
                currentChatFeedId
        );
        client.addMessage(messageData);
    }

    public static void addAttachment(String attachmentPath) {
        attachmentsData.add(FileHandler.getAttachmentDataFromPath(attachmentPath));
    }

    public static void showNewChatPage() {
        SceneControl.changeScene(SceneControl.SceneType.newChatPage);
    }

    public static void sendMessageToAllUserContacts(String message) {
        var client = Client.getInstance();
        var attachmentsId = client.addAttachments(attachmentsData);
        attachmentsData.clear();

        // TODO
        var chatFeedsData = client.getCurrentUserChatFeeds();
        for (var chatFeedData: chatFeedsData) {
            var messageData = new MessageData(
                    client.getCurrentUserData().id,
                    message,
                    attachmentsId,
                    chatFeedData.id
            );
            client.addMessage(messageData);
        }
    }

    public static void setSendMessageToUsers(ArrayList<String>userNationalIds, String message) {
        var client = Client.getInstance();
        var attachmentsId = client.addAttachments(attachmentsData);
        attachmentsData.clear();

        var userIds = new ArrayList<>(
                userNationalIds.stream().map(client::getUserDataByNationalId).map(userData -> userData.id).toList()
        );
        var chatFeedsData = client.getCurrentUserChatFeedsWithUsers(userIds);

        for (var chatFeedData: chatFeedsData) {
            var messageData = new MessageData(
                    client.getCurrentUserData().id,
                    message,
                    attachmentsId,
                    chatFeedData.id
            );
            client.addMessage(messageData);
        }
    }

    public static void newContactRequest(String nationalId) {
        var client = Client.getInstance();

        var userId = client.getUserDataByNationalId(nationalId).id;
        client.requestContact(userId);
    }

    public static void clear(){
        currentChatFeedId = null;
        attachmentsData = null;
    }
}
