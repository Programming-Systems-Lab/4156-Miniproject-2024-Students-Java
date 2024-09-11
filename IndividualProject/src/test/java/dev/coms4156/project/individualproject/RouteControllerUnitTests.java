package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
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
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("COMS", mockDepartment);

    when(mockDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    IndividualProjectApplication.myFileDatabase = mockDatabase;
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
    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("COMS", mockDepartment);

    when(mockDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    ResponseEntity<?> response = testRouteController.retrieveDepartment("COMS");
    assertEquals(mockDepartment.toString(), response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void retrieveDepartmentNotFoundTest() {
    ResponseEntity<?> response = testRouteController.retrieveDepartment("IEOR");

    assertEquals("Department Not Found", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void retrieveDepartmentExceptionTest() {
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.retrieveDepartment("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void retrieveCourseFoundTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    courseMapping.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.retrieveCourse("COMS", 1004);

    assertEquals(mockCourse.toString(), response.getBody());
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }


  @Test
  public void retrieveCourseNotFoundTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.retrieveCourse("COMS", 1004);

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
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.retrieveCourse("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void isCourseFullTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    courseMapping.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    when(mockCourse.isCourseFull()).thenReturn(true);

    ResponseEntity<?> response = testRouteController.isCourseFull("COMS", 1004);

    assertEquals(true, response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void isCourseNotFullCourseNotFoundTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.isCourseFull("COMS", 1004);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void isCourseFullExceptionTest() {
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.isCourseFull("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void getMajorCtFromDeptTest() {
    when(mockDepartment.getNumberOfMajors()).thenReturn(2700);

    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("COMS");

    assertEquals("There are 2700 majors in the department", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void getMajorCtFromDeptNotFoundTest() {
    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("IEOR");

    assertEquals("Department Not Found", response.getBody());
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }


  @Test
  public void getMajorCtFromDeptExceptionTest() {
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void identifyDeptChairTest() {
    when(mockDepartment.getDepartmentChair()).thenReturn("Luca Carloni");

    ResponseEntity<?> response = testRouteController.identifyDeptChair("COMS");

    assertEquals("Luca Carloni is the department chair", response.getBody());
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
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.identifyDeptChair("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void findCourseLocationTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    courseMapping.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);
    when(mockCourse.getCourseLocation()).thenReturn("417 IAB");

    ResponseEntity<?> response = testRouteController.findCourseLocation("COMS", 1004);

    assertEquals("417 IAB is where the course is located", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void findCourseLocationCourseNotFoundTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.findCourseLocation("COMS", 1004);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void findCourseLocationExceptionTest() {
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.findCourseLocation("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void findCourseInstructorTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    courseMapping.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);
    when(mockCourse.getInstructorName()).thenReturn("Adam Cannon");

    ResponseEntity<?> response = testRouteController.findCourseInstructor("COMS", 1004);

    assertEquals("Adam Cannon is the instructor for the coourse", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void findCourseInstructorCourseNotFoundTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.findCourseInstructor("COMS", 1004);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void findCourseInstructorExceptionTest() {
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.findCourseInstructor("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void findCourseTimeTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    courseMapping.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);
    when(mockCourse.getCourseTimeSlot()).thenReturn("11:40-12:55");

    ResponseEntity<?> response = testRouteController.findCourseTime("COMS", 1004);

    assertEquals("The course meets at: 11:40-12:55", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void findCourseTimeCourseNotFoundTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.findCourseTime("COMS", 1004);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void findCourseTimeExceptionTest() {
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.findCourseTime("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void addMajorToDeptTest() {
    doNothing().when(mockDepartment).addPersonToMajor();

    ResponseEntity<?> response = testRouteController.addMajorToDept("COMS");

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
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.addMajorToDept("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void removeMajorFromDeptTest() {
    doNothing().when(mockDepartment).dropPersonFromMajor();

    ResponseEntity<?> response = testRouteController.removeMajorFromDept("COMS");

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
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.removeMajorFromDept("COMS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void dropStudentSuccessTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    courseMapping.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);
    when(mockCourse.dropStudent()).thenReturn(true);

    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 1004);

    assertEquals("Student has been dropped.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void dropStudentNotSuccessTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    courseMapping.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);
    when(mockCourse.dropStudent()).thenReturn(false);

    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 1004);

    assertEquals("Student has not been dropped.", response.getBody());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }


  @Test
  public void dropStudentNotFoundTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 1004);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void dropStudentExceptionTest() {
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 1004);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void setEnrollmentCountTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    courseMapping.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.setEnrollmentCount("COMS", 1004, 249);

    verify(mockCourse, times(1)).setEnrolledStudentCount(249);
    assertEquals("Attributed was updated successfully.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void setEnrollmentCountCourseNotFoundTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.setEnrollmentCount("COMS", 1004, 249);

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void setEnrollmentCountExceptionTest() {
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.setEnrollmentCount("COMS", 1004, 249);

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void changeCourseTimeTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    courseMapping.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.changeCourseTime("COMS", 1004, "4:10-5:25");

    verify(mockCourse, times(1)).reassignTime("4:10-5:25");
    assertEquals("Attributed was updated successfully.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void changeCourseTimeCourseNotFoundTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.changeCourseTime("COMS", 1004, "4:10-5:25");

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void changeCourseTimeExceptionTest() {
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.changeCourseTime("COMS", 1004, "4:10-5:25");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void changeCourseTeacherTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    courseMapping.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.changeCourseTeacher("COMS", 1004, "Brian Borowski");

    verify(mockCourse, times(1)).reassignInstructor("Brian Borowski");
    assertEquals("Attributed was updated successfully.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void changeCourseTeacherCourseNotFoundTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.changeCourseTeacher("COMS", 1004, "Brian Borowski");

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void changeCourseTeacherExceptionTest() {
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.changeCourseTeacher("COMS", 1004, "Brian Borowski");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void changeCourseLocationTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    courseMapping.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.changeCourseLocation("COMS", 1004, "301 URIS");

    verify(mockCourse, times(1)).reassignLocation("301 URIS");
    assertEquals("Attributed was updated successfully.", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void changeCourseLocationCourseNotFoundTest() {
    HashMap<String, Course> courseMapping = new HashMap<>();
    when(mockDepartment.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = testRouteController.changeCourseLocation("COMS", 1004, "301 URIS");

    assertEquals("Course Not Found", response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  public void changeCourseLocationExceptionTest() {
    when(mockDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<?> response = testRouteController.changeCourseLocation("COMS", 1004, "301 URIS");

    assertEquals("An Error has occurred", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @InjectMocks
  public RouteController testRouteController;

  @Mock
  public MyFileDatabase mockDatabase;

  @Mock
  public Department mockDepartment;

  @Mock
  public Course mockCourse;
}
