package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class RouteControllerTests {

  private RouteController routeController;
  private Department testDepartment;
  private Course coms3251;
  private Course coms3827;
  private HashMap<String, Department> departmentMapping;

  @Mock
  private MyFileDatabase mockDatabase;

  private AutoCloseable closeMock;

  @BeforeEach
  public void setUpRouteControllerTests() {
    closeMock = MockitoAnnotations.openMocks(this);
    routeController = new RouteController();
    IndividualProjectApplication.myFileDatabase = mockDatabase;
    departmentMapping = new HashMap<>();
    Map<String,Course> courseMapping = new HashMap<>();
    
    testDepartment = new Department("COMS", courseMapping, "Luca Carloni", 1);
    coms3251 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    coms3827 = new Course("Daniel Rubenstein", "207 Math",
    "10:10-11:25", 300);
    when(mockDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
  }

  @AfterEach
  public void closeDownMock() throws Exception {
    closeMock.close();
  }

  /** Tests for a not found department */
  @Test
  public void testRetrieveDepartmentNotFound() {
    ResponseEntity<?> response = routeController.retrieveDepartment("ECON");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /** Tests for a found department */
  @Test
  public void testRetrieveDepartmentFound() {
    String departmentCode = "COMS";
    departmentMapping.put(departmentCode, testDepartment);
    ResponseEntity<?> response = routeController.retrieveDepartment(departmentCode);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(testDepartment.toString(), response.getBody());
  }

  /** Tests for department exception error??? */

  /** Tests for a not found course */
  @Test
  public void testRetrieveCourseNotFound() {
    departmentMapping.put("COMS", testDepartment);
    ResponseEntity<?> response = routeController.retrieveCourse("COMS", 101);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /** Tests for a not found course department */
  @Test
  public void testRetrieveCourseDepartmentNotFound() {
    ResponseEntity<?> response = routeController.retrieveCourse("ECON", 101);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /** Tests for a found course */
  @Test
  public void testRetrieveCourseFound() {
    departmentMapping.put("COMS", testDepartment);
    testDepartment.addCourse("3251", coms3251);
    ResponseEntity<?> response = routeController.retrieveCourse("COMS", 3251);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(coms3251.toString(), response.getBody());
  }

  /** Tests for is Course full, course not found */
  @Test
  public void testIsCourseFullNotFound() {
    ResponseEntity<?> response = routeController.isCourseFull("COMS", 3251);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /** Tests for is Course full, Course is not full */
  @Test
  public void testIsCourseFull(){
    departmentMapping.put("COMS", testDepartment);
    testDepartment.addCourse("3251", coms3251);
    ResponseEntity<?> response = routeController.isCourseFull("COMS", 3251);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(false, response.getBody());
  }

  /** getMajorCountFromDept, when the Department is not Found */
  @Test
  public void testGetMajorCountFromDeptNotFound() {
    ResponseEntity<?> response = routeController.getMajorCtFromDept("ECON");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /** getMajorCountFromDept, when the Department is Found */
  @Test
  public void testGetMajorCountFromDept() {
    departmentMapping.put("COMS", testDepartment);
    ResponseEntity<?> response = routeController.getMajorCtFromDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("There are: 1 majors in the department", response.getBody());
  }

  /** Tests for identifyDeptChair, when the Department is not Found */
  @Test
  public void testIdentifyDeptChairDeptNotFound() {
    ResponseEntity<?> response = routeController.identifyDeptChair("ECON");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /** Tests for identifyDeptChair, when the Department is Found */
  @Test
  public void testIdentifyDeptChairDeptFound() {
    departmentMapping.put("COMS", testDepartment);
    ResponseEntity<?> response = routeController.identifyDeptChair("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Luca Carloni is the department chair.", response.getBody());
  }

  /** Tests for findCourseLocation, when the course is found */
  @Test
  public void testFindCourseLocation(){
    departmentMapping.put("COMS", testDepartment);
    testDepartment.addCourse("3251", coms3251);
    ResponseEntity<?> response = routeController.findCourseLocation("COMS", 3251);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("402 CHANDLER is where the course is located.", response.getBody());
  }
  /** Tests for findCourseLocation, when the course is not found */
  @Test
  public void testFindCourseLocationCourseNotFound(){
    ResponseEntity<?> response = routeController.findCourseLocation("COMS", 3251);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /** Tests for findCourseInstructor, when the course is found */
  @Test
  public void testFindCourseInstructor(){
    departmentMapping.put("COMS", testDepartment);
    testDepartment.addCourse("3251", coms3251);
    ResponseEntity<?> response = routeController.findCourseInstructor("COMS", 3251);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Tony Dear is the instructor for the course.", response.getBody());
  }

  /** Tests for findCourseInstructor, when the course is not found */
  @Test
  public void testFindCourseInstructorCourseNotFound(){
    ResponseEntity<?> response = routeController.findCourseInstructor("COMS", 3251);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /** Tests for find Course time, when the course is found */
  @Test
  public void testFindCourseTime(){
    departmentMapping.put("COMS", testDepartment);
    testDepartment.addCourse("3251", coms3251);
    ResponseEntity<?> response = routeController.findCourseTime("COMS", 3251);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("The course meets at: 1:10-3:40", response.getBody());
  }

  /** Tests for find Course time, when the course is not found */
  @Test
  public void testFindCourseTimeCourseNotFound(){
    ResponseEntity<?> response = routeController.findCourseTime("COMS", 3251);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /** Tests for addMajorToDept, when the department is found */
  @Test
  public void testAddMajorToDept(){
    departmentMapping.put("COMS", testDepartment);
    ResponseEntity<?> response = routeController.addMajorToDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated successfully", response.getBody());
  }

  /** Remove Major from department, when the department is found */
  @Test
  public void testRemoveMajorFromDept(){
    departmentMapping.put("COMS", testDepartment);
    ResponseEntity<?> response = routeController.removeMajorFromDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated or is at minimum", response.getBody());
  }

  /** Tests dropStudentFromCourse, when course is found and student can be dropped */
  @Test
  public void testDropStudentFromCourse(){
    departmentMapping.put("COMS", testDepartment);
    testDepartment.addCourse("3251", coms3251);
    coms3251.enrollStudent();
    ResponseEntity<?> response = routeController.dropStudent("COMS", 3251);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Student has been dropped.", response.getBody());
  }

  /** Tests drop student from course, when the course doens't exist */
  @Test
  public void testDropStudentFromCourseCourseNotFound(){
    ResponseEntity<?> response = routeController.dropStudent("COMS", 3251);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  /** Tests setting the enrollment count for a course if the course exists. */
  @Test
  public void testSetEnrollmentCountForCourse(){
    departmentMapping.put("COMS", testDepartment);
    testDepartment.addCourse("3827", coms3827);
    ResponseEntity<?> response = routeController.setEnrollmentCount("COMS", 3827, 100);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  /** Tests changing the time of the course if the course is found */

}

