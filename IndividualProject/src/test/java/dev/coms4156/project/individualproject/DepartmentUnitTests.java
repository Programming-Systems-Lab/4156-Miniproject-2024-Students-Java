package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Department class.
 * This class contains tests to verify the behavior and functionality of the Department class.
 * It uses Spring's testing framework to set up the environment and run the tests.
 */
@SpringBootTest
@ContextConfiguration
@TestMethodOrder(OrderAnnotation.class)
public class DepartmentUnitTests {

  /**
  * Sets up test data for a {@link Department} with two {@link Course} objects.
  * 
  * <p>This method runs once before all tests, creating two courses and adding them to
  * a department. The department is stored in {@code testDepartment} for use in tests.</p>
  */
  @BeforeAll
  public static void setupDepartmentForTesting() {
    // Creating Courses
    Course course1 = new Course("Henry Yuen", "HAV 209", "10:10-11:25", 80);
    Course course2 = new Course("Suman Jana", "MUDD 1127", "1:10-2:25", 65);
    
    // Create a HashMap of courses
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("W4281", course1);
    courses.put("W4181", course2);

    testDepartment = new Department("COMS", courses, "John Doe", 0);
  }

  @Test
  @Order(1)
  public void toStringTest() {
    String expectedResult = "COMS W4281: \nInstructor: Henry Yuen; Location: HAV 209; "
                                + "Time: 10:10-11:25\nCOMS W4181: \nInstructor: Suman Jana; "
                                + "Location: MUDD 1127; Time: 1:10-2:25\n";
    assertEquals(expectedResult, testDepartment.toString());
  }

  @Test
  @Order(2)
  public void dropPersonFromMajorTest1() {
    int expectedResult = 0;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());

    testDepartment.dropPersonFromMajor();
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }

  @Test
  @Order(3)
  public void addPersonToMajorTest() {
    int expectedResult = 0;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());

    expectedResult = 1;
    testDepartment.addPersonToMajor();
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }

  @Test
  @Order(4)
  public void dropPersonFromMajorTest2() {
    int expectedResult = 1;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());

    expectedResult = 0;
    testDepartment.dropPersonFromMajor();
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }

  @Test
  @Order(5)
  public void addCreateCourseTest() {
    testDepartment.createCourse("W4156", "Gail Kaiser", "Zoom", "10:10-11:25", 200);

    String result = testDepartment.toString();
    
    String course1 = "COMS W4281: \nInstructor: Henry Yuen; Location: HAV 209; Time: 10:10-11:25";
    assertTrue(result.contains(course1));
    
    String course2 = "COMS W4181: \nInstructor: Suman Jana; Location: MUDD 1127; Time: 1:10-2:25";
    assertTrue(result.contains(course2));
    
    String course3 = "COMS W4156: \nInstructor: Gail Kaiser; Location: Zoom; Time: 10:10-11:25";
    assertTrue(result.contains(course3));
  }

  /** The test course instance used for testing. */
  public static Department testDepartment;
}

