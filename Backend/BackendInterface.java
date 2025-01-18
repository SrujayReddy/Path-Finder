import java.io.FileNotFoundException;

public interface BackendInterface {

    // public BackendPlaceholder(){
    //    super(new PlaceholderMap());
    //  }

    /**
     * read data from a file
     */
    void readData(String filepath) throws FileNotFoundException;

    /**
     * get the shortest path from a start to a destination building in the datase
     */
    ShortestPathInterface<String, Double> shortestPath(String start, String end);

    /**
     * get a string with statistics about the dataset that includes the number of
     * nodes (buildings), the number of edges, and the total walking time (sum of
     * weights) for all edges in the graph.
     */
    String statistics();
}
