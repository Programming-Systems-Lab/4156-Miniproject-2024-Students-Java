package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;


/**
 * Writes test cases to check methods in the RouteController.
 */
@SpringBootTest
@ContextConfiguration
public class RouteControllerUnitTests  {

  /**
   * Sets up a mocked MyFileDatabase, a mocked Department, a mocked Course,
   * a valid courseMap and departmentMap, and return them whenever called
   * getCourseSelection() method and getDepartmentMapping() method before
   * each test.
   */
  @BeforeEach
  public void setUpRouteControllerForTesting() {
    testRouteController = new RouteController();

    mockDatabase = mock(MyFileDatabase.class);
    mockDepartment = mock(Department.class);
    mockCourse = mock(Course.class);

    IndividualProjectApplication.overrideDatabase(mockDatabase);

    courseMap = new HashMap<>();
    courseMap.put("1004", mockCourse);

    departmentMap = new HashMap<>();
    departmentMap.put("COMS", mockDepartment);

    when(mockDepartment.getCourseSelection()).thenReturn(courseMap);
    when(mockDatabase.getDepartmentMapping()).thenReturn(departmentMap);
  }

  /**
   * Sets IndividualProjectApplication.myFileDatabase to null,
   * and sets IndividualProjectApplication.saveData be true
   * after each test.
   */
  @AfterEach
  public void tearDown() {
    IndividualProjectApplication.myFileDatabase = null;
    IndividualProjectApplication.setSaveData(true);
  }

  /**
   * Tests index() method.
   */
  @Test
  public void indexTest() {
    String expectedResult = "Welcome, "
        + "in order to make an API call direct your browser or Postman to an endpoint "
        + "\n\n This can be done using the following format: \n\n http:127.0.0"
        + ".1:8080/endpoint?arg=value";
    assertEquals(expectedResult, testRouteController.index());
  }

