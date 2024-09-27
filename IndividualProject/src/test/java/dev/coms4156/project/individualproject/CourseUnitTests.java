package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Course class.
 */

@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  public static Course testCourse;

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Kenneth Shepard", "1205 MUDD", "4:10-6:40", 250);
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Kenneth Shepard; Location: 1205 MUDD; Time: 4:10-6:40";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void testReassignInstructor() {
    testCourse.reassignInstructor("David G Vallancourt");
    assertEquals("David G Vallancourt", testCourse.getInstructorName());
  }

  @Test
  public void testReassignLocation() {
    testCourse.reassignLocation("301 PUP");
    assertEquals("301 PUP", testCourse.getCourseLocation());
  }

  @Test
  public void testReassignTime() {
    testCourse.reassignTime("4:10-5:25");
    assertEquals("4:10-5:25", testCourse.getCourseTimeSlot());
  }

  @Test
  public void testEnrollStudentSuccess() {
    testCourse.setEnrolledStudentCount(100);
    assertTrue(testCourse.enrollStudent());
    assertEquals(101, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void testEnrollStudentFailure() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(testCourse.enrollStudent());
    assertEquals(250, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void testEnrollStudentEdgeCase() {
    testCourse.setEnrolledStudentCount(249);
    assertTrue(testCourse.enrollStudent());
    assertEquals(250, testCourse.getEnrolledStudentCount());
    assertFalse(testCourse.enrollStudent());
  }

  @Test
  public void testDropStudentSuccess() {
    testCourse.setEnrolledStudentCount(100);
    assertTrue(testCourse.dropStudent());
    assertEquals(99, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void testDropStudentFailure() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent());
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void testIsCourseFullTrue() {
    testCourse.setEnrolledStudentCount(250);
    assertTrue(testCourse.isCourseFull());
  }

  @Test
  public void testIsCourseFullFalse() {
    testCourse.setEnrolledStudentCount(249);
    assertFalse(testCourse.isCourseFull());
  }

  @Test
  public void testSetEnrolledStudentCount() {
    testCourse.setEnrolledStudentCount(50);
    assertEquals(50, testCourse.getEnrolledStudentCount());
    testCourse.setEnrolledStudentCount(250);
    assertEquals(250, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void testZeroCapacityCourse() {
    Course zeroCapacityCourse = new Course("Mohamed Kamaludeen", "903 SSW", "7:00-9:30", 0);
    assertFalse(zeroCapacityCourse.enrollStudent());
    assertTrue(zeroCapacityCourse.isCourseFull());
  }

  @Test
  public void testInitialEnrollment() {
    Course newCourse = new Course("Christine P Hendon", "633 MUDD", "10:10-12:40", 100);
    assertEquals(500, newCourse.getEnrolledStudentCount());
  }

  @Test
  public void testEnrollStudentAtFullCapacity() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(testCourse.enrollStudent());
  }

  @Test
  public void testDropStudentFromEmptyCourse() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent());
  }

  @Test
  public void testFullCourseEnrollAttempt() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(testCourse.enrollStudent());
  }

  @Test
  public void testValidEnrollmentIncrease() {
    testCourse.setEnrolledStudentCount(249);
    assertTrue(testCourse.enrollStudent());
    assertEquals(250, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void testReassignInstructorNull() {
    testCourse.reassignInstructor(null);
    assertNull(testCourse.getInstructorName());
  }

  @Test
  public void testReassignLocationNull() {
    testCourse.reassignLocation(null);
    assertNull(testCourse.getCourseLocation());
  }

  @Test
  public void testReassignTimeNull() {
    testCourse.reassignTime(null);
    assertNull(testCourse.getCourseTimeSlot());
  }

  @Test
  public void testNegativeEnrollmentCount() {
    testCourse.setEnrolledStudentCount(-10);
    assertEquals(-10, testCourse.getEnrolledStudentCount());
    assertFalse(testCourse.dropStudent());
  }

  @Test
  public void testEnrollDropMultipleOperations() {
    testCourse.setEnrolledStudentCount(100);
    assertTrue(testCourse.enrollStudent());
    assertTrue(testCourse.dropStudent());
    assertTrue(testCourse.dropStudent());
    assertTrue(testCourse.enrollStudent());
    assertEquals(100, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void testToStringWithNullInstructor() {
    testCourse.reassignInstructor(null);
    String expectedResult = "\nInstructor: null; Location: 1205 MUDD; Time: 4:10-6:40";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void testToStringWithNullLocation() {
    testCourse.reassignLocation(null);
    String expectedResult = "\nInstructor: Kenneth Shepard; Location: null; Time: 4:10-6:40";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void testToStringWithNullTimeSlot() {
    testCourse.reassignTime(null);
    String expectedResult = "\nInstructor: Kenneth Shepard; Location: 1205 MUDD; Time: null";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void testZeroCapacityCourseEnrollment() {
    Course zeroCapacityCourse = new Course("David G Vallancourt", "203 MATH", "11:40-12:55", 0);
    assertFalse(zeroCapacityCourse.enrollStudent());
    assertTrue(zeroCapacityCourse.isCourseFull());
  }

  @Test
  public void testSetEnrollmentToZero() {
    testCourse.setEnrolledStudentCount(0);
    assertEquals(0, testCourse.getEnrolledStudentCount());
    assertFalse(testCourse.dropStudent());
  }

  @Test
  public void testSetEnrollmentBeyondCapacity() {
    testCourse.setEnrolledStudentCount(300);
    assertEquals(300, testCourse.getEnrolledStudentCount());
    assertFalse(testCourse.enrollStudent());
  }

  @Test
  public void testGetAvailableSeats() {
    testCourse.setEnrolledStudentCount(200);
    int expectedAvailableSeats = 250 - 200;
    assertEquals(expectedAvailableSeats, testCourse.getAvailableSeats());
  }

  @Test
  public void testGetAvailableSeatsWhenNoStudentsEnrolled() {
    testCourse.setEnrolledStudentCount(0);
    int expectedAvailableSeats = 250;
    assertEquals(expectedAvailableSeats, testCourse.getAvailableSeats());
  }

  @Test
  public void testGetAvailableSeatsWhenNearlyFull() {
    testCourse.setEnrolledStudentCount(249);
    int expectedAvailableSeats = 1;
    assertEquals(expectedAvailableSeats, testCourse.getAvailableSeats());
  }

  @Test
  public void testGetAvailableSeatsWhenFull() {
    testCourse.setEnrolledStudentCount(250);
    int expectedAvailableSeats = 0;
    assertEquals(expectedAvailableSeats, testCourse.getAvailableSeats());
  }

  @Test
  public void testGetAvailableSeatsWhenOverCapacity() {
    testCourse.setEnrolledStudentCount(260);
    int expectedAvailableSeats = -10;
    assertEquals(expectedAvailableSeats, testCourse.getAvailableSeats());
  }

  @Test
  public void testGetAvailableSeatsWhenNegativeEnrollment() {
    testCourse.setEnrolledStudentCount(-5);
    int expectedAvailableSeats = 250 + 5;
    assertEquals(expectedAvailableSeats, testCourse.getAvailableSeats());
  }

}
