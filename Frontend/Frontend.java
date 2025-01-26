import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains the methods to implement the frontend interface for the UW Path Finder app.
 */
public class Frontend implements FrontendInterface {

  private BackendInterface backend;
  private Scanner scanner;
  private boolean fileLoaded;  // flag to indicate if a file is loaded

  /**
   * Constructor for the class
   *
   * @param backend backend interface
   * @param scanner scanner for user input
   */
  public Frontend(BackendInterface backend, Scanner scanner) {
    this.backend = backend;
    this.scanner = scanner;
    this.fileLoaded = false;
  }

  /**
   * Launches the main menu loop
   */
  @Override
  public void startMain() {
    boolean running = true;
    while (running) {
      printMainMenu();
      String choice = scanner.nextLine().trim();

      switch (choice) {
        case "1" -> loadDataFile();
        case "2" -> findShortestPath();
        case "3" -> showStats();
        case "4" -> {
          exitApp();
          running = false;
        }
        default -> System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.\n");
      }
    }
  }

  /**
   * Prints the main menu to the console.
   */
  @Override
  public void startSubMenu() {
    // This method might now be redundant in the new structure;
    // you can remove it or rename it for clarity, if you prefer.
    printMainMenu();
  }

  private void printMainMenu() {
    System.out.println("------------------------------------------------");
    System.out.println("Welcome to the UW Path Finder app.");
    System.out.println("Please select an option:");
    System.out.println("1. Load data file");
    System.out.println("2. Find shortest path");
    System.out.println("3. Show statistics");
    System.out.println("4. Exit the application");
    System.out.println("------------------------------------------------");
    System.out.print("Choice: ");
  }

  /**
   * Prompts for a file path and loads the data from that file.
   */
  @Override
  public void dataFile() {
    // In the new design, we've moved the logic to loadDataFile().
    // This method can remain as is (if needed by the interface) or call loadDataFile().
    loadDataFile();
  }

  private void loadDataFile() {
    // If a file is already loaded, optionally confirm if user wants to replace the data
    if (fileLoaded) {
      System.out.println("A data file is already loaded. Do you want to load a new file and replace existing data? (y/n)");
      String ans = scanner.nextLine().trim().toLowerCase();
      if (!ans.equals("y")) {
        System.out.println("File load canceled.");
        return;
      }
    }

    boolean loadedSuccessfully = false;
    while (!loadedSuccessfully) {
      System.out.print("Enter the file path for the data set (or type 'cancel' to return to main menu): ");
      String filePath = scanner.nextLine().trim();

      if (filePath.equalsIgnoreCase("cancel")) {
        System.out.println("File load canceled. Returning to main menu.");
        break;
      }
      try {
        backend.readData(filePath);
        fileLoaded = true;
        loadedSuccessfully = true;
        System.out.println("File loaded successfully.\n");
      } catch (FileNotFoundException e) {
        System.out.println("File not found: " + e.getMessage());
      } catch (RuntimeException e) {
        System.out.println("Error loading file: " + e.getMessage());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * Displays statistics such as the number of buildings, total walking time, etc.
   */
  @Override
  public void showStats() {
    if (!fileLoaded) {
      System.out.println("No data file loaded. Please load a file first.\n");
      return;
    }
    String statistics = backend.statistics();
    System.out.println("----------- Graph Statistics -----------");
    System.out.println(statistics);
    System.out.println("----------------------------------------\n");
  }

  /**
   * Finds the shortest path from one building to another.
   */
  @Override
  public void shortestPath() {
    // In the new design, we've moved the logic to findShortestPath().
    // This method can remain as is (if needed by the interface) or call findShortestPath().
    findShortestPath();
  }

  private void findShortestPath() {
    if (!fileLoaded) {
      System.out.println("No data file loaded. Please load a file first.\n");
      return;
    }

    System.out.print("Enter the start building: ");
    String startBuilding = scanner.nextLine().trim();
    if (startBuilding.isEmpty()) {
      System.out.println("Invalid start building name.\n");
      return;
    }

    System.out.print("Enter the destination building: ");
    String destinationBuilding = scanner.nextLine().trim();
    if (destinationBuilding.isEmpty()) {
      System.out.println("Invalid destination building name.\n");
      return;
    }

    ShortestPathInterface<String, Double> path = backend.shortestPath(startBuilding, destinationBuilding);
    if (path == null || path.getPath().isEmpty()) {
      System.out.println("No path found between "
          + startBuilding + " and " + destinationBuilding + ".\n");
      return;
    }

    List<String> buildings = path.getPath();
    List<Double> walkingTimes = path.getWalkingTimes();

    System.out.println("Shortest path from " + startBuilding + " to " + destinationBuilding + ":");
    double totalWalkingTime = 0.0;

    for (int i = 0; i < buildings.size() - 1; i++) {
      double time = Math.round(walkingTimes.get(i) * 100.0) / 100.0;
      totalWalkingTime += time;

      String formattedOutput = String.format(
          "%-40s to %-40s - %6.2f seconds",
          buildings.get(i).trim(),
          buildings.get(i + 1).trim(),
          time
      );
      System.out.println(formattedOutput);
    }

    totalWalkingTime = Math.round(totalWalkingTime * 100.0) / 100.0;
    System.out.println("Total walking time: " + totalWalkingTime + " seconds.\n");
  }

  /**
   * Exits the application.
   */
  @Override
  public void exitApp() {
    System.out.println("Exiting the UW Path Finder app...");
  }

  /**
   * The main function that runs the app
   *
   * @param args
   */
  public static void main(String[] args) {
    Backend backend = new Backend();
    Frontend frontend = new Frontend(backend, new Scanner(System.in));
    frontend.startMain();
  }
}
