package Service;

public class Statistics {
    private int peopleServed = 0;
    private int robotsServed = 0;
    private int gasCarsRefueled = 0;
    private int electricCarsRefueled = 0;

    // Synchronized to prevent race conditions if accessed by multiple threads
    public synchronized void addPeopleServed() {
        peopleServed++;
        System.out.println("People served updated: " + peopleServed);
    }

    public synchronized void addRobotsServed() {
        robotsServed++;
        System.out.println("Robots served updated: " + robotsServed);
    }

    public synchronized void addGasCarsRefueled() {
        gasCarsRefueled++;
        System.out.println("Gas cars refueled updated: " + gasCarsRefueled);
    }

    public synchronized void addElectricCarsRefueled() {
        electricCarsRefueled++;
        System.out.println("Electric cars refueled updated: " + electricCarsRefueled);
    }

    // Getters (not synchronized since they're only reading)
    public int getPeopleServed() {
        return peopleServed;
    }

    public int getRobotsServed() {
        return robotsServed;
    }

    public int getGasCarsRefueled() {
        return gasCarsRefueled;
    }

    public int getElectricCarsRefueled() {
        return electricCarsRefueled;
    }
}
