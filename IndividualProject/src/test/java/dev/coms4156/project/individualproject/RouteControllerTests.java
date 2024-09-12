package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** Unit tests for the RouteController class. */
public class RouteControllerTests {

  private RouteController routeController;

  private Map<String, Department> departmentMap;

  private Department mockDepartment;

  private Course mockCourse;

  /** Sets up the test environment before each test. */
  @BeforeEach
  public void setup() {
    routeController = new RouteController();
    departmentMap = new HashMap<>();
    mockDepartment = mock(Department.class);
    mockCourse = mock(Course.class);
    MyFileDatabase mockDatabase = mock(MyFileDatabase.class);
    IndividualProjectApplication.myFileDatabase = mockDatabase;
    when(mockDatabase.getDepartmentMapping()).thenReturn(departmentMap);
  }

  /** Tests index(). */
  @Test
  public void testIndex() {
    String result = routeController.index();
    assertEquals(
        "Welcome, in order to make an API call direct your browser or Postman to an endpoint \n\n "
            + "This can be done using the following format: \n\n "
            + "http:127.0.0.1:8080/endpoint?arg=value",
        result);
  }

  /** Tests retrieveDepartment() when the department (valid case). */
  @Test
  public void testRetrieveDepartmentFound() {
    departmentMap.put("FIN", mockDepartment);
    when(mockDepartment.toString()).thenReturn("FIN Department");
    ResponseEntity<?> response = routeController.retrieveDepartment("FIN");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("FIN Department", response.getBody());
  }

  /** Tests retrieveDepartment() when the department (invalid case). */
  @Test
  public void testRetrieveDepartmentNotFound() {
    ResponseEntity<?> response = routeController.retrieveDepartment("MATH");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /** Tests retrieveCourse() when the course (valid case). */
  @Test
  public void testRetrieveCourseFound() {
    departmentMap.put("FIN", mockDepartment);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courses);
    when(mockCourse.toString()).thenReturn("FIN1004 Course");
    ResponseEntity<?> response = routeController.retrieveCourse("FIN", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("FIN1004 Course", response.getBody());
  }

  /** Tests retrieveCourse() when the course (invalid case). */
  @Test
  public void testRetrieveCourseNotFound() {
    departmentMap.put("FIN", mockDepartment);
    Map<String, Course> courses = new HashMap<>();
    when(mockDepartment.getCourseSelection()).thenReturn(courses);
    ResponseEntity<?> response = routeController.retrieveCourse("FIN", 1005);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /** Tests isCourseFull(). */
  @Test
  public void testIsCourseFull() {
    departmentMap.put("FIN", mockDepartment);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courses);
    when(mockCourse.isCourseFull()).thenReturn(true);
    ResponseEntity<?> response = routeController.isCourseFull("FIN", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(true, response.getBody());
  }

  /** Tests getMajorCtFromDept(). */
  @Test
  public void testGetMajorCountFromDept() {
    departmentMap.put("FIN", mockDepartment);
    when(mockDepartment.getNumberOfMajors()).thenReturn(100);
    ResponseEntity<?> response = routeController.getMajorCtFromDept("FIN");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("There are: 100 majors in the department", response.getBody());
  }

  /** Tests identifyDeptChair(). */
  @Test
  public void testIdentifyDeptChair() {
    departmentMap.put("FIN", mockDepartment);
    when(mockDepartment.getDepartmentChair()).thenReturn("Muller");
    ResponseEntity<?> response = routeController.identifyDeptChair("FIN");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Muller is the department chair.", response.getBody());
  }

  /** Tests findCourseLocation(). */
  @Test
  public void testFindCourseLocation() {
    departmentMap.put("FIN", mockDepartment);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courses);
    when(mockCourse.getCourseLocation()).thenReturn("LA");
    ResponseEntity<?> response = routeController.findCourseLocation("FIN", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("LA is where the course is located.", response.getBody());
  }

  /** Tests findCourseInstructor(). */
  @Test
  public void testFindCourseInstructor() {
    departmentMap.put("FIN", mockDepartment);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courses);
    when(mockCourse.getInstructorName()).thenReturn("Adam");
    ResponseEntity<?> response = routeController.findCourseInstructor("FIN", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Adam is the instructor for the course.", response.getBody());
  }

  /** Tests findCourseTime(). */
  @Test
  public void testFindCourseTime() {
    departmentMap.put("FIN", mockDepartment);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courses);
    when(mockCourse.getCourseTimeSlot()).thenReturn("11:40-12:55");
    ResponseEntity<?> response = routeController.findCourseTime("FIN", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("The course meets at: 11:40-12:55", response.getBody());
  }

  /** Tests addMajorToDept(). */
  @Test
  public void testAddMajorToDept() {
    departmentMap.put("FIN", mockDepartment);
    ResponseEntity<?> response = routeController.addMajorToDept("FIN");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated successfully", response.getBody());
  }

  /** Tests removeMajorFromDept(). */
  @Test
  public void testRemoveMajorFromDept() {
    departmentMap.put("FIN", mockDepartment);
    ResponseEntity<?> response = routeController.removeMajorFromDept("FIN");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated or is at minimum", response.getBody());
  }

  /** Tests dropStudent(). */
  @Test
  public void testDropStudent() {
    departmentMap.put("FIN", mockDepartment);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courses);
    when(mockCourse.dropStudent()).thenReturn(true);
    ResponseEntity<?> response = routeController.dropStudent("FIN", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Student has been dropped.", response.getBody());
  }

  /** Tests setEnrollmentCount(). */
  @Test
  public void testSetEnrollmentCount() {
    departmentMap.put("FIN", mockDepartment);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courses);
    ResponseEntity<?> response = routeController.setEnrollmentCount("FIN", 1004, 200);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  /** Tests changeCourseTime(). */
  @Test
  public void testChangeCourseTime() {
    departmentMap.put("FIN", mockDepartment);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courses);
    ResponseEntity<?> response = routeController.changeCourseTime("FIN", 1004, "9:00-10:30");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  /** Tests changeCourseTeacher(). */
  @Test
  public void testChangeCourseTeacher() {
    departmentMap.put("FIN", mockDepartment);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courses);
    ResponseEntity<?> response = routeController.changeCourseTeacher("FIN", 1004, "New Teacher");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  /** Tests changeCourseLocation(). */
  @Test
  public void testChangeCourseLocation() {
    departmentMap.put("FIN", mockDepartment);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", mockCourse);
    when(mockDepartment.getCourseSelection()).thenReturn(courses);
    ResponseEntity<?> response = routeController.changeCourseLocation("FIN", 1004, "New Location");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  /** Tests handleException(). */
  @Test
  public void testHandleExceptionTriggered() {
    ResponseEntity<?> response = routeController.retrieveDepartment(null);
    System.out.println(response.getBody());
    assert response.getStatusCodeValue() == 500;
  }
}
