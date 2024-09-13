package dev.coms4156.project.individualproject;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * This class contains unit tests for the RouteController class.
 */
@WebMvcTest(RouteController.class)
@AutoConfigureMockMvc
public class RouteControllerUnitTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  public MyFileDatabase testDatabase;
  public HashMap<String, Department> testMapping;

  /**
   * Sets up the mock testDatabase and testMapping for testing purposes.
   */
  @BeforeEach
  public void setupTestDatabases() {
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
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);
    courses.put("3134", coms3134);
    courses.put("3157", coms3157);
    courses.put("3203", coms3203);
    courses.put("3261", coms3261);
    courses.put("3251", coms3251);
    courses.put("3827", coms3827);
    courses.put("4156", coms4156);
    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    testMapping = new HashMap<>();
    testMapping.put("COMS", compSci);
    testDatabase.setMapping(testMapping);
    IndividualProjectApplication.myFileDatabase = testDatabase;
    when(testDatabase.getDepartmentMapping()).thenReturn(testMapping);
  }

  @Test
  public void welcomeTestSlash() throws Exception {
    mockMvc.perform(get("/")).andExpect(status().isOk())
        .andExpect(content().string("Welcome, in order to make an API call "
            + "direct your browser or Postman to an endpoint \n\n "
            + "This can be done using the following format: "
            + "\n\n http:127.0.0.1:8080/endpoint?arg=value"));
  }

  @Test
  public void welcomeTestIndex() throws Exception {
    mockMvc.perform(get("/index")).andExpect(status().isOk())
        .andExpect(content().string("Welcome, in order to make an API call "
            + "direct your browser or Postman to an endpoint \n\n "
            + "This can be done using the following format: "
            + "\n\n http:127.0.0.1:8080/endpoint?arg=value"));
  }

  @Test
  public void welcomeTestHome() throws Exception {
    mockMvc.perform(get("/home")).andExpect(status().isOk())
        .andExpect(content().string("Welcome, in order to make an API call "
            + "direct your browser or Postman to an endpoint \n\n "
            + "This can be done using the following format: "
            + "\n\n http:127.0.0.1:8080/endpoint?arg=value"));
  }

  @Test
  public void retrieveDeptTest() throws Exception {
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
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);
    courses.put("3134", coms3134);
    courses.put("3157", coms3157);
    courses.put("3203", coms3203);
    courses.put("3261", coms3261);
    courses.put("3251", coms3251);
    courses.put("3827", coms3827);
    courses.put("4156", coms4156);
    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    mockMvc.perform(get("/retrieveDept")
            .param("deptCode", "COMS")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(compSci.toString()));
  }

  @Test
  public void retrieveDeptTestFail() throws Exception {
    mockMvc.perform(get("/retrieveDept")
            .param("deptCode", "404")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void retrieveCourseTest() throws Exception {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    //data for coms dept
    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(coms1004.toString()));
  }

  @Test
  public void retrieveCourseTestFailCourse() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "404")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void retrieveCourseTestFailDept() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "404")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void courseFullTest() throws Exception {
    mockMvc.perform(get("/isCourseFull")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
  }

  @Test
  public void courseFullTestFail() throws Exception {
    mockMvc.perform(get("/isCourseFull")
            .param("deptCode", "404")
            .param("courseCode", "404")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void getMajorCtFromDeptTest() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
            .param("deptCode", "COMS")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("There are: 2700 majors in the department"));
  }

  @Test
  public void getMajorCtFromDeptTestFail() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
            .param("deptCode", "404")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void identifyDeptChairTest() throws Exception {
    mockMvc.perform(get("/idDeptChair")
            .param("deptCode", "COMS")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Luca Carloni is the department chair."));
  }

  @Test
  public void identifyDeptChairTestFail() throws Exception {
    mockMvc.perform(get("/idDeptChair")
            .param("deptCode", "404")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void findCourseLocationTest() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("417 IAB is where the course is located."));
  }

  @Test
  public void findCourseLocationTestFail() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
            .param("deptCode", "404")
            .param("courseCode", "404")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void findCourseInstructorTest() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Adam Cannon is the instructor for the course."));
  }

  @Test
  public void findCourseInstructorTestFail() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
            .param("deptCode", "404")
            .param("courseCode", "404")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void findCourseTimeTest() throws Exception {
    mockMvc.perform(get("/findCourseTime")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("The course meets at: 11:40-12:55"));
  }

  @Test
  public void findCourseTimeTestFail() throws Exception {
    mockMvc.perform(get("/findCourseTime")
            .param("deptCode", "404")
            .param("courseCode", "404")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void addMajorToDeptTest() throws Exception {
    mockMvc.perform(patch("/addMajorToDept")
            .param("deptCode", "COMS")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated successfully."));
  }

  @Test
  public void addMajorToDeptTestFail() throws Exception {
    mockMvc.perform(patch("/addMajorToDept")
            .param("deptCode", "404")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void removeMajorFromDeptTest() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
            .param("deptCode", "COMS")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated or is at minimum"));
  }

  @Test
  public void removeMajorFromDeptTestFail() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
            .param("deptCode", "404")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void dropStudentFromCourseTest() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Student has been dropped."));
  }

  @Test
  public void dropStudentFromCourseTestFail() throws Exception {
    Course testCourse = testMapping.get("COMS").getCourseSelection().get("1004");
    testCourse.setEnrolledStudentCount(0);
    mockMvc.perform(patch("/dropStudentFromCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Student has not been dropped."));
  }

  @Test
  public void dropStudentFromCourseTestFail404() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
            .param("deptCode", "404")
            .param("courseCode", "404")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void setEnrollmentCountTest() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .param("count", "100")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated successfully."));
  }

  @Test
  public void setEnrollmentCountTestFail() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
            .param("deptCode", "404")
            .param("courseCode", "404")
            .param("count", "100")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void changeCourseTeacherTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .param("teacher", "Suwei Ma")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated successfully."));
  }

  @Test
  public void changeCourseTeacherTestFail() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher")
            .param("deptCode", "404")
            .param("courseCode", "404")
            .param("teacher", "Suwei Ma")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void changeCourseLocationTest() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .param("location", "418 IAB")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated successfully."));
  }

  @Test
  public void changeCourseLocationTestFail() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation")
            .param("deptCode", "404")
            .param("courseCode", "404")
            .param("location", "418 IAB")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void changeCourseTimeTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTime")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .param("time", "10:00-12:00")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated successfully."));
  }

  @Test
  public void changeCourseTimeTestFail() throws Exception {
    mockMvc.perform(patch("/changeCourseTime")
            .param("deptCode", "404")
            .param("courseCode", "404")
            .param("time", "10:00-12:00")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void handleExceptionTest() throws Exception {
    when(testDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("TestException"));
    mockMvc.perform(get("/retrieveDept")
            .param("deptCode", "COMS")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andExpect(content().string("An Error has occurred"));
  }
}

