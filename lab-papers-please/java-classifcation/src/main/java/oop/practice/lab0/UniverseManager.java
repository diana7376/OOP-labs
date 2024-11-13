package oop.practice.lab0;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UniverseManager {

    public List<Universe> initializeUniverses() {
        Universe starWars = new Universe("Star Wars", new ArrayList<>());
        Universe hitchhikers = new Universe("Hitchhiker's", new ArrayList<>());
        Universe marvel = new Universe("Marvel", new ArrayList<>());
        Universe rings = new Universe("Lord of the Rings", new ArrayList<>());

        List<Universe> universes = new ArrayList<>();
        universes.add(starWars);
        universes.add(hitchhikers);
        universes.add(marvel);
        universes.add(rings);

        return universes;
    }

    public void saveUniverses(List<Universe> universes, String outputPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        for (Universe universe : universes) {
            mapper.writeValue(new File(outputPath + "/" + universe.name() + ".json"), universe);
        }
    }
}
