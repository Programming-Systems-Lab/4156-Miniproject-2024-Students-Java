package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Contains unit tests for each endpoint in RouteController.
 * */
@WebMvcTest(RouteController.class)
public class RouteControllerUnitTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private MyFileDatabase mockDatabase;

  private HashMap<String, Department> mockDepartments;
  private HashMap<String, Course> mockComsCourses;
  private HashMap<String, Course> mockEconCourses;
  private HashMap<String, Course> mockIeorCourses;

  /**
   * Set up the mock behavior of the static database in IndividualProjectApplication.
   */
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    mockDepartments = new HashMap<>();
    mockComsCourses = new HashMap<>();
    mockEconCourses = new HashMap<>();
    mockIeorCourses = new HashMap<>();

    // mock database for COMS Dept and Courses
    Course mockComs3827 = new Course("Daniel Rubenstein", "207 Math", "10:10-11:25", 300);
    mockComs3827.setEnrolledStudentCount(400);
    Course mockComs4156 = new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    mockComs4156.setEnrolledStudentCount(0);
    mockComsCourses.put("3827", mockComs3827);
    mockComsCourses.put("4156", mockComs4156);
    Department mockComsDepartment = new Department("COMS", mockComsCourses, "Luca Carloni", 2700);
    mockDepartments.put("COMS", mockComsDepartment);

    // mock database for Econ Dept and Courses
    Course mockEcon3827 = new Course("Matthieu Gomez", "517 HAM", "8:40-9:55", 86);
    mockEcon3827.setEnrolledStudentCount(86);
    Course mockEcon4156 = new Course("Mark Dean", "142 URIS", "2:40-3:55", 108);
    mockEcon4156.setEnrolledStudentCount(107);
    mockEconCourses.put("3827", mockEcon3827);
    mockEconCourses.put("4156", mockEcon4156);
    Department mockEconDepartment = new Department("ECON", mockEconCourses, "Michael Wood", 2345);
    mockDepartments.put("ECON", mockEconDepartment);

    // mock database for Ieor Dept and Courses
    Course mockIeor3827 = new Course("Michael Robbins", "633 MUDD", "9:00-11:30", 150);
    mockIeor3827.setEnrolledStudentCount(150);
    Course mockIeor4156 = new Course("Krzysztof M Choromanski", "633 MUDD", "7:10-9:40", 60);
    mockIeor4156.setEnrolledStudentCount(0);
    mockIeorCourses.put("3827", mockIeor3827);
    mockIeorCourses.put("4156", mockIeor4156);
    Department mockIeorDepartment = new Department("IEOR", mockIeorCourses, "Jay Sethuraman", 0);
    mockDepartments.put("IEOR", mockIeorDepartment);

    when(mockDatabase.getDepartmentMapping()).thenReturn(mockDepartments);

    // Override static database
    IndividualProjectApplication.overrideDatabase(mockDatabase);
  }

  @Test
  public void indexTest1() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "Welcome, in order to make an API call direct your browser "
                    + "or Postman to an endpoint "
                    + "\n\n This can be done using the following "
                    + "format: \n\n http:127.0.0.1:8080/endpoint?arg=value"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void indexTest2() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/index"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "Welcome, in order to make an API call direct your browser "
                            + "or Postman to an endpoint "
                            + "\n\n This can be done using the following "
                            + "format: \n\n http:127.0.0.1:8080/endpoint?arg=value"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void indexTest3() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/home"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "Welcome, in order to make an API call direct your browser "
                            + "or Postman to an endpoint "
                            + "\n\n This can be done using the following "
                            + "format: \n\n http:127.0.0.1:8080/endpoint?arg=value"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void retrieveDepartmentSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/retrieveDept")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string(mockDepartments.get("COMS").toString()))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void retrieveDepartmentNotFoundTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/retrieveDept")
                    .param("deptCode", "MATH"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void retrieveCourseSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/retrieveCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156"))
            .andExpect(status().isOk())
            .andExpect(content().string(mockComsCourses.get("4156").toString()))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void retrieveCourseDeptNotFoundTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/retrieveCourse")
                    .param("deptCode", "MATH")
                    .param("courseCode", "4156"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void retrieveCourseNotFoundTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/retrieveCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4111"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void retrieveCoursesSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/retrieveCourses")
                    .param("courseCode", "4156"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "\nInstructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25\n"
                            + "\nInstructor: Mark Dean; Location: 142 URIS; Time: 2:40-3:55\n"
                            + "\nInstructor: Krzysztof M Choromanski; "
                            + "Location: 633 MUDD; Time: 7:10-9:40\n"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void retrieveCoursesNotFoundTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/retrieveCourses")
                    .param("courseCode", "4111"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void isCourseFullSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/isCourseFull")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    String.valueOf(mockComsCourses.get("4156").isCourseFull())))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void isCourseFullNotFoundTest1() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/isCourseFull")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4111"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void isCourseFullNotFoundTest2() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/isCourseFull")
                    .param("deptCode", "MATH")
                    .param("courseCode", "4156"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void getMajorCtFromDeptSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/getMajorCountFromDept")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "There are: "
                            + mockDepartments.get("COMS").getNumberOfMajors()
                            + " majors in the department"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void getMajorCtFromDeptNotFoundTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/getMajorCountFromDept")
                    .param("deptCode", "MATH"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void identifyDeptChairSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/idDeptChair")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    mockDepartments.get("COMS").getDepartmentChair()
                            + " is the department chair."))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void identifyDeptChairNotFoundTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/idDeptChair")
                    .param("deptCode", "MATH"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void findCourseLocationSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseLocation")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    mockComsCourses.get("4156").getCourseLocation()
                            + " is where the course is located."))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void findCourseLocationNotFoundTest1() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseLocation")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4111"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void findCourseLocationNotFoundTest2() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseLocation")
                    .param("deptCode", "MATH")
                    .param("courseCode", "4156"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void findCourseInstructorSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseInstructor")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    mockComsCourses.get("4156").getInstructorName()
                            + " is the instructor for the course."))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void findCourseInstructorNotFoundTest1() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseInstructor")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4111"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void findCourseInstructorNotFoundTest2() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseInstructor")
                    .param("deptCode", "MATH")
                    .param("courseCode", "4156"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void findCourseTimeSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseTime")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "The course meets at: "
                            + mockComsCourses.get("4156").getCourseTimeSlot()))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void findCourseTimeNotFoundTest1() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseTime")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4111"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void findCourseTimeNotFoundTest2() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseTime")
                    .param("deptCode", "MATH")
                    .param("courseCode", "4156"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void addMajorToDeptSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/addMajorToDept")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attribute was updated successfully"))
            .andReturn();

    assertEquals(mockDepartments.get("COMS").getNumberOfMajors(), 2701);

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void addMajorToDeptNotFoundTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/addMajorToDept")
                    .param("deptCode", "MATH"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void removeMajorFromDeptSuccessTest1() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/removeMajorFromDept")
                    .param("deptCode", "ECON"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attribute was updated or is at minimum"))
            .andReturn();

    assertEquals(mockDepartments.get("ECON").getNumberOfMajors(), 2344);

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void removeMajorFromDeptSuccessTest2() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/removeMajorFromDept")
                    .param("deptCode", "IEOR"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attribute was updated or is at minimum"))
            .andReturn();

    assertEquals(mockDepartments.get("IEOR").getNumberOfMajors(), 0);

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void removeMajorFromDeptNotFoundTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/removeMajorFromDept")
                    .param("deptCode", "MATH"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void enrollStudentSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/enrollStudentInCourse")
                    .param("deptCode", "ECON")
                    .param("courseCode", "4156"))
            .andExpect(status().isOk())
            .andExpect(content().string("Student has been enrolled."))
            .andReturn();

    assertTrue(mockEconCourses.get("4156").isCourseFull());

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void enrollStudentFailTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/enrollStudentInCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "3827"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Student has not been enrolled."))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void enrollStudentNotFoundTest1() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/enrollStudentInCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4111"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void enrollStudentNotFoundTest2() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/enrollStudentInCourse")
                    .param("deptCode", "MATH")
                    .param("courseCode", "4156"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void dropStudentSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/dropStudentFromCourse")
                    .param("deptCode", "IEOR")
                    .param("courseCode", "3827"))
            .andExpect(status().isOk())
            .andExpect(content().string("Student has been dropped."))
            .andReturn();

    assertFalse(mockIeorCourses.get("3827").isCourseFull());

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void dropStudentFailTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/dropStudentFromCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Student has not been dropped."))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void dropStudentNotFoundTest1() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/dropStudentFromCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4111"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void dropStudentNotFoundTest2() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/dropStudentFromCourse")
                    .param("deptCode", "MATH")
                    .param("courseCode", "4156"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void setEnrollmentCountSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/setEnrollmentCount")
                    .param("deptCode", "IEOR")
                    .param("courseCode", "4156")
                    .param("count", "60"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."))
            .andReturn();

    assertTrue(mockIeorCourses.get("4156").isCourseFull());

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void setEnrollmentCountBadCountTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/setEnrollmentCount")
                    .param("deptCode", "IEOR")
                    .param("courseCode", "4156")
                    .param("count", "-10"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Count cannot be negative"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void setEnrollmentCountNotFoundTest1() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/setEnrollmentCount")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4111")
                    .param("count", "20"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void setEnrollmentCountNotFoundTest2() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/setEnrollmentCount")
                    .param("deptCode", "MATH")
                    .param("courseCode", "4156")
                    .param("count", "20"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void changeCourseTimeSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseTime")
                    .param("deptCode", "IEOR")
                    .param("courseCode", "3827")
                    .param("time", "10:10-11:25"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."))
            .andReturn();

    assertEquals(mockIeorCourses.get("3827").getCourseTimeSlot(), "10:10-11:25");

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void changeCourseTimeInvalidTimeTest1() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseTime")
                    .param("deptCode", "IEOR")
                    .param("courseCode", "3827")
                    .param("time", "10:10-15:25"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Time Slot Not Valid"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void changeCourseTimeInvalidTimeTest2() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseTime")
                    .param("deptCode", "IEOR")
                    .param("courseCode", "3827")
                    .param("time", "100:10-11:25"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Time Slot Not Valid"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void changeCourseTimeInvalidTimeTest3() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseTime")
                    .param("deptCode", "IEOR")
                    .param("courseCode", "3827")
                    .param("time", "10:10-11:61"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Time Slot Not Valid"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void changeCourseTimeNotFoundTest1() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseTime")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4111")
                    .param("time", "12:00-2:00"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void changeCourseTimeNotFoundTest2() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseTime")
                    .param("deptCode", "MATH")
                    .param("courseCode", "4156")
                    .param("time", "12:00-2:00"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void changeCourseTeacherSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseTeacher")
                    .param("deptCode", "IEOR")
                    .param("courseCode", "3827")
                    .param("teacher", "John Lennon"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."))
            .andReturn();

    assertEquals(mockIeorCourses.get("3827").getInstructorName(), "John Lennon");

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void changeCourseTeacherNotFoundTest1() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseTeacher")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4111")
                    .param("teacher", "Gail Kaiser"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void changeCourseTeacherNotFoundTest2() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseTeacher")
                    .param("deptCode", "MATH")
                    .param("courseCode", "4156")
                    .param("teacher", "Gail Kaiser"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void changeCourseLocationSuccessTest() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseLocation")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156")
                    .param("location", "HAV 314"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void changeCourseLocationNotFoundTest1() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseLocation")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4111")
                    .param("location", "HAV 314"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }

  @Test
  public void changeCourseLocationNotFoundTest2() throws Exception {
    MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseLocation")
                    .param("deptCode", "MATH")
                    .param("courseCode", "4156")
                    .param("location", "HAV 314"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"))
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    System.err.println(responseContent);
  }
}
