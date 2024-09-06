package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * This class contains unit tests for the {@link RouteController} class,
 * which handles API routes related to department and course information.
 *
 * <p>It tests the functionality of various API endpoints, including retrieving
 * department and course details, setting enrollment counts, checking if courses
 * are full, and modifying department and course information.
 *
 * <p>The tests are designed to verify the correctness of the {@code RouteController}
 * methods under different scenarios, such as successful retrievals, not found errors,
 * and data modification actions.
 *
 * <p>Each test is isolated and uses mock objects to simulate the interaction
 * between the controller and the database.
 */
public class RouteControllerTests {

  @InjectMocks
  private RouteController routeController;

  @Mock
  private MyFileDatabase mockDatabase;

  @Mock
  private Department mockDepartment;

  @Mock
  private Course mockCourse;

  private HashMap<String, Department> departmentMapping;
  private HashMap<String, Course> courseMapping;

  /**
   * Sets up the test environment before each test.
   * Initializes mocks and populates the ELEN department with sample course data.
   */
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    departmentMapping = new HashMap<>();
    courseMapping = new HashMap<>();

    // Setup mock database responses
    when(mockDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    IndividualProjectApplication.myFileDatabase = mockDatabase;

    // Populate the ELEN department with courses
    Course elen1201 = new Course("David G Vallancourt", "301 PUP", "10:10-11:25", 120);
    elen1201.setEnrolledStudentCount(108);
    Course elen3082 = new Course("Kenneth Shepard", "1205 MUDD", "4:10-6:40", 32);
    elen3082.setEnrolledStudentCount(32);
    Course elen3331 = new Course("David G Vallancourt", "203 MATH", "9:00-10:25", 80);
    elen3331.setEnrolledStudentCount(54);

    courseMapping.put("1201", elen1201);
    courseMapping.put("3082", elen3082);
    courseMapping.put("3331", elen3331);

    Department elenDept = new Department("ELEN", courseMapping, "Ioannis Kymissis", 250);
    departmentMapping.put("ELEN", elenDept);
  }

  @Test
  public void testRetrieveDepartmentSuccess() {
    ResponseEntity<?> response = routeController.retrieveDepartment("ELEN");
    String responseBody = response.getBody().toString();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(responseBody.contains("ELEN 3082"));
    assertTrue(responseBody.contains("Instructor: Kenneth Shepard"));
    assertTrue(responseBody.contains("Location: 1205 MUDD"));
    assertTrue(responseBody.contains("Time: 4:10-6:40"));

    assertTrue(responseBody.contains("ELEN 1201"));
    assertTrue(responseBody.contains("Instructor: David G Vallancourt"));
    assertTrue(responseBody.contains("Location: 301 PUP"));
    assertTrue(responseBody.contains("Time: 10:10-11:25"));

    assertTrue(responseBody.contains("ELEN 3331"));
    assertTrue(responseBody.contains("Instructor: David G Vallancourt"));
    assertTrue(responseBody.contains("Location: 203 MATH"));
    assertTrue(responseBody.contains("Time: 9:00-10:25"));
  }

