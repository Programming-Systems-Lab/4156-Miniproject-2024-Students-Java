package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * This file contains unit tests for the {@link RouteController} class.
 *
 * <p>This class tests - in varying conditions - that the functions in the
 * {@link RouteController} class function as expected - including error testing.
 * The following tests make use of Mockito to mock dependencies and emulate
 * department and course manipulation, setting values in the department among others.
 *
 * <p>The tests include scenarios for:
 * <ul>
 *   <li>Checking course capacity</li>
 *   <li>Adding and removing students (majors) from departments</li>
 *   <li>Setting the enrollment count of students for courses</li>
 *   <li>Adding or dropping students from courses</li>
 *   <li>Identifying department chairs</li>
 *   <li>Retrieving department, courses and their information</li>
 * </ul>
 */
public class RouteControllerUnitTests {

  private RouteController testRouteController;
  private Department testDepartment;
  private Course testCourse;

  /**
   * In this case, the {@code setupRouteControllerUnitTests} method ensures a clean mock
   * for each test so that conditions are constant for each test.
   *
   * <p>This method primarily creates a sample Course and Department object used
   * for testing purposes. It also includes the declaration of a mock {@code @class myFileDatabase}
   * object to test with. Given the mock {@code myFileDatabase} object,
   * testing follows consistent formatting.</p>
   */
  @BeforeEach
  public void setupRouteControllerUnitTests() {
    testRouteController = new RouteController();
    Map<String, Course> courses = new HashMap<>();
    Map<String, Department> departmentMapping = new HashMap<>();

    // Generate sample Course & Department data
    testDepartment = new Department("ECON", courses, "Hellen Keller", 500);
    departmentMapping.put("ECON", testDepartment);

    testCourse = new Course("John Doe", "Room 101", "9:00 AM - 10:30 AM", 30);
    courses.put("4156", testCourse);

    // Generate Mock myFileDatabase information.
    MyFileDatabase myFileDatabase = mock(MyFileDatabase.class); // Local variable
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    IndividualProjectApplication.myFileDatabase = myFileDatabase;
  }

  /**
   * Tests the successful retrieval of a department.
   *
   *<p>This test verifies that when a valid department code is provided,
   * the {@code retrieveDepartment()} method returns:
   * <ul>
   *     <li>HTTP 200 response</li>
   *     <li>Appropriate department info</li>
   *     <li>Appropriate response message</li>
   * </ul>
   */
  @Test
  public void testRetrieveDepartmentSuccess() {
    ResponseEntity<?> testResponse = testRouteController.retrieveDepartment("ECON");

    String responseBody = (String) testResponse.getBody();
    assertNotNull(responseBody);

    assertTrue(responseBody.contains(testCourse.toString()));
    assertTrue(responseBody.contains("4156"));
  }

  /**
   * Tests the retrieval of a department when the department is not found.
   *
   * <p>This test validates that - given an invalid or non-existent department code -
   * the {@code RetrieveDepartment()} method will return a NOT FOUND error code
   * with a fitting error message.
   */
  @Test
  public void testRetrieveDepartmentAndDepartmentNotFound() {
    ResponseEntity<?> testResponse = testRouteController.retrieveDepartment("MATH");

    assertEquals(HttpStatus.NOT_FOUND, testResponse.getStatusCode());
    assertEquals("Department Not Found", testResponse.getBody());
  }

  /**
   * Tests the successful retrieval of a Course.
   *
   *<p>This test verifies that when a valid department code is provided,
   * the {@code retrieveCourse()} method returns:
   * <ul>
   *     <li>HTTP 200 response</li>
   *     <li>Appropriate course info in string format.</li>
   * </ul>
   */
  @Test
  public void testRetrieveCourseSuccess() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Department Found", HttpStatus.OK))
      .when(spyController).retrieveDepartment("ECON");

    ResponseEntity<?> testResponse = spyController.retrieveCourse("ECON", 4156);

