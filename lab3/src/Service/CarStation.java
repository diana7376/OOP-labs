package Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CarStation {
    private final Dineable diningService;
    private final Refuelable refuelingService;
    private final Statistics stats;
    private final Queue<Car> carQueue;
    private final int semaphorePermits;

    public CarStation(Dineable diningService, Refuelable refuelingService, Statistics stats, int semaphorePermits) {
        this.diningService = diningService;
        this.refuelingService = refuelingService;
        this.stats = stats;
        this.carQueue = new LinkedList<>();
        this.semaphorePermits = semaphorePermits;
    }

    public void addCarFromJsonString(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            int id = jsonNode.get("id").asInt();
            String type = jsonNode.get("type").asText();
            String passengers = jsonNode.get("passengers").asText();
            boolean isDining = jsonNode.get("isDining").asBoolean();
            int consumption = jsonNode.get("consumption").asInt();

            Car car = new Car(id, type, passengers, isDining, consumption);
            carQueue.add(car);
            System.out.println("Car added from JSON: " + car);
        } catch (IOException e) {
            System.err.println("Failed to add car from JSON: " + e.getMessage());
        }
    }

    public Queue<Car> getCarQueue() {
        return carQueue;
    }

    public void serveCars() {
        while (!carQueue.isEmpty()) {
            Car car = carQueue.poll();
            if (car != null) {
                System.out.println("Serving car: " + car);
                if (car.isDining) {
                    diningService.serveDinner(car.getId());
                } else {
                    refuelingService.refuel(car.getId());
                }
                stats.recordService(car.getId(), car.getType(), car.getPassengers(), car.getConsumption());
            }
        }
    }

    public static class Car {
        private int id;
        private String type;
        private String passengers;
        private boolean isDining;
        private int consumption;

        public Car(int id, String type, String passengers, boolean isDining, int consumption) {
            this.id = id;
            this.type = type;
            this.passengers = passengers;
            this.isDining = isDining;
            this.consumption = consumption;
        }

        // Getters and setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPassengers() {
            return passengers;
        }

        public void setPassengers(String passengers) {
            this.passengers = passengers;
        }

        public boolean isDining() {
            return isDining;
        }

        public void setDining(boolean dining) {
            isDining = dining;
        }

        public int getConsumption() {
            return consumption;
        }

        public void setConsumption(int consumption) {
            this.consumption = consumption;
        }

        @Override
        public String toString() {
            return "Car{" +
                    "id=" + id +
                    ", type='" + type + '\'' +
                    ", passengers='" + passengers + '\'' +
                    ", isDining=" + isDining +
                    ", consumption=" + consumption +
                    '}';
        }
    }
}
