import java.util.List;

public class ShortestPathImplementation implements ShortestPathInterface<String, Double> {
  // Initialize and constructor
  private final List<String> path;
  private final List<Double> walkingTimes;
  private final Double totalPathCost;

  public ShortestPathImplementation(List<String> path, List<Double> walkingTimes,
      Double totalPathCost) {
    this.path = path;
    this.walkingTimes = walkingTimes;
    this.totalPathCost = totalPathCost;
  }

  /**
   * Implementation of the ShortestPathInterce Getter methods
   */
  @Override
  public List<String> getPath() {
    return path;
  }

  @Override
  public List<Double> getWalkingTimes() {
    return walkingTimes;
  }

  @Override
  public Double getTotalPathCost() {
    return totalPathCost;
  }
}


