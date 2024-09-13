package dev.coms4156.project.individualproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;





/**
 * def of RouteControllerUnitTests.
 */
@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig
@WebMvcTest(RouteController.class)  // This will only load the web layer
public class RouteControllerUnitTests {

  @Autowired
  private MockMvc mockMvc;

  // @MockBean
  // private MyFileDatabase myFileDatabase;

  // // Mock the IndividualProjectApplication or methods if needed
  // @MockBean
  // private IndividualProjectApplication individualProjectApplication;


  // @Test
  // public void testRetrieveDepartmentFound2() throws Exception {
  //     // Mock the behavior of the database service
  //     String[] times = { "11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55" };
  //     String[] locations = { "417 IAB", "309 HAV", "301 URIS" };
  //     Course coms9999 = new Course("Andrew", locations[0], times[0], 400);
  //     coms9999.setEnrolledStudentCount(249);
  //     HashMap<String, Course> courses = new HashMap<>();
  //     courses.put("9999", coms9999);
  //     Department mockCompSci = new Department("COMS",courses, "Andrew", 1000);
  //     HashMap<String, Department> mockDepartmentMapping = new HashMap<>();
  //     mockDepartmentMapping.put("COMS", mockCompSci);

  //     when(myFileDatabase.getDepartmentMapping()).thenReturn(mockDepartmentMapping);

  //     mockMvc.perform(get("/retrieveDept").param("deptCode", "COMS"))
  //             .andExpect(status().isOk())
  //             .andExpect(content().string("COMS 1004: \nInstructor: 
  //      Daniel Rubenstein; Location: 207 Math; Time: 10:10-11:25\n"));
  // }

  @Test
    public void testIndexEndpoint() throws Exception {
    // Expected response from the index() method
    String expectedResponse = 
            "Welcome, in order to make an API call direct your browser or Postman to an endpoint "
            + "\n\n This can be done using the following format: \n\n http:127.0.0"
            + ".1:8080/endpoint?arg=value";

    // Perform the GET request and capture the result
    MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk()) // Check if the status is OK (200)
                .andReturn(); // Return the result

    // Print the response content
    String content = result.getResponse().getContentAsString();
    System.out.println("Response : " + content);

