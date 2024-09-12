package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>This Course class represents a course in a department.</p>
 *
 * <p>It includes the instructor name, course location, time slot,
 * and the capacity for student enrollment.</p>
 *
 * <p>The class allows students to enroll and drop the course, and it can determine
 * if the course is full or not. It also allows reassignment of the instructor,
 * location, and time slot.</p>
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
   * Gets the location of the course.
   *
   * @return the location of the course.
   */
  public String getCourseLocation() {
    return this.instructorName;
  }

  /**
   * Gets the name of the instructor of the course.
   *
   * @return the name of the instructor of the course.
   */
  public String getInstructorName() {
    return this.courseLocation;
  }

  /**
   * Gets the time slot during when the course is hold.
   *
   * @return the time slot during when the course is hold.
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * Gets the string containing the course information.
   *
   * @return the string containing the course information.
   */
  public String toString() {
    return "\nInstructor: " + instructorName
            + "; Location: " + courseLocation
            + "; Time: " + courseTimeSlot;
  }

  /**
   * Changes the name of instructor of the course to another instructor's.
   *
   * @param newInstructorName A {@code String} containing the name of
   *                          the new instructor.
   */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }

  /**
   * Changes the location of the Course to another location.
   *
   * @param newLocation A {@code String} containing the new location.
   */
  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }

  /**
   * Changes the time slot of the course to another time slot.
   *
   * @param newTime A {@code String} containing the new time slot.
   */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * Sets the current number of enrolled student.
   *
   * @param count A {@code int} indicating the current enrolled student number.
   */
  public void setEnrolledStudentCount(int count) {
    this.enrolledStudentCount = count;
  }

  /**
   * Checks if the course is full.
   *
   * @return A {@code boolean}. true if the course is full, otherwise false.
   */
  public boolean isCourseFull() {
    return enrollmentCapacity > enrolledStudentCount;
  }
}
