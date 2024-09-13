package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;


/**
 * Represents a course within a department.
 * This class stores information about the course, including its instructor name,
 * course location, time slot, and capacity.
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
   * @return true if the student is successfully enrolled. false otherwise.
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
   * Gets a course's location.
   *
   * @return The course's location.
   */
  public String getCourseLocation() {
    return this.courseLocation;
  }

  /**
   * Gets the instructor's name of the specified course.
   *
   * @return The instructor's name of the specified course.
   */
  public String getInstructorName() {
    return this.instructorName;
  }

  /**
   * Gets the time slot of the specified course.
   *
   * @return The time slot of the specified course.
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * Gets the enrollment capacity of the specified course
   *
   * @return The enrollment capacity of the specified course
   */
  public int getEnrollmentCapacity() {
    return this.enrollmentCapacity;
  }

  /**
   * Gets the enrolled student count of the specified course
   *
   * @return The enrolled student count of the specified course
   */
  public int getEnrolledStudentCount() {
    return this.enrolledStudentCount;
  }

  /**
   * Returns a string representation of the course, including its instructor name, location
   * and the courses offered.
   *
   * @return A string representation of the course.
   */
  @Override
  public String toString() {
    return "\nInstructor: " + instructorName
           + "; Location: " + courseLocation
           + "; Time: " + courseTimeSlot;
  }

  /**
   * Reassigns an instructor to the course.
   *
   * @param newInstructorName The name of the new instructor.
   */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }

  /**
   * Reassigns a location to the course.
   *
   * @param newLocation The name of new location of the course.
   */
  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }

  /**
   * Reassigns time to the course.
   *
   * @param newTime The new time of the course.
   */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * Sets the number of enrolled student in the course to a specified number.
   *
   * @param count The count of enrolled students number.
   */
  public void setEnrolledStudentCount(int count) {
    this.enrolledStudentCount = count;
  }

  /**
   * Checks if the course is full.
   *
   * @return  True if the count of enrolled students is equal or greater than the capacity.
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
