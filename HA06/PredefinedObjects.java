package de.uniks.ws2425.minirpg.utils;

import de.uniks.ws2425.minirpg.model.Enemy;
import de.uniks.ws2425.minirpg.model.Hero;

import java.util.Map;

public class PredefinedObjects {
    private final Map<Integer, Enemy> enemies = Map.of(
            0, new Enemy()
                    .setName("Goblin")
                    .setCurrentLP(20)
                    .setMaxLP(20)
                    .setAttackValue(5)
                    .setDefenseValue(5)
                    .setCoins(5),
            1, new Enemy()
                    .setName("Orc")
                    .setCurrentLP(30)
                    .setMaxLP(30)
                    .setAttackValue(10)
                    .setDefenseValue(10)
                    .setCoins(7),
            2, new Enemy()
                    .setName("Troll")
                    .setCurrentLP(40)
                    .setMaxLP(40)
                    .setAttackValue(15)
                    .setDefenseValue(15)
                    .setCoins(15),
            3, new Enemy()
                    .setName("Dragon")
                    .setCurrentLP(50)
                    .setMaxLP(50)
                    .setAttackValue(20)
                    .setDefenseValue(20)
                    .setCoins(23),
            4, new Enemy()
                    .setName("Skeleton")
                    .setCurrentLP(25)
                    .setMaxLP(25)
                    .setAttackValue(30)
                    .setDefenseValue(5)
                    .setCoins(25),
            5, new Enemy()
                    .setName("Slime")
                    .setCurrentLP(5)
                    .setMaxLP(5)
                    .setAttackValue(1)
                    .setDefenseValue(1)
                    .setCoins(100)
    );

    public Hero getHero() {
        // TODO

        return new Hero();
    }

    public Enemy getEnemy(int enemy) {
        return enemies.get(enemy);
    }
}
