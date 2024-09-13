package dev.coms4156.project.individualproject;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/**
 * JUnit tests for some of the paths for routeController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
public class RouteControllerTests {

  @InjectMocks
  RouteController routeController;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(routeController).build();
  }

  @Test
  public void indexTest() throws Exception {
    mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Welcome")));
  }

  @Test
  public void retrieveDeptTest() throws Exception {
    mockMvc.perform(get("/retrieveDept")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk());

    // Testing Capitalization
    mockMvc.perform(get("/retrieveDept")
                    .param("deptCode", "cOmS"))
            .andExpect(status().isOk());

  }

  @Test
  public void retrieveInvalidDeptTest() throws Exception {
    mockMvc.perform(get("/retrieveDept")
                    .param("deptCode", "FakeDeptName"))
            .andExpect(status().isNotFound());

  }

  @Test
  public void retrieveCourseTest() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
                    .param("deptCode", "CoMS")
                    .param("courseCode", "4156"))
            .andExpect(status().isOk());

  }

  @Test
  public void retrieveInvalidCourseTest() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
                    .param("deptCode", "CoMS")
                    .param("courseCode", "123123123"))
            .andExpect(status().isNotFound());

  }

  @Test
  public void retrieveCourseDeptNotFoundTest() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
                    .param("deptCode", "FakeDepartment")
                    .param("courseCode", "123"))
            .andExpect(status().isNotFound());

  }

  @Test
  public void isCourseFullTest() throws Exception {
    mockMvc.perform(get("/isCourseFull")
                    .param("deptCode", "ChEm")
                    .param("courseCode", "2444"))
            .andExpect(status().isOk());

  }

  @Test
  public void isInvalidCourseFullTest() throws Exception {
    mockMvc.perform(get("/isCourseFull")
                    .param("deptCode", "FakeCourse")
                    .param("courseCode", "123"))
            .andExpect(status().isNotFound());

  }

  @Test
  public void getMajorCountFromDeptTest() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
                    .param("deptCode", "ChEm"))
            .andExpect(status().isOk());

  }

  @Test
  public void getMajorCountFromInvalidDeptTest() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
                    .param("deptCode", "FakeCourse"))
            .andExpect(status().isNotFound());

  }

  @Test
  public void idDeptChairTest() throws Exception {
    mockMvc.perform(get("/idDeptChair")
                    .param("deptCode", "chem"))
            .andExpect(content().string(containsString("department chair")))
            .andExpect(status().isOk());

  }

  @Test
  public void idInvalidDeptChairTest() throws Exception {
    mockMvc.perform(get("/idDeptChair")
                    .param("deptCode", "FakeCourse"))
            .andExpect(status().isNotFound());

  }

  @Test
  public void findCourseLocationTest() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
                    .param("deptCode", "chEm")
                    .param("courseCode", "2444"))
            .andExpect(status().isOk());

  }

  @Test
  public void findInvalidCourseLocationTest() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
                    .param("deptCode", "FakeCourse")
                    .param("courseCode", "123"))
            .andExpect(status().isNotFound());

  }

  @Test
  public void findCourseInstructorTest() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
                    .param("deptCode", "chEm")
                    .param("courseCode", "2444"))
            .andExpect(status().isOk());

  }

  @Test
  public void findInvalidCourseInstructorTest() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
                    .param("deptCode", "FakeCourse")
                    .param("courseCode", "123"))
            .andExpect(status().isNotFound());

  }

  @Test
  public void findCourseTimeTest() throws Exception {
    mockMvc.perform(get("/findCourseTime")
                    .param("deptCode", "chEm")
                    .param("courseCode", "2444"))
            .andExpect(status().isOk());

  }

  @Test
  public void findInvalidCourseTimeTest() throws Exception {
    mockMvc.perform(get("/findCourseTime")
                    .param("deptCode", "FakeCourse")
                    .param("courseCode", "123"))
            .andExpect(status().isNotFound());

  }

  @Test
  public void addMajorToDeptTest() throws Exception {

    mockMvc.perform(patch("/addMajorToDept?deptCode=chEm").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

  }

  @Test
  public void addInvalidMajorToDeptTest() throws Exception {
    mockMvc.perform(patch("/addMajorToDept?deptCode=FakeCourse")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

  }



}
