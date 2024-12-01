package Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonProcessingException;
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
        this.scheduler = Executors.newScheduledThreadPool(2); // Two threads: one for adding cars, one for serving
    }

    // Method to start scheduling both tasks
    public void startScheduling(int addCarInterval, int serveCarInterval) {
        scheduler.scheduleAtFixedRate(this::addCarsFromQueue, 0, addCarInterval, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::serveCarsFromQueue, 0, serveCarInterval, TimeUnit.SECONDS);
    }

    // Task to add cars from JSON files in the queue directory
    private void addCarsFromQueue() {
        try {
            semaphore.acquire();
            File folder = new File("queue");
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

            if (files != null && files.length > 0) {
                for (File file : files) {
                    try {
                        String content = new String(Files.readAllBytes(Paths.get(file.getPath())));
                        ObjectMapper objectMapper = new ObjectMapper();
                        CarStation.Car car = objectMapper.readValue(content, CarStation.Car.class);
                        carStation.addCar(car);
                        System.out.println("Car added to station: " + car.getId());

                        // Delete the processed file
                        Files.delete(Paths.get(file.getPath()));
                    } catch (JsonMappingException e) {
                        System.err.println("Failed to map data from the car JSON file: " + file.getName() + " - " + e.getMessage());
                    } catch (IOException e) {
                        System.err.println("Failed to read or delete the car JSON file: " + file.getName() + " - " + e.getMessage());
                    }
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted while acquiring semaphore: " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    // Task to serve cars from the queue
    private void serveCarsFromQueue() {
        try {
            semaphore.acquire();
            carStation.serveCars();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted while acquiring semaphore: " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    // Method to stop the scheduler gracefully
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