    // Perform GET request to "/", "/index", or "/home"
    mockMvc.perform(get("/"))
                .andExpect(status().isOk())  // Check if HTTP status is 200 OK
                .andExpect(content().string(expectedResponse));  // Check if response body matches
  }

  @Test
    public void testRetrieveDepartmentNotFound() throws Exception {
    mockMvc.perform(get("/retrieveDept").param("deptCode", "NonExistingDept"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department Not Found"));
  }

  @Test
    public void testRetrieveDepartmentFound() throws Exception {
    mockMvc.perform(get("/retrieveDept").param("deptCode", "COMS"))
                .andExpect(status().isOk())
                .andExpect(content().string("COMS 3827: \nInstructor: "
                + "Daniel Rubenstein; Location: "
                + "207 Math; Time: 10:10-11:25\nCOMS 1004: \nInstructor:"
                + " Adam Cannon; Location: 417 IAB;"
                + " Time: 11:40-12:55\nCOMS 3203: \nInstructor: Ansaf "
                + "Salleb-Aouissi; Location: 301 URIS;"
                + " Time: 10:10-11:25\nCOMS 4156: \nInstructor: Gail Kaiser;"
                + " Location: 501 NWC; Time: "
                + "10:10-11:25\nCOMS 3157: \nInstructor: Jae Lee; Location: "
                + "417 IAB; Time: 4:10-5:25\nCOMS"
                + " 3134: \nInstructor: Brian Borowski; Location: "
                + "301 URIS; Time: 4:10-5:25\nCOMS 3251: \n"
                + "Instructor: Tony Dear; Location: 402 CHANDLER; "
                + "Time: 1:10-3:40\nCOMS 3261: \nInstructor:"
                + " Josh Alman; Location: 417 IAB; Time: 2:40-3:55\n"));
  }




  @Test
    public void testRetrieveCourseFound() throws Exception {

    mockMvc.perform(get("/retrieveCourse").param("deptCode", "COMS")
                .param("courseCode", "4156"))
                .andExpect(status().isOk())
                .andExpect(content()
                .string("\nInstructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25"));
  }
    
  @Test
    public void testRetrieveCourseNotFound() throws Exception {
    // Perform the GET request and capture the result
    MvcResult result = mockMvc.perform(get("/retrieveCourse")
                .param("deptCode", "COMS").param("courseCode", "41"))
                .andReturn(); // Return the result
    // Print the response content
    String content = result.getResponse().getContentAsString();
    System.out.println("Response : " + content);

    mockMvc.perform(get("/retrieveCourse").param("deptCode", "COMS")
                .param("courseCode", "41"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
  }


  @Test
    public void testRetrieveCourseDeptNotFound() throws Exception {
    // Perform the GET request and capture the result
    MvcResult result = mockMvc.perform(get("/retrieveCourse")
                .param("deptCode", "COS").param("courseCode", "41"))
                .andReturn(); // Return the result
    // Print the response content
    String content = result.getResponse().getContentAsString();
    System.out.println("Response : " + content);

    mockMvc.perform(get("/retrieveCourse").param("deptCode", "COS")
                .param("courseCode", "41"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department Not Found"));
  }

  @Test
    public void testIsCourseFull() throws Exception {
    mockMvc.perform(get("/isCourseFull").param("deptCode", "COMS")
                .param("courseCode", "4156"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
  }

  @Test
    public void testIsCourseFullNotFound() throws Exception {
    mockMvc.perform(get("/isCourseFull").param("deptCode", "COMS")
                .param("courseCode", "416"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
  }


  @Test
    public void testgetMajorCtFromDept() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept").param("deptCode", "COMS"))
                .andExpect(status().isOk())
                .andExpect(content().string("There are: 2700 majors in the department"));
  }

  @Test
    public void testgetMajorCtFromDeptNotFound() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept").param("deptCode", "nonExisting"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department Not Found"));
  }

  @Test
    public void testidDeptChair() throws Exception {
    mockMvc.perform(get("/idDeptChair").param("deptCode", "COMS"))
                .andExpect(status().isOk())
                .andExpect(content().string("Luca Carloni is the department chair."));
  }
    
  @Test
    public void testidDeptChairNotFound() throws Exception {
    mockMvc.perform(get("/idDeptChair").param("deptCode", "nonExisting"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department Not Found"));
  }


  @Test
    public void testFindCourseLocation() throws Exception {
    mockMvc.perform(get("/findCourseLocation").param("deptCode", "COMS")
                .param("courseCode", "4156"))
                .andExpect(status().isOk())
                .andExpect(content().string("501 NWC is where the course is located."));
  }
    
  @Test
    public void testFindCourseLocationNotFound() throws Exception {
    mockMvc.perform(get("/findCourseLocation").param("deptCode", "COMS")
                .param("courseCode", "0"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
  }

  @Test
    public void testFindCourseInstructor() throws Exception {
    mockMvc.perform(get("/findCourseInstructor").param("deptCode", "COMS")
                .param("courseCode", "4156"))
                .andExpect(status().isOk())
                .andExpect(content().string("Gail Kaiser is the instructor for the course."));
  }
    
  @Test
    public void testFindCourseInstructorNotFound() throws Exception {
    mockMvc.perform(get("/findCourseInstructor").param("deptCode", "COMS")
                .param("courseCode", "0"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
  }

  @Test
    public void testFindCourseTimer() throws Exception {
    mockMvc.perform(get("/findCourseTime").param("deptCode", "COMS").param("courseCode", "4156"))
                .andExpect(status().isOk())
                .andExpect(content().string("The course meets at: 10:10-11:25"));
  }
    
  @Test
    public void testFindCourseTimeNotFound() throws Exception {
    mockMvc.perform(get("/findCourseTime").param("deptCode", "COMS").param("courseCode", "0"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
  }

  /*
  * start modifying the database (IEOR), need to mock the database
  */

  @Test
    public void testAddMajorToDept() throws Exception {
    mockMvc.perform(patch("/addMajorToDept").param("deptCode", "IEOR"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attribute was updated successfully"));
  }
    
  @Test
    public void testAddMajorToDeptNotFound() throws Exception {
    mockMvc.perform(patch("/addMajorToDept").param("deptCode", "nonExisting"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department Not Found"));
  }

  @Test
    public void testRemoveMajorFromDept() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept").param("deptCode", "IEOR"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attribute was updated or is at minimum"));
  }
    
  @Test
    public void testRemoveMajorFromDeptNotFound() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept").param("deptCode", "nonExisting"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department Not Found"));
  }

  @Test
    public void testDropStudentFromCourset() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse").param("deptCode", "IEOR")
                .param("courseCode", "2500"))
                .andExpect(status().isOk())
                .andExpect(content().string("Student has been dropped."));
  }

  @Test
    public void testDropStudentFromCoursetFail() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse").param("deptCode", "IEOR")
                .param("courseCode", "3404"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Student has not been dropped."));
  }

  @Test
    public void testDropStudentFromCourseNotFound() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse").param("deptCode", "nonExisting")
                .param("courseCode", "2500"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
  }

  @Test
    public void testSetEnrollmentCount() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount").param("deptCode", "IEOR")
                .param("courseCode", "2500").param("count", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
    public void testSetEnrollmentCountNotFound() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount").param("deptCode", "nonExisting")
                .param("courseCode", "2500").param("count", "2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
  }

  @Test
    public void testChangeCourseTime() throws Exception {
    mockMvc.perform(patch("/changeCourseTime").param("deptCode", "IEOR")
            .param("courseCode", "2500").param("time", "9:00-11:30"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
    public void testChangeCourseTimeNotFound() throws Exception {
    mockMvc.perform(patch("/changeCourseTime").param("deptCode", "nonExisting")
            .param("courseCode", "2500").param("time", "9:00-11:30"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testChangeCourseTeacher() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher").param("deptCode", "IEOR")
        .param("courseCode", "2500").param("teacher", "Michael Robbins"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attributed was updated successfully."));
  }
  
  @Test
  public void testChangeCourseTeacherNotFound() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher").param("deptCode", "nonExisting")
        .param("courseCode", "2500").param("teacher", "Michael Robbins"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testChangeCourseLocation() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation").param("deptCode", "IEOR")
        .param("courseCode", "2500").param("location", "633 MUDD"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
    public void testChangeCourseLocationNotFound() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation").param("deptCode", "nonExisting")
        .param("courseCode", "2500").param("location", "633 MUDD"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  //exception
  // @Test
  // public void testException() throws Exception {
  //     when(myFileDatabase.getDepartmentMapping())
  // .thenThrow(new RuntimeException("Database error"));
  //     mockMvc.perform(patch("/changeCourseLocation").param("deptCode", "
  //  nonExisting").param("courseCode", "2500ddd").param("location", "633 MUDD"))
  //             .andExpect(status().isOk())
  //             .andExpect(content().string("An Error has occurred"));
  // }

}
