package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * Integration tests for the RouteController class using setup parameters.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, args = {"setup"})
public class RouteControllerUnitTests {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;


  @BeforeAll
  public static void setupApplicationForTesting() {
    // Override the database with test data
    IndividualProjectApplication.overrideDatabase(new MyFileDatabase(1, "./testData.txt"));
  }

  /**
   * Test for the home route ("/index").
   */
  @Test
  public void indexTest() {
    String url = "http://localhost:" + port + "/index";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertNotNull(response.getBody());
    assertEquals("""
          Welcome, in order to make an API call direct your browser or Postman to an endpoint\s

           This can be done using the following format:\s

           http:127.0.0.1:8080/endpoint?arg=value""", response.getBody());
  }

  /**
   * Test for the retrieve department route ("/retrieveDept").
   */
  @Test
  public void retrieveDepartmentTest() {
    String url = "http://localhost:" + port + "/retrieveDept?deptCode=COMS";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    assertNotNull(response.getBody());
    assertEquals(response.getStatusCode(), HttpStatus.OK,
          "The response should contain the department 'COMS'.");
  }

  /**
   * Test for the retrieve course route ("/retrieveCourse").
   */
  @Test
  public void retrieveCourseTest() {
    String url = "http://localhost:" + port + "/retrieveCourse?deptCode=COMS&courseCode=1004";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertNotNull(response.getBody());
    assertEquals(response.getStatusCode(), HttpStatus.OK,
          "The response should contain the course details.");
  }

  /**
   * Test for the is course full route ("/isCourseFull").
   */
  @Test
  public void isCourseFullTest() {
    String url = "http://localhost:" + port + "/isCourseFull?deptCode=COMS&courseCode=1004";
    ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);

    assertNotNull(response.getBody());
    assertEquals(false, response.getBody(),
          "The course should not be full.");
  }

  /**
   * Test for the get major count route ("/getMajorCountFromDept").
   */
  @Test
  public void getMajorCountFromDeptTest() {
    String url = "http://localhost:" + port + "/getMajorCountFromDept?deptCode=COMS";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertNotNull(response.getBody());
    assertTrue(response.getStatusCode().equals(HttpStatus.OK),
          "The response should contain the number of majors.");
  }

  /**
   * Test for adding a major to the department ("/addMajorToDept").
   */
  @Test
  public void addMajorToDeptTest() {
    String url = "http://localhost:" + port + "/addMajorToDept?deptCode=COMS";
    String response = restTemplate.patchForObject(url, null, String.class);
    assertNotNull(response);
    assertTrue(response.contains("Attribute was updated successfully"),
          "The major should be added to the dept.");
  }

  /**
   * Test for changing the course time ("/changeCourseTime").
   */
  @Test
  public void changeCourseTimeTest() {
    String url = "http://localhost:" + port + "/changeCourseTime?deptCode=COMS&courseCode=1004&time=10:10-11:25";
    String response = restTemplate.patchForObject(url, null, String.class);

    assertNotNull(response);
    assertTrue(response.contains("Attributed was updated successfully"),
          "The course time should be updated.");
  }
//
  /**
   * Test for changing the course instructor ("/changeCourseTeacher").
   */
  @Test
  public void changeCourseInstructorTest() {
    String url = "http://localhost:" + port + "/changeCourseTeacher?deptCode=COMS&courseCode=1004&teacher=New Instructor";
    String response = restTemplate.patchForObject(url, null, String.class);

    assertNotNull(response);
    assertTrue(response.contains("Attributed was updated successfully"), "The course instructor should be updated.");
  }

  /**
   * Test for changing the course location ("/changeCourseLocation").
   */
  @Test
  public void changeCourseLocationTest() {
    String url = "http://localhost:" + port + "/changeCourseLocation?deptCode=COMS&courseCode=1004&location=New Location";
    String response = restTemplate.patchForObject(url, null, String.class);

    assertNotNull(response);
    assertTrue(response.contains("Attributed was updated successfully"), "The course location should be updated.");
  }
}
