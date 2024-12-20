package de.uniks.ws2425.minirpg.controller;

import de.uniks.ws2425.minirpg.App;
import de.uniks.ws2425.minirpg.Main;
import de.uniks.ws2425.minirpg.controller.subcontroller.SavedHeroViewSubController;
import de.uniks.ws2425.minirpg.model.AttackStat;
import de.uniks.ws2425.minirpg.model.DefenseStat;
import de.uniks.ws2425.minirpg.model.Game;
import de.uniks.ws2425.minirpg.model.Hero;
import de.uniks.ws2425.minirpg.service.GameService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetupController implements Controller {
    private final App app;
    private int seed = 0;

    private TextField heroNameField;
    private CheckBox hardModeButton;

    private final List<Controller> subviewController = new ArrayList<>();

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

        Button createAndStartButton = (Button) parent.lookup("#createAndStartButton");

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

        VBox savedHeroBox = (VBox) parent.lookup("#savedHeroBox");

        List<Hero> savedHeros = new ArrayList<>();
        savedHeros.add(new Hero()
                .setName("Hero 1")
                .setCurrentLP(100)
                .setMaxLP(100)
                .setCoins(12)
                .setHardMode(false)
                .withStats(
                        new AttackStat()
                                .setLevel(15)
                                .setValue(78)
                                .setCost(90),
                        new DefenseStat()
                                .setLevel(1)
                                .setValue(5)
                                .setCost(5)
                ));
        savedHeros.add(new Hero()
                .setName("Hero 2")
                .setCurrentLP(100)
                .setMaxLP(100)
                .setCoins(5000)
                .setHardMode(true)
                .withStats(
                        new AttackStat()
                                .setLevel(1)
                                .setValue(7)
                                .setCost(5),
                        new DefenseStat()
                                .setLevel(42)
                                .setValue(112)
                                .setCost(200)
                ));

        for (Hero savedHero : savedHeros) {
            SavedHeroViewSubController savedHeroViewSubController = new SavedHeroViewSubController(app, savedHero);
            Parent savedHeroView = savedHeroViewSubController.render();
            subviewController.add(savedHeroViewSubController);
            savedHeroBox.getChildren().add(savedHeroView);
        }

        return parent;
    }

    @Override
    public void destroy() {
        subviewController.forEach(Controller::destroy);
    }
}
