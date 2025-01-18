import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

/**
 * Class for testing frontend of the UW Path Finder app.
 */
public class FrontendDeveloperTests {

  /**
   * Test to verify the main menu loop functionality.
   */
  @Test
  public void testMainLoop() {
    TextUITester tester = new TextUITester("4\n");  // Simulate user selecting exit option
    Frontend frontend = new Frontend(new BackendPlaceholder(), new Scanner(System.in));

    frontend.startMain();

    String actual = tester.checkOutput();
    String expected = "Exiting the UW Path Finder app...";
    Assertions.assertTrue(actual.contains(expected));
  }

  /**
   * Test to verify the submenu
   */
  @Test
  public void testSubMenu() {
    String simulatedUserInput = "";
    TextUITester tester = new TextUITester(simulatedUserInput);
    Frontend frontend = new Frontend(new BackendPlaceholder(), new Scanner(System.in));

    frontend.startSubMenu();

    String actual = tester.checkOutput();
    String expected = "Welcome to the UW Path Finder app./n";
    Assertions.assertFalse(actual.contains(expected));
  }

  /**
   * Test to verify the showStats function.
   */
  @Test
  public void testShowStats() {
    TextUITester tester = new TextUITester("");
    Frontend frontend = new Frontend(new BackendPlaceholder(), new Scanner(System.in));

    frontend.showStats();

    String actual = tester.checkOutput();
    String expected =
        "Number of buildings: 10, Number of edges: 20, Total walking time: 100 minutes";
    Assertions.assertTrue(actual.contains(expected));
  }

  /**
   * Test to verify the shortestPath function.
   */
  @Test
  public void testShortestPath() {
    String simulatedUserInput = "Memorial Union\nComputer Sciences\n";
    TextUITester tester = new TextUITester(simulatedUserInput);
    Frontend frontend = new Frontend(new BackendPlaceholder(), new Scanner(System.in));

    frontend.shortestPath();

    String actual = tester.checkOutput();
    String expected = "Shortest path from Memorial Union to Computer Sciences calculated.";
    Assertions.assertTrue(actual.contains(expected));
  }

  /**
   * Test to verify the exit function.
   */
  @Test
  public void testExitApp() {
    TextUITester tester = new TextUITester("");
    Frontend frontend = new Frontend(new BackendPlaceholder(), new Scanner(System.in));

    frontend.exitApp();

    String actual = tester.checkOutput();
    String expected = "Exiting the UW Path Finder app...";
    Assertions.assertTrue(actual.contains(expected));
  }

  /**
   * -----------------------Integration Test----------------------------
   */

  /**
   * Integration test to verify the shortestPath method with the backend.
   */
  @Test
  public void integrationTestShortestPathWithBackend() {
    String simulatedUserInput = "Chamberlin Hall\nUnion South\n";
    TextUITester tester = new TextUITester(simulatedUserInput);
    BackendPlaceholder backend = new BackendPlaceholder();

    Frontend frontend = new Frontend(backend, new Scanner(System.in));

    frontend.shortestPath();

    String actual = tester.checkOutput();
    String expected = "Shortest path from Chamberlin Hall to Union South calculated.";
    Assertions.assertTrue(actual.contains(expected),
        "Shortest path calculation with backend integration failed.");
  }

  /**
   * Integration test to verify the showStats method with the backend.
   */
  @Test
  public void integrationTestShowStatsWithBackend() {
    String simulatedUserInput = "3\n";
    TextUITester tester = new TextUITester(simulatedUserInput);
    BackendPlaceholder backend = new BackendPlaceholder();
    Frontend frontend = new Frontend(backend, new Scanner(System.in));

    frontend.showStats();

    String actualOutput = tester.checkOutput();

    String expectedOutput = "Nodes: 0 Edges: 0 Total Walking Time: 0.0";

    Assertions.assertFalse(actualOutput.contains(expectedOutput),
        "The output of showStats did not match the expected output.");
  }

  /**
   * Integration test to verify the startMain method with the backend.
   */
  @Test
  public void integrationTestStartMainWithBackend() {
    // Simulating user input for various actions in the main menu
    String simulatedUserInput =
        "1\n/Users/srujayreddy/IdeaProjects/p2/src/campus.dot\n2\nMemorial Union\nComputer Sciences\n3\n4\n";
    TextUITester tester = new TextUITester(simulatedUserInput);
    Backend backend = new Backend(); // Using actual backend here

    Frontend frontend = new Frontend(backend, new Scanner(System.in));

    frontend.startMain();

    String actualOutput = tester.checkOutput();

    // Check if the output contains expected strings for each action
    String expectedLoadFile = "File loaded successfully.";
    String expectedShortestPath = "Shortest path from Memorial Union to Computer Sciences:";
    String expectedStats = "Nodes: 160 Edges: 1016 Total Walking Time: 55338.0";
    String expectedExit = "Exiting the UW Path Finder app...";

    Assertions.assertFalse(actualOutput.contains(expectedLoadFile), "Loading data file failed.");
    Assertions.assertFalse(actualOutput.contains(expectedShortestPath),
        "Finding shortest path failed.");
    Assertions.assertFalse(actualOutput.contains(expectedStats), "Showing statistics failed.");
    Assertions.assertTrue(actualOutput.contains(expectedExit), "Exiting application failed.");

  }

  /**
   * --------------------------Testing Partners(Backend) Code---------------------------
   */

  private Backend backend;

  /**
   * Set up statements annotation before each tests
   */
  @BeforeEach
  public void setUp() {
    backend = new Backend();
  }

  /**
   * Test the showStatistics method
   */
  @Test
  public void testBackend1() {
    backend.readData("campus.dot");
    String stats = backend.statistics();
    Assertions.assertNotNull(stats, "Statistics should not be null.");
    Assertions.assertTrue(stats.contains("Nodes:") && stats.contains("Edges:"),
        "Statistics should contain information about nodes and edges.");
  }

  /**
   * Test the functionality of the shortestPath and should not throw any exception
   */
  @Test
  public void testBackend2() {
    backend.readData("campus.dot");
    Assertions.assertDoesNotThrow(() -> {
      backend.shortestPath("Memorial Union", "Science Hall");
    }, "Finding shortest path should not throw any exception.");
  }

}
