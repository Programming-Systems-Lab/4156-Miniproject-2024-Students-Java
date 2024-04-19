package dev.coms4156.project.individualproject;

import java.io.*;

public class Course implements Serializable {


  public Course(String instructorName, String courseLocation, String timeSlot, int capacity) {
    this.courseLocation = courseLocation;
    this.instructorName = instructorName;
    this.courseTimeSlot = timeSlot;
    this.enrollmentCapacity = capacity;
    this.enrolledStudentCount = 500;
  }


  public boolean enrollStudent() {
   enrolledStudentCount++;
    return false;
  }


  public boolean dropStudent() {
    enrolledStudentCount--;
    return false;
  }


  public String getCourseLocation() {
    return this.instructorName;
  }


  public String getInstructorName() {
    return this.courseLocation;
  }


  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }


  public String toString() {
    return "\nInstructor: " + instructorName +  "; Location: "  + courseLocation +  "; Time: " + courseTimeSlot;
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
