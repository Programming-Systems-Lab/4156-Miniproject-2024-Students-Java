package dev.coms4156.project.individualproject;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


/**
 * Tests the routing functionalities for various API endpoints in the application.
 * This class performs mock MVC tests to ensure that endpoints behave as expected.
 */

@WebMvcTest(RouteController.class)
public class RouteControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MyFileDatabase myFileDatabase;

  @BeforeEach
  void setUp() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    // data for coms dept
    coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
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
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);
    courses.put("3134", coms3134);
    courses.put("3157", coms3157);
    courses.put("3203", coms3203);
    courses.put("3261", coms3261);
    courses.put("3251", coms3251);
    courses.put("3827", coms3827);
    courses.put("4156", coms4156);
    compSci = new Department("COMS", courses, "Luca Carloni", 2700);
  }

  @Test
  public void retrieveDepartmentWhenExists() throws Exception {
    mockMvc.perform(get("/retrieveDept?deptCode=COMS")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(compSci.toString()));
  }

  @Test
  public void retrieveDepartmentWhenDepartmentDoesNotExist() throws Exception {
    mockMvc.perform(get("/retrieveDept?deptCode=EEE")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Department Not Found")));
  }

  @Test
  public void retrieveCourseWhenExists() throws Exception {
    mockMvc.perform(get("/retrieveCourse?deptCode=COMS&courseCode=1004")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(coms1004.toString()));
  }

  @Test
  public void retrieveCourseWhenCourseDoesNotExist() throws Exception {
    mockMvc.perform(get("/retrieveCourse?deptCode=COMS&courseCode=999")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Course Not Found")));
  }

  @Test
  public void retrieveCourseWhenDepartmentDoesNotExist() throws Exception {
    mockMvc.perform(get("/retrieveCourse?deptCode=ABC&courseCode=1004")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Department Not Found")));
  }

  @Test
  public void isCourseFullWhenCourseIsFull() throws Exception {
    mockMvc.perform(get("/isCourseFull?deptCode=COMS&courseCode=3134")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  public void isCourseFullWhenCourseDoesNotExist() throws Exception {
    mockMvc.perform(get("/isCourseFull?deptCode=COMS&courseCode=777")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Course Not Found")));
  }


  @Test
  public void getMajorCountWhenDepartmentDoesNotExist() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept?deptCode=ABC")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Department Not Found")));
  }

  @Test
  public void identifyDeptChairWhenDepartmentExists() throws Exception {
    mockMvc.perform(get("/idDeptChair?deptCode=COMS")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Luca Carloni is the department chair.")));
  }

  @Test
  public void identifyDeptChairWhenDepartmentDoesNotExist() throws Exception {
    mockMvc.perform(get("/idDeptChair?deptCode=ABC")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Department Not Found")));
  }

  @Test
  public void findCourseLocationWhenCourseExists() throws Exception {
    mockMvc.perform(get("/findCourseLocation?deptCode=COMS&courseCode=1004")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("417 IAB is where the course is located.")));
  }

  @Test
  public void findCourseLocationWhenCourseDoesNotExist() throws Exception {
    mockMvc.perform(get("/findCourseLocation?deptCode=COMS&courseCode=777")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Course Not Found")));
  }

  @Test
  public void findCourseLocationWhenDepartmentDoesNotExist() throws Exception {
    mockMvc.perform(get("/findCourseLocation?deptCode=BIO&courseCode=1004")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Course Not Found")));
  }

  @Test
  public void findCourseInstructorWhenCourseExists() throws Exception {
    mockMvc.perform(get("/findCourseInstructor?deptCode=COMS&courseCode=1004")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
            content().string(containsString("Adam Cannon is the instructor for the course.")));
  }

  @Test
  public void findCourseInstructorWhenCourseDoesNotExist() throws Exception {
    mockMvc.perform(get("/findCourseInstructor?deptCode=COMS&courseCode=404")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Course Not Found")));
  }

  @Test
  public void findCourseInstructorWhenDepartmentDoesNotExist() throws Exception {
    mockMvc.perform(get("/findCourseInstructor?deptCode=PHYS&courseCode=1004")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Course Not Found")));
  }

  @Test
  public void findCourseTimeWhenCourseExists() throws Exception {
    mockMvc.perform(get("/findCourseTime?deptCode=COMS&courseCode=1004")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("The course meets at: 11:40-12:55")));
  }

  @Test
  public void findCourseTimeWhenCourseDoesNotExist() throws Exception {
    mockMvc.perform(get("/findCourseTime?deptCode=COMS&courseCode=404")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Course Not Found")));
  }

  @Test
  public void findCourseTimeWhenDepartmentDoesNotExist() throws Exception {
    mockMvc.perform(get("/findCourseTime?deptCode=ABC&courseCode=1004")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(2)
  public void addMajorToDeptWhenDepartmentExists() throws Exception {
    mockMvc.perform(patch("/addMajorToDept?deptCode=COMS")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Attribute was updated successfully")));
  }

  @Test
  public void addMajorToDeptWhenDepartmentDoesNotExist() throws Exception {
    mockMvc.perform(patch("/addMajorToDept?deptCode=ME")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Department Not Found")));
  }

  @Test
  @Order(3)
  public void removeMajorFromDeptSuccess() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept?deptCode=COMS")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Attribute was updated or is at minimum")));
  }

  @Test
  public void removeMajorFromDeptNotFound() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept?deptCode=EEE")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Department Not Found")));
  }

  @Test
  public void dropStudentFromCourseSuccess() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse?deptCode=COMS&courseCode=1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Student has been dropped.")));
  }


  @Test
  public void dropStudentFromCourseNotFound() throws Exception {
    mockMvc.perform(
            patch("/dropStudentFromCourse?deptCode=COMS&courseCode=999") // Non-existent course
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Course Not Found")));
  }


  @Test
  @Order(4)
  void setEnrollmentCountSuccess() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount?deptCode=COMS&courseCode=1004&count=30")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Attributed was updated successfully.")));
  }

  @Test
  void setEnrollmentCountCourseNotFound() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(new HashMap<>());

    mockMvc.perform(patch("/setEnrollmentCount?deptCode=COMS&courseCode=404&count=30")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Course Not Found")));
  }

  @Test
  void changeCourseTimeSuccess() throws Exception {
    mockMvc.perform(patch("/changeCourseTime?deptCode=COMS&courseCode=3134&time=13:00 - 14:30")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Attributed was updated successfully.")));
  }

  @Test
  void changeCourseTimeCourseNotFound() throws Exception {
    mockMvc.perform(patch("/changeCourseTime?deptCode=COMS&courseCode=999&time=09:00 - 10:30")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Course Not Found")));
  }

  @Test
  void changeCourseTeacherSuccess() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher?deptCode=COMS&courseCode=3134&teacher=Dr. ABC")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Attributed was updated successfully.")));

  }

  @Test
  void changeCourseTeacherNotFound() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher?deptCode=COMS&courseCode=999&teacher=Dr. ABC")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Course Not Found")));
  }

  @Test
  void changeCourseLocationSuccess() throws Exception {
    mockMvc.perform(
            patch("/changeCourseLocation?deptCode=COMS&courseCode=3134&location=New Location")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Attributed was updated successfully.")));
  }

  @Test
  void changeCourseLocationNotFound() throws Exception {
    // Assuming the course code does not exist
    mockMvc.perform(
            patch("/changeCourseLocation?deptCode=COMS&courseCode=999&location=New Location")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Course Not Found")));
  }

  private static Department compSci;
  private static Course coms1004;
  private static Course coms3134;
}






