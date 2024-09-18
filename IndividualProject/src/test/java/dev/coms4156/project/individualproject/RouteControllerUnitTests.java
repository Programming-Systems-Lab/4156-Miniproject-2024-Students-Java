package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Provides unit test for testing implementations in the RouteController class.
 */
public class RouteControllerUnitTests {

  private RouteController routeController;
  private MyFileDatabase mockDatabase;
  private static Map<String, Department> mapping;
  
  /**
   * Initializes the test environment by setting up a mock database with 
   * predefined department mappings.
   * 
   * <p>This method:
   * Creates sample Course and Department objects.</li>
   * Mocks the Database methods to return these objects.</li>
   * Initializes a RouteController instance.</li>
   */
  @BeforeEach
  public void setup() {
    mapping = new HashMap<>();

    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    Course coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
    coms3134.setEnrolledStudentCount(242);
    Map<String, Course> comsCourses = new HashMap<>();
    comsCourses.put("1004", coms1004);
    comsCourses.put("3134", coms3134);

    Course econ1105 = new Course("Waseem Noor", locations[1], times[3], 210);
    econ1105.setEnrolledStudentCount(187);
    Course econ2257 = new Course("Tamrat Gashaw", "428 PUP", times[2], 125);
    econ2257.setEnrolledStudentCount(63);
    Map<String, Course> econCourses = new HashMap<>();
    econCourses.put("1105", econ1105);
    econCourses.put("2257", econ2257);
    
    Department econ = new Department("ECON", econCourses, "Michael Woodford", 2345);
    Department compSci = new Department("COMS", comsCourses, "Luca Carloni", 2700);

    mapping.put("COMS", compSci);
    mapping.put("ECON", econ);

    mockDatabase = mock(MyFileDatabase.class);
    doAnswer(invocation -> {
      return null;
    }).when(mockDatabase).setMapping(mapping);

    when(mockDatabase.getDepartmentMapping()).thenReturn(mapping);

    IndividualProjectApplication.myFileDatabase = mockDatabase;

    routeController = new RouteController();
  }

  @Test
  public void testRetrieveDepartment() {
    ResponseEntity<?> response = routeController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mapping.get("COMS").toString(), response.getBody());
  }

  @Test
  public void testRetrieveCourse() {
    ResponseEntity<?> response = routeController.retrieveCourse("COMS", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mapping.get("COMS").getCourseSelection().get("1004")
        .toString(), response.getBody());
  }

  @Test
  public void testFindCourseInstructorAndUpdate() {
    ResponseEntity<?> response = routeController.findCourseInstructor("COMS", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Adam Cannon is the instructor for the course.", response.getBody());
  }

  @Test
  public void testGetMajorCount() {
    ResponseEntity<?> response = routeController.getMajorCtFromDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("There are: 2700 majors in the department", response.getBody());
  }

  @Test
  public void testBadGetCount() {
    ResponseEntity<?> response = routeController.getMajorCtFromDept("ABC");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void testFindCourseInstructorCourseNotFound() {
    ResponseEntity<?> response = routeController.findCourseInstructor("COMS", 9999);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testGetDeptChair() {
    ResponseEntity<?> response = routeController.identifyDeptChair("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Luca Carloni is the department chair.", response.getBody());

    ResponseEntity<?> badResponse = routeController.identifyDeptChair("ABC");
    assertEquals(HttpStatus.NOT_FOUND, badResponse.getStatusCode());
  }

  @Test
  public void testLocationAndUpdate() {
    ResponseEntity<?> response = routeController.findCourseLocation("COMS", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("417 IAB is where the course is located.", response.getBody());

    ResponseEntity<?> updateResponse = routeController
        .changeCourseLocation("COMS", 1004, "451 CSB");
    assertEquals(HttpStatus.OK, updateResponse.getStatusCode());

    ResponseEntity<?> checkUpdateResponse = routeController.findCourseLocation("COMS", 1004);
    assertEquals(HttpStatus.OK, checkUpdateResponse.getStatusCode());
    assertEquals("451 CSB is where the course is located.", checkUpdateResponse.getBody());
  }

  @Test
  public void testMajorCountChanges() {
    ResponseEntity<?> firstResponse = routeController.getMajorCtFromDept("COMS");
    assertEquals(HttpStatus.OK, firstResponse.getStatusCode());

    ResponseEntity<?> addResponse = routeController.addMajorToDept("COMS");
    assertEquals(HttpStatus.OK, addResponse.getStatusCode());
    
    ResponseEntity<?> postAddResponse = routeController.getMajorCtFromDept("COMS");
    assertEquals(HttpStatus.OK, postAddResponse.getStatusCode());

    assertNotEquals(firstResponse.getBody(), postAddResponse.getBody());

    ResponseEntity<?> removeResponse = routeController.removeMajorFromDept("COMS");
    assertEquals(HttpStatus.OK, removeResponse.getStatusCode());

    ResponseEntity<?> getCountResponse = routeController.getMajorCtFromDept("COMS");
    assertEquals(firstResponse.getBody(), getCountResponse.getBody());
  }

  @Test
  public void testRetrieveCourses() {
    ResponseEntity<?> removeResponse = routeController.retrieveCourses(1004);
    assertEquals(HttpStatus.OK, removeResponse.getStatusCode());
  }



  @Test 
  public void testCourseTime() {
    ResponseEntity<?> goodResponse = routeController.findCourseTime("COMS", 1004);
    assertEquals(HttpStatus.OK, goodResponse.getStatusCode());
    assertEquals("The course meets at: 11:40-12:55", goodResponse.getBody());

    ResponseEntity<?> badResponse = routeController.findCourseTime("COMS", 1111);
    assertEquals(HttpStatus.NOT_FOUND, badResponse.getStatusCode());
  }

  @Test 
  public void dropStudentTest() {
    ResponseEntity<?> goodResponse = routeController.dropStudent("COMS", 1004);
    assertEquals(HttpStatus.OK, goodResponse.getStatusCode());

    ResponseEntity<?> badResponse = routeController.dropStudent("COMS", 1111);
    assertEquals(HttpStatus.NOT_FOUND, badResponse.getStatusCode());
  }

  @Test
  public void setEnrollmentCountTest() {
    ResponseEntity<?> goodResponse = routeController.setEnrollmentCount("COMS", 1004, 1000);
    assertEquals(HttpStatus.OK, goodResponse.getStatusCode());

    ResponseEntity<?> badResponse = routeController.setEnrollmentCount("COMS", 1111, 1000);
    assertEquals(HttpStatus.NOT_FOUND, badResponse.getStatusCode());
  }
}
