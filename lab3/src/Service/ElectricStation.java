package Service;

public class ElectricStation implements Refuelable {
    private Statistics stats;

    public ElectricStation(Statistics stats) {
        this.stats = stats;
    }

    @Override
    public void refuel(int carId) {
        System.out.println("Refueling electric car:  " + carId);
        stats.addElectricCarsRefueled();
    }
}


