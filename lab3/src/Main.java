import Service.*;
import Queue.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Create statistics object
        Statistics stats = new Statistics();

        // Create refueling and dining services
        Dineable peopleDinner = new PeopleDinner(stats);
        Dineable robotDinner = new RobotDinner(stats);
        Refuelable electricStation = new ElectricStation(stats);
        Refuelable gasStation = new GasStation(stats);

        // Choose a queue implementation for the car station
        LinkedQueue<CarStation.Car> carQueue = new LinkedQueue<CarStation.Car>();

        // Create car stations
        CarStation electricCarStation = new CarStation(robotDinner, electricStation, stats, 1);
        CarStation gasCarStation = new CarStation(peopleDinner, gasStation, stats, 1);

        // Create semaphore to control scheduling
        Semaphore semaphore = new Semaphore(1);

        // Create scheduler
        Scheduler scheduler = new Scheduler(electricCarStation, semaphore);

        // Start scheduling
        try {
            System.out.println("Starting car service application...");
            scheduler.startScheduling(5, 5);  // Add cars every 5 seconds, serve cars every 5 seconds

            // Keep the main thread running for a while to allow cars to be added and served
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Main thread interrupted: " + e.getMessage());
        } finally {
            // Stop scheduling and shutdown application
            scheduler.stopScheduling();
            System.out.println("Car service application stopped.");
        }

        // Display statistics in a detailed and readable format
        System.out.println("\n==================== Final Statistics ====================");
        System.out.printf("Electric Cars Refueled: %d\n", stats.getElectricCarsRefueled());
        System.out.printf("Gas Cars Refueled: %d\n", stats.getGasCarsRefueled());
        System.out.printf("People Served: %d\n", stats.getPeopleServed());
        System.out.printf("Robots Served: %d\n", stats.getRobotsServed());
        System.out.printf("Total Dining: %d\n", stats.getPeopleServed() + stats.getRobotsServed());
        System.out.printf("Total Not Dining: %d\n", stats.getGasCarsRefueled() + stats.getElectricCarsRefueled() - stats.getPeopleServed() - stats.getRobotsServed());
        System.out.printf("Total Consumption (Electric): %d\n", stats.getElectricCarsRefueled() * 25); // Example calculation
        System.out.printf("Total Consumption (Gas): %d\n", stats.getGasCarsRefueled() * 35); // Example calculation
        System.out.println("==========================================================");

        // Log each car served step-by-step for better traceability
        System.out.println("\n================= Detailed Car Service Log ===============");
        electricCarStation.getCarQueue().forEach(car -> {
            System.out.printf("Serving Car ID: %d | Type: %s | Passengers: %s | Dining: %s | Consumption: %d\n",
                    car.getId(), car.getType(), car.getPassengers(), car.isDining() ? "Yes" : "No", car.getConsumption());
        });
        System.out.println("==========================================================");
    }
}
