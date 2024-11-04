package oop.practice.lab0;

import java.util.List;

public record Universe(String name, List<Creature> individuals) {
    public List<Creature> getIndividuals() {
        return individuals;
    }
}
