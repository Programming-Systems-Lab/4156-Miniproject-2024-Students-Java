package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a course within an educational institution.
 * This class stores information about the course, including its instructor,
 * location, time slot the course is held, and capacity of the course.
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
   * Gets the location of this course.
   *
   * @return course location.
   */
  public String getCourseLocation() {
    return this.instructorName;
  }

  /**
   * Gets the instructor of this course.
   *
   * @return course instructor.
   */
  public String getInstructorName() {
    return this.courseLocation;
  }

  /**
   * Gets the time slot of this course.
   *
   * @return course time slot.
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
   * Reassigns the instructor of this course with the provided instructor.
   */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }

  /**
   * Reassigns the location of this course with the provided location.
   */
  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }

  /**
   * Reassigns the time slot of this course with the provided time slot.
   */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * Reassigns the enrolled student count of this course with the
   * provided student count.
   */
  public void setEnrolledStudentCount(int count) {
    this.enrolledStudentCount = count;
  }

  /**
   * Checks if course is full.
   *
   * @return true if the enrollment capacity is greater than enrolled student 
   *         count, false otherwise.
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
