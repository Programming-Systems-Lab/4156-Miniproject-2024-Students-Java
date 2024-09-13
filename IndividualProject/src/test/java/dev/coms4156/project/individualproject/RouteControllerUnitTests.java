package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for RouteController
 */
@SpringBootTest
@ContextConfiguration
public class RouteControllerUnitTests {


    @BeforeEach
    public void setupRouteControllerForTesting() {
        MyFileDatabase myFileDatabase = new MyFileDatabase(0, "./data.txt");
        departmentMappingTest = myFileDatabase.getDepartmentMapping();
        routeControllerTest = new RouteController();
    }
    
    @Test
    public void retrieveDepartmentExistsTest() {
        ResponseEntity<?> response = routeControllerTest.retrieveDepartment("COMS");
        assertEquals(departmentMappingTest.get("COMS").toString() , response.getBody());
    }

    @Test
    public void retrieveDepartmentExistsFalseTest() {
        ResponseEntity<?> response = routeControllerTest.retrieveDepartment("NONEXISTENT");
        String exceptedResult = "Department Not Found";
        assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void retrieveCourseExistingTest() {
        ResponseEntity<?> response = routeControllerTest.retrieveCourse("COMS", 1004);
        assertEquals(departmentMappingTest.get("COMS").getCourseSelection().get("1004").toString(), response.getBody());
    }

    @Test
    public void retrieveCourseNonExistingTest() {
        ResponseEntity<?> response = routeControllerTest.retrieveCourse("COMS", 1023);
        String exceptedResult = "Course Not Found";
        assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void isCourseFullFalseTest() {
        ResponseEntity<?> response = routeControllerTest.isCourseFull("COMS", 1004);
        boolean exceptedResult = false;
        assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void isCourseFullTrueTest() {
        ResponseEntity<?> response = routeControllerTest.isCourseFull("PSYC", 2235);
        boolean exceptedResult = true;
        assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void isCourseFullNotFoundTest() {
      ResponseEntity<?> response = routeControllerTest.isCourseFull("PSYC", 2205);
      String exceptedResult = "Course Not Found";
      assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void getMajorCtFromDeptFoundTest() {
        ResponseEntity<?> response = routeControllerTest.getMajorCtFromDept("PSYC");
        String exceptedResult = "There are: 437 majors in the department";
        assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void getMajorCtFromDeptNotFoundTest() {
        ResponseEntity<?> response = routeControllerTest.getMajorCtFromDept("NOTFOUND");
        String exceptedResult = "Department Not Found";
        assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void identifyDeptChairFoundTest() {
        ResponseEntity<?> response = routeControllerTest.identifyDeptChair("PSYC");
        String exceptedResult = "Nim Tottenham is the department chair.";
        assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void identifyDeptChairNotFoundTest() {
        ResponseEntity<?> response = routeControllerTest.identifyDeptChair("NOTFOUND");
        String exceptedResult = "Department Not Found";
        assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void findCourseLocationFoundTest() {
      ResponseEntity<?> response = routeControllerTest.findCourseLocation("PSYC", 2235);
      String exceptedResult = "501 SCH is where the course is located.";
      assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void findCourseLocationNotFoundTest() {
      ResponseEntity<?> response = routeControllerTest.findCourseLocation("PSYC", 2205);
      String exceptedResult = "Course Not Found";
      assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void findCourseInstructorFoundTest() {
      ResponseEntity<?> response = routeControllerTest.findCourseInstructor("PSYC", 2235);
      String exceptedResult = "Katherine T Fox-Glassman is the instructor for the course.";
      assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void findCourseInstructorNotFoundTest() {
      ResponseEntity<?> response = routeControllerTest.findCourseInstructor("PSYC", 2205);
      String exceptedResult = "Course Not Found";
      assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void findCourseTimeFoundTest() {
      ResponseEntity<?> response = routeControllerTest.findCourseTime("PSYC", 2235);
      String exceptedResult = "The course meets at: 11:40-12:55";
      assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void findCourseTimeNotFoundTest() {
      ResponseEntity<?> response = routeControllerTest.findCourseTime("PSYC", 2205);
      String exceptedResult = "Course Not Found";
      assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void addMajorToDeptSuccessTest() {
      ResponseEntity<?> response = routeControllerTest.addMajorToDept("COMS");
      String exceptedResult = "Attribute was updated successfully";
      assertEquals(exceptedResult, response.getBody());
    }

    @Test
    public void addMajorToDeptNotFoundTest() {
        ResponseEntity<?> response = routeControllerTest.addMajorToDept("NOTFOUND");
        String exceptedResult = "Department Not Found";
        assertEquals(exceptedResult, response.getBody());
    }
    public RouteController routeControllerTest;
    public Map<String, Department> departmentMappingTest;
    
}
