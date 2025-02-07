package de.uniks.pmws2425.nopm.controller;

import de.uniks.pmws2425.nopm.App;
import de.uniks.pmws2425.nopm.Main;
import de.uniks.pmws2425.nopm.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController implements Controller {
    private final App app;

    @FXML
    public TextField nicknameField;
    @FXML
    public Button loginButton;

    private Parent parent;

    public LoginController(App app) {
        this.app = app;
    }

    @Override
    public String getTitle() {
        return "NoPM - Login";
    }

    @Override
    public void init() {
        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/LoginView.fxml"));
        fxmlLoader.setControllerFactory(c -> this);
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Parent render() {
        loginButton.setOnAction(event -> {
            String nickname = nicknameField.getText();
            User user = app.getChatService().login(nickname);

            if (user == null) {
                System.err.println("Login for User '" + nickname + "' not possible");
                return;
            }

            app.show(new AppController(app));
        });

        return this.parent;
    }

    @Override
    public void destroy() {

    }
}
