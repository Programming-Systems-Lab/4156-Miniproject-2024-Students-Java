package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit tests for the Route Controller class.
 */
public class RouteControllerUnitTests {

  @InjectMocks
  private RouteController routeController;

  @Mock
  private MyFileDatabase mockDatabase;

  @Mock
  private Department mockDepartment;

  @Mock
  private Course mockCourse;

  private HashMap<String, Department> mapping;
  private HashMap<String, Course> courses;

  /**
   * Mocks COMS department data with courses.
   */
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mapping = new HashMap<>();
    courses = new HashMap<>();

    // Setup mock database responses
    when(mockDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.myFileDatabase = mockDatabase;

    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};
    
    //data for coms dept
    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    Course coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
    coms3134.setEnrolledStudentCount(242);
    Course coms3157 = new Course("Jae Lee", locations[0], times[1], 400);
    coms3157.setEnrolledStudentCount(311);
    Course coms3203 = new Course("Ansaf Salleb-Aouissi", locations[2], times[2], 250);
    coms3203.setEnrolledStudentCount(215);
    Course coms3261 = new Course("Josh Alman", locations[0], times[3], 150);
    coms3261.setEnrolledStudentCount(140);
    Course coms3251 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    coms3251.setEnrolledStudentCount(99);
    Course coms3827 = new Course("Daniel Rubenstein", "207 Math", times[2], 300);
    coms3827.setEnrolledStudentCount(283);
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", times[2], 120);
    coms4156.setEnrolledStudentCount(109);
    courses.put("1004", coms1004);
    courses.put("3134", coms3134);
    courses.put("3157", coms3157);
    courses.put("3203", coms3203);
    courses.put("3261", coms3261);
    courses.put("3251", coms3251);
    courses.put("3827", coms3827);
    courses.put("4156", coms4156);
    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    mapping.put("COMS", compSci);
  }

  @Test
  public void indexTest() {
    String expectedResult = "Welcome, in order to make an API "
        + "call direct your browser or Postman to an endpoint \n\n This can be done "
        + "using the following format: \n\n http:127.0.0.1:8080/endpoint?arg=value";
    assertEquals(expectedResult, routeController.index());
  }

  @Test
  public void testRetrieveDepartment() {
    ResponseEntity<?> response = routeController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().toString().contains("Gail Kaiser"));
  
    ResponseEntity<?> response1 = routeController.retrieveDepartment("RANDOM");
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    assertEquals("Department Not Found", response1.getBody());
  }

  @Test
  public void testRetrieveCourse() {
    ResponseEntity<?> response = routeController.retrieveCourse("COMS", 3157);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().toString().contains("Jae Lee"));

    ResponseEntity<?> response1 = routeController.retrieveCourse("RANDOM", 1105);
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    assertEquals("Department Not Found", response1.getBody());
  }

  @Test
  public void testIsCourseFull() {
    ResponseEntity<?> response1 = routeController.isCourseFull("COMS", 1);
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    assertEquals("Course Not Found", response1.getBody());
  }


  @Test
  public void testGetMajorCount() {
    ResponseEntity<?> response = routeController.getMajorCtFromDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("There are: 2700 majors in the department", response.getBody());

    ResponseEntity<?> response1 = routeController.getMajorCtFromDept("RANDOM");
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    assertEquals("Department Not Found", response1.getBody());
  }

  @Test
  public void testIdDeptChair() {
    ResponseEntity<?> response = routeController.identifyDeptChair("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Luca Carloni is the department chair.", response.getBody().toString());

    ResponseEntity<?> response1 = routeController.identifyDeptChair("RANDOM");
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    assertEquals("Department Not Found", response1.getBody().toString());
  }

  @Test
  public void testFindCourseLocation() {
    ResponseEntity<?> response = routeController.findCourseLocation("COMS", 3827);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().toString().contains("207 Math is where the course is located."));

    ResponseEntity<?> response1 = routeController.findCourseLocation("COMS", 1);
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    assertEquals("Course Not Found", response1.getBody().toString());

    ResponseEntity<?> response2 = routeController.findCourseLocation("RANDOM", 3134);
    assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    assertEquals("Course Not Found", response2.getBody().toString());
  }

  @Test
  public void testFindCourseInstructor() {
    ResponseEntity<?> response = routeController.findCourseInstructor("COMS", 3157);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().toString().contains("Jae Lee is the instructor for the course."));

    ResponseEntity<?> response1 = routeController.findCourseInstructor("COMS", 1);
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    assertEquals("Course Not Found", response1.getBody());

    ResponseEntity<?> response2 = routeController.findCourseInstructor("RANDOM", 1500);
    assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    assertEquals("Course Not Found", response2.getBody());
  }

  @Test
  public void testfindCourseTime() {
    ResponseEntity<?> response = routeController.findCourseTime("COMS", 3157);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("The course meets at: 4:10-5:25", response.getBody().toString());

    ResponseEntity<?> response1 = routeController.findCourseTime("COMS", 1);
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    assertEquals("Course Not Found", response1.getBody().toString());
  }

  @Test
  public void testAddMajorToDept() {
    ResponseEntity<?> response = routeController.addMajorToDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated successfully", response.getBody());

    ResponseEntity<?> response1 = routeController.addMajorToDept("RANDOM");
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    assertEquals("Department Not Found", response1.getBody());
  }

  @Test
  public void testRemoveMajorFromDept() {
    ResponseEntity<?> response = routeController.removeMajorFromDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated", response.getBody());

    Department noMajors = new Department(
        "NM", new HashMap<>(),
        "Nicole Lin", 
        0
    );
    IndividualProjectApplication.myFileDatabase.getDepartmentMapping().put("NO_MAJOR", noMajors);
    ResponseEntity<?> response1 = routeController.removeMajorFromDept("NO_MAJOR");
    assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
    assertEquals("Attribute is at minimum", response1.getBody());

    ResponseEntity<?> response2 = routeController.removeMajorFromDept("RANDOM");
    assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    assertEquals("Department Not Found", response2.getBody());
  }

  @Test
  public void testDropStudent() {
    ResponseEntity<?> response = routeController.dropStudent("COMS", 3157);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Student has been dropped.", response.getBody());

    ResponseEntity<?> response1 = routeController.dropStudent("COMS", 1);
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    assertEquals("Course Not Found", response1.getBody());
  }

  @Test
  public void testSetEnrollmentCount() {
    ResponseEntity<?> response = routeController.setEnrollmentCount("COMS", 3134, 200);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());

    ResponseEntity<?> response1 = routeController.setEnrollmentCount("COMS", 1, 200);
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    assertEquals("Course Not Found", response1.getBody());
  }

  @Test
  public void testChangeCourseTime() {
    ResponseEntity<?> response = routeController.changeCourseTime("COMS", 4156, "6:40-7:55");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());

    ResponseEntity<?> response1 = routeController.changeCourseTime("COMS", 1, "6:40-7:55");
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    assertEquals("Course Not Found", response1.getBody());
  }

  @Test
  public void testChangeCourseTeacher() {
    ResponseEntity<?> response = routeController.changeCourseTeacher("COMS", 3251, "Xi Chen");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());

    ResponseEntity<?> response1 = routeController.changeCourseTeacher("COMS", 1, "Xi Chen");
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    assertEquals("Course Not Found", response1.getBody());
  }

  @Test
  public void testChangeCourseLocation() {
    ResponseEntity<?> response = routeController.changeCourseLocation("COMS", 3251, "501 NWC");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());

    ResponseEntity<?> response1 = routeController.changeCourseLocation("COMS", 1, "501 NWC");
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    assertEquals("Course Not Found", response1.getBody());
  }

}