package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit tests for the RouteController class.
 * This class contains tests to verify the behavior and functionality of the 
 * RouteController class.
 * It uses Spring's testing framework to set up the environment and run the tests.
 */
public class RouteControllerUnitTests {

  @Mock
  private MyFileDatabase mockFileDatabase;

  @InjectMocks
  private RouteController routeController;

  private Map<String, Department> departmentMapping;

  /**
 * Sets up the mock environment before each test.
 * 
 * <p>This method is executed before each test case to initialize mock data. 
 * It sets up department and course data for three departments (COMS, ECON, and IEOR).
 */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    Map<String, Course> comsCourses = new HashMap<>();
    comsCourses.put("1004", new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400));
    comsCourses.put("3134", new Course("Brian Borowski", "301 URIS", "4:10-5:25", 250));
    comsCourses.put("3157", new Course("Jae Lee", "417 IAB", "4:10-5:25", 400));
    comsCourses.put("4404", new Course("Alex Turner", "505 NWC", "10:10-11:25", 400));

    Map<String, Course> econCourses = new HashMap<>();
    econCourses.put("1105", new Course("Abraham Lincoln", "309 HAV", "2:40-3:55", 200));
    econCourses.put("2257", new Course("Thomas Jefferson", "428 PUP", "10:10-11:25", 125));
    econCourses.put("3412", new Course("Charles Leclerc", "702 HAM", "8:40-9:55", 100));
    econCourses.put("4404", new Course("Julian Casablancas", "125 DOD", "11:40-12:55", 100));

    Map<String, Course> ieorCourses = new HashMap<>();
    ieorCourses.put("1004", new Course("Tame Impala", "627 MUDD", "11:40-12:55", 150));
    ieorCourses.put("2500", new Course("Oscar Piastri", "303 MUDD", "4:10-5:25", 100));
    ieorCourses.put("4511", new Course("Lando Norris", "633 MUDD", "2:40-3:55", 200));
    ieorCourses.put("4404", new Course("Mac DeMarco", "702 DOD", "11:40-12:55", 100));

    comsCourses.get("3157").setEnrolledStudentCount(425);
    econCourses.get("2257").setEnrolledStudentCount(2);

    departmentMapping = new HashMap<>();
    
    Department comsDept = new Department("COMS", comsCourses, "Luca Carloni", 2700);
    departmentMapping.put("COMS", comsDept);
    
    Department econDept = new Department("ECON", econCourses, "Alex Turner", 2500);
    departmentMapping.put("ECON", econDept);
    
    Department ieorDept = new Department("IEOR", ieorCourses, "Julian Casablancas", 1800);
    departmentMapping.put("IEOR", ieorDept);

    when(mockFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    IndividualProjectApplication.myFileDatabase = mockFileDatabase;
  }

  @Test
  public void testRetrieveDepartmentExists() {
    ResponseEntity<?> response = routeController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(departmentMapping.get("COMS").toString(), response.getBody());
  }

  @Test
  public void testRetrieveDepartmentNotExists() {
    ResponseEntity<?> response = routeController.retrieveDepartment("MATH");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testRetrieveCourseDepartmentExists() {
    ResponseEntity<?> response = routeController.retrieveCourse("ECON", 2257);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void testRetrieveCourseDepartmentNotExists() {
    ResponseEntity<?> response = routeController.retrieveCourse("MATH", 2222);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testRetrieveCourseExistsDepartmentNotExist() {
    ResponseEntity<?> response = routeController.retrieveCourse("MATH", 4511);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testRetrieveCourseNotExistsDepartmentExist() {
    ResponseEntity<?> response = routeController.retrieveCourse("IEOR", 8888);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testIsCourseFullExists() {
    ResponseEntity<?> response = routeController.isCourseFull("ECON", 3412);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void testIsCourseFullNotExists() {
    ResponseEntity<?> response = routeController.isCourseFull("MATH", 3999);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testGetMajorCtFromDeptExist() {
    ResponseEntity<?> response = routeController.getMajorCtFromDept("IEOR");
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void testGetMajorCtFromDeptNotExist() {
    ResponseEntity<?> response = routeController.getMajorCtFromDept("HIST");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testIdentifyDeptChairExists() {
    ResponseEntity<?> response = routeController.identifyDeptChair("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void testIdentifyDeptChairNotExists() {
    ResponseEntity<?> response = routeController.identifyDeptChair("ANTH");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testFindCourseLocationExist() {
    ResponseEntity<?> response = routeController.findCourseLocation("COMS", 3134);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void testFindCourseLocationNotExist() {
    ResponseEntity<?> response = routeController.findCourseLocation("SOCI", 3134);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testFindCourseInstructorExist() {
    ResponseEntity<?> response = routeController.findCourseInstructor("IEOR", 2500);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void testFindCourseInstructorNotExist() {
    ResponseEntity<?> response = routeController.findCourseInstructor("PHIL", 9999);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testFindCourseTimeExist() {
    ResponseEntity<?> response = routeController.findCourseTime("IEOR", 2500);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void testFindCourseTimeNotExist() {
    ResponseEntity<?> response = routeController.findCourseTime("PHIL", 9999);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testAddMajorToDeptExist() {
    ResponseEntity<?> response = routeController.addMajorToDept("IEOR");
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void testAddMajorToDeptNotExist() {
    ResponseEntity<?> response = routeController.addMajorToDept("PHIL");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testRemoveMajorFromDeptExist() {
    ResponseEntity<?> response = routeController.removeMajorFromDept("IEOR");
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void testRemoveMajorFromDeptInvalid() {
    ResponseEntity<?> response = routeController.setNumberOfMajors("IEOR", 0);
    response = routeController.removeMajorFromDept("IEOR");
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void testRemoveMajorFromDeptNotExist() {
    ResponseEntity<?> response = routeController.removeMajorFromDept("PHIL");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testSetNumberOfMajors() {
    ResponseEntity<?> response = routeController.setNumberOfMajors("BIOL", 100);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 

    response = routeController.setNumberOfMajors("IEOR", -1);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); 

    response = routeController.setNumberOfMajors("IEOR", 500);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }


  @Test
  public void testDropStudentExist() {
    ResponseEntity<?> response = routeController.dropStudent("IEOR", 2500);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void testDropStudentInvalid() {
    ResponseEntity<?> response = routeController.setEnrollmentCount("IEOR", 2500, 0);
    response = routeController.dropStudent("IEOR", 2500);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void testDropStudentNotExist() {
    ResponseEntity<?> response = routeController.dropStudent("PHIL", 9999);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testSetEnrollmentCountExist() {
    ResponseEntity<?> response = routeController.setEnrollmentCount("IEOR", 2500, 300);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void testInvalidEnrollmentCount() {
    ResponseEntity<?> response = routeController.setEnrollmentCount("IEOR", 2500, -1);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); 
  }

  @Test
  public void testSetEnrollmentCountNotExist() {
    ResponseEntity<?> response = routeController.setEnrollmentCount("PHIL", 9999, 250);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testChangeCourseTimeExist() {
    ResponseEntity<?> response = routeController.changeCourseTime("IEOR", 2500, "4:00-6:00");
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void testChangeCourseTimeNotExist() {
    ResponseEntity<?> response = routeController.changeCourseTime("PHIL", 9999, "4:00-6:00");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testChangeCourseTeacherExist() {
    ResponseEntity<?> response = routeController.changeCourseTeacher("IEOR", 2500, "Kevin Parker");
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void testChangeCourseTeacherNotExist() {
    ResponseEntity<?> response = routeController.changeCourseTeacher("PHIL", 9999, "Jakob Ogawa");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testChangeCourseLocationExist() {
    ResponseEntity<?> response = routeController.changeCourseTeacher("IEOR", 2500, "203 SCH");
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void testChangeCourseLocationNotExist() {
    ResponseEntity<?> response = routeController.changeCourseTeacher("PHIL", 9999, "701 HAM");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void testRetrieveCourses()  {
    ResponseEntity<?> response = routeController.retrieveCourses(1004);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    System.out.println(response.getBody());
    
    response = routeController.retrieveCourses(4404);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    response = routeController.retrieveCourses(3134);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    response = routeController.retrieveCourses(2233);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    response = routeController.retrieveCourses(5774);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void testEnrollStudentInCourse() {
    ResponseEntity<?> response = routeController.enrollStudentInCourse("PHIL", 9999);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    response = routeController.enrollStudentInCourse("COMS", 3157);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    response = routeController.enrollStudentInCourse("ECON", 2257);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

}
