package de.uniks.ws2425.minirpg.service;

import de.uniks.ws2425.minirpg.model.*;
import de.uniks.ws2425.minirpg.utils.Constants;
import de.uniks.ws2425.minirpg.utils.PredefinedObjects;

import java.util.Random;

public class GameService {
    private final Random random = new Random();
    private final PredefinedObjects predefinedObjects = new PredefinedObjects();

    private Game initGame(Hero hero) {
        Game game = new Game()
                .setName("Crypt of the Black Paladin")
                .setHero(hero);

        int enemyCount = random.nextInt(1,6);

        for (int i = 0; i <= enemyCount; i++) {
            Enemy enemy = predefinedObjects.getEnemy(i);

            if (i == 0) {
                enemy.setOpponent(game.getHero());
                enemy.setStance(getRandomStance());
            } else {
                enemy.setPrevious(game.getEnemies().get(i - 1));
            }

            enemy.setGame(game);
        }

        return game;
    }

    public Game initGame(Hero hero, int seed) {
        random.setSeed(seed);
        return initGame(hero);
    }

    public void heroStatUpdate(HeroStat heroStat) {
        if (heroStat.getCost() > heroStat.getHero().getCoins()) {
            return;
        }

        heroStat.getHero().setCoins(heroStat.getHero().getCoins() - heroStat.getCost());

        heroStat.setLevel(heroStat.getLevel() + 1);
        heroStat.setValue((int) Math.round(heroStat.getValue() * Constants.UPGRADE_MODIFIER));
        heroStat.setCost((int) Math.round(heroStat.getCost() * Constants.UPGRADE_MODIFIER));
    }

    public void heroEngagesFight(Enemy enemy, Hero hero, String heroAction) {
        HeroStat attackStat = hero.getStats().stream().filter(stat -> stat instanceof AttackStat).findFirst().get();
        HeroStat defenseStat = hero.getStats().stream().filter(stat -> stat instanceof DefenseStat).findFirst().get();

        int damage;

        if (heroAction.equals(Constants.ATTACKING)) {
            if (enemy.getStance().equals(Constants.ATTACKING)) {
                // Both Attacking
                enemy.setCurrentLP(enemy.getCurrentLP() - attackStat.getValue());

                if (enemy.getCurrentLP() > 0) {
                    hero.setCurrentLP(hero.getCurrentLP() - enemy.getAttackValue());
                }

            } else {
                // Hero Attacking, Enemy Defending
                damage = attackStat.getValue() - enemy.getDefenseValue();

                if (damage < 0) {
                    damage = 0;
                }

                enemy.setCurrentLP(enemy.getCurrentLP() - damage);
            }
        } else {
            if (enemy.getStance().equals(Constants.ATTACKING)) {
                // Hero Defending, Enemy Attacking
                damage = enemy.getAttackValue() - defenseStat.getValue();

                if (damage < 0) {
                    damage = 0;
                }

                hero.setCurrentLP(hero.getCurrentLP() - damage);
            }
            // Both Defending, nothing happens
        }

        if (hero.getCurrentLP() < 0) {
            hero.setCurrentLP(0);
        }

        if (enemy.getCurrentLP() < 0) {
            enemy.setCurrentLP(0);
        }
    }

    public void evaluateFight(Enemy enemy, Hero hero) {
        if (enemy.getCurrentLP() > 0) {
            enemy.setStance(getRandomStance());
            return;
        }

        hero.setCoins(hero.getCoins() + enemy.getCoins());

        if (!hero.isHardMode()) {
            hero.setCurrentLP(hero.getMaxLP());
        }

        if (enemy.getNext() == null) {
            hero.setCurrentLP(hero.getMaxLP());
            hero.setOpponent(null);
            return;
        }

        hero.setOpponent(enemy.getNext().setStance(getRandomStance()));
    }

    private String getRandomStance() {
        return random.nextInt() % 2 == 0 ? Constants.ATTACKING : Constants.DEFENDING;
    }
}
