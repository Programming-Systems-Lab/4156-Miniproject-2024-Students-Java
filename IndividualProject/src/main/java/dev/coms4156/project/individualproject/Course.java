package dev.coms4156.project.individualproject;

// import java.io.*;
import java.io.Serializable;

/**
 * Represents a course with an instructor teaching the course, 
 * location held, time slot, max capacity, and enrolled students.
 * This class stores information about the course,
 * provides methods to enroll students, drop students, 
 * reassign instructors, reassign locations, and reassign time slots.
 */
public class Course implements Serializable {

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
    if (this.enrolledStudentCount >= this.enrollmentCapacity) {
      return false;
    } else {
      this.enrolledStudentCount++;
      return true;
    }
  }

  /**
   * Drops a student from the course if a student is enrolled.
   *
   * @return true if the student is successfully dropped, false otherwise.
   */
  public boolean dropStudent() {
    if (this.enrolledStudentCount == 0) {
      return false;
    } else {
      this.enrolledStudentCount--;
      return true;
    }
  }

  /**
   * Get a courses location.
   *
   * @return courseLocation of the course.
   */
  public String getCourseLocation() {
    // return this.instructorName;
    return this.courseLocation;
  }

  /**
   * Get a course's instructure name.
   *
   * @return instructorName of the course.
   */
  public String getInstructorName() {
    // return this.courseLocation;
    return this.instructorName;
  }

  /**
   * Get a course's time slot.
   *
   * @return courseTimeSlot of the course.
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * Get a course's information in strings.
   *
   * @return strings of instructor, location, and time slot of the course.
   */
  public String toString() {
    return "\nInstructor: " + this.instructorName 
      +  "; Location: "  + this.courseLocation 
      +  "; Time: " + this.courseTimeSlot;
  }

  /** 
   * Get a course's enrolledStudentCount. added
   *
   * @return enrolledStudentCount of the course.
   */
  public int getEnrolledStudentCount() {
    return this.enrolledStudentCount;
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
    return this.enrolledStudentCount >= this.enrollmentCapacity;
  }

  // @Serial
  // private static final long serialVersionUID = 123456L;
  // private final int enrollmentCapacity;
  // private int enrolledStudentCount;
  // private String courseLocation;
  // private String instructorName;
  // private String courseTimeSlot;
}
