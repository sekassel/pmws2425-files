package de.uniks.pmws2425.nopm.controller;

import de.uniks.pmws2425.nopm.App;
import de.uniks.pmws2425.nopm.Main;
import de.uniks.pmws2425.nopm.model.Message;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MessageController implements Controller {
    private static final DateTimeFormatter TIME_FORMAT
            = DateTimeFormatter.ofPattern("HH:mm");

    private final App app;
    private final Message message;

    @FXML
    public VBox messageBubble;
    @FXML
    public Label bodyText;
    @FXML
    public Label infoText;

    private Parent parent;

    public MessageController(App app, Message message) {
        this.app = app;
        this.message = message;
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public void init() {
        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/MessageView.fxml"));
        fxmlLoader.setControllerFactory(c -> this);
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Parent render() {
        bodyText.setText(this.message.getBody());
        infoText.setText(this.message.getTimestamp().atZone(ZoneId.systemDefault()).format(TIME_FORMAT) + " " + this.message.getSender().getNickname());
        messageBubble.setAlignment(this.message.getSender().equals(app.getChatService().getSelf()) ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        return parent;
    }

    @Override
    public void destroy() {

    }
}
