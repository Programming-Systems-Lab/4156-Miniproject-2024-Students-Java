package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RouteControllerUnitTests {

  private RouteController routeController;
  private MyFileDatabase myFileDatabase;
  private HashMap<String, Department> departmentMapping;

  @BeforeEach
  public void setUp() {
    routeController = new RouteController();

    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);

    Course coms3134 = new Course("Brian Borowski", "301 URIS", "4:10-5:25", 250);
    coms3134.setEnrolledStudentCount(242);

    HashMap<String, Course> comsCourses = new HashMap<>();
    comsCourses.put("1004", coms1004);
    comsCourses.put("3134", coms3134);

    Department comsDept = new Department("COMS", comsCourses, "Luca Carloni", 2700);

    departmentMapping = new HashMap<>();
    departmentMapping.put("COMS", comsDept);

    myFileDatabase = new MyFileDatabase(1, "./testdata.txt");
    myFileDatabase.setMapping(departmentMapping);

    IndividualProjectApplication.overrideDatabase(myFileDatabase);
  }

  @Test
  public void testRetrieveDepartment() {
    ResponseEntity<?> response = routeController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
//    assertTrue(response.getBody().toString().contains("Luca Carloni"));
    assertTrue(response.getBody().toString().contains("COMS"));
  }

  @Test
  public void testRetrieveDepartmentNotFound() {
    ResponseEntity<?> response = routeController.retrieveDepartment("MATH");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testRetrieveCourse() {
    ResponseEntity<?> response = routeController.retrieveCourse("COMS", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().toString().contains("Adam Cannon"));
  }

  @Test
  public void testRetrieveCourseNotFound() {
    ResponseEntity<?> response = routeController.retrieveCourse("COMS", 9999);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testIsCourseFull() {
    ResponseEntity<?> response = routeController.isCourseFull("COMS", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertFalse((Boolean) response.getBody());
  }

  @Test
  public void testIsCourseFullWithFullCourse() {
    Course coms3261 = new Course("Josh Alman", "417 IAB", "2:40-3:55", 150);
    coms3261.setEnrolledStudentCount(150);
    departmentMapping.get("COMS").addCourse("3261", coms3261);

    ResponseEntity<?> response = routeController.isCourseFull("COMS", 3261);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue((Boolean) response.getBody());
  }

  @Test
  public void testIsCourseFullCourseNotFound() {
    ResponseEntity<?> response = routeController.isCourseFull("COMS", 9999);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testGetMajorCtFromDept() {
    ResponseEntity<?> response = routeController.getMajorCtFromDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().toString().contains("2700 majors"));
  }

  @Test
  public void testGetMajorCtFromDeptNotFound() {
    ResponseEntity<?> response = routeController.getMajorCtFromDept("MATH");
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testFindCourseLocation() {
    ResponseEntity<?> response = routeController.findCourseLocation("COMS", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().toString().contains("417 IAB"));
  }

  @Test
  public void testFindCourseLocationCourseNotFound() {
    ResponseEntity<?> response = routeController.findCourseLocation("COMS", 9999);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testFindCourseInstructor() {
    ResponseEntity<?> response = routeController.findCourseInstructor("COMS", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().toString().contains("Adam Cannon"));
  }

  @Test
  public void testFindCourseInstructorCourseNotFound() {
    ResponseEntity<?> response = routeController.findCourseInstructor("COMS", 9999);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testRetrieveDepartmentEmptyString() {
    ResponseEntity<?> response = routeController.retrieveDepartment("");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testRetrieveCourseWithInvalidCourseCode() {
    ResponseEntity<?> response = routeController.retrieveCourse("COMS", -1);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testAddMajorToDeptDepartmentNotFound() {
    ResponseEntity<?> response = routeController.addMajorToDept("MATH");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testRemoveMajorFromDeptDepartmentNotFound() {
    ResponseEntity<?> response = routeController.removeMajorFromDept("MATH");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testDropStudentFromCourseDepartmentNotFound() {
    ResponseEntity<?> response = routeController.dropStudent("MATH", 1004);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testSetEnrollmentCountCourseNotFound() {
    ResponseEntity<?> response = routeController.setEnrollmentCount("COMS", 9999, 50);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseTimeDepartmentNotFound() {
    ResponseEntity<?> response = routeController.changeCourseTime("MATH", 1004, "10:00-11:30");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseTeacherCourseNotFound() {
    ResponseEntity<?> response = routeController.changeCourseTeacher("COMS", 9999, "John Smith");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseLocationCourseNotFound() {
    ResponseEntity<?> response = routeController.changeCourseLocation("COMS", 9999, "101 IAB");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }
  @Test
  public void testRetrieveDepartmentThrowsException() {
    IndividualProjectApplication.overrideDatabase(null); // Nullify the database to cause an exception

    ResponseEntity<?> response = routeController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("An Error has occurred", response.getBody());

    IndividualProjectApplication.overrideDatabase(myFileDatabase);
  }

  @Test
  public void testRetrieveCourseThrowsException() {
    IndividualProjectApplication.overrideDatabase(null);

    ResponseEntity<?> response = routeController.retrieveCourse("COMS", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("An Error has occurred", response.getBody());

    IndividualProjectApplication.overrideDatabase(myFileDatabase);
  }

  @Test
  public void testIsCourseFullThrowsException() {
    IndividualProjectApplication.overrideDatabase(null);

    ResponseEntity<?> response = routeController.isCourseFull("COMS", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("An Error has occurred", response.getBody());

    IndividualProjectApplication.overrideDatabase(myFileDatabase);
  }

  @Test
  public void testSetEnrollmentCountThrowsException() {
    IndividualProjectApplication.overrideDatabase(null);

    ResponseEntity<?> response = routeController.setEnrollmentCount("COMS", 1004, 100);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("An Error has occurred", response.getBody());

    IndividualProjectApplication.overrideDatabase(myFileDatabase);
  }
}
