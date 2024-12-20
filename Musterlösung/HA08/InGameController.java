package de.uniks.ws2425.minirpg.controller;

import de.uniks.ws2425.minirpg.App;
import de.uniks.ws2425.minirpg.Main;
import de.uniks.ws2425.minirpg.controller.subcontroller.EnemyViewSubController;
import de.uniks.ws2425.minirpg.controller.subcontroller.HeroStatViewSubController;
import de.uniks.ws2425.minirpg.controller.subcontroller.HeroViewSubController;
import de.uniks.ws2425.minirpg.model.Game;
import de.uniks.ws2425.minirpg.model.Hero;
import de.uniks.ws2425.minirpg.model.HeroStat;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InGameController implements Controller {
    private final App app;
    private final Game game;
    private final List<Controller> subviewController = new ArrayList<>();
    private PropertyChangeListener currentRoundListener;

    private Label currentRoundLabel;

    private int currentRound = 1;

    public InGameController(App app, Game game) {
        this.app = app;
        this.game = game;
    }

    @Override
    public String getTitle() {
        return "MiniRPG - InGame";
    }

    @Override
    public void init() {
    }

    @Override
    public Parent render() throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("view/InGame.fxml")));

        Button leaveButton = (Button) parent.lookup("#leaveButton");
        leaveButton.setOnAction(e -> app.show(new SetupController(app)));

        Label dungeonNameLabel = (Label) parent.lookup("#dungeonNameLabel");
        dungeonNameLabel.setText(game.getName());

        currentRoundLabel = (Label) parent.lookup("#currentRoundLabel");
        currentRoundLabel.setText("1");

        Label maxRoundLabel = (Label) parent.lookup("#maxRoundLabel");
        maxRoundLabel.setText(String.valueOf(game.getEnemies().size()));

        VBox heroStatBox = (VBox) parent.lookup("#heroStatBox");
        for (HeroStat stat : game.getHero().getStats()) {
            HeroStatViewSubController heroStatViewSubController = new HeroStatViewSubController(stat);
            subviewController.add(heroStatViewSubController);
            heroStatBox.getChildren().add(heroStatViewSubController.render());
        }

        HBox enemyView = (HBox) parent.lookup("#enemyBox");
        EnemyViewSubController enemyViewSubController = new EnemyViewSubController(app, game);
        subviewController.add(enemyViewSubController);
        enemyView.getChildren().add(enemyViewSubController.render());

        VBox heroView = (VBox) parent.lookup("#heroBox");
        HeroViewSubController heroViewSubController = new HeroViewSubController(app, game);
        subviewController.add(heroViewSubController);
        heroView.getChildren().add(heroViewSubController.render());

        currentRoundListener = evt -> {
            currentRound += 1;
            currentRoundLabel.setText(String.valueOf(currentRound));
        };

        game.getHero().listeners().addPropertyChangeListener(Hero.PROPERTY_OPPONENT, currentRoundListener);

        return parent;
    }

    @Override
    public void destroy() {
        subviewController.forEach(Controller::destroy);
        game.getHero().listeners().removePropertyChangeListener(Hero.PROPERTY_OPPONENT, currentRoundListener);
    }
}
