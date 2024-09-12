package dev.coms4156.project.individualproject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class test constructing department object by providing necessary parameters,
 * After creating the object, it will be compared with the manually written department
 * object. If information match, there will be no error.
 */


@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  @BeforeAll
  public static void setupDepartmentForTesting() {
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    coms4156.setEnrolledStudentCount(109);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("4156", coms4156);
    testDepartment = new Department("COMS", courses, "Luca Carloni", 2700);
  }

  @Test
  public void getNumberOfMajorsTest(){
    assertEquals(2700, testDepartment.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    assertEquals("Luca Carloni", testDepartment.getDepartmentChair());
  }

  @Test
  public void getCourseSelectionTest() {/////////////////////////////////////////
    assertEquals("Luca Carloni", testDepartment.getDepartmentChair());
  }

  @Test
  public void addPersonToMajorTest() {
    testDepartment.addPersonToMajor();
    assertEquals(2701, testDepartment.getNumberOfMajors());
    testDepartment.dropPersonFromMajor();
  }

  @Test
  public void dropPersonFromMajorTest() {
    testDepartment.dropPersonFromMajor();
    assertEquals(2699, testDepartment.getNumberOfMajors());
    testDepartment.addPersonToMajor();
  }

  @Test
  public void addCourseTest() {///////////////////////////////////////////////////////////////////////////////
    Course newCourse = new Course("Jae Lee", "417 IAB", "4:10-5:25", 311);
    testDepartment.addCourse("3157", newCourse);
    assertEquals(newCourse, testDepartment.getCourseSelection().get("3157"));
  }

  @Test
  public void createCourseTest() {////////////////////////////////////////////////////////////////////////
    testDepartment.createCourse("3157", "Jae Lee", "417 IAB", "4:10-5:25", 100);
    Course course = testDepartment.getCourseSelection().get("3157");
    assertEquals("Jae Lee", course.getInstructorName());
    assertEquals("417 IAB", course.getCourseLocation());
    assertEquals("4:10-5:25", course.getCourseTimeSlot());
    assertFalse(course.isCourseFull());
  }

  @Test
  public void toStringTest() {
    String expected = "COMS 4156: \n" +
            "Instructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25\n" +
            "COMS 3157: \n" +
            "Instructor: Jae Lee; Location: 417 IAB; Time: 4:10-5:25\n";
    System.out.println(testDepartment.toString());
    assertEquals(expected, testDepartment.toString());
  }
  public static Department testDepartment;
}