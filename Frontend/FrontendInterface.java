import java.io.FileNotFoundException;

/**
 * This interface describes the functionality of frontend, including the basic
 * methods it should implement.
 */
public interface FrontendInterface {

  /**
   * Constructor to create a fronted object using a reference to backend and an instance of scanner
   */
   // public Frontend (Backend backend, Scanner scanner);

  /**
   * This method is used to show options that would be displayed in the mainMenu
   * for continued use of the application.
   */
  public void startMain();

  /**
   * This method launches the application and allows the user to choose a specific functionality
   */
  public void startSubMenu();

  /**
   * This method specifies and loads a data file.
   */
  public void dataFile() throws FileNotFoundException;

  /**
   * This method shows statistics such as the number of buildings, total walking time, etc.
   */
  public void showStats();

  /**
   * This method displays the shortest path from any two buildings.
   */
  public void shortestPath();

  /**
   * This method exits the application.
   */
  public void exitApp();
}
