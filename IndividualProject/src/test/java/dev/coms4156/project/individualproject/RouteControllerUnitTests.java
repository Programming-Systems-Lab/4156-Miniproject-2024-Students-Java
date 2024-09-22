package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit tests for the RouteController class using setup parameters.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, args = {"setup"})
public class RouteControllerUnitTests {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;


  /**
   * Sets up application object for testing.
   * This method is run once before all tests.
   */
  @BeforeAll
  public static void setupApplicationForTesting() {
    // Override the database with test data
    IndividualProjectApplication.overrideDatabase(new MyFileDatabase(1,
          "./testData.txt"));
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
   * Test for the retrieve course route ("/retrieveCourse").
   * The course should not exist.
   */
  @Test
  public void retrieveCourseNotFoundTest() {
    String url = "http://localhost:" + port + "/retrieveCourse?deptCode=COMS&courseCode=0000";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertNotNull(response.getBody());
    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND,
          "The response should not contain a course.");
  }

  /**
   * Test for the retrieve department route ("/retrieveDept").
   * The department should not exist.
   */
  @Test
  public void retrieveDepartmentNotFoundTest() {
    String url = "http://localhost:" + port + "/retrieveDept?deptCode=INVALID";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
          "The response should return not found.");
  }

  /**
   * Test for retrieving the location of a course ("/findCourseLocation").
   */
  @Test
  public void findCourseLocationTest() {
    String deptCode = "COMS";
    String courseCode = "1004";
    String url = "http://localhost:" + port + "/findCourseLocation?deptCode="
                       + deptCode + "&courseCode=" + courseCode;

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode(),
          "The response should return HttpStatus.OK.");
    assertTrue(response.getBody().contains("417 IAB"),
          "The response should contain the correct course location.");
  }

  /**
   * Test for retrieving the course instructor ("/findCourseInstructor").
   */
  @Test
  public void findCourseInstructorTest() {
    String deptCode = "COMS";
    String courseCode = "1004";
    String url = "http://localhost:" + port
                       + "/findCourseInstructor?deptCode=" + deptCode
                       + "&courseCode=" + courseCode;

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode(),
          "The response should return HttpStatus.OK.");
    assertTrue(response.getBody().contains("Adam Cannon"),
          "The response should contain the correct instructor name.");
  }

  /**
   * Test for retrieving the course time ("/findCourseTime").
   */
  @Test
  public void findCourseTimeTest() {
    String deptCode = "COMS";
    String courseCode = "1004";
    String url = "http://localhost:" + port
                       + "/findCourseTime?deptCode=" + deptCode + "&courseCode=" + courseCode;

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode(),
          "The response should return HttpStatus.OK.");
    assertTrue(response.getBody().contains("11:40-12:55"),
          "The response should contain the correct course time.");
  }

  /**
   * Test for the retrieve department route ("/retrieveDept").
   */
  @Test
  public void retrieveAllDepartmentsTest() {
    // List of department codes
    String[] departments = {"COMS", "ECON", "IEOR", "CHEM", "PHYS", "ELEN", "PSYC"};

    // Loop over each department code and send a GET request to the /retrieveDept endpoint
    for (String deptCode : departments) {
      // Build the URL for each department
      String url = "http://localhost:" + port + "/retrieveDept?deptCode=" + deptCode;

      // Make the GET request
      ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

      // Validate the response
      assertNotNull(response.getBody(),
            "The department details should not be null for deptCode: " + deptCode);
      assertEquals(HttpStatus.OK, response.getStatusCode(),
            "The response should be HttpStatus.OK for department code: " + deptCode);
    }
  }


  /**
   * Test for the retrieve course route ("/retrieveCourse").
   * The course should exist.
   */
  @Test
  public void retrieveAllCoursesTest() {
    // Define departments and their corresponding courses
    Map<String, String[]> departmentCourses = new HashMap<>();

    // COMS department
    departmentCourses.put("COMS",
          new String[]{"1004", "3134", "3157", "3203", "3261", "3251", "3827", "4156"});

    // ECON department
    departmentCourses.put("ECON",
          new String[]{"1105", "2257", "3211", "3213", "3412", "4415", "4710", "4840"});

    // IEOR department
    departmentCourses.put("IEOR",
          new String[]{"2500", "3404", "3658", "4102", "4106", "4405", "4511", "4540"});

    // CHEM department
    departmentCourses.put("CHEM",
          new String[]{"1403", "1500", "2045", "2444", "2494", "3080", "4071", "4102"});

    // PHYS department
    departmentCourses.put("PHYS",
          new String[]{"1001", "1201", "1602", "2802", "3008", "4003", "4018", "4040"});

    // ELEN department
    departmentCourses.put("ELEN",
          new String[]{"1201", "3082", "3331", "3401", "3701", "4510", "4702", "4830"});

    // PSYC department
    departmentCourses.put("PSYC",
          new String[]{"1001", "1610", "2235", "2620", "3212", "3445", "4236", "4493"});

    // Loop over each department and its courses
    for (Map.Entry<String, String[]> department : departmentCourses.entrySet()) {
      String deptCode = department.getKey();
      String[] courses = department.getValue();

      for (String courseCode : courses) {
        // Build the URL for each course
        String url = "http://localhost:"
                           + port + "/retrieveCourse?deptCode="
                           + deptCode + "&courseCode=" + courseCode;

        // Make the GET request
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Validate the response
        assertNotNull(response.getBody(),
              "The course details should not be null for courseCode: " + courseCode);
        assertEquals(HttpStatus.OK, response.getStatusCode(),
              "The response should be HttpStatus.OK for courseCode: "
                    + courseCode + " in department " + deptCode);
      }
    }
  }




  /**
   * Test for the is course full route ("/isCourseFull").
   * The course is made to be full.
   */
  @Test
  public void courseIsFullTest() {
    String updateEnrollUrl = "http://localhost:" + port
                                   + "/updateEnrollmentCount?deptCode=COMS"
                                   + "&courseCode=1004&count=100000";
    restTemplate.patchForObject(updateEnrollUrl, null, String.class);
    String url = "http://localhost:" + port + "/isCourseFull?deptCode=COMS&courseCode=1004";
    ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
    assertNotNull(response.getBody());
    assertEquals(true, response.getBody(),
          "The course should be full.");
  }



  /**
   * Test for the is course full route ("/isCourseFull").
   * The course is not full.
   */
  @Test
  public void courseIsNotFullTest() {
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
    assertEquals(HttpStatus.OK, response.getStatusCode(),
          "The response should contain the number of majors.");
  }

  /**
   * Test for identifying the department chair ("/idDeptChair").
   */
  @Test
  public void identifyDeptChairTest() {
    String deptCode = "COMS";
    String url = "http://localhost:" + port + "/idDeptChair?deptCode=" + deptCode;

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode(),
          "The response should return HttpStatus.OK.");
    assertTrue(response.getBody().contains("Luca Carloni"),
          "The response should contain the correct department chair name.");
  }

  /**
   * Test for identifying the department chair ("/idDeptChair").
   * Case when department is not found.
   */
  @Test
  public void identifyDeptChairNotFoundTest() {
    String deptCode = "INVALID";
    String url = "http://localhost:" + port + "/idDeptChair?deptCode=" + deptCode;

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
          "The response should return HttpStatus.NOT_FOUND.");
    assertTrue(response.getBody().contains("Department Not Found"),
          "The response should indicate that the department was not found.");
  }

  /**
   * Test for removing a major from the department ("/removeMajorFromDept").
   */
  @Test
  public void removeMajorFromDeptTest() {
    String deptCode = "COMS";
    String url = "http://localhost:" + port + "/removeMajorFromDept?deptCode=" + deptCode;

    String response = restTemplate.patchForObject(url, null, String.class);

    assertNotNull(response);
    assertTrue(response.contains("Attribute was updated or is at minimum"),
          "The major count should be updated or at minimum.");
  }

  /**
   * Test for removing a major from the department ("/removeMajorFromDept").
   * Case when department is not found.
   */
  @Test
  public void removeMajorFromDeptNotFoundTest() {
    String deptCode = "INVALID";
    String url = "http://localhost:" + port + "/removeMajorFromDept?deptCode=" + deptCode;

    String response = restTemplate.patchForObject(url, null, String.class);

    assertNotNull(response);
    assertTrue(response.contains("Department Not Found"),
          "The response should indicate that the department was not found.");
  }

  /**
   * Test for dropping a student from a course ("/dropStudentFromCourse").
   */
  @Test
  public void dropStudentFromCourseTest() {
    String deptCode = "COMS";
    int courseCode = 1004;
    String url = "http://localhost:" + port + "/dropStudentFromCourse?deptCode="
                       + deptCode + "&courseCode=" + courseCode;

    String response = restTemplate.patchForObject(url, null, String.class);
    assertNotNull(response);
    assertTrue(response.contains("Student has been dropped."),
          "The response should indicate that the student was dropped.");
  }

  /**
   * Test for dropping a student from a course ("/dropStudentFromCourse").
   * Case when the course is not found.
   */
  @Test
  public void dropStudentFromCourseNotFoundTest() {
    String deptCode = "COMS";
    int courseCode = 9999; // Invalid course code
    String url = "http://localhost:" + port + "/dropStudentFromCourse?deptCode="
                       + deptCode + "&courseCode=" + courseCode;

    String response = restTemplate.patchForObject(url, null, String.class);

    assertNotNull(response);
    assertTrue(response.contains("Course Not Found"),
          "The response should indicate that the course was not found.");
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
   * Test for updating the course enrollment ("/setEnrollment").
   */
  @Test
  public void changeCourseEnrollment() {
    String deptCode = "COMS";
    String courseCode = "1004";
    String count = "100000";
    String updateEnrollUrl = "http://localhost:" + port + "/updateEnrollmentCount" + "?deptCode="
                                   + deptCode + "&courseCode=" + courseCode  + "&count=" + count;
    String response = restTemplate.patchForObject(updateEnrollUrl, null, String.class);

    assertNotNull(response);
    assertTrue(response.contains(count),
          "The course enrollment should be updated.");
  }

  /**
   * Test for changing the course time ("/updateCourseTime").
   */
  @Test
  public void changeCourseTimeTest() {
    String deptCode = "COMS";
    String courseCode = "1004";
    String newTime = "10:10-11:25";
    String changeTimeUrl = "http://localhost:" + port + "/updateCourseTime"
                                 + "?deptCode=" + deptCode + "&courseCode=" + courseCode
                                 + "&time=" + newTime;

    String response = restTemplate.patchForObject(changeTimeUrl, null, String.class);

    assertNotNull(response);
    assertTrue(response.contains(newTime),
          "The course time should be updated to " + newTime);
  }

  /**
   * Test for changing the course instructor ("/updateCourseTeacher").
   */
  @Test
  public void changeCourseInstructorTest() {
    String deptCode = "COMS";
    String courseCode = "1004";
    String newInstructor = "New Instructor";
    String changeInstructorUrl = "http://localhost:" + port + "/updateCourseTeacher"
                                       + "?deptCode=" + deptCode + "&courseCode=" + courseCode
                                       + "&teacher=" + newInstructor;

    String response = restTemplate.patchForObject(changeInstructorUrl, null, String.class);

    assertNotNull(response);
    assertTrue(response.contains(newInstructor),
          "The course instructor should be updated to " + newInstructor);
  }

  /**
   * Test for changing the course location ("/updateCourseLocation").
   */
  @Test
  public void changeCourseLocationTest() {
    String deptCode = "COMS";
    String courseCode = "1004";
    String newLocation = "New Location";
    String changeLocationUrl = "http://localhost:" + port + "/updateCourseLocation"
                                     + "?deptCode=" + deptCode + "&courseCode=" + courseCode
                                     + "&location=" + newLocation;

    String response = restTemplate.patchForObject(changeLocationUrl, null, String.class);

    assertNotNull(response);
    assertTrue(response.contains(newLocation),
          "The course location should be updated to " + newLocation);
  }
}
