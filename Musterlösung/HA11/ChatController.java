package de.uniks.pmws2425.nopm.controller;

import de.uniks.pmws2425.nopm.App;
import de.uniks.pmws2425.nopm.Main;
import de.uniks.pmws2425.nopm.model.Message;
import de.uniks.pmws2425.nopm.model.Topic;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChatController implements Controller {
    private final App app;
    private final Topic topic;

    @FXML
    public Tab topicTab;
    @FXML
    public ScrollPane messageScroll;
    @FXML
    public VBox messageBox;
    @FXML
    public TextField messageField;
    @FXML
    public Button sendButton;

    public List<Controller> subviewController = new ArrayList<>();
    private PropertyChangeListener newMessageHandler;
    private Timer fetchTimer;
    private Instant lastFetchTimestamp;

    public ChatController(App app, Topic topic) {
        this.app = app;
        this.topic = topic;
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public void init() {
        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/ChatView.fxml"));
        fxmlLoader.setControllerFactory(c -> this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.app.getChatService().loadMessages(this.topic, null);
        this.fetchTimer = new Timer();
        this.lastFetchTimestamp = Instant.now();
        this.fetchTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                app.getChatService().loadMessages(topic, lastFetchTimestamp);
                lastFetchTimestamp = Instant.now();
            }
        }, 5000, 5000);
    }

    @Override
    public Parent render() {
        this.topicTab.setText(this.topic.getTitle());

        for (Message message : this.topic.getMessages()) {
            addMessageController(message);
        }

        this.messageBox.heightProperty().addListener(this::handleScrollDown);

        this.newMessageHandler = evt -> {
            Message message = (Message) evt.getNewValue();
            addMessageController(message);
        };

        this.topic.listeners().addPropertyChangeListener(Topic.PROPERTY_MESSAGES, newMessageHandler);

        return null;
    }

    @Override
    public void destroy() {
        this.fetchTimer.cancel();
        this.fetchTimer.purge();
        this.topic.listeners().removePropertyChangeListener(Topic.PROPERTY_MESSAGES, newMessageHandler);
        this.subviewController.forEach(Controller::destroy);
    }

    public void sendMessage() {
        String message = messageField.getText();
        messageField.clear();
        app.getChatService().sendMessage(this.topic, message);
    }

    public Tab renderTab() {
        render();
        return this.topicTab;
    }

    private void handleScrollDown(Observable observable, Object oldValue, Object newValue) {
        messageScroll.setVvalue((Double) newValue);
    }

    private void addMessageController(Message message) {
        MessageController messageController = new MessageController(app, message);
        messageController.init();

        Platform.runLater(() -> {
            this.messageBox.getChildren().add(this.messageBox.getChildren().size(), messageController.render());
            this.messageScroll.setVvalue(5);
        });

        this.subviewController.add(messageController);
    }
}
