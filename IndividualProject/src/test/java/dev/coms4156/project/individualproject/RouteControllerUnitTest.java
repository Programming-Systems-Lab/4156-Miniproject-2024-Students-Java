package dev.coms4156.project.individualproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * This class is a test for RouteController class.
 */

@WebMvcTest(RouteController.class)
public class RouteControllerUnitTest {
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void indexTest() throws Exception {
    mockMvc.perform(get("/index")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect((content().string("Welcome, in order to make an API call "
                + "direct your browser or Postman to an endpoint "
                + "\n\n This can be done using the following format: \n\n http:127.0.0"
                + ".1:8080/endpoint?arg=value")));
  }

  @Test
  public void retrieveDepartmentFoundTest() throws Exception {
    mockMvc.perform(get("/retrieveDept")
            .param("deptCode", "COMS")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect((content().json("{}")));
  }

  @Test
  public void retrieveDepartmentNotFoundTest() throws Exception {
    mockMvc.perform(get("/retrieveDept")
            .param("deptCode", "MATH")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect((content().string("Department Not Found")));
  }

  @Test
  public void retrieveCourseFoundTest() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
        .param("deptCode", "COMS")
        .param("courseCode", "4156")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string("\nInstructor: Gail Kaiser; L"
        + "ocation: 501 NWC; Time: "
        + "10:10-11:25")));
  }

