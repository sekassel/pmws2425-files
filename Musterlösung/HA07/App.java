package de.uniks.ws2425.minirpg;

import de.uniks.ws2425.minirpg.controller.Controller;
import de.uniks.ws2425.minirpg.controller.SetupController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private Stage stage;
    private Controller controller;

    private int seed = 0;

    public App() {

    }

    public App(int seed) {
        this.seed = seed;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        primaryStage.setScene(new Scene(new Label("Loading...")));
        primaryStage.setTitle("MiniRPG");

        if (seed == 0) {
            show(new SetupController(this));
        } else {
            show(new SetupController(this, seed));
        }

        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> controller.destroy());
    }

    public void show(Controller controller) {
        controller.init();
        try {
            stage.getScene().setRoot(controller.render());
            stage.sizeToScene();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        if (this.controller != null) {
            this.controller.destroy();
        }
        this.controller = controller;
        stage.setTitle(controller.getTitle());
    }
}
