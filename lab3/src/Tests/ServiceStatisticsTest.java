package Tests;

import Service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ServiceStatisticsTest {

    private Statistics stats;
    private Dineable peopleDinner;
    private Dineable robotDinner;
    private Refuelable electricStation;
    private Refuelable gasStation;

    @BeforeEach
    public void setUp() {
        stats = new Statistics();
        peopleDinner = new PeopleDinner(stats);
        robotDinner = new RobotDinner(stats);
        electricStation = new ElectricStation(stats);
        gasStation = new GasStation(stats);
    }

    @Test
    public void testPeopleDinnerServed() {
        peopleDinner.serveDinner(1);
        peopleDinner.serveDinner(2);
        assertEquals(2, stats.getPeopleServed(), "People dinner count should be 2");
    }

    @Test
    public void testRobotDinnerServed() {
        robotDinner.serveDinner(3);
        assertEquals(1, stats.getRobotsServed(), "Robot dinner count should be 1");
    }

    @Test
    public void testElectricStationRefueling() {
        electricStation.refuel(4);
        electricStation.refuel(5);
        assertEquals(2, stats.getElectricCarsRefueled(), "Electric car refueling count should be 2");
    }

    @Test
    public void testGasStationRefueling() {
        gasStation.refuel(6);
        assertEquals(1, stats.getGasCarsRefueled(), "Gas car refueling count should be 1");
    }

    @Test
    public void testMultipleElectricStationsSharingStats() {
        Refuelable anotherElectricStation = new ElectricStation(stats);
        electricStation.refuel(7);
        anotherElectricStation.refuel(8);
        assertEquals(2, stats.getElectricCarsRefueled(), "Electric car refueling count should be 2 across multiple stations");
    }

    @Test
    public void testNoDiningForSomeCars() {
        gasStation.refuel(9);
        electricStation.refuel(10);
        assertEquals(0, stats.getPeopleServed(), "People dinner count should be 0");
        assertEquals(0, stats.getRobotsServed(), "Robot dinner count should be 0");
        assertEquals(1, stats.getGasCarsRefueled(), "Gas car refueling count should be 1");
        assertEquals(1, stats.getElectricCarsRefueled(), "Electric car refueling count should be 1");
    }
}
