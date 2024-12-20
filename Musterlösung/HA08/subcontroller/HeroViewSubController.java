package de.uniks.ws2425.minirpg.controller.subcontroller;

import de.uniks.ws2425.minirpg.App;
import de.uniks.ws2425.minirpg.Main;
import de.uniks.ws2425.minirpg.controller.Controller;
import de.uniks.ws2425.minirpg.controller.SetupController;
import de.uniks.ws2425.minirpg.model.Game;
import de.uniks.ws2425.minirpg.model.Hero;
import de.uniks.ws2425.minirpg.service.GameService;
import de.uniks.ws2425.minirpg.utils.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Objects;

public class HeroViewSubController implements Controller {
    private final Game game;
    private final App app;
    private final GameService gameService = new GameService();
    private PropertyChangeListener currentLPListener;
    private PropertyChangeListener coinsListener;

    public HeroViewSubController(App app, Game game) {
        this.app = app;
        this.game = game;
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public void init() {

    }

    @Override
    public Parent render() throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("view/subview/HeroView.fxml")));

        Button attackButton = (Button) parent.lookup("#attackButton");
        attackButton.setOnAction(event -> {
            gameService.heroEngagesFight(game.getHero().getOpponent(), game.getHero(), Constants.ATTACKING);
            gameService.evaluateFight(game.getHero().getOpponent(), game.getHero());
        });

        Button defenseButton = (Button) parent.lookup("#defenseButton");
        defenseButton.setOnAction(event -> {
            gameService.heroEngagesFight(game.getHero().getOpponent(), game.getHero(), Constants.DEFENDING);
            gameService.evaluateFight(game.getHero().getOpponent(), game.getHero());
        });

        Label heroNameLabel = (Label) parent.lookup("#heroNameLabel");
        heroNameLabel.setText(game.getHero().getName());

        Label heroCoinsLabel = (Label) parent.lookup("#heroCoinsLabel");
        heroCoinsLabel.setText(String.valueOf(game.getHero().getCoins()));

        Label currentLpHeroLabel = (Label) parent.lookup("#currentLpHeroLabel");
        currentLpHeroLabel.setText(String.valueOf(game.getHero().getCurrentLP()));

        Label maxLpHeroLabel = (Label) parent.lookup("#maxLpHeroLabel");
        maxLpHeroLabel.setText(String.valueOf(game.getHero().getMaxLP()));

        currentLPListener = evt -> {
            if (game.getHero().getCurrentLP() <= 0) {
                app.show(new SetupController(app));
            }

            currentLpHeroLabel.setText(String.valueOf(game.getHero().getCurrentLP()));
        };
        coinsListener = evt -> heroCoinsLabel.setText(String.valueOf(game.getHero().getCoins()));

        game.getHero().listeners().addPropertyChangeListener(Hero.PROPERTY_CURRENT_LP, currentLPListener);
        game.getHero().listeners().addPropertyChangeListener(Hero.PROPERTY_COINS, coinsListener);

        return parent;
    }

    @Override
    public void destroy() {
        game.getHero().listeners().removePropertyChangeListener(Hero.PROPERTY_CURRENT_LP, currentLPListener);
        game.getHero().listeners().removePropertyChangeListener(Hero.PROPERTY_COINS, coinsListener);
    }
}
