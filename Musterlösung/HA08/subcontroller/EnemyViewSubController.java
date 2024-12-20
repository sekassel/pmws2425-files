package de.uniks.ws2425.minirpg.controller.subcontroller;

import de.uniks.ws2425.minirpg.App;
import de.uniks.ws2425.minirpg.Main;
import de.uniks.ws2425.minirpg.controller.Controller;
import de.uniks.ws2425.minirpg.controller.SetupController;
import de.uniks.ws2425.minirpg.model.Enemy;
import de.uniks.ws2425.minirpg.model.Game;
import de.uniks.ws2425.minirpg.model.Hero;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Objects;

public class EnemyViewSubController implements Controller {
    private final App app;
    private final Game game;
    private PropertyChangeListener currentLPListener;
    private PropertyChangeListener stanceListener;

    private Enemy currentEnemy;

    private Label currentLpMonsterLabel;
    private Label maxLpMonsterLabel;
    private Label monsterNameLabel;
    private Label monsterStanceLabel;

    public EnemyViewSubController(App app, Game game) {
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
        Parent parent = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("view/subview/EnemyView.fxml")));

        currentEnemy = game.getHero().getOpponent();

        currentLpMonsterLabel = (Label) parent.lookup("#currentLpMonsterLabel");
        maxLpMonsterLabel = (Label) parent.lookup("#maxLpMonsterLabel");
        monsterNameLabel = (Label) parent.lookup("#monsterNameLabel");
        monsterStanceLabel = (Label) parent.lookup("#monsterStanceLabel");

        currentLPListener = evt -> currentLpMonsterLabel.setText(String.valueOf(currentEnemy.getCurrentLP()));
        stanceListener = evt -> monsterStanceLabel.setText(String.valueOf(currentEnemy.getStance()));

        updateView();

        game.getHero().listeners().addPropertyChangeListener(Hero.PROPERTY_OPPONENT, evt -> {
            currentEnemy.listeners().removePropertyChangeListener(Enemy.PROPERTY_CURRENT_LP, currentLPListener);
            currentEnemy.listeners().removePropertyChangeListener(Enemy.PROPERTY_STANCE, stanceListener);

            currentEnemy = game.getHero().getOpponent();

            if (currentEnemy == null) {
                app.show(new SetupController(app));
                return;
            }

            updateView();
        });

        return parent;
    }

    private void updateView() {
        currentLpMonsterLabel.setText(String.valueOf(currentEnemy.getCurrentLP()));

        maxLpMonsterLabel.setText(String.valueOf(currentEnemy.getMaxLP()));

        monsterNameLabel.setText(String.valueOf(currentEnemy.getName()));

        monsterStanceLabel.setText(String.valueOf(currentEnemy.getStance()));

        currentEnemy.listeners().addPropertyChangeListener(Enemy.PROPERTY_CURRENT_LP, currentLPListener);
        currentEnemy.listeners().addPropertyChangeListener(Enemy.PROPERTY_STANCE, stanceListener);
    }

    @Override
    public void destroy() {
        if (currentEnemy == null) {
            return;
        }

        currentEnemy.listeners().removePropertyChangeListener(Enemy.PROPERTY_CURRENT_LP, currentLPListener);
        currentEnemy.listeners().removePropertyChangeListener(Enemy.PROPERTY_STANCE, stanceListener);
    }
}
