package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
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
  public static Course testCourse;
  public static MyFileDatabase testDatabase = mock(MyFileDatabase.class);

  /**
   * Test case setup.
   */

  @BeforeEach
  public void setUp() {
    departmentMapping = new HashMap<>();
    testDepartment = new Department("4995", new HashMap<>(), "Christian Lim", 2);
    testCourse = new Course("Ben", "451 CSB", "4:10-5:40", 50);
    departmentMapping.put("4995", testDepartment);

    given(testDatabase.getDepartmentMapping())
        .willReturn((HashMap<String, Department>) departmentMapping);

    testDepartment.addCourse("451", testCourse);
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
        .andExpect(content().string("Christian Lim"));
  }

  /**
   * Test case for the retrieveDepartment method in the RouteController class.
   *
   * @throws Exception if an error occurs during the test
   */

  @Test
  public void retrieveDepartment() throws Exception {
    // Call the retrieveDepartment method in the RouteController class with
    // deptCode="4995" and expect a ResponseEntity with HttpStatus.OK
    ResponseEntity<?> response = testRouteController.retrieveDepartment("4995");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Department{departmentChair='Christian Lim', "
        + "numberOfMajors=2, deptCode='4995'}", response);
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
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Department Not Found", response);
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
    assertEquals("There are: 2 majors in the department", response);
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
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Department Not Found", response);
  }

}
