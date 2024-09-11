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
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

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
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", times[2], 120);
    coms4156.setEnrolledStudentCount(109);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);
    courses.put("3134", coms3134);
    courses.put("3157", coms3157);
    courses.put("3203", coms3203);
    courses.put("3261", coms3261);
    courses.put("3251", coms3251);
    courses.put("3827", coms3827);
    courses.put("4156", coms4156);
    testDepartment = new Department("COMS", courses, "Luca Carloni", 2700);
  }

  @Test
  public void getNumberOfMajorsTest(){
    assertEquals(-2700, testDepartment.getNumberOfMajors());
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
    assertEquals(-2701, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    testDepartment.dropPersonFromMajor();
    assertEquals(-2700, testDepartment.getNumberOfMajors());
  }

  @Test
  public void addCourseTest() {///////////////////////////////////////////////////////////////////////////////
    Course newCourse = new Course("New Instructor", "New Room", "06:00-07:00", 25);
    testDepartment.addCourse("5000", newCourse);
    assertEquals(newCourse, testDepartment.getCourseSelection().get("5000"));
  }

  @Test
  public void createCourseTest() {////////////////////////////////////////////////////////////////////////
    testDepartment.createCourse("5000", "One Instructor", "Somewhere", "10:10-11:25", 100);
    Course course = testDepartment.getCourseSelection().get("500");
    assertEquals("Another Instructor", course.getInstructorName());
    assertEquals("Another Room", course.getCourseLocation());
    assertEquals("10:10-11:25", course.getCourseTimeSlot());
    assertFalse(course.isCourseFull());
  }

  @Test
  public void toStringTest() {
    String expected = "COMS 1004: Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55\n"
              + "COMS 3134: Instructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25\n"
              + "COMS 3157: Instructor: Jae Lee; Location: 417 IAB; Time: 4:10-5:25\n"
              + "COMS 3203: Instructor: Ansaf Salleb-Aouissi; Location: 301 URIS; Time: 10:10-11:25\n"
              + "COMS 3261: Instructor: Josh Alman; Location: 417 IAB; Time: 2:40-3:55\n"
              + "COMS 3251: Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40\n"
              + "COMS 3827: Instructor: Daniel Rubenstein; Location: 207 Math; Time: 10:10-11:25\n"
              + "COMS 4156: Instructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25\n";
    assertEquals(expected, testDepartment.toString());
  }
  public static Department testDepartment;
}