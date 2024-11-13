package CoffeShop;

public class Americano extends Coffee{
    private int mlOfWater;
    private final String coffeeName = "Americano";

    public Americano(Intensity intensityOfCoffee, int mlOfWater) {
        super(intensityOfCoffee);
        this.mlOfWater = mlOfWater;
    }

    public int getMlOfWater() {
        return mlOfWater;
    }

    public void setMlOfWater(int mlOfWater) {
        this.mlOfWater = mlOfWater;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public Americano makeAmericano() {
        make();
        System.out.println("Making " + coffeeName);
        System.out.println("Adding " + mlOfWater + " ml of hot water");

        return this;
    }
    @Override
    public void printCoffeeDetails(){
        super.printCoffeeDetails();
        System.out.println("Americano is ready!");
    }



}
