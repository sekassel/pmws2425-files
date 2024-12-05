package de.uniks.ws2425.minirpg.controller;

import de.uniks.ws2425.minirpg.App;
import de.uniks.ws2425.minirpg.model.Game;
import de.uniks.ws2425.minirpg.service.GameService;
import javafx.scene.Parent;

import java.io.IOException;

public class InGameController implements Controller {
    private final App app;
    private final Game game;
    private final GameService gameService = new GameService();

    public InGameController(App app, Game game) {
        this.app = app;
        this.game = game;
    }

    @Override
    public String getTitle() {
        // TODO
        return null;
    }

    @Override
    public void init() {
    }

    @Override
    public Parent render() throws IOException {
        // TODO
        return null;
    }

    @Override
    public void destroy() {
    }
}
