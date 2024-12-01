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
        carStation = new CarStation(diningService, refuelingService, stats, 0);
    }

    @Test
    public void testAddCarAndServeCars() {
        CarStation.Car car1 = new CarStation.Car(1, "GAS", "PEOPLE", true, 50);
        CarStation.Car car2 = new CarStation.Car(2, "ELECTRIC", "ROBOTS", false, 30);
        CarStation.Car car3 = new CarStation.Car(3, "ELECTRIC", "ROBOTS", true, 40);

        carStation.addCarFromJsonString(car1.toString());
        carStation.addCarFromJsonString(car2.toString());
        carStation.addCarFromJsonString(car3.toString());

        carStation.serveCars();

        assertEquals(3, stats.getPeopleServed() + stats.getRobotsServed(), "Total cars served should be 3");
        assertEquals(120, car1.getConsumption() + car2.getConsumption() + car3.getConsumption(), "Total consumption should be 120");
        assertEquals(1, stats.getPeopleServed(), "Total people served should be 1");
        assertEquals(2, stats.getRobotsServed(), "Total robots served should be 2");
        assertEquals(2, stats.getElectricCarsRefueled(), "Total electric cars refueled should be 2");
        assertEquals(1, stats.getGasCarsRefueled(), "Total gas cars refueled should be 1");
    }

    @Test
    public void testNoDiningCars() {
        CarStation.Car car1 = new CarStation.Car(4, "ELECTRIC", "NONE", false, 60);
        CarStation.Car car2 = new CarStation.Car(5, "GAS", "NONE", false, 20);

        carStation.addCarFromJsonString(car1.toString());
        carStation.addCarFromJsonString(car2.toString());

        carStation.serveCars();

        assertEquals(2, stats.getPeopleServed() + stats.getRobotsServed(), "Total cars served should be 2");
        assertEquals(80, car1.getConsumption() + car2.getConsumption(), "Total consumption should be 80");
        assertEquals(0, stats.getPeopleServed(), "Total people served should be 0");
        assertEquals(0, stats.getRobotsServed(), "Total robots served should be 0");
        assertEquals(1, stats.getElectricCarsRefueled(), "Total electric cars refueled should be 1");
        assertEquals(1, stats.getGasCarsRefueled(), "Total gas cars refueled should be 1");
    }

    @Test
    public void testOnlyDiningCars() {
        CarStation.Car car1 = new CarStation.Car(6, "GAS", "PEOPLE", true, 25);
        CarStation.Car car2 = new CarStation.Car(7, "ELECTRIC", "ROBOTS", true, 35);

        carStation.addCarFromJsonString(car1.toString());
        carStation.addCarFromJsonString(car2.toString());

        carStation.serveCars();

        assertEquals(2, stats.getPeopleServed() + stats.getRobotsServed(), "Total cars served should be 2");
        assertEquals(60, car1.getConsumption() + car2.getConsumption(), "Total consumption should be 60");
        assertEquals(1, stats.getPeopleServed(), "Total people served should be 1");
        assertEquals(1, stats.getRobotsServed(), "Total robots served should be 1");
        assertEquals(1, stats.getElectricCarsRefueled(), "Total electric cars refueled should be 1");
        assertEquals(1, stats.getGasCarsRefueled(), "Total gas cars refueled should be 1");
    }
}
