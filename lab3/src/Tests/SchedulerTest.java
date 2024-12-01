package Tests;

import Service.CarStation;
import Service.CarProcessingException;
import Service.Scheduler;
import Service.Semaphore;
import Service.Dineable;
import Service.Refuelable;
import Service.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SchedulerTest {
    private CarStation carStation;
    private Semaphore semaphore;
    private Scheduler scheduler;
    private Dineable diningService;
    private Refuelable refuelingService;
    private Statistics stats;

    @BeforeEach
    public void setUp() {
        diningService = new Dineable() {
            @Override
            public void serveDinner(int carId) {
                System.out.println("Serving dinner for car ID: " + carId);
            }
        };
        refuelingService = new Refuelable() {
            @Override
            public void refuel(int carId) {
                System.out.println("Refueling car ID: " + carId);
            }
        };
        stats = new Statistics();
        carStation = new CarStation(diningService, refuelingService, stats, 1);
        semaphore = new Semaphore(1); // Initialize with a single permit for simplicity
        scheduler = new Scheduler(carStation, semaphore);
        System.out.println("Setup complete.");
    }

    @Test
    public void testAddCarsFromQueue_Success() throws InterruptedException, IOException, CarProcessingException {
        // Run the Python script to generate car files
        ProcessBuilder pb = new ProcessBuilder("python", "Resurse/generator.py");
        pb.inheritIO();
        Process process = pb.start();
        try {
            int exitCode = process.waitFor();
            assertEquals(0, exitCode, "The generator script did not complete successfully.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Generator script was interrupted.");
        }

        // Arrange
        File queueDir = new File("Resurse/queue");
        if (!queueDir.exists() || !queueDir.isDirectory()) {
            boolean created = queueDir.mkdirs();
            assertTrue(created, "Failed to create queue directory.");
        }

        File[] carFiles = queueDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (carFiles == null || carFiles.length == 0) {
            System.out.println("No car files found in the queue directory. Please ensure files are generated correctly.");
            fail("No car files found in the queue directory.");
        }
        int carFileCount = carFiles.length;
        System.out.println("Number of car files found: " + carFileCount);

        // Act
        System.out.println("Starting scheduler...");
        scheduler.startScheduling(1, 1);
        TimeUnit.SECONDS.sleep(10); // Increase wait time to ensure all cars are processed
        scheduler.stopScheduling();
        System.out.println("Scheduler stopped.");

        // Assert
        System.out.println("Asserting car station state...");
        assertTrue(carStation.getCarQueue().isEmpty(), "Car station should be empty after adding and serving all cars.");
    }

    @Test
    public void testServeCarsFromQueue_Success() throws InterruptedException, IOException, CarProcessingException {
        // Arrange
        File queueDir = new File("lab3/Resurse/queue");
        if (queueDir.exists()) {
            File[] carFiles = queueDir.listFiles((dir, name) -> name.endsWith(".json"));
            if (carFiles != null) {
                for (File carFile : carFiles) {
                    List<String> lines = Files.readAllLines(carFile.toPath());
                    for (String line : lines) {
                        // Assuming each line is a valid JSON representation of a car
                        carStation.addCarFromJsonString(line);
                        System.out.println("Car added to station from JSON data.");
                    }
                }
            }
        }

        // Act
        System.out.println("Starting scheduler...");
        scheduler.startScheduling(1, 1);
        TimeUnit.SECONDS.sleep(10); // Give enough time for all cars to be processed
        scheduler.stopScheduling();
        System.out.println("Scheduler stopped.");

        // Assert
        System.out.println("Asserting car station state...");
        assertTrue(carStation.getCarQueue().isEmpty(), "Car station should be empty after serving all cars.");
    }


    @Test
    public void testAddCarsFromQueue_MalformedJson() throws InterruptedException, IOException, CarProcessingException {
        // Arrange
        File queueDir = new File("Resurse/queue");
        if (!queueDir.exists() || !queueDir.isDirectory()) {
            boolean created = queueDir.mkdirs();
            assertTrue(created, "Failed to create queue directory.");
        }
        for (int i = 1; i <= 5; i++) {
            File malformedCarFile = new File(queueDir, "CarMalformed" + i + ".json");
            Files.write(Paths.get(malformedCarFile.getPath()), String.format("{\"id\": %d}", i).getBytes()); // Correct JSON format but incomplete content
            System.out.println("Malformed car file created: " + malformedCarFile.getPath());
        }

        // Act
        System.out.println("Starting scheduler...");
        scheduler.startScheduling(1, 1);
        TimeUnit.SECONDS.sleep(10);
        scheduler.stopScheduling();
        System.out.println("Scheduler stopped.");

        // Assert
        System.out.println("Asserting car station state...");
        File[] malformedFiles = queueDir.listFiles((dir, name) -> name.startsWith("CarMalformed"));
        if (malformedFiles == null || malformedFiles.length == 0) {
            System.out.println("No malformed car files found in the queue directory.");
            fail("No malformed car files found in the queue directory.");
        }
        int malformedFileCount = malformedFiles.length;
        System.out.println("Number of malformed car files found: " + malformedFileCount);
        for (File file : malformedFiles) {
            assertFalse(carStation.getCarQueue().contains(file.getName()), "Car station should not contain malformed JSON files.");
        }
        assertTrue(carStation.getCarQueue().isEmpty(), "Car station should remain empty after processing malformed JSON.");

        // Clean up
        File[] allFiles = queueDir.listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                Files.deleteIfExists(Paths.get(file.getPath()));
                System.out.println("Malformed car file deleted: " + file.getPath());
            }
        }
    }

    @Test
    public void testEmptyQueueHandling() throws InterruptedException {
        // Act
        System.out.println("Starting scheduler with empty queue...");
        scheduler.startScheduling(1, 1);
        TimeUnit.SECONDS.sleep(2);
        scheduler.stopScheduling();
        System.out.println("Scheduler stopped.");

        // Assert
        System.out.println("Asserting car station state...");
        assertTrue(carStation.getCarQueue().isEmpty(), "Car station should be empty when no cars are added.");
    }
}
