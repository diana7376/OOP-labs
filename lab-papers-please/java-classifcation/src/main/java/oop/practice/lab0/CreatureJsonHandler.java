package oop.practice.lab0;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreatureJsonHandler {

    public List<Creature> loadCreaturesFromJson(File inputFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(inputFile).get("data");
        List<Creature> creatures = new ArrayList<>();

        for (JsonNode entry : data) {
            int id = entry.get("id").asInt();
            boolean isHumanoid = entry.has("isHumanoid") && entry.get("isHumanoid").asBoolean();
            String planet = entry.has("planet") ? entry.get("planet").asText() : null;
            int age = entry.has("age") ? entry.get("age").asInt() : 0;

            List<String> traits = new ArrayList<>();
            if (entry.has("traits")) {
                for (JsonNode trait : entry.get("traits")) {
                    traits.add(trait.asText());
                }
            }
            creatures.add(new Creature(id, isHumanoid, planet, age, traits));
        }
        return creatures;
    }

    public void saveCreaturesToJson(List<Creature> creatures, File outputFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputFile, creatures);
    }
}
