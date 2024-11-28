package Service;

public class Statistics {
    private int peopleServed = 0;
    private int robotsServed = 0;
    private int gasCarsRefueled = 0;
    private int electricCarsRefueled = 0;

    public void addPeopleServed() {
        peopleServed++;
    }

    public void addRobotsServed() {
        robotsServed++;
    }

    public void addGasCarsRefueled() {
        gasCarsRefueled++;
    }

    public void addElectricCarsRefueled() {
        electricCarsRefueled++;
    }

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
