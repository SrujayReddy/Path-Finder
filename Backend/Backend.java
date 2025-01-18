import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Backend implements BackendInterface {

  // Initialize and constructor
  private DijkstraGraph<String, Double> graph;
  private double totalWalkingTime = 0.0;

  public Backend() {
    this.graph = new DijkstraGraph<>(new HashtableMap<>());
  }

  /**
   * Implementation of the BackendInterface
   */
  @Override
  public void readData(String filepath) {
    try (Stream<String> lines = Files.lines(Paths.get(filepath))) {
      lines.forEach(line -> {
        try {
          if (line.contains("--")) {
            // use regular expressions to match the lines
            String[] parts = line.split("\" -- \"|\" \\[seconds=|\\];");
            if (parts.length >= 3) {
              String node1 = parts[0].replace("\"", "");
              String node2 = parts[1].replace("\"", "");
              Double weight = Double.parseDouble(parts[2]);

              graph.insertNode(node1);
              graph.insertNode(node2);
              graph.insertEdge(node1, node2, weight);
              graph.insertEdge(node2, node1, weight);
              totalWalkingTime += weight; // accumulate total walking time
            }
          }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
          System.err.println("Parsing error in line: " + line + " - " + e.getMessage());
        }
      });
    } catch (IOException e) {
      System.err.println("IOException: " + e.getMessage());
      throw new RuntimeException("Failed to read file:" + filepath, e);
    }
  }

  @Override
  public ShortestPathInterface<String, Double> shortestPath(String start, String end) {
    if (!graph.containsNode(start) || !graph.containsNode(end)) {
      return new ShortestPathImplementation(Collections.emptyList(), Collections.emptyList(), 0.0);
    }

    // get the path by calling the shortestPathData method from DijkstraGraph
    List<String> path = graph.shortestPathData(start, end);

    Double cost = graph.shortestPathCost(start, end);

    // create a empty list passing in the weighted of each edge segment
    // List<Double> walkingTimes = Collections.emptyList();
    List<Double> walkingTimes = calculateWalkingTimes(path);

    // return the instance of ShortestPathImplementation
    return new ShortestPathImplementation(path, walkingTimes, cost);
  }

  /**
   * Record each edge weight in a list
   *
   * @param path
   * @return
   */
  private List<Double> calculateWalkingTimes(List<String> path) {
    List<Double> walkingTimes = new ArrayList<>();
    if (path != null && path.size() > 1) {
      for (int i = 0; i < path.size() - 1; i++) {
        String node1 = path.get(i);
        String node2 = path.get(i + 1);
        Double edgeWeight = graph.getEdge(node1, node2);
        walkingTimes.add(edgeWeight);
      }
    }
    return walkingTimes;
  }

  @Override
  public String statistics() {
    int nodeCount = graph.getNodeCount();
    int edgeCount = graph.getEdgeCount();

    // Use Math.ceil for rounding up
    double adjustedWalkingTime = Math.ceil(totalWalkingTime / 2);
    return "Nodes: " + nodeCount + " Edges: " + edgeCount + " Total Walking Time: " + adjustedWalkingTime;
  }


  /**
   * Main method runs the application
   */
  public static void main(String[] args) {
    // Instance for backend
    Backend backend = new Backend();

    // Scanner instance for user input
    Scanner scanner = new Scanner(System.in);

    // Frontend instance
    Frontend frontend = new Frontend(backend, scanner);

    frontend.startMain();
    scanner.close();
  }
}
