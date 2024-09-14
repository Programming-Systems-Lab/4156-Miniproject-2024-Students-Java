package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a department within an educational institution.
 * This class stores information about the department, including its code,
 * courses offered, department chair, and number of majors.
 */
public class Department implements Serializable {

  /**
   * A unique identifier for serialization, ensuring the class can be correctly
   * deserialized.
   */
  @Serial
  private static final long serialVersionUID = 234567L;

  /**
   * A map representing all courses that will be in a department.
   */
  private Map<String, Course> courses;

  /**
   * The chair of the department.
   */
  private String departmentChair;

  /**
   * A string code representing the department.
   */
  private final String deptCode;

  /**
   * Number of students (majors) in the department.
   */
  private int numberOfMajors;

  /**
   * Constructs a new Department object with the given parameters.
   *
   * @param deptCode         The code of the department.
   * @param courses          A Map containing courses offered by the department.
   * @param departmentChair  The name of the department chair.
   * @param numberOfMajors   The number of students in the department.
   */
  public Department(final String deptCode,
                    final Map<String, Course> courses,
                    final String departmentChair,
                    final int numberOfMajors) {
    this.courses = courses;
    this.departmentChair = departmentChair;
    this.numberOfMajors = numberOfMajors;
    this.deptCode = deptCode;
  }

  /**
   * Gets the number of majors in the department.
   *
   * @return The number of majors.
   */
  public int getNumberOfMajors() {
    return this.numberOfMajors;
  }

  /**
   * Gets the name of the department chair.
   *
   * @return The name of the department chair.
   */
  public String getDepartmentChair() {
    return this.departmentChair;
  }

  /**
   * Gets the courses offered by the department.
   *
   * @return A Map containing courses offered by the department.
   */
  public Map<String, Course> getCourseSelection() {
    return this.courses;
  }

  /**
   * Sets the department course list to the mapping variable.
   */
  public void setCourseSelection(final Map<String, Course> mapping) {
    this.courses = mapping;
  }

  /**
   * Increases the number of majors in the department by one.
   */
  public void addPersonToMajor() {
    this.numberOfMajors++;
  }

  /**
   * Decreases the number of majors in the department by one if it's
   * greater than zero.
   *
   * @return true if a person was successfully dropped, false otherwise.
   */
  public boolean dropPersonFromMajor() {
    if (this.numberOfMajors > 0) {
      numberOfMajors--;
      return true;
    }
    return false;
  }

  /**
   * Adds a new course to the department's course selection.
   *
   * @param courseId The ID of the course to add.
   * @param course   The Course object to add.
   */
  public void addCourse(final String courseId, final Course course) {
    courses.put(courseId, course);
  }

  /**
   * Sets the number of students (majors) in the department.
   */
  public void setNumberOfStudentsInDepartment(final int count) {
    this.numberOfMajors = count;
  }

  /**
   * Sets the chair of the department.
   */
  public void setDepartmentChair(final String deptChair) {
    this.departmentChair = deptChair;
  }

  /**
   * Creates and adds a new course to the department's course selection.
   *
   * @param courseId           The ID of the new course.
   * @param instructorName     The name of the instructor teaching the course.
   * @param courseLocation     The location where the course is held.
   * @param courseTimeSlot     The time slot of the course.
   * @param capacity           The max number of students that can enroll in the course.
   */
  public void createCourse(final String courseId, final String instructorName,
                           final String courseLocation,
                           final String courseTimeSlot,
                           final int capacity) {
    final Course newCourse = new Course(instructorName, courseLocation, courseTimeSlot, capacity);
    addCourse(courseId, newCourse);
  }

  /**
   * Returns a string representation of the department, including
   * its code and the courses offered.
   *
   * @return A string representing the department.
   */
  @Override
  public String toString() {
    final StringBuilder result = new StringBuilder();
    for (final Map.Entry<String, Course> entry : courses.entrySet()) {
      final String key = entry.getKey();
      final Course value = entry.getValue();
      result.append(deptCode).append(' ').append(key).append(": ").append(value.toString())
        .append('\n');
    }
    return result.toString();
  }

  /**
   * Returns a deep copy of the current department for debugging purposes.
   * This allows for manipulation in specific unit tests in the {@code Department}
   * class.
   *
   * @return a deep copy of the current {@code Department} instance.
   */
  public Department cloneDepartment() {
    final Map<String, Course> clonedCourses = new HashMap<>();
    for (final Map.Entry<String, Course> course : courses.entrySet()) {
      final Course courseValue = course.getValue();
      clonedCourses.put(course.getKey(), courseValue.cloneCourse());
    }
    return new Department(this.deptCode, clonedCourses, this.departmentChair, this.numberOfMajors);
  }
}