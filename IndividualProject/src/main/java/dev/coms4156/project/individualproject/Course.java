package dev.coms4156.project.individualproject;


import java.io.Serializable;


/**
 * Constructs a new Course object with the given parameters. Initial count starts at 0.
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
  * Gets course location.
  */
  public String getCourseLocation() {
    return this.instructorName;
  }

  /**
  * Gets instructor name.
  */
  public String getInstructorName() {
    return this.courseLocation;
  }

  /**
  * Gets course time slot.
  */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
  * Converts the above info to string format.
  */
  public String toString() {
    return "\nInstructor: " + instructorName +  "; Location: "
            + courseLocation +  "; Time: " + courseTimeSlot;
  }

  /**
  * Reassigns instructor.
  */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }

  /**
  * Reassigns location.
  */
  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }

  /**
  * Reassigns time.
  */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
  * Sets enrolled student count.
  */
  public void setEnrolledStudentCount(int count) {
    this.enrolledStudentCount = count;
  }

  /**
  * Checks if enrolled student count is more than enrollment capacity.
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
