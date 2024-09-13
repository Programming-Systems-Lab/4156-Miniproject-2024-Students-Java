package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a course offered within an educational institution.
 * This class stores information about the course, including its instructor,
 * location, time slot, and capacity.
 */
public class Course implements Serializable {

  @Serial
  private static final long serialVersionUID = 123456L;
  private final int enrollmentCapacity;
  private int enrolledStudentCount;
  private String courseLocation;
  private String instructorName;
  private String courseTimeSlot;

  /**
   * Constructs a new Course object with the given parameters. Initial count starts at 0.
   *
   * @param instructorName     The name of the instructor teaching the course.
   * @param courseLocation     The location where the course is held.
   * @param timeSlot           The time slot of the course.
   * @param capacity           The maximum number of students that can enroll in the course.
   */
  public Course(String instructorName, String courseLocation, String timeSlot, int capacity) {
    this.courseLocation = courseLocation;
    this.instructorName = instructorName;
    this.courseTimeSlot = timeSlot;
    this.enrollmentCapacity = capacity;
    this.enrolledStudentCount = 500;
  }

  /**
   * Enrolls a student in the course if there is space available.
   *
   * @return true if the student is successfully enrolled, false otherwise.
   */
  public boolean enrollStudent() {
    enrolledStudentCount++;
    return false;
  }

  /**
   * Drops a student from the course if a student is enrolled.
   *
   * @return true if the student is successfully dropped, false otherwise.
   */
  public boolean dropStudent() {
    enrolledStudentCount--;
    return false;
  }

  /**
   * Gets the location of a course.
   *
   * @return The course location.
   */
  public String getCourseLocation() {
    return this.courseLocation;
  }

  /**
   * Gets the instructor's name of a course.
   *
   * @return The instructor's name.
   */
  public String getInstructorName() {
    return this.instructorName;
  }

  /**
   * Gets the time slot of the course.
   *
   * @return The course time slot.
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * Provides a string representation of the course, including the instructor's name,
   * course location, and time slot.
   *
   * @return A string representation of the course.
   */
  public String toString() {
    return "\nInstructor: " + instructorName
            +  "; Location: "  + courseLocation
            +  "; Time: " + courseTimeSlot;
  }

  /**
   * Sets the instructor's name to the new provided instructor name.
   *
   * @param newInstructorName  The new instructor name.
   */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }

  /**
   * Sets the course's location to the new provided location.
   *
   * @param newLocation  The new location.
   */
  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }

  /**
   * Sets the course time slot to the new provided time slot.
   *
   * @param newTime  The new time slot.
   */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * Sets enrolled student count for a course.
   *
   * @param count  The new enrolled student count.
   */
  public void setEnrolledStudentCount(int count) {
    this.enrolledStudentCount = count;
  }

  /**
   * Checks if a course is at full enrollment capacity.
   *
   * @return true if the course enrollment capacity exceeds enrolled student count, false otherwise.
   */
  public boolean isCourseFull() {
    return enrollmentCapacity > enrolledStudentCount;
  }
}
