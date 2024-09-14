package dev.coms4156.project.individualproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RouteController.class)
public class RouteControllerTests {

  /**
   * This class contains tests for the RouteController class.
   */
  @BeforeEach
  public void setUp() {
    depMap = new HashMap<>();
    myFileDatabase = new MyFileDatabase(0, "./data.txt");
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
  }

  @Test
  public void indexTest() throws Exception {
    mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content()
                    .string("Welcome, in order to make an API call direct your browser or Postman to an endpoint "
                            + "\n\n This can be done using the following format: \n\n http:127.0.0"
                            + ".1:8080/endpoint?arg=value"));
    mockMvc.perform(get("/index"))
            .andExpect(status().isOk())
            .andExpect(content()
                    .string("Welcome, in order to make an API call direct your browser or Postman to an endpoint "
                            + "\n\n This can be done using the following format: \n\n http:127.0.0"
                            + ".1:8080/endpoint?arg=value"));
    mockMvc.perform(get("/home"))
            .andExpect(status().isOk())
            .andExpect(content()
                    .string("Welcome, in order to make an API call direct your browser or Postman to an endpoint "
                            + "\n\n This can be done using the following format: \n\n http:127.0.0"
                            + ".1:8080/endpoint?arg=value"));
  }

  @Test
  public void retrieveDepartmentNotFoundTest() throws Exception {
    mockMvc.perform(get("/retrieveDept")
                    .param("deptCode", "not-exist"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void retrieveDepartmentFoundTest() throws Exception {
    depMap = IndividualProjectApplication
            .myFileDatabase.getDepartmentMapping();
    mockMvc.perform(get("/retrieveDept")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string(depMap.get("COMS").toString()));
  }

  @Test
  public void retrieveCourseNotFoundTest() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
                    .param("deptCode", "1000")
                    .param("courseCode", "1004"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
    mockMvc.perform(get("/retrieveCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "10041"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
    mockMvc.perform(get("/retrieveCourse")
                    .param("error", "COMS"))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void retrieveCourseFoundTest() throws Exception {
    HashMap<String, Course> coursesMap;
    coursesMap = IndividualProjectApplication.myFileDatabase
            .getDepartmentMapping().get("COMS").getCourseSelection();
    mockMvc.perform(get("/retrieveCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004"))
            .andExpect(status().isOk())
            .andExpect(content().string(coursesMap.get("1004").toString()));
  }

  @Test
  public void isCourseFullTest() throws Exception {
    mockMvc.perform(get("/isCourseFull")
                    .param("deptCode", "IEOR")
                    .param("courseCode", "3404"))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    mockMvc.perform(get("/isCourseFull")
                    .param("deptCode", "CHEM")
                    .param("courseCode", "2494"))
            .andExpect(status().isOk())
            .andExpect(content().string("false"));
  }

  @Test
  public void isCourseFullNotFoundTest() throws Exception {
    mockMvc.perform(get("/isCourseFull")
                    .param("deptCode", "1")
                    .param("courseCode", "1"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
    mockMvc.perform(get("/isCourseFull")
                    .param("error", "COMS"))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void getMajorCtFromDeptTest() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string("There are: 2700 majors in the department"));
  }

  @Test
  public void getMajorCtFromDeptNotFoundTest() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
                    .param("deptCode", "1111"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
    mockMvc.perform(get("/getMajorCountFromDept")
                    .param("error", "COMS"))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void identifyDeptChairTest() throws Exception {
    mockMvc.perform(get("/idDeptChair")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string("Luca Carloni is the department chair."));
  }

  @Test
  public void identifyDeptChairNotFoundTest() throws Exception {
    mockMvc.perform(get("/idDeptChair")
                    .param("deptCode", "1111"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
    mockMvc.perform(get("/idDeptChair")
                    .param("error", "COMS"))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void findCourseLocationTest() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004"))
            .andExpect(status().isOk())
            .andExpect(content().string("417 IAB is where the course is located."));
  }

  @Test
  public void findCourseLocationNotFoundTest() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
                    .param("deptCode", "non-exist")
                    .param("courseCode", "1004"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
    mockMvc.perform(get("/findCourseLocation")
                    .param("error", "COMS"))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void findCourseInstructorTest() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004"))
            .andExpect(status().isOk())
            .andExpect(content().string("Adam Cannon is the instructor for the course."));
  }

  @Test
  public void findCourseInstructorNotFoundTest() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
                    .param("deptCode", "non-exist")
                    .param("courseCode", "1004"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
    mockMvc.perform(get("/findCourseInstructor")
                    .param("error", "COMS"))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void findCourseTimeTest() throws Exception {
    mockMvc.perform(get("/findCourseTime")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004"))
            .andExpect(status().isOk())
            .andExpect(content().string("The course meets at: 11:40-12:55 some time "));
  }

  @Test
  public void findCourseTimeNotFoundTest() throws Exception {
    mockMvc.perform(get("/findCourseTime")
                    .param("deptCode", "non-exist")
                    .param("courseCode", "1004"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
    mockMvc.perform(get("/findCourseTime")
                    .param("error", "COMS"))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void addMajorToDeptTest() throws Exception {
    mockMvc.perform(patch("/addMajorToDept")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attribute was updated successfully"));
    mockMvc.perform(get("/getMajorCountFromDept")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string("There are: 2701 majors in the department"));
  }

  @Test
  public void addMajorToDeptNotFoundTest() throws Exception {
    mockMvc.perform(patch("/addMajorToDept")
                    .param("deptCode", "non-exist"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
    mockMvc.perform(get("/addMajorToDept")
                    .param("error", "COMS"))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void removeMajorFromDeptTest() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attribute was updated or is at minimum"));
    mockMvc.perform(get("/getMajorCountFromDept")
                    .param("deptCode", "COMS"))
            .andExpect(status().isOk())
            .andExpect(content().string("There are: 2699 majors in the department"));
  }

  @Test
  public void removeMajorFromDeptNotFoundTest() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
                    .param("deptCode", "non-exist"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
    mockMvc.perform(patch("/removeMajorFromDept")
                    .param("error", "COMS"))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void dropStudentTest() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004"))
            .andExpect(status().isOk())
            .andExpect(content().string("Student has been dropped."));
  }

  @Test
  public void dropStudentNotFoundTest() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
                    .param("deptCode", "non-exist")
                    .param("courseCode", "1004"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
    mockMvc.perform(get("/dropStudentFromCourse")
                    .param("error", "COMS"))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void setEnrollmentCountTest() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004")
                    .param("count", "0"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));
    mockMvc.perform(patch("/dropStudentFromCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Student has not been dropped."));
  }

  @Test
  public void setEnrollmentCountNotFoundTest() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
                    .param("deptCode", "1111")
                    .param("courseCode", "1004")
                    .param("count", "0"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void changeCourseTimeTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTime")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004")
                    .param("time", "4:10-5:25"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));
    mockMvc.perform(get("/findCourseTime")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004"))
            .andExpect(status().isOk())
            .andExpect(content().string("The course meets at: 4:10-5:25 some time "));
  }

  @Test
  public void changeCourseTimeNotFoundTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTime")
                    .param("deptCode", "1111")
                    .param("courseCode", "1004")
                    .param("time", "0"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
    mockMvc.perform(get("/changeCourseTime")
                    .param("error", "COMS"))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void changeCourseTeacherTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004")
                    .param("teacher", "Jae Lee"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));
    mockMvc.perform(get("/findCourseInstructor")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004"))
            .andExpect(status().isOk())
            .andExpect(content().string("Jae Lee is the instructor for the course."));
  }

  @Test
  public void changeCourseTeacherNotFoundTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher")
                    .param("deptCode", "non-exist")
                    .param("courseCode", "1004")
                    .param("teacher", "Jae Lee"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void changeCourseLocationTest() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004")
                    .param("location", "301 URIS"))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));
    mockMvc.perform(get("/findCourseLocation")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004"))
            .andExpect(status().isOk())
            .andExpect(content().string("301 URIS is where the course is located."));
  }

  @Test
  public void changeCourseLocationNotFoundTest() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation")
                    .param("deptCode", "non-exist")
                    .param("courseCode", "1004")
                    .param("location", "309 HAV"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper deptMapper;
  @MockBean
  private MyFileDatabase myFileDatabase;
  private HashMap<String, Department> depMap;
}
