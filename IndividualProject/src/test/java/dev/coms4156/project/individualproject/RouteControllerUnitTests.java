package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;

public class RouteControllerUnitTests {
  @InjectMocks
  private RouteController routeController;

  @Mock
  private Department department;

  @Mock
  private MyFileDatabase myFileDatabase;

  @Mock
  private Course course;

  private HashMap<String, Department> departmentMapping;
  private HashMap<String, Course> courseMapping;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    departmentMapping = new HashMap<>();
    courseMapping = new HashMap<>();

    departmentMapping.put("CS", department);
    courseMapping.put("101", course);

    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(department.getCourseSelection()).thenReturn(courseMapping);
    routeController = new RouteController();
  }

  @Test
  public void testIndex() {
    String result = routeController.index();
    assertEquals("Welcome, in order to make an API call direct your browser or Postman to an endpoint \n\n This can be done using the following format: \n\n http:127.0.0.1:8080/endpoint?arg=value", result);
  }

  @Test
  public void testRetrieveDepartment_Found() {
    when(departmentMapping.containsKey("CS")).thenReturn(true);

    ResponseEntity<?> response = routeController.retrieveDepartment("CS");
    assertEquals(OK, response.getStatusCode());
  }

  @Test
  public void testRetrieveDepartment_NotFound() {
    when(departmentMapping.containsKey("MATH")).thenReturn(false);

    ResponseEntity<?> response = routeController.retrieveDepartment("MATH");
    assertEquals(OK, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testRetrieveCourse_Found() {
    when(departmentMapping.containsKey("CS")).thenReturn(true);
    when(courseMapping.containsKey("101")).thenReturn(true);

    ResponseEntity<?> response = routeController.retrieveCourse("CS", 101);
    assertEquals(OK, response.getStatusCode());
  }

  @Test
  public void testRetrieveCourse_NotFound() {
    when(departmentMapping.containsKey("CS")).thenReturn(true);
    when(courseMapping.containsKey("101")).thenReturn(false);

    ResponseEntity<?> response = routeController.retrieveCourse("CS", 101);
    assertEquals(NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testIsCourseFull_CourseIsFull() {
    when(course.isCourseFull()).thenReturn(true);

    boolean result = course.isCourseFull();
    assertTrue(result);
  }

  @Test
  public void testIsCourseFull_CourseIsNotFull() {
    when(course.isCourseFull()).thenReturn(false);

    boolean result = course.isCourseFull();
    assertFalse(result);
  }


  @Test
  public void testGetMajorCtFromDept_Found() {
    when(departmentMapping.containsKey("CS")).thenReturn(true);
    when(department.getNumberOfMajors()).thenReturn(100);

    ResponseEntity<?> response = routeController.getMajorCtFromDept("CS");
    assertEquals(OK, response.getStatusCode());
    assertEquals("There are: -100 majors in the department", response.getBody());
  }

  @Test
  public void testIdentifyDeptChair_Found() {
    when(departmentMapping.containsKey("CS")).thenReturn(true);
    when(department.getDepartmentChair()).thenReturn("Dr. Smith");

    ResponseEntity<?> response = routeController.identifyDeptChair("CS");
    assertEquals(OK, response.getStatusCode());
    assertEquals("Dr. Smith is the department chair.", response.getBody());
  }

  @Test
  public void testFindCourseLocation_CourseExists() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    HashMap<String, Course> courseMapping = new HashMap<>();

    courseMapping.put("101", course);
    departmentMapping.put("CS", department);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(department.getCourseSelection()).thenReturn(courseMapping);
    when(course.getCourseLocation()).thenReturn("Room 101");

    ResponseEntity<?> response = routeController.findCourseLocation("CS", 101);
    assertEquals(OK, response.getStatusCode());
    assertEquals("Room 101", response.getBody());
  }

  @Test
  public void testFindCourseLocation_CourseNotFound() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    ResponseEntity<?> response = routeController.findCourseLocation("CS", 101);
    assertEquals(NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }


  @Test
  public void testFindCourseInstructor_CourseExists() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    HashMap<String, Course> courseMapping = new HashMap<>();

    // Mock course behavior
    courseMapping.put("101", course);
    departmentMapping.put("CS", department);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(department.getCourseSelection()).thenReturn(courseMapping);
    when(course.getInstructorName()).thenReturn("Dr. Smith");

    ResponseEntity<?> response = routeController.findCourseInstructor("CS", 101);
    assertEquals(OK, response.getStatusCode());
    assertEquals("Dr. Smith is the instructor for the course.", response.getBody());
  }

  @Test
  public void testFindCourseInstructor_CourseNotFound() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    HashMap<String, Course> courseMapping = new HashMap<>();

    departmentMapping.put("CS", department);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(department.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = routeController.findCourseInstructor("CS", 102);
    assertEquals(NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testFindCourseTime_CourseExists() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    HashMap<String, Course> courseMapping = new HashMap<>();

    courseMapping.put("101", course);
    departmentMapping.put("CS", department);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(department.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = routeController.findCourseTime("CS", 101);
    assertEquals(OK, response.getStatusCode());
    assertEquals("The course meets at: some time ", response.getBody());
  }

  @Test
  public void testFindCourseTime_CourseNotFound() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("CS", department);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(department.getCourseSelection()).thenReturn(new HashMap<>());

    ResponseEntity<?> response = routeController.findCourseTime("CS", 102);
    assertEquals(NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testAddMajorToDept_DepartmentExists() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("CS", department);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    ResponseEntity<?> response = routeController.addMajorToDept("CS");
    assertEquals(OK, response.getStatusCode());
    assertEquals("Attribute was updated successfully", response.getBody());
  }

  @Test
  public void testAddMajorToDept_DepartmentNotFound() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    ResponseEntity<?> response = routeController.addMajorToDept("MATH");
    assertEquals(NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testDropStudentFromCourse_CourseExists_StudentDropped() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    HashMap<String, Course> courseMapping = new HashMap<>();

    courseMapping.put("101", course);
    departmentMapping.put("CS", department);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(department.getCourseSelection()).thenReturn(courseMapping);
    when(course.dropStudent()).thenReturn(true);

    ResponseEntity<?> response = routeController.dropStudent("CS", 101);
    assertEquals(OK, response.getStatusCode());
    assertEquals("Student has been dropped.", response.getBody());
  }

  @Test
  public void testDropStudentFromCourse_CourseExists_StudentNotDropped() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    HashMap<String, Course> courseMapping = new HashMap<>();

    courseMapping.put("101", course);
    departmentMapping.put("CS", department);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(department.getCourseSelection()).thenReturn(courseMapping);
    when(course.dropStudent()).thenReturn(false);

    ResponseEntity<?> response = routeController.dropStudent("CS", 101);
    assertEquals(BAD_REQUEST, response.getStatusCode());
    assertEquals("Student has not been dropped.", response.getBody());
  }

  @Test
  public void testSetEnrollmentCount_CourseExists() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    HashMap<String, Course> courseMapping = new HashMap<>();

    courseMapping.put("101", course);
    departmentMapping.put("CS", department);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(department.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = routeController.setEnrollmentCount("CS", 101, 50);
    assertEquals(OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void testChangeCourseTime_CourseExists() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    HashMap<String, Course> courseMapping = new HashMap<>();

    courseMapping.put("101", course);
    departmentMapping.put("CS", department);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(department.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = routeController.changeCourseTime("CS", 101, "10:00 AM");
    assertEquals(OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void testChangeCourseTime_CourseNotFound() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    ResponseEntity<?> response = routeController.changeCourseTime("CS", 101, "10:00 AM");
    assertEquals(NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseTeacher_CourseExists() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    HashMap<String, Course> courseMapping = new HashMap<>();

    courseMapping.put("101", course);
    departmentMapping.put("CS", department);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(department.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = routeController.changeCourseTeacher("CS", 101, "New Teacher");
    assertEquals(OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void testChangeCourseLocation_CourseExists() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    HashMap<String, Course> courseMapping = new HashMap<>();

    courseMapping.put("101", course);
    departmentMapping.put("CS", department);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(department.getCourseSelection()).thenReturn(courseMapping);

    ResponseEntity<?> response = routeController.changeCourseLocation("CS", 101, "New Building");
    assertEquals(OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void testChangeCourseLocation_CourseNotFound() {
    HashMap<String, Department> departmentMapping = new HashMap<>();
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    ResponseEntity<?> response = routeController.changeCourseLocation("CS", 101, "New Building");
    assertEquals(NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }


}
