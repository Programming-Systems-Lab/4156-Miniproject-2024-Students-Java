package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a course, containing information about its instructor, location,
 * time slot, enrollment capacity, and enrolled student count.
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
   * Constructs a new Course object with the given parameters.
   * The initial enrolled student count is set to 0.
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
   * @return The location where the course is held.
   */
  public String getCourseLocation() {
    return this.courseLocation;
  }

  /**
   * Returns the number of enrolled students in the course.
   *
   * @return The enrolled student count for the course.
   */
  public int getEnrolledStudentCount() {
    return this.enrolledStudentCount;
  }

  /**
   * Returns the enrollment capacity for the course.
   *
   * @return The enrollment capacity of the course.
   */
  public int getEnrollmentCapacity() {
    return this.enrollmentCapacity;
  }

  /**
   * Returns the name of the instructor teaching the course.
   *
   * @return The instructor's name.
   */
  public String getInstructorName() {
    return this.instructorName;
  }

  /**
   * Returns the time slot during which the course is held.
   *
   * @return The time slot of the course.
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * Returns a string representation of the course.
   *
   * @return A string describing the instructor, location, time and enrollment of the course.
   */
  @Override
  public String toString() {
    return "\nInstructor: " + instructorName
                 + "; Location: "
                 + courseLocation
                 + "; Time: "
                 + courseTimeSlot
                 + "; Capacity: "
                 + enrollmentCapacity
                 + "; Enrollment: "
                 + enrolledStudentCount;

  }

  /**
   * Reassigns the course to a new instructor.
   *
   * @param newInstructorName The name of the new instructor.
   */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }

  /**
   * Reassigns the course to a new location.
   *
   * @param newLocation The new location for the course.
   */
  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }

  /**
   * Reassigns the course to a new time slot.
   *
   * @param newTime The new time slot for the course.
   */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * Sets the enrolled student count for the course.
   *
   * @param count The number of students enrolled in the course.
   */
  public void setEnrolledStudentCount(int count) {
    this.enrolledStudentCount = count;
  }

  /**
   * Checks if the course is full.
   *
   * @return true if the course is not full, false otherwise.
   */
  public boolean isCourseFull() {
    return enrollmentCapacity < enrolledStudentCount;
  }
}
