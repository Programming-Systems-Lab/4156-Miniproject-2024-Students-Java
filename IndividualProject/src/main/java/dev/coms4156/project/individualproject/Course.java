package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a course in the system with details such as enrollment capacity,
 * current enrollment count, location, instructor, and time slot.
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
    if (enrollmentCapacity < 0) {
      throw new IllegalArgumentException("Course capacity cannot be less than 0");
    }
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

  public String toString() {
    return "\nInstructor: " + instructorName + "; Location: " + courseLocation + "; Time: "
        + courseTimeSlot;
  }

  public int getEnrolledStudentCount() {
    return this.enrolledStudentCount;
  }

  public void setEnrolledStudentCount(int count) {
    this.enrolledStudentCount = count;
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

  public boolean isCourseFull() {
    return enrollmentCapacity <= enrolledStudentCount;
  }

  /**
   * Returns the number of available seats left in the course.
   *
   * @return The number of available seats.
   */
  public int getAvailableSeats() {
    return enrollmentCapacity - enrolledStudentCount;
  }

}



