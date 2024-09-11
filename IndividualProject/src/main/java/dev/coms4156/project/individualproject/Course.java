package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a Course within an educational institution.
 * This class stores information about the course, including its course location,
 * course instructor, course time slot, course capacity, and course enrolled student count.
 */
public class Course implements Serializable {

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
    if (!isCourseFull()) {
      enrolledStudentCount++;
      return true;
    }
    return false;
  }

  /**
   * Drops a student from the course if a student is enrolled.
   *
   * @return true if the student is successfully dropped, false otherwise.
   */
  public boolean dropStudent() {
    if (enrolledStudentCount > 0) {
      enrolledStudentCount--;
      return true;
    }
    return false;
  }

  /**
   * Gets the course location.
   *
   * @return The location of the course as a string.
   */
  public String getCourseLocation() {
    return this.courseLocation;
  }

  /**
   * Gets the instructor name.
   *
   * @return The name of the course instructor as a string.
   */
  public String getInstructorName() {
    return this.instructorName;
  }

  /**
   * Gets the course time slot.
   *
   * @return The course time slot as a string.
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * A string representation of the course details.
   * Such as instructor name, course location, and course time slot.
   *
   * @return A string representation of the course.
   */
  public String toString() {
    return "\nInstructor: " + instructorName +
            "; Location: "  + courseLocation +
            "; Time: " + courseTimeSlot;
  }

  /**
   * Reassigns the instructor for the course.
   *
   * @param newInstructorName The name of the new instructor.
   */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }

  /**
   * Reassigns the location for the course.
   *
   * @param newLocation The new location of the course.
   */
  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }

  /**
   * Reassigns the time for the course.
   *
   * @param newTime The new time of the course.
   */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * Sets the number of enrolled students in the course.
   *
   * @param count The new count of students.
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
    return enrolledStudentCount >= enrollmentCapacity;
  }

  @Serial
  private static final long serialVersionUID = 123456L;
  private final int enrollmentCapacity;
  private int enrolledStudentCount;
  private String courseLocation;
  private String instructorName;
  private String courseTimeSlot;
}
