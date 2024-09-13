package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a course within an educational institution.
 * This class stores information about the course, including 
 * the name of its instructor, the location of the course,
 * the time slot of the course, and the capacity of the course
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
    this.enrolledStudentCount = 0;
    if (capacity < 0) {
      this.enrollmentCapacity = 0;
    } else {
      this.enrollmentCapacity = capacity;
    }
  }

  /**
   * Enrolls a student in the course if there is space available.
   *
   * @return true if the student is successfully enrolled, false otherwise.
   */
  public boolean enrollStudent() {
    if (enrollmentCapacity > enrolledStudentCount) {
      enrolledStudentCount++;
      return true;
    } else {
      return false;
    }
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
    } else {
      return false;
    }
  }

  public int getEnrolledStudentCount() {
    return this.enrolledStudentCount;
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

  public int getCapacity() {
    return this.enrollmentCapacity;
  }


  /**
   * Returns a string representation of the course. 
   * including the instructor's name, the location of the course, and the time slot.
   *
   * @return A string representation of the course.
   */
  @Override
  public String toString() {
    return "\nInstructor: " 
        + instructorName 
        +  "; Location: "  
        + courseLocation 
        +  "; Time: " 
        + courseTimeSlot;
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
   * Sets the number of students enrolled in the course. The argument(count) must be
   * a non-negative number and should not exceed the course's enrollment capacity.
   * If the provided count is invalid, an {@link IllegalArgumentException} is thrown.
   *
   * @param count the number of students to be set as enrolled in the course.
   * @throws IllegalArgumentException if the count is negative or exceeds the enrollment capacity.
   */
  public void setEnrolledStudentCount(int count) {
    if (count >= 0 && count <= enrollmentCapacity) {
      this.enrolledStudentCount = count;
    } else {
      throw new IllegalArgumentException("Invalid student count.");
    }
  }


  public boolean isCourseFull() {
    return enrollmentCapacity <= enrolledStudentCount;
  }

  @Serial
  private static final long serialVersionUID = 123456L;
  private final int enrollmentCapacity;
  private int enrolledStudentCount;
  private String courseLocation;
  private String instructorName;
  private String courseTimeSlot;
}
