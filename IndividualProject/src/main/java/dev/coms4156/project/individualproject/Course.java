package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a course with details such as the instructor, location, time slot, and enrollment
 * capacity.
 *
 * <p>This class provides methods to manage course enrollment, drop students, and update course
 * details. It also includes functionality to check if the course is full and provides a string
 * representation of the course.
 */
public class Course implements Serializable {

  @Serial private static final long serialVersionUID = 123456L;

  // max number of students that can enroll in a course, must be positive int
  private final int enrollmentCapacity;

  // current number of students enrolled in a course, must be positive int
  private int enrolledStudentCount;

  // Typically includes room number followed by building code i.e. 405 SCH
  private String courseLocation;

  // should be full name (and middle initial if applicable) of instructor
  private String instructorName;

  // Course timeslot formats (string): 'H:MM-H:MM', 'H:MM-HH:MM', 'HH:MM-H:MM', or 'HH:MM-HH:MM'
  private String courseTimeSlot;

  /**
   * Constructs a new Course object with the given parameters. Initial count starts at 0.
   *
   * @param instructorName The name of the instructor teaching the course.
   * @param courseLocation The location where the course is held.
   * @param timeSlot The time slot of the course.
   * @param capacity The maximum number of students that can enroll in the course.
   */
  public Course(String instructorName, String courseLocation, String timeSlot, int capacity) {
    // check for null/empty-string instructor name
    if (instructorName == null || instructorName.trim().isEmpty()) {
      throw new IllegalArgumentException("Instructor name cannot be null or empty.");
    }

    // check for null/empty-string course location
    if (courseLocation == null || courseLocation.trim().isEmpty()) {
      throw new IllegalArgumentException("Course location cannot be null or empty.");
    }

    // ensure valid time slot based on format described in IllegalArgumentException()
    if (!isValidTimeSlot(timeSlot)) {
      throw new IllegalArgumentException(
          "Invalid time format. Expected format: 'H:MM-H:MM', 'H:MM-HH:MM', 'HH:MM-H:MM', or "
              + "'HH:MM-HH:MM'.");
    }

    // ensure non-negative capacity
    if (capacity <= 0) {
      throw new IllegalArgumentException("Capacity must be a positive number.");
    }

    this.courseLocation = courseLocation;
    this.courseTimeSlot = timeSlot;
    this.enrollmentCapacity = capacity;
    this.enrolledStudentCount = 0;
    this.instructorName = instructorName;
  }

  /**
   * Validates the time slot format. Allowed time formats: 'H:MM-H:MM', 'H:MM-HH:MM', 'HH:MM-H:MM',
   * 'HH:MM-HH:MM'. Also checks to ensure valid hours (00-23) and minutes (00-59).
   *
   * @param timeSlot the time slot string to validate.
   * @return true if the time slot is valid, false otherwise.
   */
  public static boolean isValidTimeSlot(String timeSlot) {
    if (timeSlot == null || timeSlot.trim().isEmpty()) {
      return false;
    }

    // Allowed time formats: 'H:MM-H:MM', 'H:MM-HH:MM', 'HH:MM-H:MM', 'HH:MM-HH:MM'
    // Also checks to ensure valid hours (00-23) and minutes (00-59)
    String timePattern = "^([01]?\\d|2[0-3]):[0-5]\\d-([01]?\\d|2[0-3]):[0-5]\\d$";
    return timeSlot.matches(timePattern);
  }

  /**
   * Enrolls a student in the course if there is space available.
   *
   * @return true if the student is successfully enrolled, false otherwise.
   */
  public boolean enrollStudent() {
    if (!isCourseFull()) {
      this.enrolledStudentCount++;
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
    if (this.enrolledStudentCount > 0) {
      enrolledStudentCount--;
      return true;
    }

    return false;
  }

  /**
   * Get the location for a course.
   *
   * @return course location.
   */
  public String getCourseLocation() {
    return this.courseLocation;
  }

  /**
   * Get the time slot for a course.
   *
   * @return time slot (string).
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * Get the max number of students that can enroll in a course.
   *
   * @return enrollment capacity (int).
   */
  public int getEnrollmentCapacity() {
    return this.enrollmentCapacity;
  }

  /**
   * Get the number of students currently enrolled in a course.
   *
   * @return enrolled student count (int).
   */
  public int getEnrolledStudentCount() {
    return this.enrolledStudentCount;
  }

  /** Set the number of students to enroll in a course. */
  public void setEnrolledStudentCount(int count) {
    if (count < 0) {
      throw new IllegalArgumentException("Enrolled student count cannot be non-negative.");
    }
    this.enrolledStudentCount = count;
  }

  /**
   * Get the instructor's name for a course.
   *
   * @return instructor name.
   */
  public String getInstructorName() {
    return this.instructorName;
  }

  /**
   * Returns a string representation of the course details, including the instructor's name,
   * location, and time slot.
   *
   * @return A formatted string containing the course details.
   */
  public String toString() {
    return "\nInstructor: "
        + instructorName
        + "; Location: "
        + courseLocation
        + "; Time: "
        + courseTimeSlot;
  }

  /**
   * Sets a new instructor for a course. Cannot be null or empty string.
   *
   * @param newInstructorName the name of the new instructor to assign to the course.
   * @throws IllegalArgumentException if {@code newInstructorName} is null or an empty string.
   */
  public void reassignInstructor(String newInstructorName) {
    if (newInstructorName == null || newInstructorName.trim().isEmpty()) {
      throw new IllegalArgumentException("Instructor name cannot be null or empty.");
    }
    this.instructorName = newInstructorName;
  }

  /**
   * Sets a new location for a course. Cannot be null or empty string.
   *
   * @param newLocation the name of the new location to assign to the course.
   * @throws IllegalArgumentException if {@code newLocation} is null or an empty string.
   */
  public void reassignLocation(String newLocation) {
    if (newLocation == null || newLocation.trim().isEmpty()) {
      throw new IllegalArgumentException("Location cannot be null or empty.");
    }
    this.courseLocation = newLocation;
  }

  /**
   * Sets a new time for a course. Must match the expected format below.
   *
   * @param newTime the new time (string).
   * @throws IllegalArgumentException if {@code newTime} does not match the expected format.
   */
  public void reassignTime(String newTime) {
    // Ensure valid timeSlot
    if (!isValidTimeSlot(newTime)) {
      throw new IllegalArgumentException(
          "Invalid time format.  "
              + "Expected format: 'H:MM-H:MM', 'H:MM-HH:MM', 'HH:MM-H:MM', or 'HH:MM-HH:MM'.  "
              + "Also ensure valid hours (00-23) and minutes (00-59).");
    }

    this.courseTimeSlot = newTime;
  }

  /**
   * Returns boolean to determine if a course is full or not.
   *
   * @return true if full; else false.
   */
  public boolean isCourseFull() {
    return enrolledStudentCount >= enrollmentCapacity;
  }
}
