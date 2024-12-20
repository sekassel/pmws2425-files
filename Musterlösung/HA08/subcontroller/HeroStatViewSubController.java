package de.uniks.ws2425.minirpg.controller.subcontroller;

import de.uniks.ws2425.minirpg.Main;
import de.uniks.ws2425.minirpg.controller.Controller;
import de.uniks.ws2425.minirpg.model.AttackStat;
import de.uniks.ws2425.minirpg.model.HeroStat;
import de.uniks.ws2425.minirpg.service.GameService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Objects;

public class HeroStatViewSubController implements Controller {
    private final HeroStat stat;
    private final GameService gameService = new GameService();
    private PropertyChangeListener levelListener;
    private PropertyChangeListener valueListener;
    private PropertyChangeListener costListener;

    private Label heroStatLevelLabel;
    private Label heroStatValueLabel;
    private Label heroStatCostLabel;

    public HeroStatViewSubController(HeroStat stat) {
        this.stat = stat;
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
        Parent parent = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("view/subview/HeroStatView.fxml")));

        Label heroStatTypeLabel = (Label) parent.lookup("#heroStatTypeLabel");
        heroStatTypeLabel.setText(stat instanceof AttackStat ? "Attack" : "Defense");

        heroStatLevelLabel = (Label) parent.lookup("#heroStatLevelLabel");
        heroStatLevelLabel.setText(String.valueOf(stat.getLevel()));

        heroStatValueLabel = (Label) parent.lookup("#heroStatValueLabel");
        heroStatValueLabel.setText(String.valueOf(stat.getValue()));

        heroStatCostLabel = (Label) parent.lookup("#heroStatCostLabel");
        heroStatCostLabel.setText(String.valueOf(stat.getCost()));

        Button lvlUpButton = (Button) parent.lookup("#lvlUpButton");
        lvlUpButton.setOnAction(event -> {
            gameService.heroStatUpdate(stat);
        });

        levelListener = evt -> {
            heroStatLevelLabel.setText(String.valueOf(stat.getLevel()));
        };

        valueListener = evt -> {
            heroStatValueLabel.setText(String.valueOf(stat.getValue()));
        };

        costListener = evt -> {
            heroStatCostLabel.setText(String.valueOf(stat.getCost()));
        };

        stat.listeners().addPropertyChangeListener(HeroStat.PROPERTY_LEVEL, levelListener);
        stat.listeners().addPropertyChangeListener(HeroStat.PROPERTY_VALUE, valueListener);
        stat.listeners().addPropertyChangeListener(HeroStat.PROPERTY_COST, costListener);

        return parent;
    }

    @Override
    public void destroy() {
        stat.listeners().removePropertyChangeListener(HeroStat.PROPERTY_LEVEL, levelListener);
        stat.listeners().removePropertyChangeListener(HeroStat.PROPERTY_VALUE, valueListener);
        stat.listeners().removePropertyChangeListener(HeroStat.PROPERTY_COST, costListener);
    }
}
