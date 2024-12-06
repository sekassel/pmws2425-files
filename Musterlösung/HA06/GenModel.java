package de.uniks.ws2425.minirpg.model;

import org.fulib.builder.ClassModelDecorator;
import org.fulib.builder.ClassModelManager;
import org.fulib.builder.reflect.Link;

import java.util.List;

public class GenModel implements ClassModelDecorator {

    class Game {
        String name;

        @Link("game")
        List<Enemy> enemies;

        @Link("game")
        Hero hero;
    }

    class Enemy {
        String name;
        int currentLP;
        int maxLP;
        int coins;
        int attackValue;
        int defenseValue;
        String stance;

        @Link("enemies")
        Game game;

        @Link("opponent")
        Hero opponent;

        @Link("previous")
        Enemy next;

        @Link("next")
        Enemy previous;
    }

    class Hero {
        String name;
        int currentLP;
        int maxLP;
        int coins;
        boolean hardMode;

        @Link("hero")
        Game game;

        @Link("opponent")
        Enemy opponent;

        @Link("hero")
        List<HeroStat> stats;
    }

    class HeroStat {
        int level;
        int value;
        int cost;

        @Link("stats")
        Hero hero;
    }

    class AttackStat extends HeroStat {
    }

    class DefenseStat extends HeroStat {
    }

    @Override
    public void decorate(ClassModelManager mm) {
        mm.haveNestedClasses(GenModel.class);
    }
}
