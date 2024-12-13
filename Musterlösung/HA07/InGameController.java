package de.uniks.ws2425.minirpg.controller;

import de.uniks.ws2425.minirpg.App;
import de.uniks.ws2425.minirpg.Main;
import de.uniks.ws2425.minirpg.model.AttackStat;
import de.uniks.ws2425.minirpg.model.Game;
import de.uniks.ws2425.minirpg.model.HeroStat;
import de.uniks.ws2425.minirpg.service.GameService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Objects;

public class InGameController implements Controller {
    private final App app;
    private final Game game;
    private final GameService gameService = new GameService();

    private Button leaveButton;

    private Label dungeonNameLabel;
    private Label currentRoundLabel;
    private Label maxRoundLabel;

    private Label heroStatLevelLabel;
    private Label heroStatValueLabel;
    private Label heroStatCostLabel;

    private Label currentLpMonsterLabel;
    private Label maxLpMonsterLabel;
    private Label monsterNameLabel;
    private Label monsterStanceLabel;

    private Button attackButton;
    private Button defenseButton;
    private Label heroNameLabel;
    private Label heroCoinsLabel;
    private Label currentLpHeroLabel;
    private Label maxLpHeroLabel;

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

        leaveButton = (Button) parent.lookup("#leaveButton");
        leaveButton.setOnAction(e -> app.show(new SetupController(app)));

        dungeonNameLabel = (Label) parent.lookup("#dungeonNameLabel");
        dungeonNameLabel.setText(game.getName());

        currentRoundLabel = (Label) parent.lookup("#currentRoundLabel");
        currentRoundLabel.setText("1");

        maxRoundLabel = (Label) parent.lookup("#maxRoundLabel");
        maxRoundLabel.setText(String.valueOf(game.getEnemies().size()));

        heroStatLevelLabel = (Label) parent.lookup("#heroStatLevelLabel");
        heroStatValueLabel = (Label) parent.lookup("#heroStatValueLabel");
        heroStatCostLabel = (Label) parent.lookup("#heroStatCostLabel");

        HeroStat attackStat = game.getHero().getStats().stream().filter(stat -> stat instanceof AttackStat).findFirst().get();
        heroStatLevelLabel.setText(String.valueOf(attackStat.getLevel()));
        heroStatValueLabel.setText(String.valueOf(attackStat.getValue()));
        heroStatCostLabel.setText(String.valueOf(attackStat.getCost()));

        currentLpMonsterLabel = (Label) parent.lookup("#currentLpMonsterLabel");
        currentLpMonsterLabel.setText(String.valueOf(game.getHero().getOpponent().getCurrentLP()));

        maxLpMonsterLabel = (Label) parent.lookup("#maxLpMonsterLabel");
        maxLpMonsterLabel.setText(String.valueOf(game.getHero().getOpponent().getMaxLP()));

        monsterNameLabel = (Label) parent.lookup("#monsterNameLabel");
        monsterNameLabel.setText(String.valueOf(game.getHero().getOpponent().getName()));

        monsterStanceLabel = (Label) parent.lookup("#monsterStanceLabel");
        monsterStanceLabel.setText(String.valueOf(game.getHero().getOpponent().getStance()));

        attackButton = (Button) parent.lookup("#attackButton");
        defenseButton = (Button) parent.lookup("#defenseButton");

        heroNameLabel = (Label) parent.lookup("#heroNameLabel");
        heroNameLabel.setText(game.getHero().getName());

        heroCoinsLabel = (Label) parent.lookup("#heroCoinsLabel");
        heroCoinsLabel.setText(String.valueOf(game.getHero().getCoins()));

        currentLpHeroLabel = (Label) parent.lookup("#currentLpHeroLabel");
        currentLpHeroLabel.setText(String.valueOf(game.getHero().getCurrentLP()));

        maxLpHeroLabel = (Label) parent.lookup("#maxLpHeroLabel");
        maxLpHeroLabel.setText(String.valueOf(game.getHero().getMaxLP()));

        return parent;
    }

    @Override
    public void destroy() {
    }
}
