package ru_gb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import ru_gb.Client.ChatClient;

public class ClientController {
    @FXML
    private HBox loginBox;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ListView<String> clientList;
    @FXML
    private HBox messageBox;
    @FXML
    private TextArea messageArea;
    @FXML
    private TextField messageField;

    final ChatClient client;

    public ClientController() {
        client = new ChatClient(this);
    }

    public void onClickSendButton(ActionEvent actionEvent) {
        final String message = messageField.getText();
        if (message != null && !message.isEmpty()) {
            client.sendMessage(message);
            messageField.clear();
            messageField.requestFocus();
        }
    }


    public void addMessage(String message) {
        messageArea.appendText(message + "\n");
    }

    public void bthAuthClick(ActionEvent actionEvent) {
        client.sendMessage(Command.AUTH.getCommand() + " " + loginField.getText() + " " + passwordField.getText());

    }

    public void selectClient(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            final String message = messageField.getText();
            final String client = String.valueOf(clientList.getSelectionModel().getSelectedItems());
            messageField.setText(Command.PRIVATE_MESSAGE.getCommand() + " " + client + " " + message);
            messageField.requestFocus();
            messageField.selectEnd();
        }
    }

    public void setAuth(boolean isAuthSuccess) {
        loginBox.setVisible(!isAuthSuccess);
        messageBox.setVisible(isAuthSuccess);
    }

    public void updateClientsList(String[] clients) {
        clientList.getItems().clear();
        clientList.getItems().addAll(clients);
    }
}