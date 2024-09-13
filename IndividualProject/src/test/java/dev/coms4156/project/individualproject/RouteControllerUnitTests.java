package dev.coms4156.project.individualproject;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


/**
 * Unit tests for the RouteController class.
 * <p>
 * This class contains test cases for verifying the functionality and behavior
 * of the endpoints defined in the RouteController class. It uses
 * Spring Boot's MockMvc to perform HTTP requests and validate responses.
 * </p>
 */
@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
public class RouteControllerUnitTests {

  @Test
  public void testIndex() {
    RouteController controller = new RouteController();

    String expectedResponse = "Welcome, in order to make an API call direct "
                + "your browser or Postman to an endpoint "
                + "\n\n This can be done using the following format: \n\n http:127.0.0"
                + ".1:8080/endpoint?arg=value";

    String result = controller.index();

    assertEquals(expectedResponse, result);
  }



  private void performGetRequestTest(String route, String queryParams,
                                         ResultMatcher expectedStatus, String expectedContent)
                                            throws Exception {
    mockMvc.perform(get(route + "?" + queryParams))
                .andDo(print())
                .andExpect(expectedStatus)
                .andExpect(expectedContent == null ? content().string("") :
                        content().string(containsString(expectedContent)));
  }

  private void performPatchRequestTest(String route, String queryParams,
                                         ResultMatcher expectedStatus,
                                         String expectedContent) throws Exception {
    mockMvc.perform(patch(route + "?" + queryParams))
              .andDo(print())
              .andExpect(expectedStatus)
              .andExpect(expectedContent == null ? content().string("") :
                      content().string(containsString(expectedContent)));
  }


  /**
    * Testing the retrieval of department.
  */

  @Test
  public void testCorrectRetrieveDepartment() throws Exception {
    performGetRequestTest("/retrieveDept", "deptCode=COMS",
            status().isOk(), "COMS 1004: \nInstructor: Adam Cannon; "
            + "Location: 417 IAB; Time: 11:40-12:55\n");
  }

  @Test
  public void testWrongRetrieveDepartment() throws Exception {
    performGetRequestTest("/retrieveDept", "deptCode=electrical",
            status().isNotFound(), "Department Not Found");
  }

  @Test
  public void testNullRetrieveDepartment() throws Exception {
    performGetRequestTest("/retrieveDept", "deptCode=null",
            status().isNotFound(), "Department Not Found");
  }

  /**
    * Retrieval Courses.
  */
  @Test
  public void testCorrectRetrieveCourse() throws Exception {
    performGetRequestTest("/retrieveCourse", "deptCode=COMS&courseCode=3261",
            status().isOk(), "Instructor: Josh Alman; Location: 417 IAB; Time: 20:10-21:25");
  }

  @Test
  public void testWrongRetrieveCourse() throws Exception {
    performGetRequestTest("/retrieveCourse", "deptCode=electrical&courseCode=3261",
            status().isNotFound(), "Department Not Found");
  }


  /**
    * Is course full endpoint.
  */

  @Test
  public void testCourseFull() throws Exception {
    performGetRequestTest("/isCourseFull", "deptCode=COMS&courseCode=3261",
            status().isOk(), "true");
  }

  @Test
  public void testCourseNotAvailable() throws Exception {
    performGetRequestTest("/isCourseFull", "deptCode=COMS&courseCode=1234",
            status().isNotFound(), "Course Not Found");
  }


  /**
   * Count from department.
   */

  @Test
  public void testValidMajorsInDepartment() throws Exception {
    performGetRequestTest("/getMajorCountFromDept", "deptCode=COMS",
            status().isOk(), "There are: 2700 majors in the department");
  }

  @Test
  public void testInvalidMajorsInDepartment() throws Exception {
    performGetRequestTest("/getMajorCountFromDept", "deptCode=electrical",
            status().isNotFound(), "Department Not Found");
  }

  /**
   * Display department chair.
   */

  @Test
  public void testDeptChair() throws Exception {
    performGetRequestTest("/idDeptChair", "deptCode=COMS",
                status().isOk(), "Luca Carloni is the department chair.");
  }

  @Test
  public void testDeptChairDepartmentNotAvailable() throws Exception {
    performGetRequestTest("/idDeptChair", "deptCode=electrical",
            status().isNotFound(), "Department Not Found");
  }

