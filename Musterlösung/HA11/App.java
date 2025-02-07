package de.uniks.pmws2425.nopm;

import de.uniks.pmws2425.nopm.controller.Controller;
import de.uniks.pmws2425.nopm.controller.LoginController;
import de.uniks.pmws2425.nopm.service.ChatApiService;
import de.uniks.pmws2425.nopm.service.ChatService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import kong.unirest.core.Unirest;

public class App extends Application {
    private Stage stage;
    private Controller controller;
    private ChatService chatService;
    private ChatApiService chatApiService;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        primaryStage.setScene(new Scene(new Label("Loading...")));
        primaryStage.setTitle("NoPM");

        chatApiService = new ChatApiService();
        chatService = new ChatService(chatApiService);

        show(new LoginController(this));

        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> controller.destroy());
    }

    @Override
    public void stop() {
        if (controller != null) {
            controller.destroy();
            controller = null;
        }

        this.chatService.logout();
        Unirest.shutDown();
    }

    public void show(Controller controller) {
        controller.init();

        stage.getScene().setRoot(controller.render());
        stage.sizeToScene();

        if (this.controller != null) {
            this.controller.destroy();
        }
        this.controller = controller;
        stage.setTitle(controller.getTitle());
    }

    public ChatService getChatService() {
        return chatService;
    }
}
