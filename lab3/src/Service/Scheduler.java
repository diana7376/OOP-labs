package Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private CarStation carStation;
    private ScheduledExecutorService scheduler;
    private Semaphore semaphore;

    public Scheduler(CarStation carStation, Semaphore semaphore) {
        this.carStation = carStation;
        this.semaphore = semaphore;
        this.scheduler = Executors.newScheduledThreadPool(2); // Two threads for adding and serving cars
    }

    public void startScheduling(int addCarInterval, int serveCarInterval) {
        scheduler.scheduleAtFixedRate(this::addCarsFromQueue, 0, addCarInterval, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::serveCarsFromQueue, 0, serveCarInterval, TimeUnit.SECONDS);
    }

    private void addCarsFromQueue() {
        try {
            semaphore.acquire();
            System.out.println("Semaphore acquired for adding cars.");

            File queueDir = new File("lab3/Resurse/queue");
            File[] files = queueDir.listFiles((dir, name) -> name.endsWith(".json"));

            if (files != null && files.length > 0) {
                System.out.println("Found " + files.length + " car file(s) in the queue.");

                for (File file : files) { // Process all cars in the queue
                    try {
                        System.out.println("Reading car file: " + file.getName());
                        String content = new String(Files.readAllBytes(Paths.get(file.getPath())));
                        carStation.addCarFromJsonString(content);

                        // Delete the file after processing
                        Files.delete(Paths.get(file.getPath()));
                        System.out.println("Car file deleted: " + file.getName());
                    } catch (JsonMappingException e) {
                        System.err.println("Error parsing JSON for file: " + file.getName() + " - " + e.getMessage());
                    } catch (IOException e) {
                        System.err.println("Error reading or deleting file: " + file.getName() + " - " + e.getMessage());
                    }
                }
            } else {
                System.out.println("No car files found in the queue.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted during semaphore acquire: " + e.getMessage());
        } finally {
            semaphore.release();
            System.out.println("Semaphore released after adding cars.");
        }
    }

    private void serveCarsFromQueue() {
        try {
            semaphore.acquire();
            System.out.println("Semaphore acquired for serving cars.");
            carStation.serveCars();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted during semaphore acquire: " + e.getMessage());
        } finally {
            semaphore.release();
            System.out.println("Semaphore released after serving cars.");
        }
    }

    public void stopScheduling() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}
