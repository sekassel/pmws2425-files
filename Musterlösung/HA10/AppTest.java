package de.uniks.pmws2425.nopm;

import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.List;

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
    void viewChangeTest() {
        Assertions.assertEquals("NoPM - Login", stage.getTitle());

        clickOn("#nicknameField").write("Guido van Rossum");
        clickOn("#loginButton");

        Assertions.assertEquals("NoPM - Chat", stage.getTitle());

        Assertions.assertEquals(2, lookup("#chatTabPane").queryAs(javafx.scene.control.TabPane.class).getTabs().size());

        VBox messageBox = lookup("#messageBox").query();
        lookup("#messageBox").queryAs(javafx.scene.layout.VBox.class);

        Assertions.assertEquals(0, messageBox.getChildren().size());

        clickOn("#messageField").write("Now this i'd like to call a party.");
        clickOn("#sendButton");

        Assertions.assertEquals("", lookup("#messageField").queryAs(javafx.scene.control.TextField.class).getText());

        clickOn("#messageField").write("Splendid.");

        List<Tab> tabs = lookup("#chatTabPane").queryAs(javafx.scene.control.TabPane.class).getTabs();

        for (Tab tab : tabs) {
            if (tab.getText().equals("Hello there...")) {
                VBox messageBoxes = (VBox) tab.getContent().lookup("#messageBox");
                Assertions.assertEquals(1, messageBoxes.getChildren().size());
                break;
            }
        }

        clickOn("#logoutButton");

        Assertions.assertEquals("NoPM - Login", stage.getTitle());

    }
}
