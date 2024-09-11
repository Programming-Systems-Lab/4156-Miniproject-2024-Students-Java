package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This is just testing if we can create a department object and return expected results from each
 * method.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {
  
  /**
   * Instantiating our test department that we'll be working with throughout testing.
   */
  @BeforeAll
  public static void setupDepartmentForTesting() {
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", "11:40-12:55", 120);
    coms4156.setEnrolledStudentCount(109);
    courses = new HashMap<>();
    courses.put("4156", coms4156);
    testDept = new Department("COMS", courses, "Luca Carloni", 2700);
  }
  
  /**
   * Testing the toString function on our sample Department created in setupDepartmentForTesting();.
   */
  
  @Test
  public void toStringTest() {
    String expectedResult =
        "COMS 4156: \nInstructor: Gail Kaiser; Location: 501 NWC; Time: 11:40-12:55\n";
    assertEquals(expectedResult, testDept.toString());
  }
  
  /**
   * This tests if we can create a course and we then check the output from toString on the
   * Department object to check the course was actually added. I then delete this course from the
   * hashmap to prevent issues with the toStringTest function.
   */
  @Test
  public void createCourseTest() {
    
    testDept.createCourse("4995", "Diego Rivas", "Mom Basement", "1:00-3:00", 69420);
    
    String expectedResult =
        "COMS 4156: \nInstructor: Gail Kaiser; Location: 501 NWC; Time: 11:40-12:55\n"
        + "COMS 4995: \nInstructor: Diego Rivas; Location: Mom Basement; Time: 1:00-3:00\n";
    assertEquals(expectedResult, testDept.toString());
    
    courses.remove("4995");
  }

  /**
   * To test if we can add a person to a major, we'll add the person and then check the amount of
   * people in this major. We'll then return the number of majors back to its original amount.
   */
  @Test
  public void addPersonMajorTest() {
    int temp = testDept.getNumberOfMajors();
    testDept.addPersonToMajor();
    
    assertEquals(temp + 1, testDept.getNumberOfMajors());
    testDept.dropPersonFromMajor();
  }

  /**
   * Testing whether or not we can grab the department chair from the department object.
   *
   */
  @Test
  public void getDepartmentChairTest() {    
    assertEquals("Luca Carloni", testDept.getDepartmentChair()); 
  }

  /**
   * Testing whether or not we can grab the HashMap containing the courses and extracting the course
   * object from the HashMap.
   *
   */
  @Test
  public void getCourseSelectionTest() {    

    Course extractedCourse = testDept.getCourseSelection().get("4156");
    assertEquals("Gail Kaiser", extractedCourse.getInstructorName()); 
  }
  
  /**
   * The test department instance used for testing and the courses held within the Department.
   */
  public static Department testDept;
  public static HashMap<String, Course> courses;
}

