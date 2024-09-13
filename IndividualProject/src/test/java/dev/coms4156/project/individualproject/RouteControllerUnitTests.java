package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;


/**
 * Writes test case to check methods in the RouteController.
 */
@SpringBootTest
@ContextConfiguration
public class RouteControllerUnitTests  {

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


  @AfterEach
  public void tearDown() {
    IndividualProjectApplication.myFileDatabase = null;
    IndividualProjectApplication.setSaveData(true);
  }


  @Test
  public void indexTest() {
    String expectedResult = "Welcome, in order to make an API call direct your browser or Postman to an endpoint "
        + "\n\n This can be done using the following format: \n\n http:127.0.0"
        + ".1:8080/endpoint?arg=value";
    assertEquals(expectedResult, testRouteController.index());
  }


  @Test
  public void retrieveDepartmentFoundTest() {
    when(mockDepartment.toString()).thenReturn("COMS");

    ResponseEntity<?> response = testRouteController.retrieveDepartment("COMS");

    assertEquals("COMS", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void retrieveDepartmentNotFoundTest() {
    ResponseEntity<?> response = testRouteController.retrieveDepartment("IEOR");

    assertEquals("Department Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void retrieveDepartmentExceptionTest() {
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.retrieveDepartment("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  public void retrieveCourseFoundTest() {
    when(mockCourse.toString()).thenReturn("1004");

    ResponseEntity<?> response = testRouteController.retrieveCourse("COMS", 1004);

    assertEquals("1004", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void retrieveCourseNotFoundTest() {
    ResponseEntity<?> response = testRouteController.retrieveCourse("COMS", 3134);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void retrieveCoursesDepartmentNotFoundTest() {
    ResponseEntity<?> response = testRouteController.retrieveCourse("IEOR", 1004);

    assertEquals("Department Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void retrieveCourseExceptionTest() {
    when(mockCourse.toString()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.retrieveCourse("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  public void isCourseFullTest() {
    when(mockCourse.isCourseFull()).thenReturn(true);

    ResponseEntity<?> response = testRouteController.isCourseFull("COMS", 1004);

    assertEquals(true, response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void isCourseNotFullCourseNotFoundTest() {
    ResponseEntity<?> response = testRouteController.isCourseFull("COMS", 3134);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void isCourseFullExceptionTest() {
    when(mockCourse.isCourseFull()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.isCourseFull("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  public void getMajorCtFromDeptTest() {
    when(mockDepartment.getNumberOfMajors()).thenReturn(2700);

    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("COMS");

    assertEquals("There are: 2700 majors in the department", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void getMajorCtFromDeptNotFoundTest() {
    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("IEOR");

    assertEquals("Department Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void getMajorCtFromDeptExceptionTest() {
    when(mockDepartment.getNumberOfMajors()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  public void identifyDeptChairTest() {
    when(mockDepartment.getDepartmentChair()).thenReturn("Luca Carloni");

    ResponseEntity<?> response = testRouteController.identifyDeptChair("COMS");

    assertEquals("Luca Carloni is the department chair.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void identifyDeptChairDeptNotFoundTest() {
    ResponseEntity<?> response = testRouteController.identifyDeptChair("IEOR");

    assertEquals("Department Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void identifyDeptChairExceptionTest() {
    when(mockDepartment.getDepartmentChair()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.identifyDeptChair("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  public void findCourseLocationTest() {
    when(mockCourse.getCourseLocation()).thenReturn("417 IAB");

    ResponseEntity<?> response = testRouteController.findCourseLocation("COMS", 1004);

    assertEquals("417 IAB is where the course is located.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void findCourseLocationCourseNotFoundTest() {
    ResponseEntity<?> response = testRouteController.findCourseLocation("COMS", 3134);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void findCourseLocationExceptionTest() {
    when(mockCourse.getCourseLocation()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.findCourseLocation("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  public void findCourseInstructorTest() {
    when(mockCourse.getInstructorName()).thenReturn("Adam Cannon");

    ResponseEntity<?> response = testRouteController.findCourseInstructor("COMS", 1004);

    assertEquals("Adam Cannon is the instructor for the course.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void findCourseInstructorCourseNotFoundTest() {
    ResponseEntity<?> response = testRouteController.findCourseInstructor("COMS", 3134);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void findCourseInstructorExceptionTest() {
    when(mockCourse.getInstructorName()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.findCourseInstructor("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  public void findCourseTimeTest() {
    when(mockCourse.getCourseTimeSlot()).thenReturn("11:40-12:55");

    ResponseEntity<?> response = testRouteController.findCourseTime("COMS", 1004);

    assertEquals("The course meets at: 11:40-12:55.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void findCourseTimeCourseNotFoundTest() {
    ResponseEntity<?> response = testRouteController.findCourseTime("COMS", 3134);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void findCourseTimeExceptionTest() {
    when(mockCourse.getCourseTimeSlot()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.findCourseTime("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  public void addMajorToDeptTest() {
    ResponseEntity<?> response = testRouteController.addMajorToDept("COMS");

    verify(mockDepartment, times(1)).addPersonToMajor();
    assertEquals("Attribute was updated successfully", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void addMajorToDeptNotFoundTest() {
    ResponseEntity<?> response = testRouteController.addMajorToDept("IEOR");

    assertEquals("Department Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void addMajorToDeptExceptionTest() {
    doThrow(new RuntimeException("Database error")).when(mockDepartment).addPersonToMajor();

    ResponseEntity<?> response = testRouteController.addMajorToDept("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  public void removeMajorFromDeptTest() {
    ResponseEntity<?> response = testRouteController.removeMajorFromDept("COMS");

    verify(mockDepartment, times(1)).dropPersonFromMajor();
    assertEquals("Attribute was updated or is at minimum", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void removeMajorFromDeptNotFoundTest() {
    ResponseEntity<?> response = testRouteController.removeMajorFromDept("IEOR");

    assertEquals("Department Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void removeMajorFromDeptExceptionTest() {
    doThrow(new RuntimeException("Database error")).when(mockDepartment).dropPersonFromMajor();

    ResponseEntity<?> response = testRouteController.removeMajorFromDept("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  public void dropStudentSuccessTest() {
    when(mockCourse.dropStudent()).thenReturn(true);

    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 1004);

    assertEquals("Student has been dropped.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void dropStudentNotSuccessTest() {
    when(mockCourse.dropStudent()).thenReturn(false);

    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 1004);

    assertEquals("Student has not been dropped.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void dropStudentCourseNotFoundTest() {
    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 3134);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void dropStudentExceptionTest() {
    when(mockCourse.dropStudent()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  public void setEnrollmentCountTest() {
    ResponseEntity<?> response = testRouteController.setEnrollmentCount("COMS", 1004, 249);

    verify(mockCourse, times(1)).setEnrolledStudentCount(249);
    assertEquals("Attribute was updated successfully.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void setEnrollmentCountCourseNotFoundTest() {
    ResponseEntity<?> response = testRouteController.setEnrollmentCount("COMS", 3134, 249);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void setEnrollmentCountExceptionTest() {
    doThrow(new RuntimeException("Database error")).when(mockCourse).setEnrolledStudentCount(249);

    ResponseEntity<?> response = testRouteController.setEnrollmentCount("COMS", 1004, 249);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  public void changeCourseTimeTest() {
    ResponseEntity<?> response = testRouteController.changeCourseTime("COMS", 1004, "4:10-5:25");

    verify(mockCourse, times(1)).reassignTime("4:10-5:25");
    assertEquals("Attribute was updated successfully.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void changeCourseTimeCourseNotFoundTest() {
    ResponseEntity<?> response = testRouteController.changeCourseTime("COMS", 3134, "4:10-5:25");

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void changeCourseTimeExceptionTest() {
    doThrow(new RuntimeException("Database error")).when(mockCourse).reassignTime("4:10-5:25");

    ResponseEntity<?> response = testRouteController.changeCourseTime("COMS", 1004, "4:10-5:25");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  public void changeCourseTeacherTest() {
    ResponseEntity<?> response = testRouteController.changeCourseTeacher("COMS", 1004, "Brian Borowski");

    verify(mockCourse, times(1)).reassignInstructor("Brian Borowski");
    assertEquals("Attribute was updated successfully.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void changeCourseTeacherCourseNotFoundTest() {
    ResponseEntity<?> response = testRouteController.changeCourseTeacher("COMS", 3134, "Brian Borowski");

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void changeCourseTeacherExceptionTest() {
    doThrow(new RuntimeException("Database error")).when(mockCourse).reassignInstructor("Brian Borowski");

    ResponseEntity<?> response = testRouteController.changeCourseTeacher("COMS", 1004, "Brian Borowski");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  public void changeCourseLocationTest() {
    ResponseEntity<?> response = testRouteController.changeCourseLocation("COMS", 1004, "301 URIS");

    verify(mockCourse, times(1)).reassignLocation("301 URIS");
    assertEquals("Attribute was updated successfully.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void changeCourseLocationCourseNotFoundTest() {
    ResponseEntity<?> response = testRouteController.changeCourseLocation("COMS", 3134, "301 URIS");

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void changeCourseLocationExceptionTest() {
    doThrow(new RuntimeException("Database error")).when(mockCourse).reassignLocation("301 URIS");

    ResponseEntity<?> response = testRouteController.changeCourseLocation("COMS", 1004, "301 URIS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  private MyFileDatabase mockDatabase;

  private RouteController testRouteController;

  private Department mockDepartment;
  private Course mockCourse;
  private Map<String, Department> departmentMap;
  private Map<String, Course> courseMap;
}
