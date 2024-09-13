package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(RouteController.class) // This will only test the RouteController
public class RouteControllerUnitTests {

  @Autowired
  private MockMvc mockMvc;

  @BeforeAll
  public static void setUpRouteControllerTests() {
    testRouteController = new RouteController();
    HashMap <String, Department> departmentMapping = new HashMap<>();
    HashMap <String, Course> courseMapping = new HashMap<>();

    testCourse = new Course("Ben", "451 CSB", "4:10-5:40", 50);
    courseMapping.put("451", testCourse);

    testDepartment = new Department("4995", courseMapping, "Christian Lim", 2);
    departmentMapping.put("4995", testDepartment);
    
    MyFileDatabase mockDatabase = mock(MyFileDatabase.class);
    IndividualProjectApplication.myFileDatabase = mockDatabase;
    when(mockDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
  }

  @Test
  public void indexTest() throws Exception {
    this.mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(
            content().string("Welcome, in order to make an API call direct your browser or Postman to an endpoint "
                + "\n\n This can be done using the following format: \n\n http:127.0.0"
                + ".1:8080/endpoint?arg=value"));
  }

  @Test
  public void getDepartmentChair() throws Exception {
    this.mockMvc.perform(get("/idDeptChair?deptCode=4995"))
        .andExpect(status().isOk())
        .andExpect(content().string("Christian Lim"));
  }

  @Test
  public void retrieveDepartment() throws Exception {
    this.mockMvc.perform(get("/retrieveDept?deptCode=4995"))
        .andExpect(status().isOk())
        .andExpect(content()
            .string("Department{deptCode='4995', courses={}, departmentChair='Christian Lim', numberOfMajors=2}"));
  }

  @Test
  public void retrieveDepartmentFail() throws Exception {
    this.mockMvc.perform(get("/retrieveDept?deptCode=4990"))
        .andExpect(status().isOk())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void getMajorCtFromDept() throws Exception {
    this.mockMvc.perform(get("/getMajorCountFromDept?deptCode=4995"))
        .andExpect(status().isOk())
        .andExpect(content().string("There are: 2 majors in the department"));
  }

  @Test
  public void dropStudent() throws Exception {
    this.mockMvc.perform(get("/dropStudentFromCourse?deptCode=4995&courseCode=451&studentCount=1"))
        .andExpect(status().isOk())
        .andExpect(content().string("Student has been dropped."));
  }

  @Test
  public void retrieveCourse() throws Exception {
    this.mockMvc.perform(get("/retrieveCourse?deptCode=4995&courseCode=451"))
        .andExpect(status().isOk())
        .andExpect(content().string(
            "Course{instructorName='Ben', courseLocation='451 CSB', courseTimeSlot='4:10-5:40', enrolledStudentCount=50}"));
  }

  @Test
  public void setEnrollmentCount() throws Exception {
    this.mockMvc.perform(get("/setEnrollmentCount?deptCode=4995&enrollmentCount=2"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void changeCourseTime() throws Exception {
    this.mockMvc.perform(get("/changeCourseTime?deptCode=4995&courseCode=451&time=5:00-6:00"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void changeCourseTeacher() throws Exception {
    this.mockMvc.perform(get("/changeCourseTeacher?deptCode=4995&courseCode=451&teacher=John"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void changeCourseLocation() throws Exception {
    this.mockMvc.perform(get("/changeCourseLocation?deptCode=4995&courseCode=451&location=301 Pupin"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void isCourseFull() throws Exception {
    MvcResult result = this.mockMvc.perform(get("/isCourseFull?deptCode=4995&courseCode=451"))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();
    boolean isFull = Boolean.parseBoolean(content); // Convert response to boolean

    assertTrue(isFull);
  }

  @Test
  public void findCourseLocation() throws Exception {
    this.mockMvc.perform(get("/isCourseFull?deptCode=4995&courseCode=451"))
        .andExpect(status().isOk())
        .andExpect(content().string("The course is full."));
  }

  @Test
  public void findCourseInstructor() throws Exception {
    this.mockMvc.perform(get("/findCourseLocation?deptCode=4995&courseCode=451"))
        .andExpect(status().isOk())
        .andExpect(content().string("CSB 541 is where the course is located."));
  }

  @Test
  public void findCourseTime() throws Exception {
    this.mockMvc.perform(get("/findCourseTime?deptCode=4995&courseCode=451"))
        .andExpect(status().isOk())
        .andExpect(content().string("The course meets at: 4:10-5:40"));
  }

  @Test
  public void addMajorToDept() throws Exception {
    this.mockMvc.perform(get("/addMajorToDept?deptCode=4995"))
        .andExpect(status().isOk())
        .andExpect(content().string("Major has been added."));
  }

  @Test
  public void removeMajorFromDept() throws Exception {
    this.mockMvc.perform(get("/removeMajorFromDept?deptCode=4995"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated or is at minimum"));
  }

  public static RouteController testRouteController;
  public static Department testDepartment;
  public static Course testCourse;

}
