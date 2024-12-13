package de.uniks.ws2425.minirpg.controller;

import de.uniks.ws2425.minirpg.App;
import de.uniks.ws2425.minirpg.Main;
import de.uniks.ws2425.minirpg.model.AttackStat;
import de.uniks.ws2425.minirpg.model.DefenseStat;
import de.uniks.ws2425.minirpg.model.Game;
import de.uniks.ws2425.minirpg.model.Hero;
import de.uniks.ws2425.minirpg.service.GameService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Objects;

public class SetupController implements Controller {
    private final App app;
    private int seed = 0;

    private Button createAndStartButton;
    private TextField heroNameField;
    private CheckBox hardModeButton;

    private Label heroNameLabel;
    private Label hardModeLabel;
    private Label lpLabel;
    private Label atkLevelLabel;
    private Label defLevelLabel;
    private Label coinsLabel;
    private Button startButton;


    public SetupController(App app) {
        this.app = app;
    }

    public SetupController(App app, int seed) {
        this.app = app;
        this.seed = seed;
    }

    @Override
    public String getTitle() {
        return "MiniRPG - Setup";
    }

    @Override
    public void init() {
    }

    @Override
    public Parent render() throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("view/Setup.fxml")));

        heroNameField = (TextField) parent.lookup("#heroNameField");
        hardModeButton = (CheckBox) parent.lookup("#hardModeButton");

        createAndStartButton = (Button) parent.lookup("#createAndStartButton");

        createAndStartButton.setOnAction(e -> {
            String heroName = heroNameField.getText();
            boolean hardMode = hardModeButton.isSelected();

            Hero hero = new Hero()
                    .setName(heroName)
                    .setCurrentLP(100)
                    .setMaxLP(100)
                    .setCoins(10)
                    .setHardMode(hardMode)
                    .withStats(
                            new AttackStat()
                                    .setLevel(1)
                                    .setValue(7)
                                    .setCost(5),
                            new DefenseStat()
                                    .setLevel(1)
                                    .setValue(5)
                                    .setCost(5)
                    );

            Game game;

            if (seed == 0) {
                game = new GameService().initGame(hero);
            } else {
                game = new GameService().initGame(hero, seed);
            }

            app.show(new InGameController(app, game));
        });

        heroNameLabel = (Label) parent.lookup("#heroNameLabel");
        hardModeLabel = (Label) parent.lookup("#hardModeLabel");
        lpLabel = (Label) parent.lookup("#lpLabel");
        atkLevelLabel = (Label) parent.lookup("#atkLevelLabel");
        defLevelLabel = (Label) parent.lookup("#defLevelLabel");
        coinsLabel = (Label) parent.lookup("#coinsLabel");
        startButton = (Button) parent.lookup("#startButton");

        return parent;
    }

    @Override
    public void destroy() {
    }
}
