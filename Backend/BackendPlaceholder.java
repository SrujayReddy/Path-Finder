import java.io.FileNotFoundException;

/**
 * This class serves as a placeholder for the backend implementation of the UW Path Finder app.
 */
public class BackendPlaceholder extends BaseGraph implements BackendInterface {

  public BackendPlaceholder(){
    super(new PlaceholderMap());
  }

  /**
   * Simulates reading data from a file.
   *
   * @param filepath The path to the data file.
   */
  @Override
  public void readData(String filepath) throws FileNotFoundException {
    // Simulating data reading without actual file operations
    System.out.println("Data has been read from: " + filepath);
  }

  /**
   * Returns a placeholder shortest path between two buildings.
   *
   * @param start The starting building.
   * @param end   The destination building.
   * @return ShortestPathInterface representing the shortest path.
   */
  @Override
  public ShortestPathInterface<String, Double> shortestPath(String start, String end) {
    // Returning a null object to indicate this is a placeholder implementation
    System.out.println("Shortest path from " + start + " to " + end + " calculated.");
    return null;  // Replace with a dummy implementation as needed
  }

  /**
   * Provides placeholder statistics about the dataset.
   *
   * @return A string representing dataset statistics.
   */
  @Override
  public String statistics() {
    // Returning dummy statistics
    return "Number of buildings: 10, Number of edges: 20, Total walking time: 100 minutes";
  }
}
