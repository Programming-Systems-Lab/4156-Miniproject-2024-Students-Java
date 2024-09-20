package dev.coms4156.project.individualproject;

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
    mockEcon3827.setEnrolledStudentCount(400);
    Course mockEcon4156 = new Course("Mark Dean", "142 URIS", "2:40-3:55", 108);
    mockEcon4156.setEnrolledStudentCount(0);
    mockEconCourses.put("3827", mockEcon3827);
    mockEconCourses.put("4156", mockEcon4156);
    Department mockEconDepartment = new Department("ECON", mockEconCourses, "Michael Wood", 2345);
    mockDepartments.put("ECON", mockEconDepartment);

    // mock database for Ieor Dept and Courses
    Course mockIeor3827 = new Course("Michael Robbins", "633 MUDD", "9:00-11:30", 150);
    mockIeor3827.setEnrolledStudentCount(400);
    Course mockIeor4156 = new Course("Krzysztof M Choromanski", "633 MUDD", "7:10-9:40", 60);
    mockIeor4156.setEnrolledStudentCount(0);
    mockIeorCourses.put("3827", mockIeor3827);
    mockIeorCourses.put("4156", mockIeor4156);
    Department mockIeorDepartment = new Department("IEOR", mockIeorCourses, "Jay Sethuraman", 67);
    mockDepartments.put("IEOR", mockIeorDepartment);

    when(mockDatabase.getDepartmentMapping()).thenReturn(mockDepartments);

    // Override static database
    IndividualProjectApplication.overrideDatabase(mockDatabase);
  }

  @Test
  public void testIndex() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "Welcome, in order to make an API call direct your browser "
                    + "or Postman to an endpoint "
                    + "\n\n This can be done using the following "
                    + "format: \n\n http:127.0.0.1:8080/endpoint?arg=value"));
  }

  @Test
  public void testRetrieveDepartmentSuccess() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/retrieveDept")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string(mockDepartments.get("COMS").toString()));
  }

  @Test
  public void testRetrieveCourseSuccess() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/retrieveCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156"))
            .andExpect(status().isOk())
            .andExpect(content().string(mockComsCourses.get("4156").toString()));
  }

  @Test
  public void testRetrieveCoursesSuccess() throws Exception {
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
  public void testRetrieveCoursesNotFound() throws Exception {
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
  public void testIsCourseFullSuccess() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/isCourseFull")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    String.valueOf(mockComsCourses.get("4156").isCourseFull())));
  }

  @Test
  public void testFindCourseLocationSuccess() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseLocation")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    mockComsCourses.get("4156").getCourseLocation()
                            + " is where the course is located."));
  }

  @Test
  public void testFindCourseInstructorSuccess() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseInstructor")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    mockComsCourses.get("4156").getInstructorName()
                            + " is the instructor for the course."));
  }

  @Test
  public void testFindCourseTimeSuccess() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseTime")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "The course meets at: "
                            + mockComsCourses.get("4156").getCourseTimeSlot()));
  }

  @Test
  public void testIsCourseFullNotFound() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/isCourseFull")
                    .param("deptCode", "COMS")
                    .param("courseCode", "0000"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testGetMajorCtFromDeptSuccess() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/getMajorCountFromDept")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "There are: "
                            + mockDepartments.get("COMS").getNumberOfMajors()
                            + " majors in the department"));
  }

  @Test
  public void testIdentifyDeptChairSuccess() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/idDeptChair")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    mockDepartments.get("COMS").getDepartmentChair()
                            + " is the department chair."));
  }

  @Test
  public void testAddMajorToDeptSuccess() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/addMajorToDept")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attribute was updated successfully"));
  }

  @Test
  public void testRemoveMajorFromDeptSuccess() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/removeMajorFromDept")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attribute was updated or is at minimum"));
  }

  @Test
  public void testDropStudentSuccess() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/dropStudentFromCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "3827"))
             .andExpect(status().isOk())
             .andExpect(content().string("Student has been dropped."));
  }

  @Test
  public void testEnrollStudentSuccess() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/enrollStudentInCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156"))
            .andExpect(status().isOk())
            .andExpect(content().string("Student has been enrolled."));
  }

  @Test
  public void testEnrollStudentFail() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/enrollStudentInCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "3827"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Student has not been enrolled."));
  }

  @Test
  public void testEnrollStudentNotFound() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/enrollStudentInCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4111"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }


  @Test
  public void testRetrieveDepartmentNotFound() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/retrieveDept")
                    .param("deptCode", "MATH"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testRetrieveCourseDepartmentNotFound() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/retrieveCourse")
                    .param("deptCode", "MATH")
                    .param("courseCode", "4156"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testRetrieveCourseNotFound() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/retrieveCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "0000"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testFindCourseLocationNotFound() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseLocation")
                    .param("deptCode", "COMS")
                    .param("courseCode", "0000"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testFindCourseInstructorNotFound() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseInstructor")
                    .param("deptCode", "COMS")
                    .param("courseCode", "0000"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testFindCourseTimeNotFound() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/findCourseTime")
                    .param("deptCode", "COMS")
                    .param("courseCode", "0000"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testAddMajorToDeptNotFound() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/addMajorToDept")
                    .param("deptCode", "MATH"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testRemoveMajorFromDeptNotFound() throws Exception {
    mockMvc
              .perform(MockMvcRequestBuilders
                      .patch("/removeMajorFromDept")
                      .param("deptCode", "MATH"))
              .andExpect(status().isNotFound())
              .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testDropStudentNotFound() throws Exception {
    mockMvc
              .perform(MockMvcRequestBuilders
                      .patch("/dropStudentFromCourse")
                      .param("deptCode", "COMS")
                      .param("courseCode", "0000"))
              .andExpect(status().isNotFound())
              .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testSetEnrollmentCountNotFound() throws Exception {
    mockMvc
              .perform(MockMvcRequestBuilders
                      .patch("/setEnrollmentCount")
                      .param("deptCode", "COMS")
                      .param("courseCode", "0000")
                      .param("count", "20"))
              .andExpect(status().isNotFound())
              .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testChangeCourseTimeNotFound() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseTime")
                    .param("deptCode", "COMS")
                    .param("courseCode", "0000")
                    .param("time", "12:00-13:00"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testChangeCourseTeacherNotFound() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseTeacher")
                    .param("deptCode", "COMS")
                    .param("courseCode", "0000")
                    .param("teacher", "Gail Kaiser"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testChangeCourseLocationNotFound() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders
                    .patch("/changeCourseLocation")
                    .param("deptCode", "COMS")
                    .param("courseCode", "0000")
                    .param("location", "HAV 314"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }
}
