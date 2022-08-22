package front.app.fxml_controllers;

import front.app.views.ContactView;
import front.services.Client;
import front.services.model_handlers.Messenger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;

public class NewChat {

    @FXML
    ListView<ContactView> contactsList;      // TODO
    @FXML
    TextArea messageField;
    @FXML
    Button sendToAllButton;
    @FXML
    Button sendToButton;
    @FXML
    TextField sendToNationalIdField;
    @FXML
    Button newContactButton;
    @FXML
    TextField newContactNationalIdField;

    @FXML
    public void initialize() {
        var client = Client.getInstance();
        System.out.println(client.getCurrentUserContacts());
        contactsList.getItems().addAll(client.getCurrentUserContacts().stream().map(client::getUserData).map(ContactView::new).toList());
    }

    public void sendToAllButtonAction(ActionEvent actionEvent) {
        Messenger.sendMessageToAllUserContacts(messageField.getText());
    }

    public void sendToButtonAction(ActionEvent actionEvent) {
        Messenger.setSendMessageToUsers(
                new ArrayList<>(Arrays.asList(sendToNationalIdField.getText().split(","))),
                messageField.getText()
        );
    }

    public void newContactButtonAction(ActionEvent actionEvent) {
        Messenger.newContactRequest(newContactNationalIdField.getText());
    }
}
