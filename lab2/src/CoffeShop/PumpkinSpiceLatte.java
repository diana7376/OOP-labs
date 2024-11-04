package CoffeShop;

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

    public PumpkinSpiceLatte makePumpkinSpiceLatte() {
        makeCappuccino();
        System.out.println("Type: " + name);
        System.out.println("Adding " + mgOfPumpkinSpice + " mg of pumpkin spice");
        System.out.println("Stirring ingredients together...");

        return this;
    }
    @Override
    public void printCoffeeDetails(){
        System.out.println("Pumpkin Spice Latte is ready!");
    }

}
