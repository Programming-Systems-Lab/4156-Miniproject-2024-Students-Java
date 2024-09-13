package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*--------------------------------------------------------- */

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.util.HashMap;
import java.util.Map;

/** 
 * This is the unit test class.
*/
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /*  public Department(String deptCode, HashMap<String, Course> courses, String departmentChair,
                    int numberOfMajors) */
  @BeforeAll
  public static void setupDeptForTesting() {
    Course testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    HashMap<String, Course> courses=new HashMap<String, Course>();
    courses.put("COMS4111", testCourse);
    testDept = new Department("1", courses, "Griffin Newbold", 3);
  }

  @Test
  public void toStringTest() {
    String expectedResult = "1 COMS4111: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n";
    assertEquals(expectedResult, testDept.toString());
  }

  // @Test
  // public void Test() {
  //   String expectedResult = "1 1: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n";
  //   assertEquals(expectedResult, testDept.toString());
  // }

  // @Test
  // public void Test() {
  //   String expectedResult = "1 1: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n";
  //   assertEquals(expectedResult, testDept.toString());
  // }

  // @Test
  // public void Test() {
  //   String expectedResult = "1 1: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n";
  //   assertEquals(expectedResult, testDept.toString());
  // }

  // @Test
  // public void Test() {
  //   String expectedResult = "1 1: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n";
  //   assertEquals(expectedResult, testDept.toString());
  // }

  // @Test
  // public void Test() {
  //   String expectedResult = "1 1: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n";
  //   assertEquals(expectedResult, testDept.toString());
  // }

  // @Test
  // public void Test() {
  //   String expectedResult = "1 1: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n";
  //   assertEquals(expectedResult, testDept.toString());
  // }
  /** The test course instance used for testing. */
  public static Department testDept;
}

