package Service;

public class GasStation implements Refuelable {
    private Statistics stats;

    public GasStation(Statistics stats) {
        this.stats = stats;
    }

    @Override
    public void refuel(int carId) {
        System.out.println("Refueling gas car:  " + carId);
        stats.addGasCarsRefueled();
    }
}
