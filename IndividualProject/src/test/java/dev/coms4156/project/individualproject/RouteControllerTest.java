package dev.coms4156.project.individualproject;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Unit tests for the RouteController.
 * the tests ensures the functionality of the RouteController.
 */
@SpringBootTest
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RouteControllerTest {
  @Autowired
  WebApplicationContext wac;

  @Autowired
  ObjectMapper objectMapper;

  MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    // this is setting up the MockMvc environment with the Spring Data repository injected into the server
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  public void retrieveDepartmentTest() throws Exception {
    String deptCode = "IEOR";
    mockMvc.perform(get("/retrieveDept")
        .param("deptCode", deptCode))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("IEOR 3404")));
  }


  @Test
  public void retrieveCourseTest() throws Exception {
    String deptCode = "IEOR";
    String courseCode = "2500";

    mockMvc.perform(get("/retrieveCourse")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("2500")));
  }


  @Test
  public void getMajorCtFromDeptTest() throws Exception {
    String deptCode = "IEOR";

    mockMvc.perform(get("/getMajorCountFromDept")
        .param("deptCode", deptCode))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("67")));
  }


  @Test
  public void identifyDeptChairTest() throws Exception {
    String deptCode = "CHEM";

    mockMvc.perform(get("/idDeptChair")
        .param("deptCode", deptCode))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Laura J. Kaufman")));
  }


  @Test
  public void findCourseLocationTest() throws Exception {
    String deptCode = "CHEM";
    String courseCode = "1403";

    mockMvc.perform(get("/findCourseLocation")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("309 HAV")));
  }

  @Test
  public void findCourseInstructorTest() throws Exception {
    String deptCode = "CHEM";
    String courseCode = "1403";

    mockMvc.perform(get("/findCourseInstructor")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Ruben M Savizky")));
  }

  @Test
  public void findCourseTimeTest() throws Exception {
    String deptCode = "CHEM";
    String courseCode = "1403";

    mockMvc.perform(get("/findCourseTime")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("6:10-7:25")));
  }

  @Test
  @Order(1)
  public void addMajorToDeptTest() throws Exception {
    String deptCode = "CHEM";

    mockMvc.perform(get("/addMajorToDept")
        .param("deptCode", deptCode))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("251")));
  }

  @Test
  @Order(2)
  public void removeMajorFromDeptTest() throws Exception {
    String deptCode = "CHEM";

    mockMvc.perform(get("/removeMajorFromDept")
        .param("deptCode", deptCode))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("250")));
  }

  @Test
  @Order(3)
  public void dropStudentTest() throws Exception {
    String deptCode = "CHEM";
    String courseCode = "1403";

    mockMvc.perform(get("/dropStudent")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Student has been dropped.")));
  }

  @Test
  @Order(4)
  public void setEnrollmentCountTest() throws Exception {
    String deptCode = "ECON";
    String courseCode = "1105";
    String enrollmentCount = "110";

    mockMvc.perform(get("/setEnrollmentCount")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode)
        .param("count", enrollmentCount))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Attributed was updated successfully.")));
  }

  @Test
  void changeCourseTimeTest() throws Exception {
    String deptCode = "ECON";
    String courseCode = "1105";
    String newTime = "11:40-12:55";

    mockMvc.perform(get("/changeCourseTime")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode)
        .param("time", newTime))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Attributed was updated successfully.")));
  }

  @Test
  void changeCourseTeacherTest() throws Exception {
    String deptCode = "ECON";
    String courseCode = "1105";
    String newTeacher = "Tamrat Gashaw";

    mockMvc.perform(get("/changeCourseTeacher")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode)
        .param("teacher", newTeacher))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Attributed was updated successfully.")));
  }

  @Test
  void changeCourseLocationTest() throws Exception {
    String deptCode = "ECON";
    String courseCode = "1105";
    String newLocation = "301 URIS";

    mockMvc.perform(get("/changeCourseLocation")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode)
        .param("location", newLocation))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Attributed was updated successfully.")));
  }

}