  /**
    * location of a course.
    */

  @Test
  public void testValidCourseLocation() throws Exception {
    performGetRequestTest("/findCourseLocation", "deptCode=COMS&courseCode=3261",
            status().isOk(), "");
  }

  @Test
  public void testInvalidCourseLocation() throws Exception {
    performGetRequestTest("/findCourseLocation", "deptCode=electrical&courseCode=1234",
            status().isNotFound(), "");
  }

  /**
   * Find course instructor.
   */
  @Test
  public void testValidCourseInstructor() throws Exception {
    performGetRequestTest("/findCourseInstructor", "deptCode=COMS&courseCode=3261",
            status().isOk(), "Josh Alman is the instructor for the course.");
  }

  @Test
  public void testInvalidCourseInstructor() throws Exception {
    performGetRequestTest("/findCourseInstructor", "deptCode=electrical&courseCode=1234",
            status().isNotFound(), "Course Not Found");
  }

  /**
   * Find course time.
   */

  @Test
  public void testValidCourseTime() throws Exception {
    performGetRequestTest("/findCourseTime", "deptCode=COMS&courseCode=3261",
            status().isOk(), "The course meets at: 20:10-21:25");
  }

  @Test
  public void testInvalidCourseTime() throws Exception {
    performGetRequestTest("/findCourseTime", "deptCode=electrical&courseCode=1234",
            status().isNotFound(), "Course Not Found");
  }


  /**
   * Add major to Dept.
   */

  @Test
  public void addStudentToDepartment() throws Exception {
    performPatchRequestTest("/addMajorToDept", "deptCode=COMS",
            status().isOk(), "Attribute was updated successfully");
  }

  @Test
  public void addStudentToDepartment2() throws Exception {
    performPatchRequestTest("/addMajorToDept", "deptCode=electrical",
            status().isNotFound(), "Department Not Found");
  }

  /**
   * Remove a student from specified dept.
   */

  @Test
  public void testRemoveStudentFromDepartment() throws Exception {
    performPatchRequestTest("/removeMajorFromDept", "deptCode=COMS",
            status().isOk(), "Attribute was updated or is at minimum");
  }

  @Test
  public void testRemoveStudentFromDepartment2() throws Exception {
    performPatchRequestTest("/removeMajorFromDept", "deptCode=electrical",
            status().isNotFound(), "Department Not Found");
  }


  /**
   * Remove a student from a specific course.
   */
  @Test
  public void testDropStudentFromCourse() throws Exception {
    performPatchRequestTest("/dropStudentFromCourse", "deptCode=COMS&courseCode=3261",
            status().isOk(), "Student has been dropped");
  }

  @Test
  public void testDropStudentFromCourse2() throws Exception {
    performPatchRequestTest("/dropStudentFromCourse", "deptCode=electrical?courseCode=1234",
            status().isBadRequest(), "");
  }

  /**
   * Update the enrollment count for a specified course in department.
   */

  @Test
  public void testEnrollmentCount() throws Exception {
    performPatchRequestTest("/setEnrollmentCount", "deptCode=COMS&courseCode=3261&count=350",
            status().isOk(), "Attribute was updated successfully.");
  }

  @Test
  public void testEnrollmentCount2() throws Exception {
    performPatchRequestTest("/setEnrollmentCount", "deptCode=electrical&courseCode=1234&count=450",
                status().isNotFound(), "Course Not Found");
  }


  /**
   * Change time of a course.
   */

  @Test
  public void testChangeCourseTime() throws Exception {
    performPatchRequestTest("/changeCourseTime", "deptCode=COMS&courseCode=3261&time=20:10-21:25",
            status().isOk(), "Attribute was updated successfully.");
  }

  /**
   * Change instructor of a course.
   */

  @Test
  public void testChangeInstructor() throws Exception {
    performPatchRequestTest("/changeCourseTeacher",
            "deptCode=COMS&courseCode=3261&teacher=Josh Alman",
            status().isOk(), "Attribute was updated successfully.");
  }


  /**
   * Update location of a specified course in department.
   */
  @Test
  public void testUpdateCourse() throws Exception {
    performPatchRequestTest("/changeCourseLocation", "deptCode=COMS&courseCode=4156&location=VIT",
            status().isOk(), "Attribute was updated successfully.");
  }


  @Autowired
  private MockMvc mockMvc;

}