  @Test
  public void retrieveCoursesFoundTest() throws Exception {
    mockMvc.perform(get("/retrieveCourses")
            .param("courseCode", "1001")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string(
            "\nInstructor: Szabolcs Marka; Location: 301 PUP; Time: 2:40-3:55"
            + "\nInstructor: Patricia G Lindemann; Location: 501 SCH; Time: 1:10-2:25")));
  }

  @Test
  public void retrieveCoursesNotFoundTest() throws Exception {
    mockMvc.perform(get("/retrieveCourses")
            .param("courseCode", "9999")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course NOT FOUND")));
  }


  @Test
  public void retrieveCourseNotFoundDepartmentTest() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "MATH")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Department Not Found")));
  }

  @Test
  public void retrieveCourseNotFoundCourseTest() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "9999")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void isCourseFullTest1() throws Exception {

  }

  @Test
  public void isCourseFullTest2() throws Exception {

  }

  @Test
  public void getMajorCtFromDeptSuccessTest() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
            .param("deptCode", "ECON")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string("There are: 2345 majors in the department")));
  }

  @Test
  public void getMajorCtFromDeptNotSuccessTest() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
            .param("deptCode", "MATH")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Department Not Found")));
  }

  @Test
  public void identifyDeptChairSuccessTest() throws Exception {
    mockMvc.perform(get("/idDeptChair")
            .param("deptCode", "COMS")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string("Luca Carloni is the department chair.")));
  }

  @Test
  public void identifyDeptChairNotSuccessTest() throws Exception {
    mockMvc.perform(get("/idDeptChair")
            .param("deptCode", "MATH")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Department Not Found")));
  }

  @Test
  public void findCourseLocationSuccessTest() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
            .param("deptCode", "COMS")
            .param("courseCode", "4156")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string("501 NWC is where the course is located.")));
  }

  @Test
  public void findCourseLocationNotSuccessTest1() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
            .param("deptCode", "MATH")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void findCourseLocationNotSuccessTest2() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
            .param("deptCode", "COMS")
            .param("courseCode", "9999")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }



  @Test
  public void findCourseInstructorSuccessTest() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
            .param("deptCode", "COMS")
            .param("courseCode", "3203")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string("Ansaf Salleb-Aouissi is the instructor "
            + "for the course.")));
  }

  @Test
  public void findCourseInstructorNotSuccessTest1() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
            .param("deptCode", "COMS")
            .param("courseCode", "9999")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void findCourseInstructorNotSuccessTest2() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
            .param("deptCode", "MATH")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }


  @Test
  public void findCourseTimeSuccessTest() throws Exception {
    mockMvc.perform(get("/findCourseTime")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string("The course meets at: 11:40-12:55.")));
  }

  @Test
  public void findCourseTimeNotSuccessTest1() throws Exception {
    mockMvc.perform(get("/findCourseTime")
            .param("deptCode", "COMS")
            .param("courseCode", "9999")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void findCourseTimeNotSuccessTest2() throws Exception {
    mockMvc.perform(get("/findCourseTime")
            .param("deptCode", "MATH")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void addMajorToDeptSuccessTest() throws Exception {
    mockMvc.perform(patch("/addMajorToDept")
            .param("deptCode", "COMS")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string("Attribute was updated successfully")));
  }

  @Test
  public void addMajorToDeptNotSuccessTest() throws Exception {
    mockMvc.perform(patch("/addMajorToDept")
            .param("deptCode", "MATH")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Department Not Found")));
  }

  @Test
  public void removeMajorFromDeptSuccessTest() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
            .param("deptCode", "COMS")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string("Attribute was updated or is at minimum")));
  }

  @Test
  public void removeMajorFromDeptNotSuccessTest() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
            .param("deptCode", "MATH")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Department Not Found")));
  }


  @Test
  public void dropStudentSuccessTest() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string("Student has been dropped.")));
  }

  @Test
  public void dropStudentNotSuccessTest1() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "9999")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void dropStudentNotSuccessTest2() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
            .param("deptCode", "MATH")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }


  @Test
  public void setEnrollmentCountSuccessTest() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .param("count", "10")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string("Attributed was updated successfully.")));
  }

  @Test
  public void setEnrollmentCountNotSuccessTest1() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
            .param("deptCode", "COMS")
            .param("courseCode", "9999")
            .param("count", "10")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void setEnrollmentCountNotSuccessTest2() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
            .param("deptCode", "MATH")
            .param("courseCode", "1004")
            .param("count", "10")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void enrollStudentInCourseSuccessTest() throws Exception {
    mockMvc.perform(patch("/enrollStudentInCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string("Enrollment Success")));
  }

  @Test
  public void enrollStudentInCourseNotSuccessTest1() throws Exception {
    mockMvc.perform(patch("/enrollStudentInCourse")
            .param("deptCode", "MATH")
            .param("courseCode", "1004")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void enrollStudentInCourseNotSuccessTest2() throws Exception {
    mockMvc.perform(patch("/enrollStudentInCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "9999")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void changeCourseTimeSuccessTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTime")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .param("time", "11:40-12:55")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string("Attributed was updated successfully.")));
  }

  @Test
  public void changeCourseTimeNotSuccessTest1() throws Exception {
    mockMvc.perform(patch("/changeCourseTime")
            .param("deptCode", "COMS")
            .param("courseCode", "9999")
            .param("time", "4:10-5:25")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void changeCourseTimeNotSuccessTest2() throws Exception {
    mockMvc.perform(patch("/changeCourseTime")
            .param("deptCode", "MATH")
            .param("courseCode", "1004")
            .param("time", "4:10-5:25")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void changeCourseTeacherSuccessTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher")
            .param("deptCode", "COMS")
            .param("courseCode", "3261")
            .param("teacher", "Josh Alman")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string("Attributed was updated successfully.")));
  }

  @Test
  public void changeCourseTeacherNotSuccessTest1() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher")
            .param("deptCode", "COMS")
            .param("courseCode", "9999")
            .param("teacher", "Jae Lee")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void changeCourseTeacherNotSuccessTest2() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher")
            .param("deptCode", "MATH")
            .param("courseCode", "1004")
            .param("teacher", "Jae Lee")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void changeCourseLocationSuccessTest() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation")
            .param("deptCode", "COMS")
            .param("courseCode", "3251")
            .param("location", "402 CHANDLER")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect((content().string("Attributed was updated successfully.")));
  }

  @Test
  public void changeCourseLocationNotSuccessTest1() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation")
            .param("deptCode", "COMS")
            .param("courseCode", "9999")
            .param("location", "402 CHANDLER")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

  @Test
  public void changeCourseLocationNotSuccessTest2() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation")
            .param("deptCode", "MATH")
            .param("courseCode", "1004")
            .param("location", "402 CHANDLER")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect((content().string("Course Not Found")));
  }

}
