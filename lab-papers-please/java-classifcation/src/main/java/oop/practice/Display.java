package oop.practice;

public class Display {
    private int width;
    private int height;
    private float ppi;
    private String model;

    public Display(int width, int height, float ppi, String model) {
        this.width = width;
        this.height = height;
        this.ppi = ppi;
        this.model = model;
    }

    public String getModel() {
        return model;
    }


    public void compareSize(Display m){
        int thisArea = this.width * this.height;
        int otherArea = m.width * m.height;

        if (thisArea > otherArea) {
            System.out.println(this.model + " is larger in size than " + m.model);
        } else if (thisArea < otherArea) {
            System.out.println(m.model + " is larger in size than " + this.model);
        } else {
            System.out.println(this.model + " and " + m.model + " are the same size.");
        }
    }

    public void compareSharpness(Display m) {
        if (this.ppi > m.ppi) {
            System.out.println(this.model + " is sharper than " + m.model);
        } else if (this.ppi < m.ppi) {
            System.out.println(m.model + " is sharper than " + this.model);
        } else {
            System.out.println(this.model + " and " + m.model + " have the same sharpness.");
        }
    }

    public void compareWithMonitor(Display m) {
        System.out.println("Comparing " + this.model + " with " + m.model + ":");
        compareSize(m);
        compareSharpness(m);
    }

}
