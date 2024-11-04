package Task1;

public class PumpkinSpiceLatte extends Cappuccino{
    private int mgOfPumpkinSpice;
    private final String name = "PumpkinSpiceLatte";

    public PumpkinSpiceLatte(Intensity intensityOfCoffee, int mlOfWater, int mgOfPumpkinSpice) {
        super(intensityOfCoffee, mlOfWater);
        this.mgOfPumpkinSpice = mgOfPumpkinSpice;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getMgOfPumpkinSpice() {
        return mgOfPumpkinSpice;
    }

    public void setMgOfPumpkinSpice(int mgOfPumpkinSpice) {
        this.mgOfPumpkinSpice = mgOfPumpkinSpice;
    }

    @Override
    public void printCoffeeDetails(){
        super.printCoffeeDetails();
        System.out.println("Type: " + name);
        System.out.println("Add " + mgOfPumpkinSpice + " mg of pumpkin spice.");
    }

}