  /**
   * Tests retrieveDepartment() method.
   */
  @Test
  public void retrieveDepartmentTest() {
    when(mockDepartment.toString()).thenReturn("COMS");

    ResponseEntity<?> response = testRouteController.retrieveDepartment("COMS");

    assertEquals("COMS", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests retrieveDepartment() method when department not found.
   */
  @Test
  public void retrieveDepartmentTestWhenDeptNotFound() {
    ResponseEntity<?> response = testRouteController.retrieveDepartment("IEOR");

    assertEquals("Department Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests retrieveDepartment() method when there is exception.
   */
  @Test
  public void retrieveDepartmentTestWhenExceptionExists() {
    when(mockDepartment.toString()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.retrieveDepartment("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /**
   * Tests retrieveCourse() method.
   */
  @Test
  public void retrieveCourseTest() {
    when(mockCourse.toString()).thenReturn("1004");

    ResponseEntity<?> response = testRouteController.retrieveCourse("COMS", 1004);

    assertEquals("1004", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests retrieveCourse() method when course not found.
   */
  @Test
  public void retrieveCourseTestCourseNotFound() {
    ResponseEntity<?> response = testRouteController.retrieveCourse("COMS", 3134);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests retrieveCourse() method when department not found.
   */
  @Test
  public void retrieveCoursesTestWhenDeptNotFound() {
    ResponseEntity<?> response = testRouteController.retrieveCourse("IEOR", 1004);

    assertEquals("Department Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests retrieveCourse() method when there is exception.
   */
  @Test
  public void retrieveCourseTestExceptionExists() {
    when(mockCourse.toString()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.retrieveCourse("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /**
   * Tests isCourseFull() method.
   */
  @Test
  public void isCourseFullTest() {
    when(mockCourse.isCourseFull()).thenReturn(true);

    ResponseEntity<?> response = testRouteController.isCourseFull("COMS", 1004);

    assertEquals(true, response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests isCourseFull() method when course not found.
   */
  @Test
  public void isCourseNotFullTestWhenCourseNotFound() {
    ResponseEntity<?> response = testRouteController.isCourseFull("COMS", 3134);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests isCourseFull() method when there is exception.
   */
  @Test
  public void isCourseFullTestWhenExceptionExists() {
    when(mockCourse.isCourseFull()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.isCourseFull("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /**
   * Tests getMajorCtFromDept() method.
   */
  @Test
  public void getMajorCtFromDeptTest() {
    when(mockDepartment.getNumberOfMajors()).thenReturn(2700);

    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("COMS");

    assertEquals("There are: 2700 majors in the department", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests getMajorCtFromDept() method when department not found.
   */
  @Test
  public void getMajorCtFromTestWhenDeptNotFound() {
    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("IEOR");

    assertEquals("Department Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests getMajorCtFromDept() method when there is exception.
   */
  @Test
  public void getMajorCtFromDeptTestWhenExceptionExists() {
    when(mockDepartment.getNumberOfMajors()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /**
   * Tests identifyDeptChairTest() method.
   */
  @Test
  public void identifyDeptChairTest() {
    when(mockDepartment.getDepartmentChair()).thenReturn("Luca Carloni");

    ResponseEntity<?> response = testRouteController.identifyDeptChair("COMS");

    assertEquals("Luca Carloni is the department chair.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests identifyDeptChairTest() method when department not found.
   */
  @Test
  public void identifyDeptChairTestWhenDeptNotFound() {
    ResponseEntity<?> response = testRouteController.identifyDeptChair("IEOR");

    assertEquals("Department Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests identifyDeptChairTest() method when there is exception.
   */
  @Test
  public void identifyDeptChairTestWhenExceptionExists() {
    when(mockDepartment.getDepartmentChair()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.identifyDeptChair("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /**
   * Tests findCourseLocation() method.
   */
  @Test
  public void findCourseLocationTest() {
    when(mockCourse.getCourseLocation()).thenReturn("417 IAB");

    ResponseEntity<?> response = testRouteController.findCourseLocation("COMS", 1004);

    assertEquals("417 IAB is where the course is located.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests findCourseLocation() method when course not found.
   */
  @Test
  public void findCourseLocationTestWhenCourseNotFound() {
    ResponseEntity<?> response = testRouteController.findCourseLocation("COMS", 3134);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests findCourseLocation() method when there is exception.
   */
  @Test
  public void findCourseLocationTestWhenExceptionExists() {
    when(mockCourse.getCourseLocation()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.findCourseLocation("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /**
   * Tests findCourseInstructor() method.
   */
  @Test
  public void findCourseInstructorTest() {
    when(mockCourse.getInstructorName()).thenReturn("Adam Cannon");

    ResponseEntity<?> response = testRouteController.findCourseInstructor("COMS", 1004);

    assertEquals("Adam Cannon is the instructor for the course.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests findCourseInstructor() method when course not found.
   */
  @Test
  public void findCourseInstructorTestWhenCourseNotFound() {
    ResponseEntity<?> response = testRouteController.findCourseInstructor("COMS", 3134);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests findCourseInstructor() method when there is exception.
   */
  @Test
  public void findCourseInstructorTestWhenExceptionExists() {
    when(mockCourse.getInstructorName()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.findCourseInstructor("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /**
   * Tests findCourseTime() method.
   */
  @Test
  public void findCourseTimeTest() {
    when(mockCourse.getCourseTimeSlot()).thenReturn("11:40-12:55");

    ResponseEntity<?> response = testRouteController.findCourseTime("COMS", 1004);

    assertEquals("The course meets at: 11:40-12:55.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests findCourseTime() method when course not found.
   */
  @Test
  public void findCourseTimeTestWhenCourseNotFound() {
    ResponseEntity<?> response = testRouteController.findCourseTime("COMS", 3134);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests findCourseTime() method when there is exception.
   */
  @Test
  public void findCourseTimeTestWhenExceptionExists() {
    when(mockCourse.getCourseTimeSlot()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.findCourseTime("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /**
   * Tests addMajorToDept() method.
   */
  @Test
  public void addMajorToDeptTest() {
    ResponseEntity<?> response = testRouteController.addMajorToDept("COMS");

    verify(mockDepartment, times(1)).addPersonToMajor();
    assertEquals("Attribute was updated successfully", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests addMajorToDept() method when department not found.
   */
  @Test
  public void addMajorToDeptTestWhenDeptNotFound() {
    ResponseEntity<?> response = testRouteController.addMajorToDept("IEOR");

    assertEquals("Department Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests addMajorToDept() method when there is exception.
   */
  @Test
  public void addMajorToDeptTestWhenExceptionExists() {
    doThrow(new RuntimeException("Database error")).when(mockDepartment).addPersonToMajor();

    ResponseEntity<?> response = testRouteController.addMajorToDept("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /**
   * Tests removeMajorFromDept() method.
   */
  @Test
  public void removeMajorFromDeptTest() {
    ResponseEntity<?> response = testRouteController.removeMajorFromDept("COMS");

    verify(mockDepartment, times(1)).dropPersonFromMajor();
    assertEquals("Attribute was updated or is at minimum", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests removeMajorFromDept() method when department not found.
   */
  @Test
  public void removeMajorFromDeptTestWhenDeptNotFound() {
    ResponseEntity<?> response = testRouteController.removeMajorFromDept("IEOR");

    assertEquals("Department Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests removeMajorFromDept() method when there is exception.
   */
  @Test
  public void removeMajorFromDeptTestWhenExceptionExists() {
    doThrow(new RuntimeException("Database error")).when(mockDepartment).dropPersonFromMajor();

    ResponseEntity<?> response = testRouteController.removeMajorFromDept("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /**
   * Tests dropStudent() method.
   */
  @Test
  public void dropStudentTest() {
    when(mockCourse.dropStudent()).thenReturn(true);

    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 1004);

    assertEquals("Student has been dropped.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests dropStudent() method when fails.
   */
  @Test
  public void dropStudentTestWhenFail() {
    when(mockCourse.dropStudent()).thenReturn(false);

    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 1004);

    assertEquals("Student has not been dropped.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests dropStudent() method when course not found.
   */
  @Test
  public void dropStudentTestWhenCourseNotFound() {
    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 3134);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests dropStudent() method when there is exception.
   */
  @Test
  public void dropStudentTestWhenExceptionExists() {
    when(mockCourse.dropStudent()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /**
   * Tests setEnrollmentCount() method.
   */
  @Test
  public void setEnrollmentCountTest() {
    ResponseEntity<?> response = testRouteController.setEnrollmentCount("COMS",
                                1004, 249);

    verify(mockCourse, times(1)).setEnrolledStudentCount(249);
    assertEquals("Attribute was updated successfully.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests setEnrollmentCount() method when course not found.
   */
  @Test
  public void setEnrollmentCountTestWhenCourseNotFound() {
    ResponseEntity<?> response = testRouteController.setEnrollmentCount("COMS",
                                3134, 249);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests setEnrollmentCount() method when there is exception.
   */
  @Test
  public void setEnrollmentCountTestWhenExceptionExists() {
    doThrow(new RuntimeException("Database error")).when(mockCourse).setEnrolledStudentCount(249);

    ResponseEntity<?> response = testRouteController.setEnrollmentCount("COMS",
                                1004, 249);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /**
   * Tests changeCourseTime() method.
   */
  @Test
  public void changeCourseTimeTest() {
    ResponseEntity<?> response = testRouteController.changeCourseTime("COMS",
                                1004, "4:10-5:25");

    verify(mockCourse, times(1)).reassignTime("4:10-5:25");
    assertEquals("Attribute was updated successfully.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests changeCourseTime() method when course not found.
   */
  @Test
  public void changeCourseTimeTestWhenCourseNotFound() {
    ResponseEntity<?> response = testRouteController.changeCourseTime("COMS",
                                3134, "4:10-5:25");

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests changeCourseTime() method when there is exception.
   */
  @Test
  public void changeCourseTimeTestWhenExceptionExists() {
    doThrow(new RuntimeException("Database error")).when(mockCourse)
                                .reassignTime("4:10-5:25");

    ResponseEntity<?> response = testRouteController.changeCourseTime("COMS",
                                1004, "4:10-5:25");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /**
   * Tests changeCourseTeacher() method.
   */
  @Test
  public void changeCourseTeacherTest() {
    ResponseEntity<?> response = testRouteController
                  .changeCourseTeacher("COMS", 1004, "Brian Borowski");

    verify(mockCourse, times(1))
                        .reassignInstructor("Brian Borowski");
    assertEquals("Attribute was updated successfully.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests changeCourseTeacher() method when course not found.
   */
  @Test
  public void changeCourseTeacherTestWhenCourseNotFound() {
    ResponseEntity<?> response = testRouteController
                  .changeCourseTeacher("COMS", 3134, "Brian Borowski");

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests changeCourseTeacher() method when there is exception.
   */
  @Test
  public void changeCourseTeacherTestWhenExceptionExists() {
    doThrow(new RuntimeException("Database error")).when(mockCourse)
                                            .reassignInstructor("Brian Borowski");

    ResponseEntity<?> response = testRouteController
                  .changeCourseTeacher("COMS", 1004, "Brian Borowski");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /**
   * Tests changeCourseLocation() method.
   */
  @Test
  public void changeCourseLocationTest() {
    ResponseEntity<?> response = testRouteController.changeCourseLocation("COMS",
                                1004, "301 URIS");

    verify(mockCourse, times(1)).reassignLocation("301 URIS");
    assertEquals("Attribute was updated successfully.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Tests changeCourseLocation() method when course not found.
   */
  @Test
  public void changeCourseLocationTestWhenCourseNotFound() {
    ResponseEntity<?> response = testRouteController.changeCourseLocation("COMS",
                                3134, "301 URIS");

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Tests changeCourseLocation() method when there is exception.
   */
  @Test
  public void changeCourseLocationTestWhenExceptionExists() {
    doThrow(new RuntimeException("Database error")).when(mockCourse)
                            .reassignLocation("301 URIS");

    ResponseEntity<?> response = testRouteController.changeCourseLocation("COMS",
                                1004, "301 URIS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  /** The MyFileDatabase instance used for testing. */
  private MyFileDatabase mockDatabase;

  /** The RouteController instance used for testing. */
  private RouteController testRouteController;

  /** The Department instance used for testing. */
  private Department mockDepartment;

  /** The Course instance used for testing. */
  private Course mockCourse;

  /** The Map instance used for testing. */
  private Map<String, Department> departmentMap;

  /** The Map instance used for testing. */
  private Map<String, Course> courseMap;
}
