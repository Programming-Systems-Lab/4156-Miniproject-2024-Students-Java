package dev.coms4156.project.individualproject;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
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

  /**
   * set up the MockMvc environment.
   */
  @BeforeEach
  public  void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }


  @Test
  public void retrieveDepartmentTest() throws Exception {
    String deptCode = "IEOR";

    // expected result  will depend on the Database data initialized in IndividualProjectApplication
    mockMvc.perform(get("/retrieveDept")
        .param("deptCode", deptCode))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.courseSelection.size()", is(8)));

    mockMvc.perform(get("/retrieveDept")
        .param("deptCode", " dept code to be not found"))
        .andExpect(status().isNotFound());
  }


  @Test
  public void retrieveCourseTest() throws Exception {
    String deptCode = "IEOR";
    String courseCode = "2500";

    mockMvc.perform(get("/retrieveCourse")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.enrolledStudentCount", is(52)));

    mockMvc.perform(get("/retrieveCourse")
        .param("deptCode", "deptCode code to be not found")
        .param("courseCode", "999999"))
        .andExpect(status().isNotFound());
  }


  @Test
  public void getMajorCtFromDeptTest() throws Exception {
    String deptCode = "IEOR";

    mockMvc.perform(get("/getMajorCountFromDept")
        .param("deptCode", deptCode))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.numberOfMajors", is(67)));

    deptCode = "IEORRRR";

    mockMvc.perform(get("/getMajorCountFromDept")
            .param("deptCode", deptCode))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }


  @Test
  public void identifyDeptChairTest() throws Exception {
    String deptCode = "CHEM";

    mockMvc.perform(get("/idDeptChair")
        .param("deptCode", deptCode))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.departmentChair", is("Laura J. Kaufman")));

    deptCode = "CHEMMMM";

    mockMvc.perform(get("/idDeptChair")
        .param("deptCode", deptCode))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }


  @Test
  public void findCourseLocationTest() throws Exception {
    String deptCode = "CHEM";
    String courseCode = "1403";

    mockMvc.perform(get("/findCourseLocation")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.location", is("309 HAV")));

    mockMvc.perform(get("/findCourseLocation")
        .param("deptCode", "dept code to be not found")
        .param("courseCode", "999999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void findCourseInstructorTest() throws Exception {
    String deptCode = "CHEM";
    String courseCode = "1403";

    mockMvc.perform(get("/findCourseInstructor")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.instructor", is("Ruben M Savizky")));

    mockMvc.perform(get("/findCourseInstructor")
        .param("deptCode", "dept code to be not found")
        .param("courseCode", "999999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void findCourseTimeTest() throws Exception {
    String deptCode = "CHEM";
    String courseCode = "1403";

    mockMvc.perform(get("/findCourseTime")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.courseTime", is("6:10-7:25")));

    mockMvc.perform(get("/findCourseTime")
        .param("deptCode", "dept code to be not found")
        .param("courseCode", "999999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void addMajorToDeptTest() throws Exception {
    String deptCode = "CHEM";

    mockMvc.perform(patch("/addMajorToDept")
        .param("deptCode", deptCode))
        .andExpect(status().isOk());


  }

  @Test
  public void removeMajorFromDeptTest() throws Exception {
    String deptCode = "CHEM";

    mockMvc.perform(patch("/removeMajorFromDept")
        .param("deptCode", deptCode))
        .andExpect(status().isOk());
  }

  @Test
  public void dropStudentTest() throws Exception {
    String deptCode = "CHEM";
    String courseCode = "1403";

    mockMvc.perform(patch("/dropStudentFromCourse")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Student has been dropped.")));
  }

  @Test
  public void setEnrollmentCountTest() throws Exception {
    String deptCode = "ECON";
    String courseCode = "1105";
    String enrollmentCount = "110";

    mockMvc.perform(patch("/setEnrollmentCount")
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

    mockMvc.perform(patch("/changeCourseTime")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode)
        .param("time", newTime))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.course.courseTimeSlot", is(newTime)));
  }

  @Test
  void changeCourseTeacherTest() throws Exception {
    String deptCode = "ECON";
    String courseCode = "1105";
    String newTeacher = "Tamrat Gashaw";

    mockMvc.perform(patch("/changeCourseTeacher")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode)
        .param("teacher", newTeacher))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.course.instructorName", is(newTeacher)));

  }

  @Test
  void changeCourseLocationTest() throws Exception {
    String deptCode = "ECON";
    String courseCode = "1105";
    String newLocation = "301 URIS";

    mockMvc.perform(patch("/changeCourseLocation")
        .param("deptCode", deptCode)
        .param("courseCode", courseCode)
        .param("location", newLocation))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.course.courseLocation", is(newLocation)));
  }

}