    assertEquals("\nInstructor: John Doe; Location: Room 101; "
        + "Time: 9:00 AM - 10:30 AM; Capacity: 30", testResponse.getBody());
    assertEquals(HttpStatus.OK, testResponse.getStatusCode());
  }

  /**
   * Tests the retrieval of a course when the course is not found.
   *
   * <p>This test validates that - given a valid department and an
   * invalid or non-existent department code - the {@code RetrieveDepartment()}
   * method will return a NOT FOUND error code with a fitting error message.
   */
  @Test
  public void testRetrieveCourseCourseNotFound() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Department Found", HttpStatus.OK))
      .when(spyController).retrieveDepartment("ECON");

    ResponseEntity<?> testResponse = spyController.retrieveCourse("ECON", 9999);

    assertEquals("Course Not Found", testResponse.getBody());
    assertEquals(HttpStatus.NOT_FOUND, testResponse.getStatusCode());
  }

  /**
   * Tests the retrieval of a course when the department is not found.
   *
   * <p>This test validates that - given an invalid department code,
   * the {@code retrieveCourse()} method will return a NOT FOUND error
   * code with a fitting error message.
   */
  @Test
  public void testRetrieveCourseAndDepartmentNotFound() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND))
      .when(spyController).retrieveDepartment("MATH");

    ResponseEntity<?> testResponse = spyController.retrieveCourse("MATH", 4156);

    assertEquals("Department Not Found", testResponse.getBody());
    assertEquals(HttpStatus.NOT_FOUND, testResponse.getStatusCode());
  }

  /**
   * Tests the checking of a course when it is full given the course
   * exists and is valid.
   *
   * <p>This test validates that - given a valid course code, and
   * the Course is at capacity the {@code isCourseFull()}
   * method will return a boolean. and an OK code
   **/
  @Test
  public void testIsCourseFullAndCourseExistsAndIsFull() {
    testCourse.setEnrolledCount(30);

    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Course Found", HttpStatus.OK))
      .when(spyController).retrieveCourse("ECON", 4156);

    ResponseEntity<?> testResponse = spyController.isCourseFull("ECON", 4156);

    assertTrue(testResponse.getBody() instanceof Boolean);
    assertTrue((Boolean) testResponse.getBody());
    assertEquals(HttpStatus.OK, testResponse.getStatusCode());
  }

  /**
   * Tests the checking of a course when it is not full given the course
   * exists and is valid.
   *
   * <p>This test validates that - given a valid course code, and
   * the Course is not at capacity the {@code isCourseFull()}
   * method will return a boolean. and an OK code
   **/
  @Test
  public void testIsCourseFullAndCourseExistsAndIsNotFull() {
    testCourse.setEnrolledCount(20);

    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Course Found", HttpStatus.OK))
      .when(spyController).retrieveCourse("ECON", 4156);

    ResponseEntity<?> testResponse = spyController.isCourseFull("ECON", 4156);

    assertTrue(testResponse.getBody() instanceof Boolean);
    assertFalse((Boolean) testResponse.getBody());
    assertEquals(HttpStatus.OK, testResponse.getStatusCode());
  }

  /**
   * Tests if a course is full, given the Course is not found.
   *
   * <p>This test validates that - given an invalid or non-existent Course code,
   * the {@code isCourseFull()} method will return a NOT FOUND error
   * code with a fitting error message.
   */
  @Test
  public void testIsCourseFullAndCourseNotFound() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND))
      .when(spyController).retrieveCourse("COMS", 9999);

    ResponseEntity<?> testResponse = spyController.isCourseFull("COMS", 9999);

    assertEquals("Course Not Found", testResponse.getBody());
    assertEquals(HttpStatus.NOT_FOUND, testResponse.getStatusCode());
  }

  /**
   * Tests the retrieval of the department's number of majors (# students)
   * given the department is valid and exists.
   *
   * <p>This test validates that - given an invalid department code,
   * the {@code getMajorCtFromDept()} method will return an OK
   * code with a fitting response in a string format.
   */
  @Test
  public void testGetMajorCtFromDeptAndDepartmentExists() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Department Found", HttpStatus.OK))
      .when(spyController).retrieveDepartment("ECON");

    ResponseEntity<?> testResponse = spyController.getMajorCtFromDept("ECON");

    String expectedMessage = "There are: " + testDepartment.getNumberOfMajors()
          + " majors in the department";
    assertEquals(expectedMessage, testResponse.getBody());
    assertEquals(HttpStatus.OK, testResponse.getStatusCode());
  }

  /**
   * Tests the retrieval of the department's number of majors (# students)
   * given the department is not found.
   *
   * <p>This test validates that - given an invalid department code,
   * the {@code getMajorCtFromDept()} method will return a NOT FOUND error
   * code with a fitting error message.
   */
  @Test
  public void testGetMajorCtFromDeptAndDepartmentNotFound() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND))
      .when(spyController).retrieveDepartment("MATH");

    ResponseEntity<?> testResponse = spyController.getMajorCtFromDept("MATH");

    assertEquals("Department Not Found", testResponse.getBody());
    assertEquals(HttpStatus.FORBIDDEN, testResponse.getStatusCode());
  }

  /**
   * Tests the successful retrieval of a Department.
   *
   *<p>Firstly this tests assumes the Deparment exists. Then
   * this test verifies that when a valid department code
   * is provided, the {@code identifyDeptChair()} method returns
   * <ul>
   *     <li>HTTP 200 response</li>
   *     <li>Appropriate department chair info in string format.</li>
   * </ul>
   */
  @Test
  public void testIdentifyDeptChairAndDepartmentExists() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Department Found", HttpStatus.OK))
      .when(spyController).retrieveDepartment("ECON");

    ResponseEntity<?> testResponse = spyController.identifyDeptChair("ECON");

    String expectedMessage = testDepartment.getDepartmentChair() + " is the department chair.";
    assertEquals(expectedMessage, testResponse.getBody());
    assertEquals(HttpStatus.OK, testResponse.getStatusCode());
  }

  /**
   * Tests the retrieval of the department's department chair,
   * given the department is not found.
   *
   * <p>This test validates that - given an invalid department code,
   * the {@code identifyDeptChair()} method will return a NOT FOUND error
   * code with a fitting error message.
   */
  @Test
  public void testIdentifyDeptChairAndDepartmentNotFound() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND))
      .when(spyController).retrieveDepartment("MATH");

    ResponseEntity<?> testResponse = spyController.identifyDeptChair("MATH");

    assertEquals("Department Not Found", testResponse.getBody());
    assertEquals(HttpStatus.NOT_FOUND, testResponse.getStatusCode());
  }

  /**
   * Tests the successful retrieval of a Course Location.
   *
   *<p>Firstly this tests if the Course exists. If it does exist, then
   * this test verifies that when a valid department code and course code
   * is provided, the {@code findCourseLocation()} method returns:
   * <ul>
   *     <li>HTTP 200 response</li>
   *     <li>Appropriate course location info in string format.</li>
   * </ul>
   */
  @Test
  public void testFindCourseLocationAndCourseExists() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Course Found", HttpStatus.OK))
      .when(spyController).retrieveCourse("ECON", 4156);

    ResponseEntity<?> testResponse = spyController.findCourseLocation("ECON", 4156);

    String expectedMessage = testCourse.getCourseLocation() + " is where the course is located.";
    assertEquals(expectedMessage, testResponse.getBody());
    assertEquals(HttpStatus.OK, testResponse.getStatusCode());
  }

  /**
   * Tests the retrieval of a course's location, given the course
   * does not exist or is not found.
   *
   * <p>This test validates that - given an invalid course code,
   * the {@code findCourseLocation()} method will return a NOT FOUND error
   * code with a fitting error message.
   */
  @Test
  public void testFindCourseLocationAndCourseDoesNotExist() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND))
      .when(spyController).retrieveCourse("COMS", 9999);

    ResponseEntity<?> testResponse = spyController.findCourseLocation("COMS", 9999);

    assertEquals("Course Not Found", testResponse.getBody());
    assertEquals(HttpStatus.NOT_FOUND, testResponse.getStatusCode());
  }

  /**
   * Tests the successful retrieval of a Course Instructor.
   *
   *<p>Firstly this tests if the Course exists. If it does exist, then
   * this test verifies that when a valid department code and course code
   * is provided, the {@code findCourseInstructor()} method checks if the
   * return of a:
   * <ul>
   *     <li>HTTP 200 response</li>
   *     <li>Appropriate course instructor info in string format.</li>
   * </ul>
   */
  @Test
  public void testFindCourseInstructorAndCourseExists() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Course Found", HttpStatus.OK))
      .when(spyController).retrieveCourse("ECON", 4156);

    ResponseEntity<?> testResponse = spyController.findCourseInstructor("ECON", 4156);

    String expectedMessage = testCourse.getInstructorName() + " is the instructor for the course.";
    assertEquals(expectedMessage, testResponse.getBody());
    assertEquals(HttpStatus.OK, testResponse.getStatusCode());
  }

  /**
   * Tests the retrieval of a course's instructor, given the course
   * does not exist or is not found.
   *
   * <p>This test validates that - given an invalid course code,
   * the {@code findCourseInstructor()} method will return a NOT FOUND error
   * code with a fitting error message.
   */
  @Test
  public void testFindCourseInstructorAndCourseDoesNotExist() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND))
      .when(spyController).retrieveCourse("COMS", 9999);

    ResponseEntity<?> testResponse = spyController.findCourseInstructor("COMS", 9999);

    assertEquals("Course Not Found", testResponse.getBody());
    assertEquals(HttpStatus.NOT_FOUND, testResponse.getStatusCode());
  }

  /**
   * Tests the successful retrieval of a Course Time.
   *
   *<p>Firstly this tests if the Course exists. If it does exist, then
   * this test verifies that when a valid department code and course code
   * is provided, the {@code findCourseTime()} method checks if the
   * return of a:
   * <ul>
   *     <li>HTTP 200 response</li>
   *     <li>Appropriate course time info in string format.</li>
   * </ul>
   */
  @Test
  public void testFindCourseTimeAndCourseExists() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Course Found", HttpStatus.OK))
      .when(spyController).retrieveCourse("ECON", 4156);

    ResponseEntity<?> testResponse = spyController.findCourseTime("ECON", 4156);

    assertEquals(HttpStatus.OK, testResponse.getStatusCode());
    String expectedMessage = "The course meets at: " + testCourse.getCourseTimeSlot();
    assertEquals(expectedMessage, testResponse.getBody());
  }

  /**
   * Tests the retrieval of a course's timeslot, given the course
   * does not exist or is not found.
   *
   * <p>This test validates that - given an invalid course code,
   * the {@code findCourseTime()} method will return a NOT FOUND error
   * code with a fitting error message.
   */
  @Test
  public void testFindCourseTimeAndCourseDoesNotExist() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND))
      .when(spyController).retrieveCourse("COMS", 9999);

    ResponseEntity<?> testResponse = spyController.findCourseTime("COMS", 9999);

    assertEquals("Course Not Found", testResponse.getBody());
    assertEquals(HttpStatus.NOT_FOUND, testResponse.getStatusCode());
  }

  /**
   * Tests the addition of a major (# students) to a department
   * given the department exists and is valid
   *
   * <p>This test validates that - given an invalid department code,
   * the {@code addMajorToDept()} method will return an OK
   * code with a fitting message. It also ensures the number
   * of majors was incremented.
   */
  @Test
  public void testAddMajorToDeptAndDepartmentExists() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Department Found", HttpStatus.OK))
      .when(spyController).retrieveDepartment("ECON");

    int initNumMajors = testDepartment.getNumberOfMajors();

    ResponseEntity<?> testResponse = spyController.addMajorToDept("ECON");

    assertEquals(HttpStatus.OK, testResponse.getStatusCode());
    assertEquals(initNumMajors + 1, testDepartment.getNumberOfMajors());
    assertEquals("Attribute was updated successfully", testResponse.getBody());
  }

  /**
   * Tests the addition of a major (# students) to a department
   * given the department is not found.
   *
   * <p>This test validates that - given an invalid department code,
   * the {@code addMajorToDept()} method will return a NOT FOUND error
   * code with a fitting error message. It also ensures the number
   * of majors was not incremented.
   */
  @Test
  public void testAddMajorToDeptAndDepartmentNotFound() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND))
      .when(spyController).retrieveDepartment("MATH");

    ResponseEntity<?> testResponse = spyController.addMajorToDept("MATH");

    assertEquals("Department Not Found", testResponse.getBody());
    assertEquals(500, testDepartment.getNumberOfMajors());
    assertEquals(HttpStatus.NOT_FOUND, testResponse.getStatusCode());
  }

  /**
   * Tests the removal of a major (a student) from a specific department
   * assuming the department exists and is valid.
   *
   * <p>This test validates that - given a valid department code,
   * the {@code removeMajorFromDept()} method will return an OK message
   * and validate if the decrement of number of majors has been completed.
   */
  @Test
  public void testRemoveMajorFromDeptAndDepartmentExists() {
    int initialNumberOfMajors = testDepartment.getNumberOfMajors();

    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Department Found", HttpStatus.OK))
      .when(spyController).retrieveDepartment("ECON");

    ResponseEntity<?> testResponse = spyController.removeMajorFromDept("ECON");

    assertEquals(HttpStatus.OK, testResponse.getStatusCode());
    assertEquals("Attribute was updated or is at minimum", testResponse.getBody());
    assertEquals(initialNumberOfMajors - 1, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests the successful dropping of a student from a course.
   *
   * <p>This test verifies that a student is dropped from a course
   * when if the enrolledStudentCount is above 0
   * <ul>
   *     <li>HTTP 200 response</li>
   *     <li>Appropriate department info</li>
   *     <li>EnrolledStudentCount after dropping student</li>
   * </ul>
   */
  @Test
  public void testDropStudentFromCourseSuccess() {
    Course course = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    course.setEnrolledCount(1); // Ensure at least one student is enrolled

    Map<String, Course> coursesMapping = new HashMap<>();
    coursesMapping.put("1004", course);
    testDepartment.setCourseSelection(coursesMapping);

    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Course Found", HttpStatus.OK))
      .when(spyController).retrieveCourse("ECON", 1004);

    ResponseEntity<?> testResponse = spyController.dropStudent("ECON", 1004);

    assertEquals("Student has been dropped.", testResponse.getBody());
    assertEquals(0, course.getEnrolledCount());  // Ensure the count is decremented
    assertEquals(HttpStatus.OK, testResponse.getStatusCode());
  }

  /**
   * Tests the dropping of a student from a course given the course
   * does not exist or is not found.
   *
   * <p>This test validates that - given an invalid course code,
   * the {@code dropStudentFromCourse()} method will return a NOT FOUND error
   * code with a fitting error message.
   */
  @Test
  public void testDropStudentFromCourseAndCourseNotFound() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND))
      .when(spyController).retrieveCourse("COMS", 9999);  // An invalid course code

    ResponseEntity<?> testResponse = spyController.dropStudent("COMS", 9999);

    assertEquals("Course Not Found", testResponse.getBody());
    assertEquals(HttpStatus.NOT_FOUND, testResponse.getStatusCode());
  }

  /**
   * Tests the dropping of a student from a course given the course
   * is empty.
   *
   * <p>This test validates that - given a course code -
   * the {@code dropStudentFromCourse()} method will return a BAD REQUEST
   * code and that no students will be dropped from the course
   * since the course is already empty.
   */
  @Test
  public void testDropStudentFromCourseAndNoStudentsToDrop() {
    Course course = new Course("Donald Duck", "3939 DIS",
        "11:40-18:00", 259);
    course.setEnrolledCount(0); // No students enrolled

    Map<String, Course> coursesMapping = new HashMap<>();
    coursesMapping.put("3939", course);
    testDepartment.setCourseSelection(coursesMapping);

    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Course Found", HttpStatus.OK))
      .when(spyController).retrieveCourse("ECON", 3939);

    ResponseEntity<?> testResponse = spyController.dropStudent("ECON", 3939);

    assertEquals(HttpStatus.BAD_REQUEST, testResponse.getStatusCode());
    assertEquals("Student has not been dropped.", testResponse.getBody());
    assertEquals(0, course.getEnrolledCount());
  }

  /**
   * Tests the setting of enrollment of a course given the course
   * does not exist or is not found.
   *
   * <p>This test validates that - given an invalid course code,
   * the {@code setEnrollmentCount()} method will return a NOT FOUND error
   * code with a fitting error message.
   */
  @Test
  public void testSetEnrollmentCountAndCourseNotFound() {
    RouteController spyController = spy(testRouteController);
    doReturn(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND))
      .when(spyController).retrieveCourse("COMS", 9999);  // Invalid course code

    ResponseEntity<?> testResponse = spyController.setEnrollmentCount("COMS", 9999, 100);

    assertEquals("Course Not Found", testResponse.getBody());
    assertEquals(HttpStatus.NOT_FOUND, testResponse.getStatusCode());
  }
}