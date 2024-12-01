package Tests;

import Service.CarStation;
import Service.CarProcessingException;
import Service.Scheduler;
import Service.Semaphore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SchedulerTest {
    private CarStation carStation;
    private Semaphore semaphore;
    private Scheduler scheduler;

    @BeforeEach
    public void setUp() {
        carStation = mock(CarStation.class);
        semaphore = mock(Semaphore.class);
        scheduler = new Scheduler(carStation, semaphore);
    }

    @Test
    public void testAddCarsFromQueue_Success() throws InterruptedException, IOException, CarProcessingException {
        // Arrange
        File queueDir = new File("queue");
        queueDir.mkdir();
        File testCarFile = new File(queueDir, "Car1.json");
        Files.write(Paths.get(testCarFile.getPath()), "{\"id\": 1, \"type\": \"ELECTRIC\"}".getBytes());

        doNothing().when(semaphore).acquire();
        doNothing().when(semaphore).release();

        // Act
        scheduler.startScheduling(1, 1);
        TimeUnit.SECONDS.sleep(2);
        scheduler.stopScheduling();

        // Assert
        verify(semaphore, atLeastOnce()).acquire();
        verify(carStation, atLeastOnce()).addCar(any(CarStation.Car.class));

        // Clean up
        Files.deleteIfExists(Paths.get(testCarFile.getPath()));
    }

    @Test
    public void testServeCarsFromQueue_Success() throws InterruptedException {
        // Arrange
        doNothing().when(semaphore).acquire();
        doNothing().when(semaphore).release();

        // Act
        scheduler.startScheduling(1, 1);
        TimeUnit.SECONDS.sleep(2);
        scheduler.stopScheduling();

        // Assert
        verify(semaphore, atLeastOnce()).acquire();
        verify(carStation, atLeastOnce()).serveCars();
    }

    @Test
    public void testAddCarsFromQueue_MalformedJson() throws InterruptedException, IOException, CarProcessingException {
        // Arrange
        File queueDir = new File("queue");
        queueDir.mkdir();
        File malformedCarFile = new File(queueDir, "Car1.json");
        Files.write(Paths.get(malformedCarFile.getPath()), "{\"id\": " /* Incomplete JSON */.getBytes());

        doNothing().when(semaphore).acquire();
        doNothing().when(semaphore).release();

        // Act
        scheduler.startScheduling(1, 1);
        TimeUnit.SECONDS.sleep(2);
        scheduler.stopScheduling();

        // Assert
        verify(semaphore, atLeastOnce()).acquire();
        verify(carStation, never()).addCar(any(CarStation.Car.class));

        // Clean up
        Files.deleteIfExists(Paths.get(malformedCarFile.getPath()));
    }

    @Test
    public void testEmptyQueueHandling() throws InterruptedException {
        // Arrange
        doNothing().when(semaphore).acquire();
        doNothing().when(semaphore).release();

        // Act
        scheduler.startScheduling(1, 1);
        TimeUnit.SECONDS.sleep(2);
        scheduler.stopScheduling();

        // Assert
        verify(semaphore, atLeastOnce()).acquire();
        verify(carStation, never()).addCar(any(CarStation.Car.class));
    }
}
