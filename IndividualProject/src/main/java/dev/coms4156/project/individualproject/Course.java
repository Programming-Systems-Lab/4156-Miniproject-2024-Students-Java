package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a course within an educational institution.
 * This class stores information about the course, including its instructor,
 * location, timeslot, and the capacity of the class itself.
 */
public class Course implements Serializable {

  /**
   * A unique identifier for serialization, ensuring the class can be correctly
   * deserialized.
   */
  @Serial
  private static final long serialVersionUID = 123456L;

  /**
   * number of students that can enroll.
   */
  private final int enrollCapacity;

  /**
   * number of students currently enrolled.
   */
  private int enrolledCount;

  /**
   * the location of the course (e.g. which classroom).
   */
  private String courseLocation;

  /**
   * which instructor is teaching the class.
   */
  private String instructorName;

  /**
   * when is the class scheduled.
   */
  private String courseTimeSlot;


  /**
   * Constructs a new Course object. Initial count starts at 0.
   *
   * @param instructorName     The name of the instructor teaching the course.
   * @param courseLocation     The location where the course is held.
   * @param timeSlot           The time slot of the course.
   * @param capacity           The max # of students that can enroll in the course.
   */
  public Course(final String instructorName, final String courseLocation,
                final String timeSlot, final int capacity) {
    this.courseLocation = courseLocation;
    this.instructorName = instructorName;
    this.courseTimeSlot = timeSlot;
    this.enrollCapacity = capacity;
    this.enrolledCount = 0;
  }


  /**
   * Enrolls a student in the course if there is space available.
   *
   * @return true if the student is successfully enrolled, false otherwise.
   */
  public boolean enrollStudent() {
    if (isCourseFull()) {
      return false;
    } else {
      enrolledCount++;
      return true;
    }
  }

  /**
   * Drops a student from the course if a student is enrolled.
   *
   * @return true if the student is successfully dropped, false otherwise.
   */
  public boolean dropStudent() {
    if (this.enrolledCount > 0) {
      this.enrolledCount--;
      return true;
    } else {
      return false;
    }
  }


  /**
   * Returns the course location.
   *
   * @return courseLocation
   */
  public String getCourseLocation() {
    return this.courseLocation;
  }

  /**
   * Returns the course instructor.
   *
   * @return instructorName
   */
  public String getInstructorName() {
    return this.instructorName;
  }

  /**
   * Returns the course timeslot.
   *
   * @return courseTimeslot
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * Returns the course capacity.
   *
   * @return enrolledCapacity
   */
  public int getCourseCapacity() {
    return this.enrollCapacity;
  }

  /**
   * Returns the course current enrollment count.
   *
   * @return enrolledCount
   */
  public int getEnrolledCount() {
    return this.enrolledCount;
  }

  /**
   * toString() method represents a Course as a String.
   *
   * <p>@return a String representing the information in a Course.
   */
  @Override
  public String toString() {
    return "\nInstructor: " + this.instructorName +  "; Location: "
      + this.courseLocation +  "; Time: " + this.courseTimeSlot
      + "; Capacity: " + this.enrollCapacity;
  }

  /**
   * sets the course instructor.
   */
  public void reassignInstructor(final String newInstructorName) {
    this.instructorName = newInstructorName;
  }

  /**
   * sets the course location.
   */
  public void reassignLocation(final String newLocation) {
    this.courseLocation = newLocation;
  }


  /**
   * sets the course timeslot.
   */
  public void reassignTime(final String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * sets the enrolled student count.
   */
  public void setEnrolledCount(final int count) {
    this.enrolledCount = count;
  }


  /**
   * checks is the course full or not.
   *
   *  @return boolean representing if course is full or not.
   */
  public boolean isCourseFull() {
    return enrollCapacity <= enrolledCount;
  }


  /**
   * Creates and returns a deep copy of the current Course instance.
   * Useful for debugging and altering conditions in unit tests.
   *
   * @return a deep copy of the current Course instance.
   */
  public Course cloneCourse() {
    return new Course(this.instructorName, this.courseLocation,
      this.courseTimeSlot, this.enrollCapacity);
  }



}
