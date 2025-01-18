import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains the methods to implement the frontend interface for the UW Path Finder app.
 */
public class Frontend implements FrontendInterface {

  private BackendInterface backend;
  private Scanner scanner;

  /**
   * Constructor for the class
   *
   * @param backend
   * @param scanner
   */
  public Frontend(BackendInterface backend, Scanner scanner) {
    this.backend = backend;
    this.scanner = scanner;
  }

  /**
   * This method is used to show options that would be displayed in the mainMenu for continued use
   * of the application.
   */
  @Override
  public void startMain() {
    boolean running = true;
    while (running) {
      startSubMenu();
      String choice = scanner.nextLine();

      switch (choice) {
        case "1":
          dataFile();
          break;
        case "2":
          shortestPath();
          break;
        case "3":
          showStats();
          break;
        case "4":
          exitApp();
          running = false;
          break;
        default:
          System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
      }
    }
  }

  /**
   * This method launches the application and allows the user to choose a specific functionality
   */
  @Override
  public void startSubMenu() {
    System.out.println("Welcome to the UW Path Finder app.");
    System.out.println("""
        Select an option:
        1. Load data file
        2. Find shortest path
        3. Show statistics
        4. Exit the application""");
  }

  /**
   * This method specifies and loads a data file.
   */
  @Override
  public void dataFile() {
    System.out.println("Enter the file path for the data set: ");
    String filePath = scanner.nextLine();

    try {
      backend.readData(filePath);
      System.out.println("File loaded successfully.");
    } catch (RuntimeException | FileNotFoundException e) {
      System.out.println("Error loading file: " + e.getMessage());
    }
  }


  /**
   * This method shows statistics such as the number of buildings, total walking time, etc.
   */
  @Override
  public void showStats() {
    String statistics = backend.statistics();
    System.out.println(statistics);
  }

  /**
   * This method displays the shortest path from any two buildings.
   */
  @Override
  public void shortestPath() {
    System.out.println("Enter the start building: ");
    String startBuilding = scanner.nextLine();
    System.out.println("Enter the destination building: ");
    String destinationBuilding = scanner.nextLine();

    ShortestPathInterface<String, Double> path = backend.shortestPath(startBuilding, destinationBuilding);
    if (path != null && !path.getPath().isEmpty()) {
      List<String> buildings = path.getPath();
      List<Double> walkingTimes = path.getWalkingTimes();

      System.out.println("Shortest path from " + startBuilding + " to " + destinationBuilding + ":");
      double totalWalkingTime = 0.0;

      for (int i = 0; i < buildings.size() - 1; i++) {
        double time = Math.round(walkingTimes.get(i) * 100.0) / 100.0;
        totalWalkingTime += time;

        // Trim building names and format the output
        String formattedOutput = String.format("%-40s to %-40s - %6.2f minutes",
            buildings.get(i).trim(),
            buildings.get(i + 1).trim(),
            time);
        System.out.println(formattedOutput);
      }

      totalWalkingTime = Math.round(totalWalkingTime * 100.0) / 100.0;
      System.out.println("Total walking time: " + totalWalkingTime + " minutes.");
    } else {
      System.out.println("No path found between " + startBuilding + " and " + destinationBuilding + ".");
    }
  }


  /**
   * This method exits the application.
   */
  @Override
  public void exitApp() {
    System.out.println("Exiting the UW Path Finder app...");
  }

  /**
   * The main function that runs the app
   *
   * param args
   */
  public static void main(String[] args) {

    Backend backend = new Backend();

    Frontend frontend = new Frontend(backend, new Scanner(System.in));

    frontend.startMain();
  }
}

