import java.util.List;

public interface ShortestPathInterface<NodeType, EdgeType extends Number> {

    // List<NodeType> path;

    // public ShortestPathInterface(List<NodeType> path){
    // this.path = path;
    // }

    /**
     * @return the path as a list of buildings along the path
     */
    List<NodeType> getPath();

    /**
     * @return a list of the walking times of the path segments
     */
    List<EdgeType> getWalkingTimes();

    /**
     * @return the total path cost as the estimated time it takes to walk from the
     *         start to the destination building
     */
    EdgeType getTotalPathCost();
}
