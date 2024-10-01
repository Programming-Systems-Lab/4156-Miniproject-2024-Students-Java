package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains tests for source code.
 */
@SpringBootTest
@ContextConfiguration
public class RouteControllerUnitTests {

  @BeforeAll
  public static void setupRouteControllerForTesting() {
    testRouteController = new RouteController();
  }

  @Test
  public void retrieveDepartmentTests() {
    Map<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    ResponseEntity<?> response = testRouteController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(departmentMapping.get("COMS").toString(), response.getBody());

    response = testRouteController.retrieveDepartment("COMX");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void retrieveCourseTests() {
    Map<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    Map<String, Course> coursesMapping;
    coursesMapping = departmentMapping.get("COMS").getCourseSelection();
    ResponseEntity<?> response = testRouteController.retrieveCourse("COMS", 4156);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(coursesMapping.get("4156").toString(), response.getBody());

    response = testRouteController.retrieveCourse("COMX", 4156);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());

    response = testRouteController.retrieveCourse("COMS", 4150);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void isCourseFullTests() {
    ResponseEntity<?> response = testRouteController.isCourseFull("COMS", 4156);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(true, response.getBody());

    response = testRouteController.isCourseFull("COMS", 4150);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void getMajorCtFromDeptTests() {
    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("There are: 2700 majors in the department", response.getBody());

    response = testRouteController.retrieveDepartment("COMX");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void identifyDeptChairTests() {
    ResponseEntity<?> response = testRouteController.identifyDeptChair("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Luca Carloni is the department chair.", response.getBody());

    response = testRouteController.retrieveDepartment("COMX");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void findCourseLocationTests() {
    ResponseEntity<?> response = testRouteController.findCourseLocation("COMS", 4156);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("501 NWC is where the course is located.", response.getBody());

    response = testRouteController.findCourseLocation("COMS", 4150);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void findCourseInstructorTests() {
    ResponseEntity<?> response = testRouteController.findCourseInstructor("COMS", 4156);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Gail Kaiser is the instructor for the course.", response.getBody());

    response = testRouteController.findCourseLocation("COMS", 4150);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void findCourseTimeTests() {
    ResponseEntity<?> response = testRouteController.findCourseTime("COMS", 4156);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("The course meets at: 10:10-11:25.", response.getBody());

    response = testRouteController.findCourseLocation("COMS", 4150);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void addDropMajorToDeptTests() {
    ResponseEntity<?> response = testRouteController.addMajorToDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated successfully", response.getBody());
    
    response = testRouteController.removeMajorFromDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated or is at minimum", response.getBody());

    response = testRouteController.addMajorToDept("COMX");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());

    response = testRouteController.removeMajorFromDept("COMX");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void dropStudentTests() {
    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 4156);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Student has been dropped.", response.getBody());
    Map<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    Map<String, Course> coursesMapping;
    coursesMapping = departmentMapping.get("COMS").getCourseSelection();
    Course coms4156 = coursesMapping.get("4156");
    coms4156.enrollStudent();

    response = testRouteController.dropStudent("COMS", 4150);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void setEnrollmentCountTests() {
    ResponseEntity<?> response = testRouteController.setEnrollmentCount("COMS", 4156, 99);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
    Map<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    Map<String, Course> coursesMapping;
    coursesMapping = departmentMapping.get("COMS").getCourseSelection();
    Course coms4156 = coursesMapping.get("4156");
    coms4156.setEnrolledStudentCount(109);

    response = testRouteController.setEnrollmentCount("COMS", 4150, 99);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void changeCourseTimeTests() {
    ResponseEntity<?> response = testRouteController.changeCourseTime("COMS", 4156, "2:40-3:55");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
    Map<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    Map<String, Course> coursesMapping;
    coursesMapping = departmentMapping.get("COMS").getCourseSelection();
    Course coms4156 = coursesMapping.get("4156");
    coms4156.reassignTime("10:10-11:25");

    response = testRouteController.changeCourseTime("COMS", 4150, "2:40-3:55");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void changeCourseTeacherTests() {
    ResponseEntity<?> response = testRouteController.changeCourseTeacher("COMS", 4156, "Someone");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
    Map<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    Map<String, Course> coursesMapping;
    coursesMapping = departmentMapping.get("COMS").getCourseSelection();
    Course coms4156 = coursesMapping.get("4156");
    coms4156.reassignInstructor("Gail Kaiser");

    response = testRouteController.changeCourseTeacher("COMS", 4150, "Someone");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void changeCourseLocationTests() {
    ResponseEntity<?> response = testRouteController.changeCourseLocation("COMS", 4156, "Mudd");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
    Map<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    Map<String, Course> coursesMapping;
    coursesMapping = departmentMapping.get("COMS").getCourseSelection();
    Course coms4156 = coursesMapping.get("4156");
    coms4156.reassignLocation("501 NWC");

    response = testRouteController.changeCourseLocation("COMS", 4150, "Mudd");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void retrieveCoursesTests() {
    ResponseEntity<?> response = testRouteController.retrieveCourses(4102);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("CHEM 4102: \n"
        + "Instructor: Dalibor Sames; Location: 320 HAV; Time: 10:10-11:25\n"
        + "IEOR 4102: \n"
        + "Instructor: Antonius B Dieker; Location: 209 HAM; Time: 10:10-11:25\n", 
        response.getBody());

    response = testRouteController.retrieveCourses(9999);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void enrollStudentInCourseTests() {
    ResponseEntity<?> response = testRouteController.enrollStudentInCourse("COMS", 4156);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Student has been enrolled.", response.getBody());

    Map<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    Map<String, Course> coursesMapping;
    coursesMapping = departmentMapping.get("COMS").getCourseSelection();
    Course coms4156 = coursesMapping.get("4156");
    coms4156.setEnrolledStudentCount(120);

    response = testRouteController.enrollStudentInCourse("COMS", 4156);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Student has not been enrolled because course is full.", response.getBody());

    coms4156.setEnrolledStudentCount(109);

    response = testRouteController.dropStudent("COMS", 4150);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /** The test course instance used for testing. */
  private static RouteController testRouteController;
}

