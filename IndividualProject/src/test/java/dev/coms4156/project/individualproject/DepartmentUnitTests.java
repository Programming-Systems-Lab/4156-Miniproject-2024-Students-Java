package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the {@link Department} class.
 *
 * <p>This class contains test cases to validate the functionality of the {@link Department} class
 * methods.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /** The test department instance used for testing. */
  private Course testCourse;

  private Department testDepartment;
  private Department emptyDepartment;

  /** Set up the Department unit tests by initializing a test Course and test Department */
  @BeforeEach
  public void setUp() {
    // course/department with majors
    testCourse = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 30);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", testCourse);
    testDepartment = new Department("COMS", courses, "Luca Carloni", 50);
  }

  @Test
  public void constructorTestValidConstructor() {
    Department department = new Department("ECON", new HashMap<>(), "Michael Woodford", 2345);
    assertNotNull(department, "Expected to identify a non-null department");
  }

  @Test
  public void constructorTestInvalidDeptCode() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new Department("", new HashMap<>(), "Luca Carloni", 2345),
        "Expected to throw IllegalArgumentException for null department code");

    assertThrows(
        IllegalArgumentException.class,
        () -> new Department(null, new HashMap<>(), "Luca Carloni", 2345),
        "Expected to throw IllegalArgumentException for null department code");
  }

  @Test
  public void addPersonToMajorIncreasesNumberOfMajors() {
    testDepartment.addPersonToMajor();
    assertEquals(
        51,
        testDepartment.getNumberOfMajors(),
        "Expected to successfully increment the number of majors by 1");
  }

  @Test
  public void dropPersonFromMajorDecreaseNumberOfMajors() {
    testDepartment.dropPersonFromMajor();
    assertEquals(
        49,
        testDepartment.getNumberOfMajors(),
        "Expected to successfully decrement the number of majors by 1");
  }

  @Test
  public void dropPersonFromMajorWhenNoMajorsThrowsException() {
    // course/department without majors
    emptyDepartment = new Department("COMS", new HashMap<>(), "Luca Carloni", 0);

    assertThrows(
        IllegalArgumentException.class,
        () -> emptyDepartment.dropPersonFromMajor(),
        "Expected to throw IllegalArgumentException when 0 students in major and dropping a "
            + "person");
  }

  @Test
  public void addCourseValidCourseIdAddsCourse() {
    assertEquals(
        testCourse,
        testDepartment.getCourseSelection().get("1004"),
        "Expected to get course selection associated with 1004 courseId");
  }

  @Test
  public void addCourseInvalidCourseIdThrowsException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> testDepartment.addCourse(null, testCourse),
        "Expected to throw IllegalArgumentException when null course id");

    assertThrows(
        IllegalArgumentException.class,
        () -> testDepartment.addCourse("", testCourse),
        "Expected to throw IllegalArgumentException when empty string as course id");
  }

  @Test
  public void addCourseNullCourseThrowsException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> testDepartment.addCourse("3445", null),
        "Expected to throw IllegalArgumentException when null course");
  }

  @Test
  public void createCourseCreatesAndAddsCourse() {
    // values to test
    String courseId = "3134";
    String instructorName = "Brian Borowski";
    String courseLocation = "301 URIS";
    String courseTimeSlot = "14:00-15:00";
    int capacity = 250;

    // create course to test against
    testDepartment.createCourse(courseId, instructorName, courseLocation, courseTimeSlot, capacity);
    Course newCourse = testDepartment.getCourseSelection().get(courseId);

    // ensure proper input validation
    assertNotNull(newCourse);
    assertEquals(instructorName, newCourse.getInstructorName());
    assertEquals(courseLocation, newCourse.getCourseLocation());
    assertEquals(courseTimeSlot, newCourse.getCourseTimeSlot());
    assertEquals(capacity, newCourse.getEnrollmentCapacity());
  }

  @Test
  public void toStringReturnsDepartmentRepresentation() {
    String expectedResult = "COMS 1004: " + testCourse.toString() + "\n";
    assertEquals(
        expectedResult,
        testDepartment.toString(),
        """
            Expected to return...\

            '{deptCode} {courseId}:\

            Instructor: {instructorName}; Location: {courseLocation};\
             Time: {courseTimeSlot}""");
  }
}
