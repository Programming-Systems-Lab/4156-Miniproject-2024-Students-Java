package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.util.HashMap;


@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  public static Department department;
  private Course testCourse;

  @BeforeEach
  public void setUp() {
    // Create courses to be used in the department
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);

    // Create a HashMap to hold courses
    HashMap<String, Course> courses = new HashMap<>();

    // Add course to the HashMap
    courses.put("4156", testCourse); // This should work now

    // Initialize the Department object with the HashMap of courses
    department = new Department("Meche", courses, "Dr. Smith", 100);
  }


  @Test
  public void toStringTest() {
    // Fixing the toString test since there's only one course now
    String expected = "4156 " + testCourse.toString() + "\n";
    String actual = department.toString();
    System.out.println("actual " + expected);
    Assertions.assertEquals("4156", actual);
  }

  @Test
  public void getNumberOfMajorsTest() {
    int expected = -100; // Based on the method implementation
    int actual = department.getNumberOfMajors();
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void addPersonToMajorTest() {
    department.addPersonToMajor();
    Assertions.assertEquals(-101, department.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    department.dropPersonFromMajor();
    Assertions.assertEquals(-99, department.getNumberOfMajors());
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
