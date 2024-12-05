package de.uniks.ws2425.minirpg;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class AppTest extends ApplicationTest {
    private App app;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        app = new App();
        app.start(stage);
    }

    @Test
    void changeViewTest() {
        // TODO
    }
}
