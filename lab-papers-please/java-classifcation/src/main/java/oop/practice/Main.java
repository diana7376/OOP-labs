package oop.practice;

import com.fasterxml.jackson.databind.ObjectMapper;
import oop.practice.lab0.*;
import oop.practice.lab1.Assistant;
import oop.practice.lab1.Display;
import oop.practice.lab1.FileReader;
import oop.practice.lab1.TextData;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.nio.file.AccessDeniedException;

public class Main {
  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) throws IOException {
    System.out.println(args[0]);
    CreatureJsonHandler jsonHandler = new CreatureJsonHandler();
    UniverseManager universeManager = new UniverseManager();

    // Load creatures from JSON file
    File inputFile = new File("D:/POO Labs/OOP-lab1/lab-papers-please/java-classifcation/src/main/resources/input.json");
    List<Creature> creatures = jsonHandler.loadCreaturesFromJson(inputFile);

    // Initialize universes
    List<Universe> universes = universeManager.initializeUniverses();

    displayMenu(args, creatures, inputFile, universes, universeManager);

    scanner.close(); // Close scanner once at the end
  }

  private static void displayMenu(String[] args, List<Creature> creatures, File inputFile, List<Universe> universes, UniverseManager universeManager) throws IOException {
    int maxId = creatures.stream().mapToInt(Creature::getId).max().orElse(0);
    String menuChoice = "";

    while (!menuChoice.equals("0")) {
      System.out.println("Menu:");
      System.out.println("1. Display all creatures.");
      System.out.println("2. Add new creature.");
      System.out.println("3. Display creature by id.");
      System.out.println("4. Display creatures with even or odd id.");
      System.out.println("5. Classify creatures into their universes.");
      System.out.println("6. Lab 1");
      System.out.println("0. Exit...");
      System.out.print("Choose a number: ");
      menuChoice = scanner.nextLine();

      switch (menuChoice) {
        case "1":
          for (Creature creature : creatures) {
            creature.displayAllCreatureDetails();
          }
          break;

        case "2":
          Creature newCreature = new Creature(0, false, null, 0, List.of());
          newCreature.addNewCreature(creatures, maxId, inputFile, new ObjectMapper());
          maxId++;
          break;

        case "3":
          System.out.print("Enter the ID of the creature you want to display: ");
          int searchId = Integer.parseInt(scanner.nextLine());
          Creature.displayCreatureById(creatures, searchId);
          break;

        case "4":
          System.out.print("Even or odd (e/o)? ");
          String choice = scanner.nextLine().toLowerCase();
          boolean isEven = choice.equals("e");
          System.out.println(isEven ? "Displaying creatures with even IDs:" : "Displaying creatures with odd IDs:");
          for (Creature creature : creatures) {
            if ((creature.getId() % 2 == 0) == isEven) {
              creature.displayAllCreatureDetails();
            }
          }
          break;

        case "5":
          Universe starWars = universes.get(0);
          Universe hitchhikers = universes.get(1);
          Universe marvel = universes.get(2);
          Universe rings = universes.get(3);
          for (Creature creature : creatures) {
            CreatureClassifier.classifyCreature(creature, starWars, marvel, hitchhikers, rings);
          }
          String outputPath = "src/main/resources/output";
          universeManager.saveUniverses(universes, outputPath);
          System.out.println("Classified creatures and saved universes to JSON files.");
          break;

        case "6":
          menuLab1(args);
          break;
        case "0":
          System.out.println("Exiting program....");
          break;

        default:
          System.out.println("Invalid choice. Please select a valid option.");
      }
    }
  }

  private static void menuLab1(String[] args) {
    String menuLab1 = "";
    while (!menuLab1.equals("0")) {
      System.out.println("Menu:");
      System.out.println("1. Task 1.");
      System.out.println("2. Task 2.");
      System.out.println("3. Task 3.");
      System.out.println("4. Task 4.");
      System.out.println("0. Exit...");
      System.out.print("Choose a number: ");
      menuLab1 = scanner.nextLine();

      switch (menuLab1) {
        case "1":
          displayClass();
          break;
        case "2":
          analyzeTextFile(args);
          break;
        case "3":
          assistantClass();
          break;
        case "4":
          analyzeMultipleTextFiles(args);
          break;
        case "0":
          System.out.println("Exiting Task Menu...");
          break;

        default:
          System.out.println("Invalid choice. Please select a valid option.");
      }
    }
  }

  private static void displayClass() {
    Display display1 = new Display(1920, 1080, 401.5f, "Display 1");
    Display display2 = new Display(2560, 1440, 326.0f, "Display 2");
    Display display3 = new Display(1280, 720, 280.5f, "Display 3");

    display1.compareWithMonitor(display2);
    System.out.println();

    display1.compareWithMonitor(display3);
    System.out.println();

    display2.compareWithMonitor(display3);
  }

  private static void analyzeTextFile(String[] args) {

    if (args.length == 0) {
      System.out.println("No file paths provided.");
      return;
    }

    for (String filePath : args) {
      try {

        String content = FileReader.readFileIntoString(filePath);


        TextData textData = new TextData(content, filePath);

        System.out.println("----- Results for " + filePath + " -----");
        System.out.println(content);
        System.out.println("----------------------------------------\n");
      } catch (Exception e) {
        System.out.println("Error processing file: " + filePath);
        e.printStackTrace();
      }
    }
  }

  private static void assistantClass() {
    Display display1 = new Display(1920, 1080, 401.5f, "Display 1");
    Display display2 = new Display(2560, 1440, 326.0f, "Display 2");
    Display display3 = new Display(1280, 720, 280.5f, "Display 3");

    Assistant assistant = new Assistant("Tech Assistant", 1600, 900, 300.0f, "Assistant Display");
    assistant.assignDisplay(display1);
    assistant.assignDisplay(display2);
    assistant.assignDisplay(display3);

    assistant.assist();
    assistant.buyDisplay(display2);
    assistant.assist();
  }

  private static void analyzeMultipleTextFiles( String[] args) {

    if (args.length == 0) {
      System.out.println("No file paths provided. Please provide file paths as command-line arguments.");
      return;
    }

    for (String filePath : args) {
      try {
        // Read file content
        String content = FileReader.readFileIntoString(filePath);

        // Create TextData object and process text
        TextData textData = new TextData(content, filePath);

        // Display processed text data
        System.out.println("----- Results for " + filePath + " -----");
        System.out.println(textData);
        System.out.println("----------------------------------------\n");
      } catch (Exception e) {
        System.out.println("Error processing file: " + filePath);
        e.printStackTrace();
      }
    }
  }
}
