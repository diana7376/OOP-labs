package Service;

public class PeopleDinner implements Dineable {
    private Statistics stats;

    public PeopleDinner(Statistics stats) {
        this.stats = stats;
    }

    @Override
    public void serveDinner(int carId) {
        System.out.println("Serving dinner to people in car:  " + carId);
        stats.addPeopleServed();
    }
}
