package Task1;

public class Cappuccino extends Coffee{

    private int mlOfMilk;
    private final String coffee = "Cappuccino";

    public Cappuccino(Intensity intensity, int mlOfWater) {
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




}

