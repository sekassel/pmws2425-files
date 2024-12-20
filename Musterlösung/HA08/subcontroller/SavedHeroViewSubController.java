package de.uniks.ws2425.minirpg.controller.subcontroller;

import de.uniks.ws2425.minirpg.App;
import de.uniks.ws2425.minirpg.Main;
import de.uniks.ws2425.minirpg.controller.Controller;
import de.uniks.ws2425.minirpg.controller.InGameController;
import de.uniks.ws2425.minirpg.model.AttackStat;
import de.uniks.ws2425.minirpg.model.DefenseStat;
import de.uniks.ws2425.minirpg.model.Hero;
import de.uniks.ws2425.minirpg.model.HeroStat;
import de.uniks.ws2425.minirpg.service.GameService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Objects;

public class SavedHeroViewSubController implements Controller {
    private final App app;
    private final Hero savedHero;

    public SavedHeroViewSubController(App app, Hero savedHero) {
        this.app = app;
        this.savedHero = savedHero;
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
        Parent parent = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("view/subview/SavedHeroView.fxml")));

        Label heroNameLabel = (Label) parent.lookup("#heroNameLabel");
        heroNameLabel.setText(savedHero.getName());

        Label hardModeLabel = (Label) parent.lookup("#heroModeLabel");
        hardModeLabel.setText(savedHero.isHardMode() ? "Hard Mode" : "Normal Mode");

        Label lpLabel = (Label) parent.lookup("#lpLabel");
        lpLabel.setText(String.valueOf(savedHero.getCurrentLP()));

        Label coinsLabel = (Label) parent.lookup("#coinsLabel");
        coinsLabel.setText(String.valueOf(savedHero.getCoins()));

        HeroStat attackStat = savedHero.getStats().stream().filter(stat -> stat instanceof AttackStat).findFirst().get();
        HeroStat defenseStat = savedHero.getStats().stream().filter(stat -> stat instanceof DefenseStat).findFirst().get();

        Label atkLevelLabel = (Label) parent.lookup("#atkLevelLabel");
        atkLevelLabel.setText(String.valueOf(attackStat.getLevel()));

        Label defLevelLabel = (Label) parent.lookup("#defLevelLabel");
        defLevelLabel.setText(String.valueOf(defenseStat.getLevel()));

        Button startButton = (Button) parent.lookup("#startButton");
        startButton.setOnAction(e -> app.show(new InGameController(app, new GameService().initGame(savedHero))));

        return parent;
    }

    @Override
    public void destroy() {

    }
}
