package CoffeShop;

public class SyrupCappuccino extends Cappuccino {

    private SyrupType syrup;
    private final String coffee = "SyrupCappuccino";

    public SyrupCappuccino(Intensity intensity, int mltrOfMilk, String syrup) {
        super(intensity, mltrOfMilk);
        this.syrup = SyrupType.valueOf(syrup);
    }

    @Override
    public String getCoffee() {
        return coffee;
    }

    public SyrupType getSyrup() {
        return syrup;
    }

    public void setSyrup(SyrupType syrup) {
        this.syrup = syrup;
    }

    public SyrupCappuccino makeSyrupCappuccino() {
        makeCappuccino();
        System.out.println("Type: " + coffee);
        System.out.println("Adding " + syrup + " syrup");
        return this;
    }
    @Override
    public void printCoffeeDetails(){
        System.out.println("Syrup Cappuccino is ready!");
    }

}
