package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

/**
 * Unit tests for the RouteController class.
 */

public class RouteControllerUnitTests {

  private RouteController routeController;
  private MyFileDatabase mockDatabase;

  /**
   * Sets up the test environment before each test runs.
   */
  @BeforeEach
  public void setup() {
    mockDatabase = new MyFileDatabase(1, "testDatabase.ser");
    HashMap<String, Department> departments = new HashMap<>();
    departments.put("ELEN", new Department("ELEN", new HashMap<>(), "this.departmentChair", 250));
    mockDatabase.setMapping(departments);

    IndividualProjectApplication.myFileDatabase = mockDatabase;
    routeController = new RouteController();
  }

  @Test
  public void testIndexRoute() {
    String response = routeController.index();
    assertNotNull(response);
    assertTrue(response.contains("http:127.0.0.1:8080/endpoint?arg=value"));
  }

  @Test
  public void testRetrieveDepartmentFound() {
    ResponseEntity<?> response = routeController.retrieveDepartment("ELEN");
    assertEquals(200, response.getStatusCodeValue());
    assertTrue(response.getBody().toString().contains("this.departmentChair"));
  }

  @Test
  public void testRetrieveDepartmentNotFound() {
    ResponseEntity<?> response = routeController.retrieveDepartment("INVALID");
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testRetrieveDepartmentWithNullCode() {
    ResponseEntity<?> response = routeController.retrieveDepartment(null);
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testAddMajorToDept() {
    ResponseEntity<?> response = routeController.addMajorToDept("ELEN");
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("Attribute was updated successfully", response.getBody());

    Department updatedDepartment = mockDatabase.getDepartmentMapping().get("ELEN");
    assertEquals(251, updatedDepartment.getNumberOfMajors());
  }

  @Test
  public void testAddMajorToNonExistentDept() {
    ResponseEntity<?> response = routeController.addMajorToDept("INVALID");
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testRemoveMajorFromDept() {
    Department dept = mockDatabase.getDepartmentMapping().get("ELEN");
    dept.addPersonToMajor(); // Make sure there's at least one major
    ResponseEntity<?> response = routeController.removeMajorFromDept("ELEN");
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("Attribute was updated or is at minimum", response.getBody());

    assertEquals(250, dept.getNumberOfMajors());
  }

  @Test
  public void testRemoveMajorFromDeptNonExistent() {
    ResponseEntity<?> response = routeController.removeMajorFromDept("INVALID");
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseLocationForElen() {
    Department dept = mockDatabase.getDepartmentMapping().get("ELEN");
    dept.createCourse("3082", "Kenneth Shepard", "1205 MUDD", "4:10-6:40", 100);

    ResponseEntity<?> response = routeController.changeCourseLocation("ELEN", 3082, "Room 102");
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("Attributed was updated successfully.", response.getBody());

    Course updatedCourse = dept.getCourseSelection().get("3082");
    assertEquals("1205 MUDD", updatedCourse.getCourseLocation());
  }

  @Test
  public void testChangeCourseLocationWithNullLocation() {
    ResponseEntity<?> response = routeController.changeCourseLocation("ELEN", 3082, null);
    assertEquals(400, response.getStatusCodeValue());
    assertEquals("Invalid location", response.getBody());
  }

  @Test
  public void testChangeCourseLocationWithInvalidCourse() {
    ResponseEntity<?> response = routeController.changeCourseLocation("ELEN", 999, "Room 102");
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseLocationWithInvalidDepartment() {
    ResponseEntity<?> response = routeController.changeCourseLocation("INVALID", 3082, "Room 102");
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseTeacherForElen() {
    Department dept = mockDatabase.getDepartmentMapping().get("ELEN");
    dept.createCourse("3082", "Kenneth Shepard", "1205 MUDD", "4:10-6:40", 100);

    ResponseEntity<?> response = routeController.changeCourseTeacher("ELEN", 3082, "Keren Bergman");
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("Attributed was updated successfully.", response.getBody());

    Course updatedCourse = dept.getCourseSelection().get("3082");
    assertEquals("Keren Bergman", updatedCourse.getInstructorName());
  }

  @Test
  public void testChangeCourseTeacherWithNullTeacher() {
    ResponseEntity<?> response = routeController.changeCourseTeacher("ELEN", 3082, null);
    assertEquals(400, response.getStatusCodeValue());
    assertEquals("Invalid teacher", response.getBody());
  }

  @Test
  public void testSetEnrollmentCountForElen() {
    Department dept = mockDatabase.getDepartmentMapping().get("ELEN");
    dept.createCourse("3082", "Kenneth Shepard", "1205 MUDD", "4:10-6:40", 100);

    ResponseEntity<?> response = routeController.setEnrollmentCount("ELEN", 3082, 75);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("Attributed was updated successfully.", response.getBody());

    Course updatedCourse = dept.getCourseSelection().get("3082");
    assertEquals(75, updatedCourse.getEnrolledStudentCount());
  }

  @Test
  public void testSetEnrollmentCountWithInvalidInput() {
    ResponseEntity<?> response = routeController.setEnrollmentCount("ELEN", 3082, -10);
    assertEquals(400, response.getStatusCodeValue());
    assertEquals("Invalid enrollment count", response.getBody());
  }

  @Test
  public void testIdentifyDeptChair() {
    ResponseEntity<?> response = routeController.identifyDeptChair("ELEN");
    assertEquals(200, response.getStatusCodeValue());
    assertTrue(response.getBody().toString().contains("this is the department chair."));
  }

  @Test
  public void testIdentifyDeptChairNotFound() {
    ResponseEntity<?> response = routeController.identifyDeptChair("INVALID");
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testGetMajorCtFromDept() {
    ResponseEntity<?> response = routeController.getMajorCtFromDept("ELEN");
    assertEquals(200, response.getStatusCodeValue());
    assertTrue(response.getBody().toString().contains("There are: 250 majors in the department"));
  }

  @Test
  public void testGetMajorCtFromDeptNotFound() {
    ResponseEntity<?> response = routeController.getMajorCtFromDept("INVALID");
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testFindCourseInstructor() {
    Department dept = mockDatabase.getDepartmentMapping().get("ELEN");
    dept.createCourse("3082", "Kenneth Shepard", "1205 MUDD", "4:10-6:40", 100);

    ResponseEntity<?> response = routeController.findCourseInstructor("ELEN", 3082);
    assertEquals(200, response.getStatusCodeValue());
    assertTrue(response.getBody().toString().contains("This is the instructor of course."));
  }

  @Test
  public void testFindCourseInstructorNotFound() {
    ResponseEntity<?> response = routeController.findCourseInstructor("ELEN", 999);
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testFindCourseLocation() {
    Department dept = mockDatabase.getDepartmentMapping().get("ELEN");
    dept.createCourse("3082", "Kenneth Shepard", "1205 MUDD", "4:10-6:40", 100);

    ResponseEntity<?> response = routeController.findCourseLocation("ELEN", 3082);
    assertEquals(200, response.getStatusCodeValue());
    assertTrue(response.getBody().toString().contains("1205 MUDD is where the course is located."));
  }

  @Test
  public void testFindCourseLocationNotFound() {
    ResponseEntity<?> response = routeController.findCourseLocation("ELEN", 999);
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testFindCourseTime() {
    Department dept = mockDatabase.getDepartmentMapping().get("ELEN");
    dept.createCourse("3082", "Kenneth Shepard", "1205 MUDD", "4:10-6:40", 100);

    ResponseEntity<?> response = routeController.findCourseTime("ELEN", 3082);
    assertEquals(200, response.getStatusCodeValue());
    assertTrue(response.getBody().toString().contains("The course meets at: 4:10-6:40"));
  }

  @Test
  public void testFindCourseTimeNotFound() {
    ResponseEntity<?> response = routeController.findCourseTime("ELEN", 999);
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseTimeForElen() {
    Department dept = mockDatabase.getDepartmentMapping().get("ELEN");
    dept.createCourse("3082", "Kenneth Shepard", "1205 MUDD", "4:10-6:40", 100);

    ResponseEntity<?> response = routeController.changeCourseTime("ELEN", 3082, "11:00-12:00");
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("Attributed was updated successfully.", response.getBody());

    Course updatedCourse = dept.getCourseSelection().get("3082");
    assertEquals("11:00-12:00", updatedCourse.getCourseTimeSlot());
  }

  @Test
  public void testChangeCourseTimeWithNullTime() {
    ResponseEntity<?> response = routeController.changeCourseTime("ELEN", 3082, null);
    assertEquals(400, response.getStatusCodeValue());
    assertEquals("Invalid time", response.getBody());
  }

  @Test
  public void testDropStudentForElen() {
    Department dept = mockDatabase.getDepartmentMapping().get("ELEN");
    dept.createCourse("3082", "Kenneth Shepard", "1205 MUDD", "4:10-6:40", 100);
    Course course = dept.getCourseSelection().get("3082");
    course.setEnrolledStudentCount(50);

    ResponseEntity<?> response = routeController.dropStudent("ELEN", 3082);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("Student has been dropped.", response.getBody());
    assertEquals(49, course.getEnrolledStudentCount());
  }

  @Test
  public void testDropStudentFromEmptyCourse() {
    Department dept = mockDatabase.getDepartmentMapping().get("ELEN");
    dept.createCourse("3082", "Kenneth Shepard", "1205 MUDD", "4:10-6:40", 100);
    Course course = dept.getCourseSelection().get("3082");
    course.setEnrolledStudentCount(0);

    ResponseEntity<?> response = routeController.dropStudent("ELEN", 3082);
    assertEquals(400, response.getStatusCodeValue());
    assertEquals("Student has not been dropped.", response.getBody());
  }

  @Test
  public void testDropStudentNonExistentCourse() {
    ResponseEntity<?> response = routeController.dropStudent("ELEN", 999);
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testIsCourseFull() {
    Department dept = mockDatabase.getDepartmentMapping().get("ELEN");
    dept.createCourse("3082", "Kenneth Shepard", "1205 MUDD", "4:10-6:40", 250);

    Course course = dept.getCourseSelection().get("3082");
    course.setEnrolledStudentCount(250); // Full

    ResponseEntity<?> response = routeController.isCourseFull("ELEN", 3082);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("The course is full.", response.getBody());

    course.setEnrolledStudentCount(249); // Not full
    response = routeController.isCourseFull("ELEN", 3082);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("The course is not full.", response.getBody());
  }

  @Test
  public void testRetrieveCoursesValid() {
    Department dept = mockDatabase.getDepartmentMapping().get("ELEN");
    dept.createCourse("3082", "Kenneth Shepard", "1205 MUDD", "4:10-6:40", 100);

    ResponseEntity<?> response = routeController.retrieveCourses("3082");
    assertEquals(200, response.getStatusCodeValue());
    assertTrue(response.getBody().toString().contains("Kenneth Shepard"));
    assertTrue(response.getBody().toString().contains("1205 MUDD"));
  }

  @Test
  public void testRetrieveCoursesInvalidCourseCode() {
    ResponseEntity<?> response = routeController.retrieveCourses("INVALID");
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testRetrieveCoursesNullCourseCode() {
    ResponseEntity<?> response = routeController.retrieveCourses(null);
    assertEquals(400, response.getStatusCodeValue());
    assertEquals("Invalid course code.", response.getBody());
  }

  @Test
  public void testEnrollStudentInCourseSuccess() {
    Department dept = mockDatabase.getDepartmentMapping().get("ELEN");
    dept.createCourse("3082", "Kenneth Shepard", "1205 MUDD", "4:10-6:40", 100);

    ResponseEntity<?> response = routeController.enrollStudentInCourse("ELEN", "3082");
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("Student enrolled successfully in course: 3082", response.getBody());
  }

  @Test
  public void testEnrollStudentInCourseCourseFull() {
    Department dept = mockDatabase.getDepartmentMapping().get("ELEN");
    dept.createCourse("3082", "Kenneth Shepard", "1205 MUDD", "4:10-6:40", 100);
    Course course = dept.getCourseSelection().get("3082");
    course.setEnrolledStudentCount(250);

    ResponseEntity<?> response = routeController.enrollStudentInCourse("ELEN", "3082");
    assertEquals(400, response.getStatusCodeValue());
    assertEquals("Course is full, enrollment failed.", response.getBody());
  }

  @Test
  public void testEnrollStudentInCourseInvalidDepartment() {
    ResponseEntity<?> response = routeController.enrollStudentInCourse("INVALID", "3082");
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Department Not Found.", response.getBody());
  }

  @Test
  public void testEnrollStudentInCourseInvalidCourse() {
    ResponseEntity<?> response = routeController.enrollStudentInCourse("ELEN", "INVALID");
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Course Not Found.", response.getBody());
  }

  @Test
  public void testEnrollStudentInCourseNullDepartment() {
    ResponseEntity<?> response = routeController.enrollStudentInCourse(null, "3082");
    assertEquals(400, response.getStatusCodeValue());
    assertEquals("Invalid department code.", response.getBody());
  }

  @Test
  public void testEnrollStudentInCourseNullCourse() {
    ResponseEntity<?> response = routeController.enrollStudentInCourse("ELEN", null);
    assertEquals(400, response.getStatusCodeValue());
    assertEquals("Invalid course code.", response.getBody());
  }

  @Test
  public void testCreateCourseWithNegativeCapacity() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      new Course("Kenneth Shepard", "1205 MUDD", "4:10-6:40", -10);
    });
    assertEquals("Course capacity cannot be less than 0", thrown.getMessage());
  }



}
