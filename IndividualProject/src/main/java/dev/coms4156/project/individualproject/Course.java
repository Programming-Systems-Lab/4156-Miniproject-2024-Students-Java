package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a course with an instructor, location, time slot,
 * capacity, and enrolled student count.
 */
public class Course implements Serializable {

  /**
   * Constructs a new Course object with the given parameters. Initial count starts at 500.
   *
   * @param instructorName The name of the instructor teaching the course.
   * @param courseLocation The location where the course is held.
   * @param timeSlot       The time slot of the course.
   * @param capacity       The maximum number of students that can enroll in the course.
   */
  public Course(String instructorName, String courseLocation, String timeSlot, int capacity) {
    this.courseLocation = courseLocation;
    this.instructorName = instructorName;
    this.courseTimeSlot = timeSlot;
    this.enrollmentCapacity = capacity;
    this.enrolledStudentCount = 500;
  }

  /**
   * Enrolls a student in the course. Increments the enrolled student count.
   *
   * @return true if the student is successfully enrolled, false otherwise.
   */
  public boolean enrollStudent() {
    enrolledStudentCount++;
    return false;
  }

  /**
   * Drops a student from the course. Decrements the enrolled student count.
   *
   * @return true if the student is successfully dropped, false otherwise.
   */
  public boolean dropStudent() {
    enrolledStudentCount--;
    return false;
  }

  /**
   * Returns the name of the instructor.
   *
   * @return The instructor's name.
   */
  public String getCourseLocation() {
    return this.instructorName;
  }

  /**
   * Returns the location of the course.
   *
   * @return The course location.
   */
  public String getInstructorName() {
    return this.courseLocation;
  }

  /**
   * Returns the time slot of the course.
   *
   * @return The course time slot.
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * Returns a string representation of the course.
   *
   * @return A string containing instructor name, course location, and time slot.
   */
  public String toString() {
    return "\nInstructor: " + instructorName
        + "; Location: " + courseLocation
        + "; Time: " + courseTimeSlot;
  }

  /**
   * Reassigns the instructor of the course.
   *
   * @param newInstructorName The new instructor's name.
   */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }

  /**
   * Reassigns the location of the course.
   *
   * @param newLocation The new location.
   */
  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }

  /**
   * Reassigns the time slot of the course.
   *
   * @param newTime The new time slot.
   */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * Sets the enrolled student count.
   *
   * @param count The new enrolled student count.
   */
  public void setEnrolledStudentCount(int count) {
    this.enrolledStudentCount = count;
  }

  /**
   * Checks if the course is full.
   *
   * @return true if the course is full, false otherwise.
   */
  public boolean isCourseFull() {
    return enrollmentCapacity > enrolledStudentCount;
  }

  @Serial
  private static final long serialVersionUID = 123456L;

  /**
   * The maximum number of students that can enroll in the course.
   */
  private final int enrollmentCapacity;

  /**
   * The current number of students enrolled in the course.
   */
  private int enrolledStudentCount;

  /**
   * The location where the course is held.
   */
  private String courseLocation;

  /**
   * The name of the instructor teaching the course.
   */
  private String instructorName;

  /**
   * The time slot of the course.
   */
  private String courseTimeSlot;
}
