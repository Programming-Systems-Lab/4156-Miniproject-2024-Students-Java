package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a course in the system.
 * This class stores details about a course, 
 * including its code, name, and other relevant information.
 * Implements {@link Serializable} to allow course objects to be serialized for 
 * storage or transmission.
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
    enrolledStudentCount++;
    return true;
  }

  /**
   * Drops a student from the course if a student is enrolled.
   *
   * @return true if the student is successfully dropped, false otherwise.
   */
  public boolean dropStudent() {
    enrolledStudentCount--;
    return true;
  }

  /**
   * Returns the location of the course.
   *
   * @return the location of the course.
   */
  public String getCourseLocation() {
    return this.courseLocation;
  }

  /**
   * Returns the name of the instructor teaching the course.
   *
   * @return the name of the instructor teaching the course.
   */
  public String getInstructorName() {
    return this.instructorName;
  }

  /**
   * Returns the time slot of the course.
   *
   * @return the time slot of the course.
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * Returns a string representation of the course.
   *
   * @return a string representation of the course.
   */
  public String toString() {
    return "\nInstructor: " + instructorName +  "; Location: "  
        + courseLocation +  "; Time: " + courseTimeSlot;
  }

  /**
   * Reassigns the instructor of the course.
   *
   * @param newInstructorName the new instructor name.
   */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }

  /**
   * Reassigns the location of the course.
   *
   * @param newLocation the new location.
   */
  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }

  /**
   * Reassigns the time slot of the course.
   *
   * @param newTime the new time slot.
   */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * Sets the count of enrolled students in the course.
   *
   * @param count the count of enrolled students.
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
    if (this.enrolledStudentCount >= this.enrollmentCapacity) {
      return true;
    }
    return false;
  }

  @Serial
  private static final long serialVersionUID = 123456L;
  private final int enrollmentCapacity;
  private int enrolledStudentCount;
  private String courseLocation;
  private String instructorName;
  private String courseTimeSlot;
}
