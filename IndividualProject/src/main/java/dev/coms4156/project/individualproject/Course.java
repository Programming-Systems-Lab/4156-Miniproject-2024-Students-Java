package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;


/**
 * Represents a course within an educational institution.
 * This class stores information about the course, including,
 * instructor's name, course location, time slot, enrollment capacity,
 * and the amount of students enrolled in this course.
 */
public class Course implements Serializable {

  /**
   * Constructs a new Course object with the given parameters. Initial count
   * starts at 0.
   *
   * @param instructorName The name of the instructor teaching the course.
   * @param courseLocation The location where the course is held.
   * @param timeSlot       The time slot of the course.
   * @param capacity       The maximum number of students that can enroll in the
   *                       course.
   */
  public Course(String instructorName, String courseLocation, String timeSlot, int capacity) {
    this.courseLocation = courseLocation;
    this.instructorName = instructorName;
    this.courseTimeSlot = timeSlot;
    this.enrollmentCapacity = capacity;
    this.enrolledStudentCount = 500;
  }

  /**
   * Enrolls a student in the course if the course is not full.
   *
   * @return true if the student is successfully enrolled, false otherwise.
   */
  public boolean enrollStudent() {
    boolean isCourseFull = this.isCourseFull();
    if (!isCourseFull) {
      enrolledStudentCount++;
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Drops a student from the course if at least one student is enrolled.
   *
   * @return true if the student is successfully dropped, false otherwise.
   */
  public boolean dropStudent() {
    boolean isCourseEmpty = this.enrolledStudentCount < 1;
    if (!isCourseEmpty) {
      enrolledStudentCount--;
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Gets the location of where the course is held.
   *
   * @return The location where the course is held.
   */
  public String getCourseLocation() {
    return this.courseLocation;
  }

  /**
   * Gets the instructor's name for this course.
   *
   * @return The instuctor's name for this course.
   */
  public String getInstructorName() {
    return this.instructorName;
  }

  /**
   * Retrieves the time slot for this course.
   *
   * @return The time slot for this course.
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * Returns a string that contains the instructor's name, course location, and
   * the course's time slot.
   *
   * @return The instructor's name, course location, and course's time slot.
   */
  public String toString() {
    return "\nInstructor: " + instructorName
      + "; Location: " + courseLocation
      + "; Time: " + courseTimeSlot;
  }

  /**
   * Changes the name of the instructor of this course.
   *
   * @param newInstructorName The name of the new instructor to be assigned.
   */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }

  /**
   * Changes the location for this course is being held.
   *
   * @param newLocation The new location where this course is held.
   */
  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }

  /**
   * Changes the time slot for this course.
   *
   * @param newTime The new time slot.
   */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * Sets the current count value of how many students are enrolled in this
   * course.
   *
   * @param count The new count for student enrollment.
   */
  public void setEnrolledStudentCount(int count) {
    this.enrolledStudentCount = count;
  }

  /**
   * Checks to see if the max enrollment capacity is being exceeded by the current
   * student enrolled count.
   *
   * @return true is course is full, false otherwise.
   */
  public boolean isCourseFull() {
    return enrollmentCapacity > enrolledStudentCount;
  }

  @Serial
  private static final long serialVersionUID = 123456L;
  private final int enrollmentCapacity;
  private int enrolledStudentCount;
  private String courseLocation;
  private String instructorName;
  private String courseTimeSlot;
}
