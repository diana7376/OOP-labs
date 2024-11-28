package Service;

public class RobotDinner implements Dineable {
    private Statistics stats;

    public RobotDinner(Statistics stats) {
        this.stats = stats;
    }

    @Override
    public void serveDinner(int carId) {
        System.out.println("Serving dinner to robots in car:  " + carId);
        stats.addRobotsServed();
    }
}
