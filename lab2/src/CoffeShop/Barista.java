package CoffeShop;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Barista {

    //menu
    private static final Map<String, Double> coffeeMenu = new HashMap<>();

    static {
        coffeeMenu.put("Americano", 20.0);
        coffeeMenu.put("Cappuccino", 24.0);
        coffeeMenu.put("Pumpkin Spice Latte", 45.0);
        coffeeMenu.put("Syrup Cappuccino", 30.0);
    }

    public void displayMenu() {
        System.out.println("Welcome to the Coffee Shop! Here’s our menu:");
        for (Map.Entry<String, Double> entry : coffeeMenu.entrySet()) {
            System.out.printf("%s: %.2f lei\n", entry.getKey(), entry.getValue());
        }
    }

    public void takeOrder() {
        Scanner scanner = new Scanner(System.in);

        String choice = null;

        while (true) {
            System.out.print("\nWhat kind of coffee would you like? ");
            choice = scanner.nextLine();

            if (coffeeMenu.containsKey(choice)) {
                break;
            }else {
                System.out.println("Sorry, we don't have that coffee. Please choose again.");
            }
        }

        Intensity intensity = askForIntensity(scanner);

        Coffee coffee = prepareCoffee(choice, intensity, scanner);
            if (coffee != null) {
            coffee.printCoffeeDetails();
            System.out.println("Enjoy your " + choice + "!");
        }
    }

    private Intensity askForIntensity(Scanner scanner) {
        System.out.print("Please select the intensity (LIGHT, NORMAL, STRONG): ");
        String intensityChoice = scanner.nextLine().toUpperCase();

        switch (intensityChoice) {
            case "LIGHT":
                return Intensity.LIGHT;
            case "NORMAL":
                return Intensity.NORMAL;
            case "STRONG":
                return Intensity.STRONG;
            default:
                System.out.println("Invalid intensity, setting to NORMAL by default.");
                return Intensity.NORMAL;
        }
    }

    private int getAmountBasedOnIntensity(Intensity intensity) {
        switch (intensity) {
            case STRONG:
                return 50;
            case NORMAL:
                return 100;
            case LIGHT:
                return 150;
            default:
                return 100;  // default
        }
    }
    private SyrupType askForSyrup(Scanner scanner) {
        System.out.print("Please select a syrup (MACADAMIA, VANILLA, COCONUT, CARAMEL, CHOCOLATE, POPCORN): ");
        String syrupChoice = scanner.nextLine().toUpperCase();

        try {
            return SyrupType.valueOf(syrupChoice);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid syrup type, setting to VANILLA by default.");
            return SyrupType.VANILLA;
        }
    }

    private Coffee prepareCoffee(String coffeeType, Intensity intensity, Scanner scanner) {
        int amount = getAmountBasedOnIntensity(intensity);
        switch (coffeeType) {
            case "Americano":
                return new Americano(intensity, amount).makeAmericano();
            case "Cappuccino":
                return new Cappuccino(intensity, amount).makeCappuccino();
            case "Pumpkin Spice Latte":
                return new PumpkinSpiceLatte(intensity, amount, 20).makePumpkinSpiceLatte();
            case "Syrup Cappuccino":
                String syrupType = String.valueOf(askForSyrup(scanner));
                return new SyrupCappuccino(intensity, amount, syrupType).makeSyrupCappuccino();
            default:
                return null;
        }
    }
}

