package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;

public class RouteControllerUnitTests {
  private RouteController routeController;
  private MyFileDatabase myFileDatabase;
  private IndividualProjectApplication app;

  @BeforeEach
  public void setUp() {
    app = new IndividualProjectApplication();
    myFileDatabase = new MyFileDatabase(0, "./data.txt");
    app.myFileDatabase = myFileDatabase;
    app.resetDataFile();

    routeController = new RouteController();
  }

  @Test
  public void testIndex() {
    String result = routeController.index();
    assertEquals("Welcome, in order to make an API call direct your browser or Postman to an endpoint \n\n This can be done using the following format: \n\n http:127.0.0.1:8080/endpoint?arg=value", result);
  }

  @Test
  public void testRetrieveDepartment_Found() {
    ResponseEntity<?> response = routeController.retrieveDepartment("COMS");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().toString().contains("COMS"));
  }

  @Test
  public void testRetrieveDepartment_NotFound() {
    ResponseEntity<?> response = routeController.retrieveDepartment("UNKNOWN");

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }


  @Test
  public void testRetrieveCourseSuccess() {
    ResponseEntity<?> response = routeController.retrieveCourse("CHEM", 1403);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("\nInstructor: Ruben M Savizky; Location: 309 HAV; Time: 6:10-7:25", response.getBody());
  }

  @Test
  public void testRetrieveCourseNotFound() {
    ResponseEntity<?> response = routeController.retrieveCourse("CHEM", 9999);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testRetrieveCourseDepartmentNotFound() {
    ResponseEntity<?> response = routeController.retrieveCourse("NONEXISTENT", 0000);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testIsCourseFull_Full() {
    ResponseEntity<?> response = routeController.isCourseFull("IEOR", 4106);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(false, response.getBody());
  }

  @Test
  public void testIsCourseFull_NotFull() {
    ResponseEntity<?> response = routeController.isCourseFull("IEOR", 4102);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(true, response.getBody());
  }

  @Test
  public void testIsCourseFullCourseNotFound() {
    ResponseEntity<?> response = routeController.isCourseFull("IEOR", 9999);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testIsCourseFullDepartmentNotFound() {
    ResponseEntity<?> response = routeController.isCourseFull("NONEXISTENT", 1001);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testGetMajorCtFromDeptSuccess() {
    ResponseEntity<?> response = routeController.getMajorCtFromDept("COMS");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("There are: 2700 majors in the department", response.getBody());
  }

  @Test
  public void testGetMajorCtFromDeptDepartmentNotFound() {
    ResponseEntity<?> response = routeController.getMajorCtFromDept("NONEXISTENT");

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testIdentifyDeptChairSuccess() {
    String deptCode = "COMS";
    String expectedChair = "Luca Carloni";
    ResponseEntity<?> response = routeController.identifyDeptChair(deptCode);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedChair + " is the department chair.", response.getBody());
  }

  @Test
  public void testIdentifyDeptChairDepartmentNotFound() {
    ResponseEntity<?> response = routeController.identifyDeptChair("NONEXISTENT");

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testFindCourseLocationSuccess() {
    ResponseEntity<?> response = routeController.findCourseLocation("ECON", 1105);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("309 HAV is where the course is located.", response.getBody());
  }

  @Test
  public void testFindCourseLocationCourseNotFound() {
    ResponseEntity<?> response = routeController.findCourseLocation("ECON", 9999);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testFindCourseLocationDepartmentNotFound() {
    ResponseEntity<?> response = routeController.findCourseLocation("NONEXISTENT", 1001);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testFindCourseInstructorSuccess() {
    ResponseEntity<?> response = routeController.findCourseInstructor("ECON", 1105);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Waseem Noor is the instructor for the course.", response.getBody());
  }

  @Test
  public void testFindCourseInstructorCourseNotFound() {
    ResponseEntity<?> response = routeController.findCourseInstructor("ECON", 9999);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testFindCourseInstructorDepartmentNotFound() {
    ResponseEntity<?> response = routeController.findCourseInstructor("NONEXISTENT", 1001);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testFindCourseTimeSuccess() {
    ResponseEntity<?> response = routeController.findCourseTime("ECON", 1105);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("The course meets at: 2:40-3:55.", response.getBody());
  }

  @Test
  public void testFindCourseTimeCourseNotFound() {
    ResponseEntity<?> response = routeController.findCourseTime("ECON", 9999);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testFindCourseTimeDepartmentNotFound() {
    ResponseEntity<?> response = routeController.findCourseTime("NONEXISTENT", 1001);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testAddMajorToDeptSuccess() {
    String deptCode = "COMS";
    Department department = app.myFileDatabase.getDepartmentMapping().get(deptCode);

    ResponseEntity<?> response = routeController.addMajorToDept(deptCode);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated successfully", response.getBody());
    assertEquals(2701, department.getNumberOfMajors());
  }

  @Test
  public void testAddMajorToDept_DepartmentNotFound() {
    String deptCode = "NONEXISTENT";
    ResponseEntity<?> response = routeController.addMajorToDept(deptCode);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testRemoveMajorToDeptSuccess() {
    String deptCode = "COMS";
    Department department = app.myFileDatabase.getDepartmentMapping().get(deptCode);

    ResponseEntity<?> response = routeController.removeMajorFromDept(deptCode);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated or is at minimum", response.getBody());
    assertEquals(2699, department.getNumberOfMajors());
  }

  @Test
  public void testRemoveMajorToDept_DepartmentNotFound() {
    String deptCode = "NONEXISTENT";
    ResponseEntity<?> response = routeController.removeMajorFromDept(deptCode);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testDropStudentSuccess() {
    String deptCode = "COMS";
    int courseCode = 1004;

    ResponseEntity<?> response = routeController.dropStudent(deptCode, courseCode);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Student has been dropped.", response.getBody());
  }

  @Test
  public void testDropStudentFail() {
    String deptCode = "COMS";
    int courseCode = 1004;
    Department department = app.myFileDatabase.getDepartmentMapping().get(deptCode);
    Course course = department.getCourseSelection().get(Integer.toString(courseCode));
    course.setEnrolledStudentCount(0);

    ResponseEntity<?> response = routeController.dropStudent(deptCode, courseCode);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Student has not been dropped.", response.getBody());
  }

  @Test
  public void testDropStudentCourseNotFound() {
    String deptCode = "COMS";
    int courseCode = 9999;
    ResponseEntity<?> response = routeController.dropStudent(deptCode, courseCode);

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testDropStudentDepartmentNotFound() {
    String deptCode = "NONEXISTENT";
    int courseCode = 1001;

    ResponseEntity<?> response = routeController.dropStudent(deptCode, courseCode);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testSetEnrollmentSuccess() {
    String deptCode = "COMS";
    int courseCode = 1004;

    ResponseEntity<?> response = routeController.setEnrollmentCount(deptCode, courseCode, 10);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void testSetEnrollmentCourseNotFound() {
    String deptCode = "COMS";
    int courseCode = 9999;
    ResponseEntity<?> response = routeController.setEnrollmentCount(deptCode, courseCode, 10);

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testSetEnrollmentDepartmentNotFound() {
    String deptCode = "NONEXISTENT";
    int courseCode = 1001;

    ResponseEntity<?> response = routeController.setEnrollmentCount(deptCode, courseCode,10);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseTimeSuccess() {
    String deptCode = "COMS";
    int courseCode = 1004;
    Department department = app.myFileDatabase.getDepartmentMapping().get(deptCode);
    Course course = department.getCourseSelection().get(Integer.toString(courseCode));

    ResponseEntity<?> response = routeController.changeCourseTime(deptCode, courseCode, "1:10-3:40");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
    assertEquals("1:10-3:40", course.getCourseTimeSlot());
  }

  @Test
  public void testChangeCourseTimeCourseNotFound() {
    String deptCode = "COMS";
    int courseCode = 9999;
    ResponseEntity<?> response = routeController.changeCourseTime(deptCode, courseCode, "1:10-3:40");

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseTimeDepartmentNotFound() {
    String deptCode = "NONEXISTENT";
    int courseCode = 1001;

    ResponseEntity<?> response = routeController.changeCourseTime(deptCode, courseCode, "1:10-3:40");

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseTeacherSuccess() {
    String deptCode = "COMS";
    int courseCode = 1004;
    Department department = app.myFileDatabase.getDepartmentMapping().get(deptCode);
    Course course = department.getCourseSelection().get(Integer.toString(courseCode));

    ResponseEntity<?> response = routeController.changeCourseTeacher(deptCode, courseCode, "new teacher");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
    assertEquals("new teacher", course.getInstructorName());
  }

  @Test
  public void testChangeCourseTeacherCourseNotFound() {
    String deptCode = "COMS";
    int courseCode = 9999;
    ResponseEntity<?> response = routeController.changeCourseTeacher(deptCode, courseCode, "new teacher");

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseTeacherDepartmentNotFound() {
    String deptCode = "NONEXISTENT";
    int courseCode = 1001;

    ResponseEntity<?> response = routeController.changeCourseTeacher(deptCode, courseCode, "new teacher");

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseLocationSuccess() {
    String deptCode = "COMS";
    int courseCode = 1004;
    Department department = app.myFileDatabase.getDepartmentMapping().get(deptCode);
    Course course = department.getCourseSelection().get(Integer.toString(courseCode));

    ResponseEntity<?> response = routeController.changeCourseLocation(deptCode, courseCode, "new location");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
    assertEquals("new location", course.getCourseLocation());
  }

  @Test
  public void testChangeCourseLocationCourseNotFound() {
    String deptCode = "COMS";
    int courseCode = 9999;
    ResponseEntity<?> response = routeController.changeCourseLocation(deptCode, courseCode, "new location");

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseLocationDepartmentNotFound() {
    String deptCode = "NONEXISTENT";
    int courseCode = 1001;

    ResponseEntity<?> response = routeController.changeCourseLocation(deptCode, courseCode, "new location");

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }


}
