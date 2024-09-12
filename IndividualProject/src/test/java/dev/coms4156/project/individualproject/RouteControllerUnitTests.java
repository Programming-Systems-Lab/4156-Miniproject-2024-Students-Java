package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * This class test constructing Route Controller object by providing necessary parameters,
 * After creating the object, it will be compared with the manually written RC
 * object. If information match, there will be no error.
 */

@SpringBootTest
public class RouteControllerUnitTests {
  @BeforeAll
  public static void setupRouteControllerTest() {
    testrouteController = new RouteController();
  }

  @Test
  public void indexTest() {
    String expected =
            "Welcome, in order to make an API call direct your browser or Postman to an endpoint "
            + "\n\n This can be done using the following format: \n\n http:127.0.0"
            + ".1:8080/endpoint?arg=value";
    assertEquals(expected, testrouteController.index());
  }


  @Test
  public void retrieveDepartmentTest() {
    HashMap<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    assertEquals(new ResponseEntity<>(departmentMapping.get("COMS").toString(),
            HttpStatus.OK),
                testrouteController.retrieveDepartment("COMS")
    );
    assertEquals(
            new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND),
            testrouteController.retrieveDepartment("ABC")
    );
  }

  @Test
  public void retrieveCourseTest() {
    assertEquals(
            new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND),
            testrouteController.retrieveCourse("ABC", 4156)
    );
    assertEquals(
            new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND),
            testrouteController.retrieveCourse("COMS", 5001)
    );
    ResponseEntity<?> responseEntity =
            testrouteController. retrieveCourse("COMS", 3157);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    String expectedBody = "\nInstructor: Jae Lee; Location: 417 IAB; Time: 4:10-5:25";
    assertEquals(expectedBody, responseEntity.getBody());
  }

  @Test
  public void isCourseFullTest() {
    assertEquals(
              HttpStatus.OK,
              testrouteController.isCourseFull("COMS", 4156).getStatusCode()
    );
    assertEquals(
              HttpStatus.OK,
              testrouteController.isCourseFull("ECON", 4840).getStatusCode()
    );
    assertEquals(
              HttpStatus.NOT_FOUND,
              testrouteController.isCourseFull("ABC", 4156).getStatusCode()
    );
  }

  @Test
  public void  getMajorCtFromDeptTest() {
    assertEquals(
              HttpStatus.OK,
              testrouteController.getMajorCtFromDept("COMS").getStatusCode()
    );
    assertEquals(
              "There are: 2700 majors in the department",
              testrouteController.getMajorCtFromDept("COMS").getBody()
    );
    assertEquals(
              HttpStatus.OK,
              testrouteController.getMajorCtFromDept("ECON").getStatusCode()
    );
    assertEquals(
              HttpStatus.NOT_FOUND,
              testrouteController.getMajorCtFromDept("ABC").getStatusCode()
    );
  }

  @Test
  public void identifyDeptChairTest() {
    assertEquals(
              HttpStatus.OK,
              testrouteController.identifyDeptChair("COMS").getStatusCode()
    );
    assertEquals(
              HttpStatus.NOT_FOUND,
              testrouteController.identifyDeptChair("ABC").getStatusCode()
    );
  }

  @Test
  public void findCourseLocationTest() {
    assertEquals(
              HttpStatus.OK,
              testrouteController.findCourseLocation("COMS", 4156).getStatusCode()
    );
    assertEquals(
              "501 NWC is where the course is located.",
              testrouteController.findCourseLocation("COMS", 4156).getBody()
    );
    assertEquals(
              HttpStatus.NOT_FOUND,
              testrouteController.findCourseLocation("ABC", 4156).getStatusCode()
    );
  }

  @Test
  public void findCourseInstructorTest() {
    assertEquals(
            HttpStatus.OK,
            testrouteController.findCourseInstructor("COMS", 4156).getStatusCode()
    );
    assertEquals(
            "Gail Kaiser is the instructor for the course.",
            testrouteController.findCourseInstructor("COMS", 4156).getBody()
    );
    assertEquals(
            HttpStatus.NOT_FOUND,
            testrouteController.findCourseInstructor("ABC", 4156).getStatusCode()
    );
  }

  @Test
  public void findCourseTimeTest() {
    assertEquals(
            HttpStatus.OK,
            testrouteController.findCourseTime("COMS", 4156).getStatusCode()
    );
    assertEquals(
            "The course meets at: 10:10-11:25.",
            testrouteController.findCourseTime("COMS", 4156).getBody()
    );
    assertEquals(
            HttpStatus.NOT_FOUND,
            testrouteController.findCourseTime("ABC", 4156).getStatusCode()
    );
  }

  @Test
  public void addMajorToDeptTest() {
    assertEquals(
            HttpStatus.OK,
            testrouteController.addMajorToDept("COMS").getStatusCode()
    );
    assertEquals(
            HttpStatus.NOT_FOUND,
            testrouteController.addMajorToDept("ABC").getStatusCode()
    );
  }

  @Test
  public void removeMajorFromDeptTest() {
    assertEquals(
            HttpStatus.OK,
            testrouteController.removeMajorFromDept("COMS").getStatusCode()
    );
    assertEquals(
            HttpStatus.NOT_FOUND,
            testrouteController.removeMajorFromDept("ABC").getStatusCode()
    );
  }

  @Test
  public void dropStudentTest() {
    assertEquals(
            HttpStatus.OK,
            testrouteController. dropStudent("COMS", 4156).getStatusCode()
    );
    assertEquals(
            "Student has been dropped.",
            testrouteController. dropStudent("COMS", 4156).getBody()
    );
    assertEquals(
            HttpStatus.NOT_FOUND,
            testrouteController. dropStudent("ABC", 4156).getStatusCode()
    );
  }

  @Test
  public void setEnrollmentCountTest() {
    assertEquals(
            HttpStatus.OK,
            testrouteController. setEnrollmentCount("COMS", 4156, 10).getStatusCode()
    );
    assertEquals(
            "Attributed was updated successfully.",
            testrouteController.setEnrollmentCount("COMS", 4156, 50).getBody()
    );
    assertEquals(
            HttpStatus.NOT_FOUND,
            testrouteController.setEnrollmentCount("ABC", 4156, 100).getStatusCode()
    );
  }

  public static RouteController testrouteController;
}
