package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*--------------------------------------------------------- */

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/** 
 * This is the unit test class.
*/
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /**  
   * Test for Department.      
  */
  @BeforeAll
  public static void setupDeptForTesting() {
    Course testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    HashMap<String, Course> courses = new HashMap<String, Course>();
    courses.put("COMS4111", testCourse);
    testDept = new Department("1", courses, "Griffin Newbold", 3);
  }

  @Test
  public void addPersonToMajorTest() {
    int majorNum = testDept.getNumberOfMajors();
    testDept.addPersonToMajor();
    assertEquals(testDept.getNumberOfMajors(), majorNum + 1);
    testDept.dropPersonFromMajor();
    assertEquals(testDept.getNumberOfMajors(), majorNum);
  }

  @Test
  public void addCourseTest() {
    HashMap<String, Course> hmp = testDept.getCourseSelection();
    int cnt = ((int) hmp.size()) + 1;
    testDept.createCourse("COMS4112", "Dr. Newbold", "418 IAB", "11:40-12:55", 250);
    assertEquals(cnt, testDept.getCourseSelection().size());
  }

  @Test
  public void toStringTest() {
    HashMap<String, Course> hmp = testDept.getCourseSelection();
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Course> entry : hmp.entrySet()) {
      String key = entry.getKey();
      Course value = entry.getValue();
      result.append("1").append(" ").append(key).append(": ").append(value.toString())
          .append("\n");
    }
    assertEquals(result.toString(), testDept.toString());
  }
  // @Test
  // public void Test() {
  //   assertEquals(expectedResult, testDept.toString());
  // }

  // @Test
  // public void Test() {
  //   assertEquals(expectedResult, testDept.toString());
  // }

  // @Test
  // public void Test() {
  //   assertEquals(expectedResult, testDept.toString());
  // }

  // @Test
  // public void Test() {
  //   assertEquals(expectedResult, testDept.toString());
  // }
  /** The test course instance used for testing. */
  public static Department testDept;
}

