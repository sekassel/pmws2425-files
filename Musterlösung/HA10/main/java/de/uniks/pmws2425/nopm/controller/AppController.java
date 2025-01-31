package de.uniks.pmws2425.nopm.controller;

import de.uniks.pmws2425.nopm.App;
import de.uniks.pmws2425.nopm.Main;
import de.uniks.pmws2425.nopm.model.Topic;
import de.uniks.pmws2425.nopm.service.ChatService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppController implements Controller {
    private final App app;
    private final List<Controller> subviewController = new ArrayList<>();

    @FXML
    public TabPane chatTabPane;
    @FXML
    public Button logoutButton;

    private Parent parent;

    public AppController(App app) {
        this.app = app;
    }

    @Override
    public String getTitle() {
        return "NoPM - Chat";
    }

    @Override
    public void init() {
        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/AppView.fxml"));
        fxmlLoader.setControllerFactory(c -> this);
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Parent render() {
        chatTabPane.getTabs().clear();

        for (Topic topic : app.getChatService().getTopics()) {
            ChatController topicSubController = new ChatController(app, topic);
            topicSubController.init();
            this.chatTabPane.getTabs().add(topicSubController.renderTab());
            this.subviewController.add(topicSubController);
        }

        logoutButton.setOnAction(event -> {
            app.getChatService().logout();
            app.show(new LoginController(app));
        });

        return this.parent;
    }

    @Override
    public void destroy() {
        subviewController.forEach(Controller::destroy);
    }
}
