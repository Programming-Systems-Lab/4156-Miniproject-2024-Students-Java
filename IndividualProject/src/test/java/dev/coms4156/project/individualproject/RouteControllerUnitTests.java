package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.javatuples.Pair;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * These are Unit tests for the RouteController class.
 * The class initializes the application using the setup param.
 * This is done to trigger the resetData script to populate the test database.
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

    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
  }

  /**
   * Test for the retrieve department route ("/retrieveDept").
   * The department should not exist.
   */
  @Test
  public void retrieveDepartmentNotFoundTest() {
    String url = "http://localhost:" + port + "/retrieveDept?deptCode=INVALID";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Test for ("/findCourseLocation").
   */
  @Test
  public void findCourseLocationTest() {
    String deptCode = "COMS";
    String courseCode = "1004";
    String url = "http://localhost:" + port + "/findCourseLocation?deptCode="
                       + deptCode + "&courseCode=" + courseCode;

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().contains("417 IAB"));
  }

  /**
   * Test for ("/findCourseInstructor").
   */
  @Test
  public void findCourseInstructorTest() {
    String deptCode = "COMS";
    String courseCode = "1004";
    String url = "http://localhost:" + port
                       + "/findCourseInstructor?deptCode=" + deptCode
                       + "&courseCode=" + courseCode;

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().contains("Adam Cannon"));
  }

  /**
   * Test for ("/findCourseTime").
   */
  @Test
  public void findCourseTimeTest() {
    String deptCode = "COMS";
    String courseCode = "1004";
    String url = "http://localhost:" + port
                       + "/findCourseTime?deptCode=" + deptCode + "&courseCode=" + courseCode;

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().contains("11:40-12:55"));
  }

  /**
   * Test for ("/retrieveDept").
   */
  @Test
  public void retrieveAllDepartmentsTest() {
    String[] departments = {"COMS", "ECON", "IEOR", "CHEM", "PHYS", "ELEN", "PSYC"};

    for (String deptCode : departments) {
      String url = "http://localhost:" + port + "/retrieveDept?deptCode=" + deptCode;

      ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

      assertEquals(HttpStatus.OK, response.getStatusCode());
    }
  }


  /**
   * Test for ("/retrieveCourse").
   * This tests that each course exists, based on the data populated in the application file.
   */
  @Test
  public void retrieveAllCoursesTest() {
    Map<String, String[]> departmentCourses = new HashMap<>();

    departmentCourses.put("COMS",
          new String[]{"1004", "3134", "3157", "3203", "3261", "3251", "3827", "4156"});

    departmentCourses.put("ECON",
          new String[]{"1105", "2257", "3211", "3213", "3412", "4415", "4710", "4840"});

    departmentCourses.put("IEOR",
          new String[]{"2500", "3404", "3658", "4102", "4106", "4405", "4511", "4540"});

    departmentCourses.put("CHEM",
          new String[]{"1403", "1500", "2045", "2444", "2494", "3080", "4071", "4102"});

    departmentCourses.put("PHYS",
          new String[]{"1001", "1201", "1602", "2802", "3008", "4003", "4018", "4040"});

    departmentCourses.put("ELEN",
          new String[]{"1201", "3082", "3331", "3401", "3701", "4510", "4702", "4830"});

    departmentCourses.put("PSYC",
          new String[]{"1001", "1610", "2235", "2620", "3212", "3445", "4236", "4493"});

    for (Map.Entry<String, String[]> department : departmentCourses.entrySet()) {
      String deptCode = department.getKey();
      String[] associatedCourses = department.getValue();

      for (String courseCode : associatedCourses) {
        String url = "http://localhost:"
                           + port + "/retrieveCourse?deptCode="
                           + deptCode + "&courseCode=" + courseCode;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
      }
    }
  }


  /**
   * Test for ("/retrieveCourse").
   * This tests that each course exists, based on the data populated in the application file.
   */
  @Test
  public void retrieveCourseTest() {
    Integer courseCode = 1004;
    String url = "http://localhost:"
                           + port + "/retrieveCourses"
                           + "?courseCode=" + courseCode;
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    List<Object> jArray = new JSONArray(response.getBody()).toList();
    ArrayList<String> stringArray = new ArrayList<>();

    for (Object item: jArray)
      stringArray.add((String) item);

    ArrayList<Pair<String, Integer>>correctResult = new ArrayList<>();

    Map<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    Department[] departments = departmentMapping.values().toArray(new Department[0]);

    assertTrue(departments.length > 0);

    for (Department dept: departments)
      if (dept.getCourseSelection().containsKey(courseCode.toString()))
        correctResult.add(new Pair<>(dept.getCode(), courseCode) );

    assertTrue(correctResult.size() > 0);

    // For each item in response, ensure both the course code and dept code
    // match an entry in the correctResult array
    for (String resItem: stringArray) {
      boolean found = false;
      for (Pair<String, Integer> c: correctResult)
        if (resItem.contains(c.getValue0()) && resItem.contains(c.getValue1().toString())) {
          found = true;
          break;
        }
      assertTrue(found);
    }
  }


  /**
   * Test for ("/isCourseFull").
   * Case where course is full.
   */
  @Test
  public void courseIsFullTest() {
    String updateEnrollUrl = "http://localhost:" + port
                                   + "/updateEnrollmentCount?deptCode=COMS"
                                   + "&courseCode=1004&count=100000";
    restTemplate.patchForObject(updateEnrollUrl, null, String.class);
    String url = "http://localhost:" + port + "/isCourseFull?deptCode=COMS&courseCode=1004";
    ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
    assertEquals(true, response.getBody());
  }



  /**
   * Test for ("/isCourseFull").
   * Case where the course is not full.
   */
  @Test
  public void courseIsNotFullTest() {
    String url = "http://localhost:" + port + "/isCourseFull?deptCode=COMS&courseCode=1004";
    ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);

    assertEquals(false, response.getBody());
  }

  /**
   * Test for ("/getMajorCountFromDept").
   */
  @Test
  public void getMajorCountFromDeptTest() {
    String url = "http://localhost:" + port + "/getMajorCountFromDept?deptCode=COMS";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Test for ("/idDeptChair").
   */
  @Test
  public void identifyDeptChairTest() {
    String deptCode = "COMS";
    String url = "http://localhost:" + port + "/idDeptChair?deptCode=" + deptCode;

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().contains("Luca Carloni"));
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

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertTrue(response.getBody().contains("Department Not Found"));
  }

  /**
   * Test for ("/removeMajorFromDept").
   */
  @Test
  public void removeMajorFromDeptTest() {
    String deptCode = "COMS";
    String url = "http://localhost:" + port + "/removeMajorFromDept?deptCode=" + deptCode;

    String response = restTemplate.patchForObject(url, null, String.class);

    assertTrue(response.contains("Attribute was updated or is at minimum"));
  }

  /**
   * Test for ("/removeMajorFromDept").
   * Case when department is not found.
   */
  @Test
  public void removeMajorFromDeptNotFoundTest() {
    String deptCode = "INVALID";
    String url = "http://localhost:" + port + "/removeMajorFromDept?deptCode=" + deptCode;

    String response = restTemplate.patchForObject(url, null, String.class);

    assertTrue(response.contains("Department Not Found"));
  }

  /**
   * Test for ("/dropStudentFromCourse").
   */
  @Test
  public void dropStudentFromCourseTest() {
    String deptCode = "COMS";
    int courseCode = 1004;
    String url = "http://localhost:" + port + "/dropStudentFromCourse?deptCode="
                       + deptCode + "&courseCode=" + courseCode;

    String response = restTemplate.patchForObject(url, null, String.class);
    assertTrue(response.contains("Student has been dropped."));
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

    assertTrue(response.contains("Course Not Found"));
  }

  /**
   * Test for ("/addMajorToDept").
   */
  @Test
  public void addMajorToDeptTest() {
    String url = "http://localhost:" + port + "/addMajorToDept?deptCode=COMS";
    String response = restTemplate.patchForObject(url, null, String.class);
    assertTrue(response.contains("Attribute was updated successfully"));
  }

  /**
   * Test for ("/setEnrollment").
   */
  @Test
  public void changeCourseEnrollment() {
    String deptCode = "COMS";
    String courseCode = "1004";
    String count = "100000";
    String updateEnrollUrl = "http://localhost:" + port + "/updateEnrollmentCount" + "?deptCode="
                                   + deptCode + "&courseCode=" + courseCode  + "&count=" + count;
    String response = restTemplate.patchForObject(updateEnrollUrl, null, String.class);

    assertTrue(response.contains(count));
  }

  /**
   * Test for ("/updateCourseTime").
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

    assertTrue(response.contains(newTime));
  }

  /**
   * Test for ("/updateCourseTeacher").
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

    assertTrue(response.contains(newInstructor));
  }

  /**
   * Test for ("/updateCourseLocation").
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

    assertTrue(response.contains(newLocation));
  }
}
