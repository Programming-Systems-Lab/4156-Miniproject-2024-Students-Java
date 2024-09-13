package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Initialise the moch database for testing the RouteController class.
 *
 * @return A response entity indicating the result of the operation.
 */

@SpringBootTest
@AutoConfigureMockMvc
public class RouteControllerUnitTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private Map<String, Department> departmentMapping;

  public static RouteController testRouteController = new RouteController();
  public static Department testDepartment;
  public static MyFileDatabase testDatabase;

  /**
   * Test case setup.
   */

  @BeforeEach
  public void setUp() {
    departmentMapping = new HashMap<>();
    HashMap<String, Course> courseMapping = new HashMap<String, Course>();
    courseMapping = new HashMap<String, Course>();

    Course testCourse = new Course("Ben", "451 CSB", "4:10-5:40", 50);

    testDepartment = new Department("4995", courseMapping, "Christian Lim", 2);
    departmentMapping.put("4995", testDepartment);
    testDepartment.addCourse("451", testCourse);
    MyFileDatabase mockDatabase = mock(MyFileDatabase.class);
    IndividualProjectApplication.myFileDatabase = mockDatabase;

    when(mockDatabase.getDepartmentMapping())
        .thenReturn((HashMap<String, Department>) departmentMapping);

  }

  /**
   * Test case for the index endpoint.
   * 
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void indexTest() throws Exception {
    // Perform a GET request to the root endpoint and expect a 200 OK status
    this.mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(
            content().string("Welcome, in order to make an API call direct your browser "
                + "or Postman to an endpoint "
                + "\n\n This can be done using the following format: \n\n http:127.0.0"
                + ".1:8080/endpoint?arg=value"));
  }

  /**
   * Test case for the getDepartmentChair endpoint.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void getDepartmentChair() throws Exception {
    // Perform a GET request to the /idDeptChair endpoint with deptCode=4995 and
    // expect a 200 OK status
    this.mockMvc.perform(get("/idDeptChair?deptCode=4995"))
        .andExpect(status().isOk())
        .andExpect(content().string("Christian Lim is the department chair."));
  }

  /**
   * Test case for the retrieveDepartment method in the RouteController class.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void retrieveDepartment() throws Exception {
    ResponseEntity<?> response = testRouteController.retrieveDepartment("4995");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("4995 451: \nInstructor: Ben; Location: "
        + "451 CSB; Time: 4:10-5:40\n", response.getBody());
  }

  /**
   * Test case for the retrieveDepartment method in the RouteController class when
   * the department is not found.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void retrieveDepartmentFail() throws Exception {
    // Call the retrieveDepartment method in the RouteController class with
    // deptCode="5000" and expect a ResponseEntity with HttpStatus.OK
    ResponseEntity<?> response = testRouteController.retrieveDepartment("5000");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /**
   * Test case for the getMajorCtFromDept method in the RouteController class.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void getMajorCtFromDept() throws Exception {
    // Call the getMajorCtFromDept method in the RouteController class with
    // deptCode="4995" and expect a ResponseEntity with HttpStatus.OK
    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("4995");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("There are: 2 majors in the department", response.getBody());
  }

  /**
   * Test case for the getMajorCtFromDept method in the RouteController class when
   * the department is not found.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void getMajorCtFromDeptFail() throws Exception {
    // Call the getMajorCtFromDept method in the RouteController class with
    // deptCode="5000" and expect a ResponseEntity with HttpStatus.OK
    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("5000");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /**
   * Test case for the removeMajorCtFromDept method in the RouteController class.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void removeMajorCtFromDept() throws Exception {
    // Call the getMajorCtFromDept method in the RouteController class with
    // deptCode="4995" and expect a ResponseEntity with HttpStatus.OK
    ResponseEntity<?> response = testRouteController.removeMajorFromDept("4995");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated or is at minimum", response.getBody());
  }

  /**
   * Test case for the removeMajorCtFromDept method in the RouteController class
   * when
   * the department is not found.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void removeMajorCtFromDeptFail() throws Exception {
    // Call the getMajorCtFromDept method in the RouteController class with
    // deptCode="5000" and expect a ResponseEntity with HttpStatus.OK
    ResponseEntity<?> response = testRouteController.removeMajorFromDept("500");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /**
   * Test case for the addMajorToDept method in the RouteController class when
   * the department is found.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void addMajoToDept() throws Exception {
    // Call the getMajorCtFromDept method in the RouteController class with
    // deptCode="5000" and expect a ResponseEntity with HttpStatus.OK
    ResponseEntity<?> response = testRouteController.addMajorToDept("4995");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated successfully", response.getBody());
  }

  /**
   * Test case for the addMajorToDept method in the RouteController class when
   * the department is not found.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void addMajoToDeptFail() throws Exception {
    // Call the getMajorCtFromDept method in the RouteController class with
    // deptCode="5000" and expect a ResponseEntity with HttpStatus.OK
    ResponseEntity<?> response = testRouteController.addMajorToDept("5000");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /**
   * Test case for the findCourseTime method in the RouteController class.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void findCourseTime() throws Exception {
    // Call the findCourseTime method in the RouteController class with
    // deptCode="4995" and courseCode="451" and expect a ResponseEntity with
    // HttpStatus.OK
    ResponseEntity<?> response = testRouteController.findCourseTime("4995", 100);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Course time: 4:10-5:40", response.getBody());
  }

  /**
   * Test case for the findCourseTime method in the RouteController class on fail.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void findCourseTimeFail() throws Exception {
    // Call the findCourseTime method in the RouteController class with
    // deptCode="4995" and courseCode="451" and expect a ResponseEntity with
    // HttpStatus.OK
    ResponseEntity<?> response = testRouteController.findCourseTime("4995", 451);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /**
   * Test case for the getCourseLocation method in the RouteController class.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void findCourseLocation() throws Exception {
    // Call the getCourseLocation method in the RouteController class with
    // deptCode="4995" and courseCode="451" and expect a ResponseEntity with
    // HttpStatus.OK
    ResponseEntity<?> response = testRouteController.findCourseLocation("4995", 451);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("451 CSB is where the course is located", response.getBody());
  }

  /**
   * Test case for the getCourseLocation method in the RouteController class on
   * fail.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void findCourseLocationFail() throws Exception {
    // Call the getCourseLocation method in the RouteController class with
    // deptCode="4995" and courseCode="500" and expect a ResponseEntity with
    // HttpStatus.OK
    ResponseEntity<?> response = testRouteController.findCourseLocation("4995", 500);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /**
   * Test case for the findCourseInstructor method in the RouteController class.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void findCourseInstructor() throws Exception {
    // Call the findCourseInstructor method in the RouteController class with
    // deptCode="4995" and courseCode="451" and expect a ResponseEntity with
    // HttpStatus.OK
    ResponseEntity<?> response = testRouteController.findCourseInstructor("4995", 451);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Ben", response.getBody());
  }

  /**
   * Test case for the findCourseInstructor method in the RouteController class on fail.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void findCourseInstructorFail() throws Exception {
    // Call the findCourseInstructor method in the RouteController class with
    // deptCode="4995" and courseCode="500" and expect a ResponseEntity with
    // HttpStatus.OK
    ResponseEntity<?> response = testRouteController.findCourseInstructor("4995", 500);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /**
   * Test case for the isCourseFull method in the RouteController class.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void isCourseFull() throws Exception {
    // Call the isCourseFull method in the RouteController class with
    // deptCode="4995" and courseCode="451" and expect a ResponseEntity with
    // HttpStatus.OK

    ResponseEntity<?> response = testRouteController.isCourseFull("4995", 451);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(false, response.getBody());
  }

  /**
   * Test case for the isCourseFull method in the RouteController class on fail.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void isCourseFullFail() throws Exception {
    // Call the isCourseFull method in the RouteController class with
    // deptCode="4995" and courseCode="451" and expect a ResponseEntity with
    // HttpStatus.OK

    ResponseEntity<?> response = testRouteController.isCourseFull("5000", 451);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /**
   * Test case for the setEnrollmentCount method in the RouteController class on fail.
   * 
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void setEnrollmentCountFail() throws Exception {
    // Call the setEnrollmentCount method in the RouteController class with
    // deptCode="4995" and courseCode="451" and expect a ResponseEntity with
    // HttpStatus.OK

    ResponseEntity<?> response = testRouteController.setEnrollmentCount("5000", 540, 10);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /**
   * Test case for the setEnrollmentCount method in the RouteController class on fail.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void setEnrollmentCount() throws Exception {
    // Call the setEnrollmentCount method in the RouteController class with
    // deptCode="4995" and courseCode="451" and expect a ResponseEntity with
    // HttpStatus.OK

    ResponseEntity<?> response = testRouteController.setEnrollmentCount("4995", 451, 10);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Attributed was updated successfully", response.getBody());
  }

  /**
   * Test case for the changeCourseTime method in the RouteController class.
   *
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void changeCourseTime() throws Exception {
    // Call the changeCourseTime method in the RouteController class with
    // deptCode="4995" and courseCode="451" and expect a ResponseEntity with
    // HttpStatus.OK

    ResponseEntity<?> response = testRouteController.changeCourseTime("4995", 451, "10:10-11:40");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Attributed was updated successfully", response.getBody());
  }

  /**
   * Test case for the changeCourseTime method in the RouteController class on fail.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void changeCourseTimeFail() throws Exception {
    // Call the changeCourseTime method in the RouteController class with
    // deptCode="4995" and courseCode="451" and expect a ResponseEntity with
    // HttpStatus.OK

    ResponseEntity<?> response = testRouteController.changeCourseTime("5000", 451, "10:10-11:40");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /**
   * Test case for the changeCourseTeacher method in the RouteController class on.
   *
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void changeCourseTeacher() throws Exception {
    // Call the changeCourseTeacher method in the RouteController class with
    // deptCode="4995" and courseCode="451" and expect a ResponseEntity with
    // HttpStatus.OK

    ResponseEntity<?> response = testRouteController.changeCourseTeacher("4995", 451, "james");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Attributed was updated successfully", response.getBody());
  }

  /**
   * Test case for the changeCourseTeacher method in the RouteController class on fail.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void changeCourseTeacherFail() throws Exception {
    // Call the changeCourseTeacher method in the RouteController class with
    // deptCode="4995" and courseCode="451" and expect a ResponseEntity with
    // HttpStatus.OK

    ResponseEntity<?> response = testRouteController.changeCourseTeacher("5000", 451, "James");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /**
   * Test case for the changeCourseLocation method in the RouteController class.
   *
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void changeCourseLocation() throws Exception {
    // Call the changeCourseLocation method in the RouteController class with
    // deptCode="4995" and courseCode="451" and expect a ResponseEntity with
    // HttpStatus.OK

    ResponseEntity<?> response = testRouteController.changeCourseLocation("4995", 451, "Pupin301");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Attributed was updated successfully", response.getBody());
  }

  /**
   * Test case for the changeCourseLocation method in the RouteController class on fail.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void changeCourseLocationFail() throws Exception {
    // Call the changeCourseLocation method in the RouteController class with
    // deptCode="4995" and courseCode="451" and expect a ResponseEntity with
    // HttpStatus.OK

    ResponseEntity<?> response = testRouteController.changeCourseLocation("5000", 451, "Pupin 301");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }
}