  @Test
  public void testRetrieveDepartmentNotFound() {
    ResponseEntity<?> response = routeController.retrieveDepartment("MATH");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testGetMajorCountFromDept() {
    ResponseEntity<?> response = routeController.getMajorCtFromDept("ELEN");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("There are: 250 majors in the department", response.getBody());
  }

  @Test
  public void testGetMajorCountFromNonExistentDept() {
    // Assuming "NONEXISTENT" is not a valid department
    ResponseEntity<?> response = routeController.getMajorCtFromDept("NONEXISTENT");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testRetrieveCourseSuccess() {
    ResponseEntity<?> response = routeController.retrieveCourse("ELEN", 1201);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().toString().contains("David G Vallancourt"));
  }

  @Test
  public void testRetrieveCourseNotFound() {
    ResponseEntity<?> response = routeController.retrieveCourse("ELEN", 9999);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testRetrieveCourseFound() {
    ResponseEntity<?> response = routeController.retrieveCourse("ELEN", 1201);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().toString().contains("David G Vallancourt"));
  }

  @Test
  public void testIsCourseFullWhenNotFull() {
    ResponseEntity<?> response = routeController.isCourseFull("ELEN", 1201);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Course is not full", response.getBody());
  }

  @Test
  public void testIsCourseFullWhenFull() {
    ResponseEntity<?> response = routeController.isCourseFull("ELEN", 3082);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals("Course is full", response.getBody());
  }

  @Test
  public void testIsCourseFullNotFound() {
    ResponseEntity<?> response = routeController.isCourseFull("ELEN", 9999);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testSetEnrollmentCountSuccess() {
    ResponseEntity<?> response = routeController.setEnrollmentCount("ELEN", 1201, 110);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void testSetEnrollmentCountCourseNotFound() {
    ResponseEntity<?> response = routeController.setEnrollmentCount("ELEN", 9999, 110);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseTimeSuccess() {
    ResponseEntity<?> response = routeController.changeCourseTime("ELEN", 1201, "2:00-3:15");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void testChangeCourseLocationSuccess() {
    ResponseEntity<?> response = routeController.changeCourseLocation("ELEN", 1201, "501 Butler");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void testChangeCourseTeacherSuccess() {
    ResponseEntity<?> response = routeController.changeCourseTeacher("ELEN", 1201, "John Doe");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void testAddMajorToDeptSuccess() {
    ResponseEntity<?> response = routeController.addMajorToDept("ELEN");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated successfully", response.getBody());
  }

  @Test
  public void testRemoveMajorFromDeptSuccess() {
    ResponseEntity<?> response = routeController.removeMajorFromDept("ELEN");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Major count was updated successfully", response.getBody());
  }

  @Test
  public void testRemoveMajorFromDeptNoMajorsLeft() {
    Department elenDeptWithZeroMajors = new Department("ELEN", new HashMap<>(),
        "Ioannis Kymissis", 0);
    IndividualProjectApplication.myFileDatabase
      .getDepartmentMapping().put("ELEN", elenDeptWithZeroMajors);
    ResponseEntity<?> response = routeController.removeMajorFromDept("ELEN");
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Attribute was updated or is at minimum", response.getBody());
  }

  @Test
  public void testRemoveMajorFromDeptNotFound() {
    ResponseEntity<?> response = routeController.removeMajorFromDept("NONEXISTENT");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }


  @Test
  public void testDropStudentSuccess() {
    ResponseEntity<?> response = routeController.dropStudent("ELEN", 1201);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Student has been dropped.", response.getBody());
  }

  @Test
  public void testDropStudentCourseNotFound() {
    ResponseEntity<?> response = routeController.dropStudent("ELEN", 9999);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testIdentifyDeptChairSuccess() {
    ResponseEntity<?> response = routeController.identifyDeptChair("ELEN");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Ioannis Kymissis is the department chair.", response.getBody().toString());
  }

  @Test
  public void testIdentifyDeptChairDepartmentNotFound() {
    ResponseEntity<?> response = routeController.identifyDeptChair("NON_EXISTENT_DEPT");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody().toString());
  }

  @Test
  public void testFindCourseLocationSuccess() {
    ResponseEntity<?> response = routeController.findCourseLocation("ELEN", 1201);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().toString().contains("301 PUP is where the course is located."));
  }

  @Test
  public void testFindCourseLocationCourseNotFound() {
    ResponseEntity<?> response = routeController.findCourseLocation("ELEN", 9999);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody().toString());
  }

  @Test
  public void testFindCourseLocationDepartmentNotFound() {
    ResponseEntity<?> response = routeController.findCourseLocation("NON_EXISTENT_DEPT", 1201);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody().toString());
  }

  @Test
  public void testFindCourseInstructorSuccess() {
    ResponseEntity<?> response = routeController.findCourseInstructor("ELEN", 1201);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().toString()
        .contains("David G Vallancourt is the instructor for the course."));
  }

  @Test
  public void testFindCourseInstructorCourseNotFound() {
    ResponseEntity<?> response = routeController.findCourseInstructor("ELEN", 9999);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testFindCourseInstructorDepartmentNotFound() {
    ResponseEntity<?> response = routeController.findCourseInstructor("NON_EXISTENT_DEPT", 1201);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }


}
