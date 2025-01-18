// --== CS400 File Header Information ==--
// Name: SRUJAY REDDY JAKKIDI
// Email: jakkidi@wisc.edu
// Group: E15
// Group TA: Lakshika
// Lecturer: GARY DAHL
// Notes to Grader: NONE

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * This class extends the BaseGraph data structure with additional methods for computing the total
 * cost and list of node data along the shortest path connecting a provided starting to ending
 * nodes. This class makes use of Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number> extends BaseGraph<NodeType, EdgeType>
    implements GraphADT<NodeType, EdgeType> {

  /**
   * While searching for the shortest path between two nodes, a SearchNode contains data about one
   * specific path between the start node and another node in the graph. The final node in this path
   * is stored in its node field. The total cost of this path is stored in its cost field. And the
   * predecessor SearchNode within this path is referenced by the predecessor field (this field is
   * null within the SearchNode containing the starting node in its node field).
   *
   * SearchNodes are Comparable and are sorted by cost so that the lowest cost SearchNode has the
   * highest priority within a java.util.PriorityQueue.
   */
  protected class SearchNode implements Comparable<SearchNode> {
    public Node node;
    public double cost;
    public SearchNode predecessor;

    public SearchNode(Node node, double cost, SearchNode predecessor) {
      this.node = node;
      this.cost = cost;
      this.predecessor = predecessor;
    }

    public int compareTo(SearchNode other) {
      if (cost > other.cost)
        return +1;
      if (cost < other.cost)
        return -1;
      return 0;
    }
  }

  /**
   * Constructor that sets the map that the graph uses.
   *
   * @param map the map that the graph uses to map a data object to the node object it is stored in
   */
  public DijkstraGraph(MapADT<NodeType, Node> map) {
    super(map);
  }

  /**
   * This helper method creates a network of SearchNodes while computing the shortest path between
   * the provided start and end locations. The SearchNode that is returned by this method is
   * represents the end of the shortest path that is found: it's cost is the cost of that shortest
   * path, and the nodes linked together through predecessor references represent all of the nodes
   * along that shortest path (ordered from end to start).
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return SearchNode for the final end node within the shortest path
   * @throws NoSuchElementException when no path from start to end is found or when either start or
   *                                end data do not correspond to a graph node
   */
  protected SearchNode computeShortestPath(NodeType start, NodeType end) {
    // Check if both start and end nodes exist in the graph
    if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
      throw new NoSuchElementException("Start or end node not found in graph");
    }

    // Priority queue for nodes to visit, sorted by path cost
    PriorityQueue<SearchNode> queue = new PriorityQueue<>();
    // Map to track visited nodes and the cost to reach them
    MapADT<NodeType, SearchNode> visitedNodes = new PlaceholderMap<>();

    // Get start node and add it to the queue with zero cost
    Node startNode = nodes.get(start);
    queue.add(new SearchNode(startNode, 0, null));

    // Process nodes in the queue
    while (!queue.isEmpty()) {
      // Poll the queue to get the node with the lowest cost
      SearchNode current = queue.poll();

      // Skip the node if it has been visited with a lower cost
      if (visitedNodes.containsKey(current.node.data) && visitedNodes.get(
          current.node.data).cost <= current.cost)
        continue;

      // Mark the current node as visited
      visitedNodes.put(current.node.data, current);

      // If the current node is the end node, return it
      if (current.node.data.equals(end))
        return current;

      // Explore all adjacent nodes
      for (Edge edge : current.node.edgesLeaving) {
        Node adjacent = edge.successor;
        double newCost = current.cost + edge.data.doubleValue();

        // Add adjacent node to the queue if not visited or found a shorter path
        if (!visitedNodes.containsKey(adjacent.data) || visitedNodes.get(
            adjacent.data).cost > newCost) {
          queue.add(new SearchNode(adjacent, newCost, current));
        }
      }
    }

    // Throw exception if no path is found
    throw new NoSuchElementException("No path exists between the specified nodes");
  }

  /**
   * Returns the list of data values from nodes along the shortest path from the node with the
   * provided start value through the node with the provided end value. This list of data values
   * starts with the start value, ends with the end value, and contains intermediary values in the
   * order they are encountered while traversing this shorteset path. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return list of data item from node along this shortest path
   */
  public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    // Compute the shortest path
    SearchNode endNode = computeShortestPath(start, end);

    // Create a list to store the path
    List<NodeType> path = new LinkedList<>();
    // Backtrack from the end node to build the path
    while (endNode != null) {
      path.add(0, endNode.node.data); // Add each node to the start of the list
      endNode = endNode.predecessor; // Move to the predecessor node
    }

    return path; // Return the constructed path
  }

  /**
   * Returns the cost of the path (sum over edge weights) of the shortest path freom the node
   * containing the start data to the node containing the end data. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return the cost of the shortest path between these nodes
   */
  public double shortestPathCost(NodeType start, NodeType end) {
    // Compute the shortest path and return its cost
    SearchNode endNode = computeShortestPath(start, end);
    return endNode.cost;
  }

  // JUnit test methods

  /**
   * Test method to confirm the results match the computation from the lecture example. This test
   * will check the shortest path from node A to node E.
   */
  @Test
  public void testShortestPathFromAtoE() {
    // Setup the graph as per the example
    DijkstraGraph<String, Integer> graph = createGraphFromExample();

    // The expected cost and path from A to E as computed from the lecture slide
    double expectedCost = 14; // The cost from A -> B -> E
    List<String> expectedPath = Arrays.asList("A", "B", "E");

    // Assert the cost is as expected
    Assertions.assertEquals(expectedCost, graph.shortestPathCost("A", "E"));

    // Assert the path is as expected
    Assertions.assertEquals(expectedPath, graph.shortestPathData("A", "E"));
  }

  /**
   * Test method to check the cost and sequence of data along the shortest path between a different
   * start and end node, specifically from node A to node G.
   */
  @Test
  public void testShortestPathFromAtoG() {
    // Setup the graph as per the example
    DijkstraGraph<String, Integer> graph = createGraphFromExample();

    // The expected cost and path from A to G as computed from the lecture slide
    double expectedCost = 9; // The cost from A -> C -> D -> F -> G
    List<String> expectedPath = Arrays.asList("A", "C", "D", "F", "G");

    // Assert the cost is as expected
    Assertions.assertEquals(expectedCost, graph.shortestPathCost("A", "G"));

    // Assert the path is as expected
    Assertions.assertEquals(expectedPath, graph.shortestPathData("A", "G"));
  }

  /**
   * Test method to check the behavior when no path exists between two nodes. This test will check
   * for a path from node E to node C.
   */
  @Test
  public void testNoPathExists() {
    // Setup the graph as per the example
    DijkstraGraph<String, Integer> graph = createGraphFromExample();

    // Assert that a NoSuchElementException is thrown when no path exists
    Assertions.assertThrows(NoSuchElementException.class, () -> {
      graph.shortestPathCost("E", "C");
    });
  }

  // Utility method to create the graph from the example
  private DijkstraGraph<String, Integer> createGraphFromExample() {
    DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    // Add nodes
    graph.insertNode("A");
    graph.insertNode("B");
    graph.insertNode("C");
    graph.insertNode("D");
    graph.insertNode("E");
    graph.insertNode("F");
    graph.insertNode("G");
    graph.insertNode("H");
    // Add edges as per the example slide
    graph.insertEdge("A", "B", 4);
    graph.insertEdge("A", "C", 2);
    graph.insertEdge("B", "E", 10);
    graph.insertEdge("C", "D", 5);
    graph.insertEdge("D", "B", 1);
    graph.insertEdge("D", "F", 0);
    graph.insertEdge("E", "F", 4);
    graph.insertEdge("F", "G", 2);
    graph.insertEdge("G", "H", 4);
    // Note: Add all other edges as per the graph in the lecture slide
    return graph;
  }

}
