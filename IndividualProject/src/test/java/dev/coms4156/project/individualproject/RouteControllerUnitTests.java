package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Map;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
public class RouteControllerUnitTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MyFileDatabase myFileDatabase;

  private Map<String, Department> departmentMapping;

  // ...

  public static RouteController testRouteController = new RouteController();
  public static Department testDepartment;
  public static Course testCourse;

  @BeforeEach
  public void setUp() {
    departmentMapping = new HashMap<>();
    testDepartment = new Department("4995", new HashMap<>(), "Christian Lim", 2);
    testCourse = new Course("Ben", "451 CSB", "4:10-5:40", 50);
    departmentMapping.put("4995", testDepartment);

    given(myFileDatabase.getDepartmentMapping()).willReturn((HashMap<String, Department>) departmentMapping);

    testDepartment.addCourse(null, testCourse);
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
    ResponseEntity<?> response = testRouteController.retrieveDepartment("4995");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Department{departmentChair='Christian Lim', numberOfMajors=2, deptCode='4995'}", response);
  }

  @Test
  public void retrieveDepartmentFail() throws Exception {
    ResponseEntity<?> response = testRouteController.retrieveDepartment("5000");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Department Not Found", response);
  }

  @Test
  public void getMajorCtFromDept() throws Exception {
    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("4995");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("There are: 2 majors in the department", response);
  }

  @Test
  public void getMajorCtFromDeptFail() throws Exception {
    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("5000");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Department Not Found", response);
  }

  @Test
  public void dropStudent() throws Exception {
    ResponseEntity<?> response = testRouteController.dropStudent("4995", 451);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Student has been dropped.", response);
  }

  @Test
  public void retrieveCourse() throws Exception {
    ResponseEntity<?> response = testRouteController.retrieveCourse("4995", 451);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(
        "Course{instructorName='Ben', courseLocation='451 CSB', courseTimeSlot='4:10-5:40', enrolledStudentCount=50}",
        response);
  }

  @Test
  public void setEnrollmentCount() throws Exception {
    ResponseEntity<?> response = testRouteController.setEnrollmentCount("4995", 451, 3);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response);
  }

  @Test
  public void changeCourseTime() throws Exception {
    ResponseEntity<?> response = testRouteController.changeCourseTime("4995", 451, "3:10-5:40");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response);
  }

  @Test
  public void changeCourseTeacher() throws Exception {
    ResponseEntity<?> response = testRouteController.changeCourseTeacher("4995", 451, "James");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response);
  }

  @Test
  public void changeCourseLocation() throws Exception {
    ResponseEntity<?> response = testRouteController.changeCourseLocation("4995", 451, "Pupin 301");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response);
  }

  @Test
  public void isCourseFull() throws Exception {
    ResponseEntity<?> response = testRouteController.isCourseFull("4995", 451);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(true, response);
  }

  @Test
  public void findCourseLocation() throws Exception {
    ResponseEntity<?> response = testRouteController.findCourseLocation("4995", 451);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("CSB 541", response);
  }

  @Test
  public void findCourseInstructor() throws Exception {
    ResponseEntity<?> response = testRouteController.findCourseInstructor("4995", 451);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Ben", response);
  }


  @Test
  public void findCourseTime() throws Exception {
    ResponseEntity<?> response = testRouteController.findCourseTime("4995", 451);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("4:10-5:40", response);
  }

  @Test
  public void addMajorToDept() throws Exception {
    ResponseEntity<?> response = testRouteController.addMajorToDept("4995");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated successfully", response);
  }

  @Test
  public void removeMajorFromDept() throws Exception {
    ResponseEntity<?> response = testRouteController.removeMajorFromDept("4995");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated successfully", response);
  }
}
