package Tests;

import Service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Queue;
import java.util.LinkedList;

public class CarStationTest {

    private Statistics stats;
    private Dineable diningService;
    private Refuelable refuelingService;
    private CarStation carStation;

    @BeforeEach
    public void setUp() {
        stats = new Statistics();
        diningService = new Dineable() {
            @Override
            public void serveDinner(int carId) {
                System.out.println("Serving dinner to car " + carId);
            }
        };
        refuelingService = new Refuelable() {
            @Override
            public void refuel(int carId) {
                System.out.println("Refueling car " + carId);
            }
        };
        carStation = new CarStation(diningService, refuelingService, stats);
    }

    @Test
    public void testAddCarAndServeCars() {
        CarStation.Car car1 = new CarStation.Car(1, true, false, true, 50);
        CarStation.Car car2 = new CarStation.Car(2, false, true, false, 30);
        CarStation.Car car3 = new CarStation.Car(3, true, true, true, 40);

        carStation.addCar(car1);
        carStation.addCar(car2);
        carStation.addCar(car3);

        carStation.serveCars();

        assertEquals(3, carStation.getTotalCarsServed(), "Total cars served should be 3");
        assertEquals(120, carStation.getTotalConsumption(), "Total consumption should be 120");
        assertEquals(1, stats.getPeopleServed(), "Total people served should be 1");
        assertEquals(1, stats.getRobotsServed(), "Total robots served should be 1");
        assertEquals(2, stats.getElectricCarsRefueled(), "Total electric cars refueled should be 2");
        assertEquals(1, stats.getGasCarsRefueled(), "Total gas cars refueled should be 1");
    }

    @Test
    public void testNoDiningCars() {
        CarStation.Car car1 = new CarStation.Car(4, false, false, true, 60);
        CarStation.Car car2 = new CarStation.Car(5, false, false, false, 20);

        carStation.addCar(car1);
        carStation.addCar(car2);

        carStation.serveCars();

        assertEquals(2, carStation.getTotalCarsServed(), "Total cars served should be 2");
        assertEquals(80, carStation.getTotalConsumption(), "Total consumption should be 80");
        assertEquals(0, stats.getPeopleServed(), "Total people served should be 0");
        assertEquals(0, stats.getRobotsServed(), "Total robots served should be 0");
        assertEquals(1, stats.getElectricCarsRefueled(), "Total electric cars refueled should be 1");
        assertEquals(1, stats.getGasCarsRefueled(), "Total gas cars refueled should be 1");
    }

    @Test
    public void testOnlyDiningCars() {
        CarStation.Car car1 = new CarStation.Car(6, true, false, false, 25);
        CarStation.Car car2 = new CarStation.Car(7, true, true, true, 35);

        carStation.addCar(car1);
        carStation.addCar(car2);

        carStation.serveCars();

        assertEquals(2, carStation.getTotalCarsServed(), "Total cars served should be 2");
        assertEquals(60, carStation.getTotalConsumption(), "Total consumption should be 60");
        assertEquals(1, stats.getPeopleServed(), "Total people served should be 1");
        assertEquals(1, stats.getRobotsServed(), "Total robots served should be 1");
        assertEquals(1, stats.getElectricCarsRefueled(), "Total electric cars refueled should be 1");
        assertEquals(1, stats.getGasCarsRefueled(), "Total gas cars refueled should be 1");
    }
}

