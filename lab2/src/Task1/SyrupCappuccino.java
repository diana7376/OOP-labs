package Task1;

public class SyrupCappuccino extends Cappuccino {

    private SyrupType syrup;
    private final String coffee = "SyrupCappuccino";

    public SyrupCappuccino(Intensity intensity, int mlOfWater, SyrupType syrup) {
        super(intensity, mlOfWater);
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
}
