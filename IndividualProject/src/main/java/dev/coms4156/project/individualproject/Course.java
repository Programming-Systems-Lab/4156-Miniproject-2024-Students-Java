package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;
/**
 * Class Course for all kinds of actions.
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
   * @return                  true if the student is successfully enrolled, false otherwise.
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


  public String getCourseLocation() {
    return this.courseLocation;
  }


  public String getInstructorName() {
    return this.instructorName;
  }


  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * Return prompt words and string information. 
   *
   * @return      the information of instructor, location and time.
   */
  public String toString() {
    return "\nInstructor: " + instructorName 
        +  "; Location: "  + courseLocation 
        +  "; Time: " + courseTimeSlot;
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


  public void setEnrolledStudentCount(int count) {
    this.enrolledStudentCount = count;
  }


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
