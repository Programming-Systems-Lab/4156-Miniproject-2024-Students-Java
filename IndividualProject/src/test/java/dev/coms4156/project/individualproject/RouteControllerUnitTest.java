package dev.coms4156.project.individualproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


/**
 * This class is built to test the RouteController class, which we have created to enable
 * API calls to the application.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class RouteControllerUnitTest {

  @Test
  public void indexTest() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string(
        "Welcome, in order to make an API call direct your browser or Postman to an endpoint " 
        + "\n\n This can be done using the following format: "
        + "\n\n http:127.0.0.1:8080/endpoint?arg=value"));
  }

  @Test
  public void retrieveDepartmentTest() throws Exception {
    String deptCode = "COMS";
    String expectedJsonResponse = "{\"departmentChair\":\"Luca Carloni\"}";
    
    // Check if the department chair in the responded json is Luca Carloni
    mockMvc.perform(get("/retrieveDept")
        .param("deptCode", deptCode))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedJsonResponse));
    
    // Case that the department code is invalid
    String failDeptCode = "AAAA";
    mockMvc.perform(get("/retrieveDept")
        .param("deptCode", failDeptCode))
        .andExpect(status().isNotFound());
  }

  @Test
  public void retrieveCourseTest() throws Exception {
    String deptCode = "PSYC";
    int courseCode = 1001;
    String expectedJson = "{\"instructorName\":\"Patricia G Lindemann\", \"courseLocation\":\"501 SCH\", \"courseTimeSlot\":\"1:10-2:25\"}";

    // Case that the department code and course code are valid, 
    // we see if the instructor is Patricia G Lindemann 
    // and courseLocation is 501 SCH
    mockMvc.perform(get("/retrieveCourse")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedJson));

    // Case that the course code is invalid
    int failCourseCode = 4157;
    mockMvc.perform(get("/retrieveCourse")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(failCourseCode)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));

    // Case that the department code is invalid
    String failDeptCode = "AAAA";
    mockMvc.perform(get("/retrieveCourse")
        .param("deptCode", failDeptCode)
        .param("courseCode", String.valueOf(courseCode)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void retrieveCourses_Success() throws Exception {
      int courseCode = 1001;
      // {"PHYS":"\nInstructor: Szabolcs Marka; Location: 301 PUP; Time: 2:40-3:55","PSYC":"\nInstructor: Patricia G Lindemann; Location: 501 SCH; Time: 1:10-2:25"}
      String expectedJson = "{\"PHYS\":\"\\nInstructor: Szabolcs Marka; Location: 301 PUP; " 
                            + "Time: 2:40-3:55\",\"PSYC\":\"\\nInstructor: Patricia G Lindemann; "
                            + "Location: 501 SCH; Time: 1:10-2:25\"}";
      // Case where the course code exists across multiple departments
      mockMvc.perform(get("/retrieveCourses")
          .param("courseCode", String.valueOf(courseCode)))
          .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(content().json(expectedJson));
  }

  @Test
  public void retrieveCourses_CourseNotFound() throws Exception {
      int failCourseCode = 9999; // Non-existent course code

      // Case where the course code does not exist
      mockMvc.perform(get("/retrieveCourses")
          .param("courseCode", String.valueOf(failCourseCode)))
          .andExpect(status().isNotFound())
          .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void isCourseFullTest() throws Exception {
    String deptCode = "CHEM";
    int courseCode = 2444;

    // case that the course is full {}
    String expectedJson = "{\"isFull\": true}";
    mockMvc.perform(get("/isCourseFull")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedJson));

    // case that the course is not full
    String anotherDeptCode = "COMS";
    int anotherCourseCode = 4156;

    String expectedJsonNotFull = "{\"isFull\": false}";
    mockMvc.perform(get("/isCourseFull")
        .param("deptCode", anotherDeptCode)
        .param("courseCode", String.valueOf(anotherCourseCode)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedJsonNotFull));

    // case that the course code is invalid
    int failCourseCode = 9999;
    mockMvc.perform(get("/isCourseFull")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(failCourseCode)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));

    // case that the department code is invalid
    String failDeptCode = "AAAA";
    mockMvc.perform(get("/isCourseFull")
        .param("deptCode", failDeptCode)
        .param("courseCode", String.valueOf(courseCode)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }


  @Test
  public void getMajorCtFromDeptTest() throws Exception {
    String deptCode = "COMS";

    // Expected to have 2700 majors in the department
    String expectedJson = "{\"majorCt\": 2700}";

    // Case that the department code is valid
    mockMvc.perform(get("/getMajorCountFromDept")
        .param("deptCode", deptCode))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedJson));

    // Case that the department code is invalid
    String failDeptCode = "AAAA"; 
    mockMvc.perform(get("/getMajorCountFromDept")
        .param("deptCode", failDeptCode))
        .andExpect(status().isForbidden())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void identifyDeptChairTest() throws Exception {
    String deptCode = "COMS";
    String expectedDeptChair = "{\"departmentChair\": \"Luca Carloni\"}";

    // Case that the department code is valid
    mockMvc.perform(get("/idDeptChair")
        .param("deptCode", deptCode))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedDeptChair));

    // Case that the department code is invalid
    String failDeptCode = "AAAA";
    mockMvc.perform(get("/idDeptChair")
        .param("deptCode", failDeptCode))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void findCourseLocationTest() throws Exception {
    String deptCode = "COMS";
    int courseCode = 4156;
    String expectedLocation = "{\"location\":\"501 NWC\"}";

    // Case that the department code and course code are valid
    mockMvc.perform(get("/findCourseLocation")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedLocation));

    // Case that the course code is invalid
    int failCourseCode = 9999;
    mockMvc.perform(get("/findCourseLocation")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(failCourseCode)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));

    // Case that the department code is invalid
    String failDeptCode = "AAAA";
    mockMvc.perform(get("/findCourseLocation")
        .param("deptCode", failDeptCode)
        .param("courseCode", String.valueOf(courseCode)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void findCourseInstructorTest() throws Exception {
    String deptCode = "COMS";
    int courseCode = 4156;

    // Case that the department code and course code are valid, 
    // we expect the instructor to be Gail Kaiser
    String expectedJson = "{\"instructor\":\"Gail Kaiser\"}";

    mockMvc.perform(get("/findCourseInstructor")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedJson));

    // Case that the department code is invalid
    int failCourseCode = 9999;
    mockMvc.perform(get("/findCourseInstructor")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(failCourseCode)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void findCourseTimeTest() throws Exception {
    String deptCode = "COMS";
    int courseCode = 4156;

    // Expected to have the course time to be 10:10-11:25
    String expectedJson = "{\"time\":\"10:10-11:25\"}";

    // Case that the department code and course code are valid
    mockMvc.perform(get("/findCourseTime")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedJson));

    // Case that the course code is invalid
    int failCourseCode = 9999;
    mockMvc.perform(get("/findCourseTime")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(failCourseCode)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }


  @Test
  public void addMajorToDeptTest() throws Exception {
    String deptCode = "COMS";

    // Case that the department code is valid
    mockMvc.perform(patch("/addMajorToDept")
        .param("deptCode", deptCode))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Attribute was updated successfully"));

    // Case that the department code is invalid
    String failDeptCode = "AAAA";
    mockMvc.perform(patch("/addMajorToDept")
        .param("deptCode", failDeptCode))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void removeMajorFromDeptTest() throws Exception {
    String deptCode = "COMS";

    // Case that the department code is valid
    mockMvc.perform(patch("/removeMajorFromDept")
        .param("deptCode", deptCode))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Attribute was updated or is at minimum"));

    // Case that the department code is invalid
    String failDeptCode = "AAAA";
    mockMvc.perform(patch("/removeMajorFromDept")
        .param("deptCode", failDeptCode))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }


  @Test
  public void enrollStudent_Success() throws Exception {
    String deptCode = "COMS";
    int courseCode = 4156;

    // Case that student is successfully enrolled in the course
    mockMvc.perform(patch("/enrollStudentInCourse")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Successfully enrolled student"));
    
    // Drop the studnet to maintain the number
    mockMvc.perform(patch("/dropStudentFromCourse")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Student has been dropped."));
  }

  @Test
  public void enrollStudent_CourseNotFound() throws Exception {
      String deptCode = "COMS";
      int failCourseCode = 9999; // Non-existent course code

      // Case where the course code does not exist
      mockMvc.perform(patch("/enrollStudentInCourse")
          .param("deptCode", deptCode)
          .param("courseCode", String.valueOf(failCourseCode)))
          .andExpect(status().isBadRequest())
          .andExpect(content().string("Failed to enroll student"));
  }

  @Test
  public void enrollStudent_DepartmentNotFound() throws Exception {
      String failDeptCode = "AAAA"; // Non-existent department code
      int courseCode = 4156;

      // Case where the department code does not exist
      mockMvc.perform(patch("/enrollStudentInCourse")
          .param("deptCode", failDeptCode)
          .param("courseCode", String.valueOf(courseCode)))
          .andExpect(status().isBadRequest())
          .andExpect(content().string("Failed to enroll student"));
  }

  @Test
  public void enrollStudent_FailureToEnroll() throws Exception {
    String deptCode = "COMS";
    int courseCode = 4156;

    // Set the enrollment count to the capacity (120)
    mockMvc.perform(patch("/setEnrollmentCount")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode))
        .param("count", "120"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Attributed was updated successfully."));
    
    // Case where enrollment fails for some reason (e.g., course is full)
    mockMvc.perform(patch("/enrollStudentInCourse")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Failed to enroll student"));
    
    // Set the student num back to maintain the number
    mockMvc.perform(patch("/setEnrollmentCount")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode))
        .param("count", "109"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void dropStudentFromCourseTest() throws Exception {
    String deptCode = "COMS";
    int courseCode = 4156;

    // Case that the department code and course code are valid
    mockMvc.perform(patch("/dropStudentFromCourse")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Student has been dropped."));

    // Case that the department code and course code are valid, but the course is empty
    mockMvc.perform(patch("/setEnrollmentCount")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode))
        .param("count", "0"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Attributed was updated successfully."));
      
    mockMvc.perform(patch("/dropStudentFromCourse")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Student has not been dropped."));
    
    // Case that the course code is invalid
    int failCourseCode = 9999;
    mockMvc.perform(patch("/dropStudentFromCourse")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(failCourseCode)))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Course Not Found"));

    // case that the department code is invalid
    String failDeptCode = "AAAA";
    mockMvc.perform(patch("/dropStudentFromCourse")
        .param("deptCode", failDeptCode)
        .param("courseCode", String.valueOf(courseCode)))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Course Not Found"));
    
    // Add the student back to the course
    mockMvc.perform(patch("/setEnrollmentCount")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode))
        .param("count", "109"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Attributed was updated successfully."));
    
  }
  
  @Test
  public void setEnrollmentCountTest() throws Exception {
    String deptCode = "COMS";
    int courseCode = 4156;

    // Case: Successfully set enrollment count
    mockMvc.perform(patch("/setEnrollmentCount")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode))
        .param("count", "100"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Attributed was updated successfully."));

    // Case: Course not found
    int failCourseCode = 9999;
    mockMvc.perform(patch("/setEnrollmentCount")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(failCourseCode))
        .param("count", "100"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Course Not Found"));

    // Case: Department not found
    String failDeptCode = "AAAA";
    mockMvc.perform(patch("/setEnrollmentCount")
        .param("deptCode", failDeptCode)
        .param("courseCode", String.valueOf(courseCode))
        .param("count", "100"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Course Not Found"));

    // Case that the count is negative
    mockMvc.perform(patch("/setEnrollmentCount")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode))
        .param("count", "-10"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void changeCourseTimeTest() throws Exception {
    String deptCode = "COMS";
    int courseCode = 4156;
    String newTime = "2:00-3:30";

    // Case: Successfully change course time
    mockMvc.perform(patch("/changeCourseTime")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode))
        .param("time", newTime))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Attributed was updated successfully."));

    // Case that the course code is invalid
    int failCourseCode = 9999;
    mockMvc.perform(patch("/changeCourseTime")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(failCourseCode))
        .param("time", newTime))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Course Not Found"));

    // Case that the department code is invalid
    String failDeptCode = "AAAA";
    mockMvc.perform(patch("/changeCourseTime")
        .param("deptCode", failDeptCode)
        .param("courseCode", String.valueOf(courseCode))
        .param("time", newTime))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void changeCourseTeacherTest() throws Exception {
    String deptCode = "COMS";
    int courseCode = 4156;
    String newTeacher = "John Doe";

    // Case that the department code and course code are valid
    mockMvc.perform(patch("/changeCourseTeacher")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode))
        .param("teacher", newTeacher))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Attributed was updated successfully."));

    // Case that the course code is invalid
    int invalidCourseCode = 9999;
    mockMvc.perform(patch("/changeCourseTeacher")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(invalidCourseCode))
        .param("teacher", newTeacher))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Course Not Found"));

    // Case that the department code is invalid
    String invalidDeptCode = "INVALID";
    mockMvc.perform(patch("/changeCourseTeacher")
        .param("deptCode", invalidDeptCode)
        .param("courseCode", String.valueOf(courseCode))
        .param("teacher", newTeacher))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void changeCourseLocationTest() throws Exception {
    String deptCode = "COMS";
    int courseCode = 4156;
    String newLocation = "New Building, Room 101";

    // Case that the department code and course code are valid
    mockMvc.perform(patch("/changeCourseLocation")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(courseCode))
        .param("location", newLocation))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Attributed was updated successfully."));

    // Case that the course code is invalid
    int invalidCourseCode = 9999;
    mockMvc.perform(patch("/changeCourseLocation")
        .param("deptCode", deptCode)
        .param("courseCode", String.valueOf(invalidCourseCode))
        .param("location", newLocation))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Course Not Found"));

    // Case that the department code is invalid
    String invalidDeptCode = "INVALID";
    mockMvc.perform(patch("/changeCourseLocation")
        .param("deptCode", invalidDeptCode)
        .param("courseCode", String.valueOf(courseCode))
        .param("location", newLocation))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("Course Not Found"));
  }

  @Autowired
  private MockMvc mockMvc;
}
