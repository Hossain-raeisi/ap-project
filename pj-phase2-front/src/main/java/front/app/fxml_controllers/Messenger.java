package front.app.fxml_controllers;

import front.app.views.ChatFeedMemberView;
import front.app.views.ChatFeedView;
import front.app.views.MessageView;
import front.services.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static front.app.Util.stopWorkerThreads;

public class Messenger {

    static volatile ArrayList<Thread> updaterThreads = new ArrayList<>();

    @FXML
    Button newChatButton;
    @FXML
    TextField attachmentPathField;
    @FXML
    Button sendMessage;
    @FXML
    Button addAttachment;
    @FXML
    TextField messageField;
    @FXML
    ListView<ChatFeedView> chatFeedsList;
    @FXML
    ListView<MessageView> chat;
    @FXML
    ListView<ChatFeedMemberView> chatFeedMembers;

    @FXML
    public void initialize() {
        stopWorkerThreads(updaterThreads);
        updaterThreads.clear();
        var client = Client.getInstance();

        activateChatFeedsListUpdate();
        chatFeedsList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, chatFeedView, t1) ->
                        chatFeedSelected(chatFeedsList.getSelectionModel().getSelectedItem().getChatFeedId())
        );

        activateChatMessagesUpdate();

        var chatFeedData = front.services.util.Messenger.getCurrentChatFeedData();
        if (chatFeedData != null)
            chatFeedMembers.getItems().addAll(chatFeedData.membersId.stream().map(client::getUserData).map(ChatFeedMemberView::new).toList());

    }

    public void activateChatFeedsListUpdate() {
        Task<ObservableList<ChatFeedView>> task = new Task<>() {
            @Override
            protected ObservableList<ChatFeedView> call() {
                try {
                    while (true) {
                        var userChatFeeds = front.services.util.Messenger.getUserChatFeeds();
                        List<ChatFeedView> result = new ArrayList<>(userChatFeeds.stream().map(ChatFeedView::new).toList());
                        updateValue(FXCollections.observableList(result));
                        TimeUnit.SECONDS.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        chatFeedsList.itemsProperty().bind(task.valueProperty());
        var thread = new Thread(task);
        thread.start();
        updaterThreads.add(thread);
    }

    public void activateChatMessagesUpdate() {
        Task<ObservableList<MessageView>> task = new Task<>() {
            @Override
            protected ObservableList<MessageView> call() {
                var client = Client.getInstance();
                var chatFeedData = front.services.util.Messenger.getCurrentChatFeedData();

                if (chatFeedData == null) {
                    sendMessage.setVisible(false);
                    addAttachment.setVisible(false);
                    messageField.setVisible(false);
                    chat.setVisible(false);
                    attachmentPathField.setVisible(false);
                    return null;
                }

                try {
                    while (true) {
                        if (chatFeedData.messagesId == null) {
                            TimeUnit.SECONDS.sleep(10);
                            chatFeedData = front.services.util.Messenger.getCurrentChatFeedData();
                            continue;
                        }
                        System.out.println("hello");
                        List<MessageView> result = new ArrayList<>(chatFeedData.messagesId.stream().map(client::getMessageData).map(MessageView::new).toList());
                        updateValue(FXCollections.observableList(result));
                        TimeUnit.SECONDS.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        chat.itemsProperty().bind(task.valueProperty());
        var thread = new Thread(task);
        thread.start();
        updaterThreads.add(thread);
    }

    public void chatFeedSelected(UUID chatFeedId) {
        front.services.util.Messenger.chatSelected(chatFeedId);
    }

    public void sendMessageButtonAction(ActionEvent actionEvent) {
        var text = messageField.getText();
        front.services.util.Messenger.sendMessage(text);
    }

    public void addAttachmentButtonAction(ActionEvent actionEvent) {
        var attachmentPath = attachmentPathField.getText();
        front.services.util.Messenger.addAttachment(attachmentPath);
    }

    public void newChatButtonAction(ActionEvent actionEvent) {
        front.services.util.Messenger.showNewChatPage();
    }


}

