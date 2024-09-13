package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RouterControllerUnitTests {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void indexTest() throws Exception {
    String expectedResult = "Welcome, in order to make an API call direct your browser or Postman to an endpoint "
            + "\n\n This can be done using the following format: \n\n http:127.0.0"
            + ".1:8080/endpoint?arg=value";

    mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResult));

    mockMvc.perform(get("/index"))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResult));

    mockMvc.perform(get("/home"))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResult));
  }

  @Test
  public void retrieveDeptNotFoundTest() throws Exception {
    mockMvc.perform(get("/retrieveDept")
            .param("deptCode", "PHY"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }


  @Test
  public void retrieveDeptTest() throws Exception {
    mockMvc.perform(get("/retrieveDept")
                    .param("deptCode", "PHYS"))
            .andExpect(status().isOk());
  }


  @Test
  public void retrieveDeptSmallerCaseTest() throws Exception {
    mockMvc.perform(get("/retrieveDept")
                    .param("deptCode", "phys"))
            .andExpect(status().isOk());
  }


  @Test
  public void retrieveCourseTest() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
                    .param("deptCode", "CHEM")
                    .param("courseCode", "2444"))
            .andExpect(status().isOk());
  }


  @Test
  public void retrieveCourseDeptNotFoundTest() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
                    .param("deptCode", "CHE")
                    .param("courseCode", "2444")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void retrieveCourseCourseNotFoundTest() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
                    .param("deptCode", "CHEM")
                    .param("courseCode", "2445")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void isCourseFullNotFoundTest() throws Exception {
    mockMvc.perform(get("/isCourseFull")
                    .param("deptCode", "CHE")
                    .param("courseCode", "2494")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void isCourseFullFalseTest() throws Exception {
    mockMvc.perform(get("/isCourseFull")
                    .param("deptCode", "CHEM")
                    .param("courseCode", "2494")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(false)));
  }

  @Test
  public void isCourseFullTestEdge() throws Exception {
    mockMvc.perform(get("/isCourseFull")
                    .param("deptCode", "CHEM")
                    .param("courseCode", "2444")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(true)));
  }

  @Test
  public void isCourseFullTrueTest() throws Exception {
    mockMvc.perform(get("/isCourseFull")
                    .param("deptCode", "PSYC")
                    .param("courseCode", "2235")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(true)));
  }

  @Test
  public void getMajorCountFromDeptTest() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
                    .param("deptCode", "PSYC")
            )
            .andExpect(status().isOk())
            .andExpect(content().string("There are: 437 majors in the department"));
  }

  @Test
  public void getMajorCountFromDeptNotFoundTest() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
                    .param("deptCode", "P")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void identifyDeptChairNotFoundTest() throws Exception {
    mockMvc.perform(get("/idDeptChair")
                    .param("deptCode", "P")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void identifyDeptChairTest() throws Exception {
    mockMvc.perform(get("/idDeptChair")
                    .param("deptCode", "PSYC")
            )
            .andExpect(status().isOk())
            .andExpect(content().string("Nim Tottenham is the department chair."));
  }

  @Test
  public void findCourseLocationTest() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
                    .param("deptCode", "CHEM")
                    .param("courseCode", "2494")
            )
            .andExpect(status().isOk())
            .andExpect(content().string("202 HAV is where the course is located."));
  }

  @Test
  public void findCourseLocationNotFoundTest() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
                    .param("deptCode", "C")
                    .param("courseCode", "2494")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }


  @Test
  public void findCourseInstructorNotFoundTest() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
                    .param("deptCode", "CHEM")
                    .param("courseCode", "140")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }
  @Test
  public void findCourseInstructorTest() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
                    .param("deptCode", "CHEM")
                    .param("courseCode", "1403")
            )
            .andExpect(status().isOk())
            .andExpect(content().string("Ruben M Savizky is the instructor for the course."));
  }

  @Test
  public void findCourseTimeNotFoundTest() throws Exception {
    mockMvc.perform(get("/findCourseTime")
                    .param("deptCode", "IEOR")
                    .param("courseCode", "2494")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void findCourseTimeTest() throws Exception {
    mockMvc.perform(get("/findCourseTime")
                    .param("deptCode", "IEOR")
                    .param("courseCode", "2500")
            )
            .andExpect(status().isOk())
            .andExpect(content().string("The course meets at: 11:40-12:55"));
  }

  @Test
  public void addMajorToDeptNotFoundTest() throws Exception {
    mockMvc.perform(patch("/addMajorToDept")
                    .param("deptCode", "C"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void addMajorToDeptTest() throws Exception {
    mockMvc.perform(patch("/addMajorToDept")
                    .param("deptCode", "IEOR")
            )
            .andExpect(status().isOk())
            .andExpect(content().string("Attribute was updated successfully"));
  }

  @Test
  public void removeMajorFromDeptNotFoundTest() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
                    .param("deptCode", "C")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void removeMajorFromDeptTest() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
                    .param("deptCode", "PSYC")
            )
            .andExpect(status().isOk())
            .andExpect(content().string("Attribute was updated or is at minimum"));
  }

  @Test
  public void dropStudentFromCourseNotFoundTest() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
                    .param("deptCode", "C")
                    .param("courseCode", "2494")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }


  @Test
  public void dropStudentFromCourseTest() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
                    .param("deptCode", "IEOR")
                    .param("courseCode", "2500")
            )
            .andExpect(status().isOk())
            .andExpect(content().string("Student has been dropped."));
  }

  @Test
  public void dropStudentFromCourseEdgeTest() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
                    .param("deptCode", "ECON")
                    .param("courseCode", "1105")
                    .param("count", "0"))
                    .andExpect(status().isOk());

    mockMvc.perform(patch("/dropStudentFromCourse")
                    .param("deptCode", "ECON")
                    .param("courseCode", "1105")
            )
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Student has not been dropped."));
  }

  @Test
  public void setEnrollmentCountNotFoundTest() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
                    .param("deptCode", "C")
                    .param("courseCode", "2494")
                    .param("count", "100")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void setEnrollmentCountTest() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
                    .param("deptCode", "COMS")
                    .param("courseCode", "3251")
                    .param("count", "100")
            )
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));
  }


  @Test
  public void changeCourseTimeNotFoundTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTime")
                    .param("deptCode", "C")
                    .param("courseCode", "2494")
                    .param("time", "11:00 - 12:00")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }


  @Test
  public void changeCourseTimeTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTime")
                    .param("deptCode", "COMS")
                    .param("courseCode", "3251")
                    .param("time", "11:00 - 12:00")
            )
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));
  }


  @Test
  public void changeCourseTeacherNotFoundTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher")
                    .param("deptCode", "COMS")
                    .param("courseCode", "2494")
                    .param("teacher", "Lebron James")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }


  @Test
  public void changeCourseTeacherTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004")
                    .param("teacher", "Lebron James")
            )
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void changeCourseLocationNotFoundTest() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation")
                    .param("deptCode", "C")
                    .param("courseCode", "2494")
                    .param("location", "Mudd 833")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void changeCourseLocationTest() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation")
                    .param("deptCode", "COMS")
                    .param("courseCode", "1004")
                    .param("location", "Mudd 833")
            )
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));
  }
}
