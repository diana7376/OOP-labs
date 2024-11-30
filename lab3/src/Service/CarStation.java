package Service;

import java.util.LinkedList;
import java.util.Queue;

public class CarStation {
    private Dineable diningService;
    private Refuelable refuelingService;
    private Queue<Car> carQueue;
    private int totalCarsServed;
    private int totalConsumption;
    private Statistics stats;

    public CarStation(Dineable diningService, Refuelable refuelingService, Statistics stats) {
        this.diningService = diningService;
        this.refuelingService = refuelingService;
        this.carQueue = new LinkedList<>();
        this.totalCarsServed = 0;
        this.totalConsumption = 0;
        this.stats = stats;
    }

    // Method to add a car to the station queue
    public void addCar(Car car) {
        carQueue.add(car);
    }

    // Method to serve cars from the queue
    public void serveCars() {
        while (!carQueue.isEmpty()) {
            Car car = carQueue.poll();
            if (car.isDiningRequired()) {
                diningService.serveDinner(car.getId());
                if (car.isRobot()) {
                    System.out.println("Adding robot served for car ID: " + car.getId());
                    stats.addRobotsServed();
                } else {
                    System.out.println("Adding person served for car ID: " + car.getId());
                    stats.addPeopleServed();
                }
            }
            refuelingService.refuel(car.getId());
            if (car.isElectric()) {
                stats.addElectricCarsRefueled();
            } else {
                stats.addGasCarsRefueled();
            }
            totalCarsServed++;
            totalConsumption += car.getConsumption();
        }
    }

    // Getter for total cars served
    public int getTotalCarsServed() {
        return totalCarsServed;
    }

    // Getter for total consumption
    public int getTotalConsumption() {
        return totalConsumption;
    }

    // Car class for demonstration purposes
    public static class Car {
        private int id;
        private boolean diningRequired;
        private boolean isRobot;
        private boolean isElectric;
        private int consumption;

        public Car(int id, boolean diningRequired, boolean isRobot, boolean isElectric, int consumption) {
            this.id = id;
            this.diningRequired = diningRequired;
            this.isRobot = isRobot;
            this.isElectric = isElectric;
            this.consumption = consumption;
        }

        public int getId() {
            return id;
        }

        public boolean isDiningRequired() {
            return diningRequired;
        }

        public boolean isRobot() {
            return isRobot;
        }

        public boolean isElectric() {
            return isElectric;
        }

        public int getConsumption() {
            return consumption;
        }
    }
}
