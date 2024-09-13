package dev.coms4156.project.individualproject;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * This class contains unit tests for the RouteController class.
 */
@WebMvcTest(RouteController.class)
public class RouteControllerUnitTests {

  @BeforeEach
  void setupForTesting() {
    routeController = new RouteController();
    fileDatabase = new MyFileDatabase(0, "./data.txt");
    IndividualProjectApplication.myFileDatabase = fileDatabase;
  }

  @Test
  public void retrieveDeptTestWhenExists() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .get("/retrieveDept").param("deptCode", "COMS")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content()
                    .string(fileDatabase.getDepartmentMapping().get("COMS").toString()));
  }

  @Test
  public void retrieveDeptTestWhenDoesNotExist() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .get("/retrieveDept").param("deptCode", "NONEXISTENT")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void retrieveCourseTestWhenExists() throws Exception {
    String expectedResult = "\nInstructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25";
    mvc.perform(MockMvcRequestBuilders
                    .get("/retrieveCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(expectedResult));
  }

  @Test
  public void retrieveCourseTestWhenDepartmentDoesNotExist() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .get("/retrieveCourse")
                    .param("deptCode", "NONEXISTENT")
                    .param("courseCode", "4156")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void retrieveCourseTestWhenCourseDoesNotExist() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .get("/retrieveCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "0")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void isCourseFullWhenFull() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .get("/isCourseFull")
                    .param("deptCode", "IEOR")
                    .param("courseCode", "2500")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string("true"));
  }

  @Test
  public void isCourseFullWhenNotFull() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .get("/isCourseFull")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string("false"));
  }

  @Test
  public void getMajorCountFromDeptTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .get("/getMajorCountFromDept")
                    .param("deptCode", "COMS")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string("There are: 2700 majors in the department"));
  }

  @Test
  public void identifyDeptChairTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .get("/idDeptChair")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string("Luca Carloni is the department chair."));
  }

  @Test
  public void findCourseLocationTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .get("/findCourseLocation")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string("501 NWC is where the course is located."));
  }

  @Test
  public void findCourseInstructorTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .get("/findCourseInstructor")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content()
                    .string("Gail Kaiser is the instructor for the course."));
  }

  @Test
  public void findCourseTimeTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .get("/findCourseTime")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string("The course meets at: 10:10-11:25"));
  }

  @Test
  public void addMajorToDeptTest() throws Exception {
    // Add a major
    mvc.perform(MockMvcRequestBuilders
                    .patch("/addMajorToDept")
                    .param("deptCode", "COMS")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Attribute was updated successfully"));

    // Verify major count increased
    mvc.perform(MockMvcRequestBuilders
                    .get("/getMajorCountFromDept")
                    .param("deptCode", "COMS")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("There are: 2701 majors in the department"));
  }

  @Test
  public void removeMajorToDeptTest() throws Exception {
    // Add a major
    mvc.perform(MockMvcRequestBuilders
                    .patch("/removeMajorFromDept")
                    .param("deptCode", "COMS")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Attribute was updated or is at minimum"));

    // Verify major count increased
    mvc.perform(MockMvcRequestBuilders
                    .get("/getMajorCountFromDept")
                    .param("deptCode", "COMS")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("There are: 2699 majors in the department"));
  }

  @Test
  public void dropStudentTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .patch("/dropStudentFromCourse")
                    .param("deptCode", "CHEM")
                    .param("courseCode", "2444")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Student has been dropped."));

    mvc.perform(MockMvcRequestBuilders
                    .get("/isCourseFull")
                    .param("deptCode", "CHEM")
                    .param("courseCode", "2444")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string("false"));

    mvc.perform(MockMvcRequestBuilders
                    .patch("/dropStudentFromCourse")
                    .param("deptCode", "ELEN")
                    .param("courseCode", "4702")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Student has been dropped."));
    mvc.perform(MockMvcRequestBuilders
                    .patch("/dropStudentFromCourse")
                    .param("deptCode", "ELEN")
                    .param("courseCode", "4702")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Student has been dropped."));
    mvc.perform(MockMvcRequestBuilders
                    .patch("/dropStudentFromCourse")
                    .param("deptCode", "ELEN")
                    .param("courseCode", "4702")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Student has been dropped."));
    mvc.perform(MockMvcRequestBuilders
                    .patch("/dropStudentFromCourse")
                    .param("deptCode", "ELEN")
                    .param("courseCode", "4702")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Student has been dropped."));
    mvc.perform(MockMvcRequestBuilders
                    .patch("/dropStudentFromCourse")
                    .param("deptCode", "ELEN")
                    .param("courseCode", "4702")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Student has been dropped."));
    mvc.perform(MockMvcRequestBuilders
                    .patch("/dropStudentFromCourse")
                    .param("deptCode", "ELEN")
                    .param("courseCode", "4702")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Student has not been dropped."));
  }

  @Test
  public void setEnrollmentCountTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .get("/isCourseFull")
                    .param("deptCode", "ELEN")
                    .param("courseCode", "4702")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string("false"));

    mvc.perform(MockMvcRequestBuilders
                    .patch("/setEnrollmentCount")
                    .param("deptCode", "ELEN")
                    .param("courseCode", "4702")
                    .param("count", "50")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));

    mvc.perform(MockMvcRequestBuilders
                    .get("/isCourseFull")
                    .param("deptCode", "ELEN")
                    .param("courseCode", "4702")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string("true"));
  }

  @Test
  public void changeCourseTimeTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .patch("/changeCourseTime")
                    .param("deptCode", "ELEN")
                    .param("courseCode", "4702")
                    .param("time", "10:10-12:40")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));

    mvc.perform(MockMvcRequestBuilders
                    .get("/findCourseTime")
                    .param("deptCode", "ELEN")
                    .param("courseCode", "4702")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string("The course meets at: 10:10-12:40"));
  }

  @Test
  public void changeCourseTeacherTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .patch("/changeCourseTeacher")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156")
                    .param("teacher", "Adam Cannon")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));

    String expectedResult = "\nInstructor: Adam Cannon; Location: 501 NWC; Time: 10:10-11:25";
    mvc.perform(MockMvcRequestBuilders
                    .get("/retrieveCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(expectedResult));
  }

  @Test
  public void changeCourseLocationTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .patch("/changeCourseLocation")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156")
                    .param("location", "417 IAB")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));

    String expectedResult = "\nInstructor: Gail Kaiser; Location: 417 IAB; Time: 10:10-11:25";
    mvc.perform(MockMvcRequestBuilders
                    .get("/retrieveCourse")
                    .param("deptCode", "COMS")
                    .param("courseCode", "4156")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(expectedResult));
  }

  private static RouteController routeController;
  private static MyFileDatabase fileDatabase;

  @Autowired
  private MockMvc mvc;
}
