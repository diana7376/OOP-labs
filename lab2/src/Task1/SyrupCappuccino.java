package Task1;

public class SyrupCappuccino extends Cappuccino {

    private SyrupType syrup;
    private final String coffee = "SyrupCappuccino";

    public SyrupCappuccino(Intensity intensity, int mltrOfMilk, SyrupType syrup) {
        super(intensity, mltrOfMilk);
        this.syrup = syrup;
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

    @Override
    public void printCoffeeDetails(){
        super.printCoffeeDetails();
        System.out.println("Type: " + coffee);
        System.out.println("Add " + syrup + " syrup.");
    }

}
