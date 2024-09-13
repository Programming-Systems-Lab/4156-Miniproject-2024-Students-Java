package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

/**
 * Class contains all the test logic for the Department class
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  public static Department testDepartment;

  @BeforeEach
  public void setupDepartmentForTesting() {

    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55",  400);
    coms1004.setEnrolledStudentCount(249);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    testDepartment = new Department("COMS", courses, "Luca Carloni", 2700);
  }


  @Test
  public void getNumberOfMajorsTest(){
    assertEquals(testDepartment.getNumberOfMajors(), 2700);
  }


  @Test
  public void getDepartmentChairTest(){
    assertEquals(testDepartment.getDepartmentChair(), "Luca Carloni");
  }


  @Test
  public void addMajorTest(){
    testDepartment.addPersonToMajor();
    assertEquals(testDepartment.getNumberOfMajors(), 2701);
  }


  @Test
  public void dropMajorTest(){
    testDepartment.dropPersonFromMajor();
    assertEquals(testDepartment.getNumberOfMajors(), 2699);
  }


  @Test
  public void toStringTest(){
    String expectedResult = "COMS 1004: Adam Cannon, 417 IAB, 11:40-12:55, Max Capacity: 400, Enrolled: 249\n";
    assertEquals(expectedResult, testDepartment.toString());
  }


  @Test
  public void addCourseTest(){
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    coms4156.setEnrolledStudentCount(109);
    testDepartment.addCourse("4156", coms4156);
    String expectedResult =  "COMS 1004: Adam Cannon, 417 IAB, 11:40-12:55, Max Capacity: 400, Enrolled: 249\n" +
            "COMS 4156: Gail Kaiser, 501 NWC, 10:10-11:25, Max Capacity: 120, Enrolled: 109\n";

    assertEquals(testDepartment.toString(), expectedResult);
  }


  @Test
  public void createCourseTest(){
    testDepartment.createCourse("3134", "Brian Borowski", "301 URIS", "10:10-11:25", 250 );
    String expectedResult =  "COMS 1004: Adam Cannon, 417 IAB, 11:40-12:55, Max Capacity: 400, Enrolled: 249\n" +
            "COMS 4156: Gail Kaiser, 501 NWC, 10:10-11:25, Max Capacity: 120, Enrolled: 109\n" +
            "COMS 3134: Brian Borowski, 301 URIS, 10:10-11:25, Max Capacity: 250, Enrolled: 0\n";

  }




}
