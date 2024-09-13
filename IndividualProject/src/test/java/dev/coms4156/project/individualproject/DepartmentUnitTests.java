package dev.coms4156.project.individualproject;

import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Unit test for Department class.
 * <p>
 * The unit test below define the unit test for
 * Department.java class using Spring
 * Boots testing support.
 * </p>
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  public static Department department;
  private Course testCourse;

  /**
   * Setting up the test course and random department with it.
   */
  @BeforeEach
  public void setUp() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);

    HashMap<String, Course> courses = new HashMap<>();

    courses.put("4156", testCourse);

    department = new Department("Meche", courses, "Dr. Smith", 100);
  }


  @Test
  public void toStringTest() {
    String actual = department.toString();
    String expected = "Meche 4156: \n"
            + "Instructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n";
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void getNumberOfMajorsTest() {
    int expected = 100;
    int actual = department.getNumberOfMajors();
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void addPersonToMajorTest() {
    department.addPersonToMajor();
    Assertions.assertEquals(101, department.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    department.dropPersonFromMajor();
    Assertions.assertEquals(99, department.getNumberOfMajors());
  }

  @Test
  public void addCourseTest() {
    Course newCourse = new Course("Instructor C", "Room 103", "3:00 PM", 20);
    department.addCourse("CSE103", newCourse);

    HashMap<String, Course> courses = department.getCourseSelection();
    Assertions.assertTrue(courses.containsKey("CSE103"));
    Assertions.assertEquals(newCourse, courses.get("CSE103"));
  }
}
