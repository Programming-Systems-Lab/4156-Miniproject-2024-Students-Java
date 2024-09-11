package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

@SpringBootTest
@ContextConfiguration

public class DepartmentUnitTests {
    @BeforeAll
    public static void setupDepartmentForTesting() {
      String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
      String[] locations = {"417 IAB", "309 HAV", "301 URIS"};
      
      //data for coms dept
      Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
      coms1004.setEnrolledStudentCount(249);
      Course coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
      coms3134.setEnrolledStudentCount(242);
      Course coms3157 = new Course("Jae Lee", locations[0], times[1], 400);
      coms3157.setEnrolledStudentCount(311);
      Course coms3203 = new Course("Ansaf Salleb-Aouissi", locations[2], times[2], 250);
      coms3203.setEnrolledStudentCount(215);
      Course coms3261 = new Course("Josh Alman", locations[0], times[3], 150);
      coms3261.setEnrolledStudentCount(140);
      Course coms3251 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
      coms3251.setEnrolledStudentCount(99);
      Course coms3827 = new Course("Daniel Rubenstein", "207 Math", times[2], 300);
      coms3827.setEnrolledStudentCount(283);
      courses = new HashMap<>();
      courses.put("1004", coms1004);
      courses.put("3134", coms3134);
      courses.put("3157", coms3157);
      courses.put("3203", coms3203);
      courses.put("3261", coms3261);
      courses.put("3251", coms3251);
      courses.put("3827", coms3827);
      testDepartment= new Department("COMS", courses, "Luca Carloni", 2700);
    }
    
    
  @Test
  public void numberOfMajorsTest() {
    testDepartment= new Department("COMS", courses, "Luca Carloni", 2700);
    assertEquals(2700, testDepartment.getNumberOfMajors());
  }
  
  @Test
  public void getDepartmentChairTest() {
    testDepartment= new Department("COMS", courses, "Luca Carloni", 2700);
    assertEquals("Luca Carloni", testDepartment.getDepartmentChair());
  }

  @Test
  public void getCourseSelectionTest() {
    testDepartment= new Department("COMS", courses, "Luca Carloni", 2700);
    assertEquals(courses, testDepartment.getCourseSelection());
  }

  @Test
  public void addPersonToMajorTest() {
    testDepartment= new Department("COMS", courses, "Luca Carloni", 2700);
    testDepartment.addPersonToMajor();
    assertEquals(2701, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    testDepartment= new Department("COMS", courses, "Luca Carloni", 2700);
    testDepartment.dropPersonFromMajor();
    assertEquals(2699, testDepartment.getNumberOfMajors());
    testDepartment= new Department("EMPTY", null, "None", 0);
    testDepartment.dropPersonFromMajor();
    assertEquals(0, testDepartment.getNumberOfMajors());
  }

  @Test
  public void addCourseTest() {
    testDepartment= new Department("COMS", courses, "Luca Carloni", 2700);
    testDepartment.addCourse(null, null);
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    coms4156.setEnrolledStudentCount(109);
    testDepartment.addCourse("4156", coms4156);
    assertEquals(coms4156, testDepartment.getCourseSelection().get("4156"));
  }

  @Test
  public void createCourseTest() {
    testDepartment= new Department("COMS", courses, "Luca Carloni", 2700);
    testDepartment.createCourse("4156","Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    coms4156.setEnrolledStudentCount(109);
    assertEquals(coms4156.toString(), testDepartment.getCourseSelection().get("4156").toString());
  }

  @Test
  public void toStringTest() {
    HashMap<String, Course> computerCourses = new HashMap<>();
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    Course coms3827 = new Course("Daniel Rubenstein", "207 Math", "10:10-11:25", 300);
    computerCourses.put("4156",coms4156);
    computerCourses.put("3827",coms3827);
    testDepartment= new Department("COMS", computerCourses, "Luca Carloni", 2700);
    String res = "Department: COMS\n" + "courses offered: \n" + "3827: \n" + 
                "Instructor: Daniel Rubenstein; Location: 207 Math; Time: 10:10-11:25\n" + 
                "4156: \n" + "Instructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25";
    assertEquals(res, testDepartment.toString());
  }
  
    
  
  
  
    /** The test instance used for testing. */
    public static Department testDepartment;
    public static HashMap<String, Course> courses;
     
}
