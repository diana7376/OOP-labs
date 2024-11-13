package oop.practice.lab0;

import java.util.ArrayList;
import java.util.List;

public class CreatureClassifier {

    public static void classifyCreature(Creature creature, Universe starWars, Universe marvel, Universe hitchhikers, Universe rings) {
        int starWarsScore = 0;
        int marvelScore = 0;
        int hitchhikersScore = 0;
        int ringsScore = 0;

        if (creature.isHumanoid() != null) {
            if (creature.isHumanoid()) {
                marvelScore++;
                hitchhikersScore++;
                ringsScore++;
            } else {
                starWarsScore++;
            }
        }

        String planet = creature.getPlanet();
        if (planet != null) {
            switch (planet) {
                case "Earth": marvelScore += 10; ringsScore += 10; break;
                case "Asgard": marvelScore += 10; break;
                case "Betelgeuse": hitchhikersScore += 10; break;
                case "Vogsphere": hitchhikersScore += 10; break;
                case "Kashyyyk": starWarsScore += 10; break;
                case "Endor": starWarsScore += 10; break;
            }
        }

        Integer age = creature.getAge();
        if (age != null) {
            if (age <= 200) {
                marvelScore++; hitchhikersScore++; starWarsScore++; ringsScore++;
            } else if (age > 200 && age < 500) {
                ringsScore++; starWarsScore++; marvelScore++;
            } else if (age >= 500 && age < 5000) {
                marvelScore++;
            } else if (age > 5000) {
                ringsScore++;
            }
        }

        List<String> traits = creature.getTraits();
        if (traits != null && !traits.isEmpty()) {
            if (traits.contains("HAIRY")) starWarsScore++;
            if (traits.contains("TALL")) marvelScore++;
            if (traits.contains("SHORT")) starWarsScore++;
            if (traits.contains("BLONDE")) marvelScore++;
            if (traits.contains("EXTRA_ARMS")) hitchhikersScore++;
            if (traits.contains("GREEN")) hitchhikersScore++;
            if (traits.contains("BULKY")) { hitchhikersScore++; ringsScore++; }
            if (traits.contains("POINTY_EARS")) ringsScore++;
        }

        int maxScore = Math.max(Math.max(starWarsScore, marvelScore), Math.max(hitchhikersScore, ringsScore));
        List<String> topUniverses = new ArrayList<>();
        if (starWarsScore == maxScore) topUniverses.add("Star Wars");
        if (marvelScore == maxScore) topUniverses.add("Marvel");
        if (hitchhikersScore == maxScore) topUniverses.add("Hitchhiker's");
        if (ringsScore == maxScore) topUniverses.add("Lord of the Rings");

        if (topUniverses.size() > 1) {
            applyTieBreaker(creature, topUniverses, starWars, marvel, hitchhikers, rings);
        } else {
            assignToUniverse(creature, topUniverses.get(0), starWars, marvel, hitchhikers, rings);
        }
    }

    private static void assignToUniverse(Creature creature, String universeName, Universe starWars, Universe marvel, Universe hitchhikers, Universe rings) {
        switch (universeName) {
            case "Star Wars": starWars.getIndividuals().add(creature); break;
            case "Marvel": marvel.getIndividuals().add(creature); break;
            case "Hitchhiker's": hitchhikers.getIndividuals().add(creature); break;
            case "Lord of the Rings": rings.getIndividuals().add(creature); break;
        }
    }

    private static void applyTieBreaker(Creature creature, List<String> topUniverses, Universe starWars, Universe marvel, Universe hitchhikers, Universe rings) {
        if (creature.isHumanoid() && creature.getTraits().contains("BULKY") && topUniverses.contains("Lord of the Rings")) {
            rings.getIndividuals().add(creature);
        } else if (creature.getTraits().contains("GREEN") && creature.getTraits().contains("BULKY") && topUniverses.contains("Hitchhiker's")) {
            hitchhikers.getIndividuals().add(creature);
        } else {
            assignToUniverse(creature, topUniverses.get(0), starWars, marvel, hitchhikers, rings);
        }
    }
}
