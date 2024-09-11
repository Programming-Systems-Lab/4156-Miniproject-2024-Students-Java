package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

@SpringBootTest
@ContextConfiguration


  

public class RCUnitTests {
  @BeforeAll
  public static void setupRCForTesting() {
    rc = new RouteController();
    dept = "nosuchdept";
    courseId1 = 0000;
    coms = "COMS";
    coms4156=4156;
    SpringApplication.run(IndividualProjectApplication.class, "setup");
    departmentMapping=IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
  }

  @Test
  public void indexTest() {
    String expected = "Welcome, in order to make an API call direct your browser or Postman to an endpoint "
    + "\n\n This can be done using the following format: \n\n http:127.0.0"
    + ".1:8080/endpoint?arg=value";
    assertEquals(expected, rc.index());
  }

  @Test
  public void retrieveDepartmentTest() {
    assertEquals(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND), rc.retrieveDepartment(dept));
    assertEquals(new ResponseEntity<>(departmentMapping.get(coms.toUpperCase()).toString(),
    HttpStatus.OK), rc.retrieveDepartment(coms));
    assertEquals(new ResponseEntity<>("An Error has occurred", HttpStatus.OK), rc.retrieveDepartment(null));
  }

  @Test
  public void retrieveCourseTest() {
    assertEquals(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND), rc.retrieveCourse(dept,courseId1));
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND), rc.retrieveCourse(coms,courseId1));
    coursesMapping = departmentMapping.get(coms.toUpperCase()).getCourseSelection();
    assertEquals(new ResponseEntity<>(coursesMapping.get(Integer.toString(coms4156)).toString(), HttpStatus.OK),
    rc.retrieveCourse(coms,coms4156));
  }

  @Test
  public void isCourseFullTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND), rc.isCourseFull(coms,courseId1));
    coursesMapping = departmentMapping.get(coms.toUpperCase()).getCourseSelection();
    assertEquals(new ResponseEntity<>(false, HttpStatus.OK),
    rc.isCourseFull(coms,coms4156));
  }

  @Test
  public void getMajorCtFromDeptTest() {
    assertEquals(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND), rc.getMajorCtFromDept(dept));
    assertEquals(new ResponseEntity<>("There are: " + Integer.toString(departmentMapping
    .get(coms).getNumberOfMajors()) + " majors in the department", HttpStatus.OK),rc.getMajorCtFromDept(coms));
  }

  @Test
  public void identifyDeptChairTest() {
    assertEquals(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND), rc.identifyDeptChair(dept));
    assertEquals(new ResponseEntity<>("Luca Carloni" + " is "
    + "the department chair.", HttpStatus.OK),rc.identifyDeptChair(coms));
  }

  @Test
  public void findCourseLocationTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND), 
    rc.findCourseLocation(dept,courseId1));
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND), 
    rc.findCourseLocation(coms,courseId1));
    coursesMapping = departmentMapping.get(coms.toUpperCase()).getCourseSelection();
    assertEquals(new ResponseEntity<>("501 NWC" + " is where the course "
    + "is located.", HttpStatus.OK),
    rc.findCourseLocation(coms,coms4156));
  }

  @Test
  public void findCourseInstructorTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND), 
    rc.findCourseInstructor(dept,courseId1));
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND), 
    rc.findCourseInstructor(coms,courseId1));
    coursesMapping = departmentMapping.get(coms.toUpperCase()).getCourseSelection();
    assertEquals(new ResponseEntity<>("Gail Kaiser" + 
    " is the instructor for the course.", HttpStatus.OK),
    rc.findCourseInstructor(coms,coms4156));
  }

  @Test
  public void findCourseTimeTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND), 
    rc.findCourseTime(dept,courseId1));
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND), 
    rc.findCourseTime(coms,courseId1));
    coursesMapping = departmentMapping.get(coms.toUpperCase()).getCourseSelection();
    assertEquals(new ResponseEntity<>("The course meets at: " + "10:10-11:25", HttpStatus.OK),
    rc.findCourseTime(coms,coms4156));
  }

  @Test
  public void addMajorToDeptTest() {
    assertEquals(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND), rc.addMajorToDept(dept));
    assertEquals(new ResponseEntity<>("Attribute was updated successfully", HttpStatus.OK), 
    rc.addMajorToDept(coms));
  }

  @Test
  public void removeMajorFromDeptTest() {
    assertEquals(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND), rc.removeMajorFromDept(dept));
    assertEquals(new ResponseEntity<>("Attribute was updated or is at minimum", HttpStatus.OK), 
    rc.removeMajorFromDept(coms));
  }

  @Test
  public void dropStudentTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND), 
    rc.dropStudent(dept,courseId1));
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND), 
    rc.dropStudent(coms,courseId1));
    coursesMapping = departmentMapping.get(coms.toUpperCase()).getCourseSelection();
    assertEquals(new ResponseEntity<>("Student has been dropped.", HttpStatus.OK),
    rc.dropStudent(coms,coms4156));
  }

  @Test
  public void setEnrollmentCountTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND), 
    rc.setEnrollmentCount(dept,courseId1,100));
    coursesMapping = departmentMapping.get(coms.toUpperCase()).getCourseSelection();
    assertEquals(new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK),
    rc.setEnrollmentCount(coms,coms4156,10));
    rc.setEnrollmentCount(coms,coms4156,109);
  }

  @Test
  public void changeCourseTimeTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND), 
    rc.changeCourseTime(dept,courseId1,"10:10-11:25"));
    coursesMapping = departmentMapping.get(coms.toUpperCase()).getCourseSelection();
    assertEquals(new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK),
    rc.changeCourseTime(coms,coms4156,"9:10-10:25"));
    rc.changeCourseTime(coms,coms4156,"10:10-11:25");
  }

  @Test
  public void changeCourseTeacherTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND), 
    rc.changeCourseTeacher(dept,courseId1,"TA"));
    coursesMapping = departmentMapping.get(coms.toUpperCase()).getCourseSelection();
    assertEquals(new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK),
    rc.changeCourseTeacher(coms,coms4156,"TA"));
    rc.changeCourseTeacher(coms,coms4156,"Gail Kaiser");
  }

  @Test
  public void changeCourseLocationTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND), 
    rc.changeCourseLocation(dept,courseId1,"Online"));
    coursesMapping = departmentMapping.get(coms.toUpperCase()).getCourseSelection();
    assertEquals(new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK),
    rc.changeCourseLocation(coms,coms4156,"Online"));
    rc.changeCourseLocation(coms,coms4156,"501 NWC");
  }

  

  /** The test instance used for testing. */
  public static RouteController rc;
  public static HashMap<String, Department> departmentMapping;
  public static HashMap<String, Course> coursesMapping;
  public static String dept;
  public static int courseId1;
  public static String coms;
  public static int coms4156;
}
