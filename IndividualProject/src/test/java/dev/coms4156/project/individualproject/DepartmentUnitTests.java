package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Department} class.
 *
 * <p>This class contains test cases to validate the functionality of the {@link Department} class
 * methods.
 */
public class DepartmentUnitTests {

  private Department department;
  private Course course1;
  private Course course2;
  private HashMap<String, Course> courses;
  private Department emptyDepartment;

  /** Set up the Department unit tests by initializing a test Course and test Department. */
  @BeforeEach
  public void setUp() {
    course1 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 30);
    course2 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    courses = new HashMap<>();
    courses.put("1004", course1);
    courses.put("3251", course2);
    department = new Department("COMS", courses, "Luca Carloni", 2700);
  }

  @Test
  public void constructorTestValidConstructor() {
    assertNotNull(department, "Expected to identify a non-null department");
  }

  @Test
  public void constructorTestEmptyStringDeptCode() {
    // test for empty-string department code
    assertThrows(
        IllegalArgumentException.class,
        () -> new Department("", new HashMap<>(), "Luca Carloni", 2345),
        "Expected to throw IllegalArgumentException for department code==''");
  }

  @Test
  public void constructorTestNullDeptCode() {
    // test for null department code
    assertThrows(
        IllegalArgumentException.class,
        () -> new Department(null, new HashMap<>(), "Luca Carloni", 2345),
        "Expected to throw IllegalArgumentException for null department code");
  }

  @Test
  public void constructorTestNegativeNumberOfMajors() {
    // test for negative number of majors
    assertThrows(
        IllegalArgumentException.class,
        () -> new Department(null, new HashMap<>(), "Luca Carloni", -3),
        "Expected to throw IllegalArgumentException for negative number of majors");
  }

  @Test
  public void getNumberOfMajorsInputValidation() {
    assertEquals(2700, department.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairInputValidation() {
    assertEquals("Luca Carloni", department.getDepartmentChair());
  }

  @Test
  public void getDepartmentCodeInputValidation() {
    assertEquals("COMS", department.getDepartmentCode());
  }

  @Test
  public void getCourseSelectionInputValidation() {
    assertEquals(courses, department.getCourseSelection());
  }

  @Test
  public void addPersonToMajorIncreasesNumberOfMajors() {
    department.addPersonToMajor();
    assertEquals(
        2701,
        department.getNumberOfMajors(),
        "Expected to successfully increment the number of majors by 1");
  }

  @Test
  public void dropPersonFromMajorDecreaseNumberOfMajors() {
    department.dropPersonFromMajor();
    assertEquals(
        2699,
        department.getNumberOfMajors(),
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
        course1,
        department.getCourseSelection().get("1004"),
        "Expected to get course selection associated with 1004 courseId");
  }

  @Test
  public void addCourseInvalidNullCourseIdThrowsException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> department.addCourse(null, course1),
        "Expected to throw IllegalArgumentException when null course id");
  }

  @Test
  public void addCourseInvalidEmptyStringCourseIdThrowsException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> department.addCourse("", course1),
        "Expected to throw IllegalArgumentException when empty string as course id");
  }

  @Test
  public void addCourseNullCourseThrowsException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> department.addCourse("3445", null),
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
    department.createCourse(courseId, instructorName, courseLocation, courseTimeSlot, capacity);
    Course newCourse = department.getCourseSelection().get(courseId);

    // ensure proper input validation
    assertEquals(3, department.getCourseSelection().size());
    assertNotNull(department.getCourseSelection().get("3134"));
    assertNotNull(newCourse);
    assertEquals(instructorName, newCourse.getInstructorName());
    assertEquals(courseLocation, newCourse.getCourseLocation());
    assertEquals(courseTimeSlot, newCourse.getCourseTimeSlot());
    assertEquals(capacity, newCourse.getEnrollmentCapacity());
  }

  @Test
  public void toStringReturnsDepartmentRepresentation() {
    String course1ExpectedResult = "COMS 1004: " + course1.toString() + "\n";
    String course2ExpectedResult = "COMS 3251: " + course2.toString() + "\n";
    String expectedResult = course1ExpectedResult + course2ExpectedResult;
    assertEquals(
        expectedResult,
        department.toString(),
        """
            Expected to return...\

            '{deptCode} {courseId}:\

            Instructor: {instructorName}; Location: {courseLocation};\
             Time: {courseTimeSlot}""");
  }
}
