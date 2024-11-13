package CoffeShop;

public class Cappuccino extends Coffee{

    private int mlOfMilk;
    private final String coffee = "Cappuccino";

    public Cappuccino(Intensity intensity, int mlOfMilk) {
        super(intensity);
        this.mlOfMilk = mlOfMilk;
    }

    public int getmlOfMilk() {
        return mlOfMilk;
    }

    public void setmlOfMilk(int mlOfMilk) {
        this.mlOfMilk = mlOfMilk;
    }

    public String getCoffee() {
        return coffee;
    }

    public Cappuccino makeCappuccino() {
        make();
        System.out.println("Making " + coffee);
        System.out.println("Adding " + mlOfMilk + " ml of steamed milk");

        return this;
    }

    @Override
    public void printCoffeeDetails(){
        super.printCoffeeDetails();
        System.out.println("Cappuccino is ready!");
    }


}

