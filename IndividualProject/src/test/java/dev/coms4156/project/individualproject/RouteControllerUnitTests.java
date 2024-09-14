package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

/**
 * RouteController Unit Tests.
 */
@SpringBootTest
@ContextConfiguration
public class RouteControllerUnitTests {

  private RouteController routeController;
  private MyFileDatabase mockDatabase;

  /**
   * Sets up the test environment before each test method.
   * Initializes the mock database and the RouteController instance.
   */
  @BeforeEach
  public void setup() {
    mockDatabase = mock(MyFileDatabase.class);
    IndividualProjectApplication.myFileDatabase = mockDatabase;
    routeController = new RouteController();
  }

  /**
   * Test for index endpoint.
   */
  @Test
  public void testIndex() {
    String response = routeController.index();
    String expected = "Welcome, in order to make an API call direct your browser or Postman "
            + "to an endpoint \n\n This can be done using the following format: \n\n http:127.0.0"
            + ".1:8080/endpoint?arg=value";
    assertEquals(expected, response);
  }

  /**
   * Test for retrieving a department that exists.
   */
  @Test
  public void testRetrieveDepartmentExists() {
    HashMap<String, Department> mockMapping = new HashMap<>();
    Department mockDepartment = new Department("COMS", new HashMap<>(), "Jae Woo Lee", 100);
    mockMapping.put("COMS", mockDepartment);
    when(mockDatabase.getDepartmentMapping()).thenReturn(mockMapping);

    ResponseEntity<?> response = routeController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockDepartment.toString(), response.getBody());
  }

  /**
   * Test for retrieving a department that does not exist.
   */
  @Test
  public void testRetrieveDepartmentNotExists() {
    when(mockDatabase.getDepartmentMapping()).thenReturn(new HashMap<>());

    ResponseEntity<?> response = routeController.retrieveDepartment("MATH");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /**
   * Test for handling an exception during department retrieval.
   */
  @Test
  public void testRetrieveDepartmentWithException() {
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Test Exception"));

    ResponseEntity<?> response = routeController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An Error has occurred", response.getBody());
  }

  /**
   * Test for retrieving a course that exists.
   */
  @Test
  public void testRetrieveCourseExists() {
    HashMap<String, Department> mockDepartmentMapping = new HashMap<>();
    HashMap<String, Course> mockCourseMapping = new HashMap<>();
    Course mockCourse = new Course("Jae Woo Lee", "417 IAB", "1:10-2:25", 100);
    mockCourseMapping.put("3157", mockCourse);
    Department mockDepartment = new Department("COMS", mockCourseMapping, "Jae Woo Lee", 100);
    mockDepartmentMapping.put("COMS", mockDepartment);

    when(mockDatabase.getDepartmentMapping()).thenReturn(mockDepartmentMapping);

    ResponseEntity<?> response = routeController.retrieveCourse("COMS", 3157);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockCourse.toString(), response.getBody());
  }

  /**
   * Test for retrieving a course that does not exist.
   */
  @Test
  public void testRetrieveCourseNotExists() {
    HashMap<String, Department> mockDepartmentMapping = new HashMap<>();
    Department mockDepartment = new Department("COMS", new HashMap<>(), "Jae Woo Lee", 100);
    mockDepartmentMapping.put("COMS", mockDepartment);

    when(mockDatabase.getDepartmentMapping()).thenReturn(mockDepartmentMapping);

    ResponseEntity<?> response = routeController.retrieveCourse("COMS", 3157);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /**
   * Test for handling an exception during course retrieval.
   */
  @Test
  public void testRetrieveCourseWithException() {
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Test Exception"));

    ResponseEntity<?> response = routeController.retrieveCourse("COMS", 3157);
    assertEquals("Department Not Found", response.getBody());
  }

  /**
   * Test for checking if a course is full.
   */
  @Test
  public void testIsCourseFull() {
    HashMap<String, Department> mockDepartmentMapping = new HashMap<>();
    HashMap<String, Course> mockCourseMapping = new HashMap<>();
    Course mockCourse = new Course("Jae Woo Lee", "417 IAB", "1:10-2:25", 100);
    mockCourse.setEnrolledStudentCount(100);
    mockCourseMapping.put("3157", mockCourse);
    Department mockDepartment = new Department("COMS", mockCourseMapping, "Jae Woo Lee", 100);
    mockDepartmentMapping.put("COMS", mockDepartment);

    when(mockDatabase.getDepartmentMapping()).thenReturn(mockDepartmentMapping);

    ResponseEntity<?> response = routeController.isCourseFull("COMS", 3157);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(true, response.getBody());
  }

  /**
   * Test for course full check when course does not exist.
   */
  @Test
  public void testIsCourseFullCourseNotExists() {
    HashMap<String, Department> mockDepartmentMapping = new HashMap<>();
    Department mockDepartment = new Department("COMS", new HashMap<>(), "Jae Woo Lee", 100);
    mockDepartmentMapping.put("COMS", mockDepartment);

    when(mockDatabase.getDepartmentMapping()).thenReturn(mockDepartmentMapping);

    ResponseEntity<?> response = routeController.isCourseFull("COMS", 3157);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /**
   * Test for adding a major to a department.
   */
  @Test
  public void testAddMajorToDept() {
    HashMap<String, Department> mockDepartmentMapping = new HashMap<>();
    Department mockDepartment = new Department("COMS", new HashMap<>(), "Jae Woo Lee", 100);
    mockDepartmentMapping.put("COMS", mockDepartment);

    when(mockDatabase.getDepartmentMapping()).thenReturn(mockDepartmentMapping);

    ResponseEntity<?> response = routeController.addMajorToDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(101, mockDepartment.getNumberOfMajors());
  }

  /**
   * Test for handling department not found while adding a major.
   */
  @Test
  public void testAddMajorToDeptDepartmentNotFound() {
    when(mockDatabase.getDepartmentMapping()).thenReturn(new HashMap<>());

    ResponseEntity<?> response = routeController.addMajorToDept("MATH");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /**
   * Test for dropping a student from a course.
   */
  @Test
  public void testDropStudentFromCourse() {
    HashMap<String, Department> mockDepartmentMapping = new HashMap<>();
    HashMap<String, Course> mockCourseMapping = new HashMap<>();
    Course mockCourse = new Course("Jae Woo Lee", "417 IAB", "1:10-2:25", 100);
    mockCourse.setEnrolledStudentCount(50);
    mockCourseMapping.put("3157", mockCourse);
    Department mockDepartment = new Department("COMS", mockCourseMapping, "Jae Woo Lee", 100);
    mockDepartmentMapping.put("COMS", mockDepartment);

    when(mockDatabase.getDepartmentMapping()).thenReturn(mockDepartmentMapping);

    ResponseEntity<?> response = routeController.dropStudent("COMS", 3157);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(49, mockCourse.getEnrolledStudentCount());
  }

  /**
   * Test for handling course not found while dropping a student.
   */
  @Test
  public void testDropStudentCourseNotFound() {
    HashMap<String, Department> mockDepartmentMapping = new HashMap<>();
    Department mockDepartment = new Department("COMS", new HashMap<>(), "Jae Woo Lee", 100);
    mockDepartmentMapping.put("COMS", mockDepartment);

    when(mockDatabase.getDepartmentMapping()).thenReturn(mockDepartmentMapping);

    ResponseEntity<?> response = routeController.dropStudent("COMS", 3157);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }
}
