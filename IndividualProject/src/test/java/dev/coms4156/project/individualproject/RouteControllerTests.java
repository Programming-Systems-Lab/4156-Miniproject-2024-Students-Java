package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit testing for the RouteController Class.
 */
@SpringBootTest
@ContextConfiguration
public class RouteControllerTests {

  @BeforeAll
  public static void setupDepartmentForTesting() {
    testFileDatabase = new MyFileDatabase(0, "./data.txt");
    testRouteController = new RouteController();
    testDepartments = testFileDatabase.getDepartmentMapping();
    testDepartment = testDepartments.get("COMS");
  }

  @Test
  public void retrieveDepartmentTest() {
  }


 /** The routeController instances used for testing. */
  public static MyFileDatabase testFileDatabase;
  public static RouteController testRouteController;
  public static Department testDepartment;
  public static HashMap<String, Department> testDepartments;
}
