// --== CS400 File Header Information ==--
// Name: <Changlin Hu>
// Email: <chu244@wisc.edu>
// Group and Team: <E15>
// Group TA: <Lakshika Rathi>
// Lecturer: <Gary Dahl>
// Notes to Grader: <N/A>

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

public class BackendDeveloperTests {

  private Backend backend;

  /**
   * Set up statements annotation before each tests
   */
  @BeforeEach
  public void setUp() {
    backend = new Backend();
  }

  /**
   * Testcase1 Test read data from file
   */
  @Test
  public void testCase1() {
    String FileName = "campus.dot";
    Assertions.assertDoesNotThrow(() -> {
      backend.readData(FileName);
    }, "Reading campus.dot file should not throw any exception.");
  }

  /**
   * Testcase2 Test the validation of statistic method
   */
  @Test
  public void testCase2() {
    backend.readData("campus.dot");
    String stats = backend.statistics();
    Assertions.assertNotNull(stats, "Statistics should not be null.");
    Assertions.assertTrue(stats.contains("Nodes:") && stats.contains("Edges:"),
        "Statistics should contain information about nodes and edges.");
  }

  /**
   * Testcase3 Test the functionality of the shortestPath and should not throw any exception
   */
  @Test
  public void testCase3() {
    backend.readData("campus.dot");
    Assertions.assertDoesNotThrow(() -> {
      backend.shortestPath("Memorial Union", "Science Hall");
    }, "Finding shortest path should not throw any exception.");
  }

  /**
   * Testcase4 Test the Non-existence path
   */
  @Test
  public void testCase4() {
    backend.readData("campus.dot");
    ShortestPathInterface<String, Double> shortestPath =
        backend.shortestPath("NonExistentLocation", "Science Hall");
    Assertions.assertTrue(shortestPath.getPath().isEmpty(),
        "Path should be empty for a non-existent location.");
  }

  /**
   * Testcase5 Test to see if it's truely return the calculateWalkingTimes of each path segment
   */
  @Test
  public void testCase5() {
    backend.readData("campus.dot");
    ShortestPathInterface<String, Double> shortestPath =
        backend.shortestPath("Wendt Commons", "Computer Sciences and Statistics");
    List<Double> walkingTimes = shortestPath.getWalkingTimes();

    Assertions.assertFalse(walkingTimes.isEmpty(), "Walking times list should not be empty.");
    walkingTimes.forEach(pathSegment -> Assertions.assertTrue(pathSegment > 0,
        "Each walking segment should be greater than 0."));
  }


  /**
   * -----------------------Integration Test----------------------------
   */

  /**
   * IntegrationTest1 Test the main menu functionality
   */
  @Test
  public void integrationTest1() {
    String simulatedUserInput = "4\n";
    TextUITester tester = new TextUITester(simulatedUserInput);
    BackendInterface backend = new Backend();
    FrontendInterface frontend = new Frontend(backend, new Scanner(System.in));

    frontend.startMain();

    String actual = tester.checkOutput();
    String expected = "Exiting the UW Path Finder app...";
    Assertions.assertTrue(actual.contains(expected));
  }


  /**
   * IntegrationTest2 Test the shortestPath method function to display the shortest path between two
   * locations
   */
  @Test
  public void integrationTest2() {
    String simulatedUserInput = "2\nMemorial Union\nScience Hall\n4\n";
    TextUITester tester = new TextUITester(simulatedUserInput);
    BackendInterface backend = new Backend();
    FrontendInterface frontend = new Frontend(backend, new Scanner(System.in));

    frontend.startMain();

    String actual = tester.checkOutput();
    String expected = "Shortest path from Memorial Union to Science Hall";
    Assertions.assertTrue(actual.contains(expected));
  }
}


