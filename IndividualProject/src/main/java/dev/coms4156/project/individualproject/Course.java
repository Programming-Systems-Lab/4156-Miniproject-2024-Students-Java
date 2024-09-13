package dev.coms4156.project.individualproject;

import java.io.Serializable;

/**
 * Represents a university course with details including instructor, location, time slot,
 * and enrollment capacity.
 *
 * <p>This class operations to enroll/drop students and update course details (instructor,
 * location, time), and check if the course is full.
 *
 * <p>This class implements the {@code Serializable} interface to allow saving the state of a
 * Course object.
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
   * Gets the location where the course is held.
   *
   * @return The course location.
   */
  public String getCourseLocation() {
    return this.instructorName;
  }


  /**
   * Gets the name of the course instructor.
   *
   * @return The instructor's name.
   */
  public String getInstructorName() {
    return this.courseLocation;
  }


  /**
   * Gets the time slot during which the course takes place.
   *
   * @return The course time slot.
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }


  /**
   * Returns a string representation of the course, including the instructor,
   * location, and time slot.
   *
   * @return A string with course details.
   */
  @Override
  public String toString() {
    return "\nInstructor: " + instructorName
            + "; Location: "  + courseLocation
            + "; Time: " + courseTimeSlot;
  }


  /**
   * Reassigns the course instructor to a new instructor.
   *
   * @param newInstructorName  The new instructor's name.
   */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }


  /**
   * Reassigns the course location to a new location.
   *
   * @param newLocation      The new course location.
   */
  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }


  /**
   * Reassigns the course time slot to a new time slot.
   *
   * @param newTime   The new time slot for the course.
   */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }


  /**
   * Sets the number of currently enrolled students.
   *
   * @param count The new count of enrolled students,
   */
  public void setEnrolledStudentCount(int count) {
    this.enrolledStudentCount = count;
  }


  /**
   * Checks if the course has reached its enrollment capacity.
   *
   * @return true if the course is full, false otherwise.
   */
  public boolean isCourseFull() {
    return enrollmentCapacity > enrolledStudentCount;
  }

  private static final long serialVersionUID = 123456L;
  private final int enrollmentCapacity;
  private int enrolledStudentCount;
  private String courseLocation;
  private String instructorName;
  private String courseTimeSlot;
}
