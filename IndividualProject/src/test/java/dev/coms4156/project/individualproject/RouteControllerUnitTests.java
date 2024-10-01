package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains tests for source code.
 */
@SpringBootTest
@ContextConfiguration
public class RouteControllerUnitTests {

  @BeforeAll
  public static void setupRouteControllerForTesting() {
    testRouteController = new RouteController();
  }

  @Test
  public void retrieveDepartmentTests() {
    HashMap<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

    ResponseEntity<?> response = testRouteController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(departmentMapping.get("COMS").toString(), response.getBody());
  }

  @Test
  public void retrieveDepartmentNotExistTests() {
    ResponseEntity<?> response = testRouteController.retrieveDepartment("COMX");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void retrieveCourseTests() {
    HashMap<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    HashMap<String, Course> coursesMapping;
    coursesMapping = departmentMapping.get("COMS").getCourseSelection();

    ResponseEntity<?> response = testRouteController.retrieveCourse("COMS", 4156);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(coursesMapping.get("4156").toString(), response.getBody());
  }

  @Test
  public void retrieveCourseNotExistTests() {
    ResponseEntity<?> response = testRouteController.retrieveCourse("COMX", 4156);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());

    response = testRouteController.retrieveCourse("COMS", 4150);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /** The test course instance used for testing. */
  private static RouteController testRouteController;
}

