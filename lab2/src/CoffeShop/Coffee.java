package CoffeShop;

public class Coffee {

    protected Intensity coffeeIntensity;
    private final String name = "Coffee";

    public Coffee(Intensity coffeeIntensity) {
        this.coffeeIntensity = coffeeIntensity;
    }

    public String getName() {
        return name;
    }

    public Intensity getCoffeeIntensity() {
        return coffeeIntensity;
    }

    public void setCoffeeIntensity(Intensity coffeeIntensity) {
        this.coffeeIntensity = coffeeIntensity;
    }

    public final void make() {
        System.out.println("Starting to make " + name + "...");
        System.out.println("Coffee intensity: " + coffeeIntensity);
    }

    public void printCoffeeDetails(){

    }

}
