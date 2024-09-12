package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/** 
 * This test class verifies the functionality of the {@link Course} class by testing its methods.
 * It uses JUnit for testing and verifies the expected behavior of the course object.
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseTests {

  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", defaultStudentCap);
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
  public static int defaultStudentCap = 10;
}



  

