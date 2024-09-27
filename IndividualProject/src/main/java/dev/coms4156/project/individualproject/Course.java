package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Course class represents a course in a department.
 * This class contains attributes and methods related to managing
 * a course, including course code, location, and other relevant details.
 * It implements Serializable to allow the course objects to be saved and
 * retrieved from storage.
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
    if (enrolledStudentCount < enrollmentCapacity) {
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

  public String getCourseLocation() {
    return this.courseLocation;
  }


  public String getInstructorName() {
    return this.instructorName;
  }


  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  @Override
  public String toString() {
    return "\nInstructor: " + instructorName +  "; Location: " 
      + courseLocation +  "; Time: " + courseTimeSlot;
  }


  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }


  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }


  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * Sets the enrolled student count for the course. 
   * 
   *
   * @param count an {@code int} representing the new enrolled student count for 
   *              the course. The value must be greater than or equal to 0.
   * 
   * @return {@code true} if the enrolled student count was successfully updated, 
   *         {@code false} if the provided count was negative and the update 
   *         was not performed.
   */
  public boolean setEnrolledStudentCount(int count) {
    if (count >= 0) {
      this.enrolledStudentCount = count;
      return true;
    }
    return false;
  }

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
