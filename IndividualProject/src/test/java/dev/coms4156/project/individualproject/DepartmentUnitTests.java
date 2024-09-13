package dev.coms4156.project.individualproject;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This test class verifies the functionality of the {@link Department} class
 * by testing its methods.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  @BeforeEach
  public void setupDepartmentForTesting() {
    testCourses = new HashMap<String, Course>();
    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    testCourses.put("1004", coms1004);
    testDep = new Department("COMS", testCourses, "Luca Carloni", defaultMajors);
  }

  @Test
  public void getNumberofMajorsTest() {
    Assertions.assertEquals(defaultMajors, testDep.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    Assertions.assertEquals("Luca Carloni", testDep.getDepartmentChair());
  }

  @Test
  public void getCourseSelectionTest() {
    Assertions.assertEquals(testCourses, testDep.getCourseSelection());
  }

  @Test
  public void addPersonToMajorTest() {
    Assertions.assertEquals(defaultMajors, testDep.getNumberOfMajors());
    testDep.addPersonToMajor();
    Assertions.assertEquals(defaultMajors + 1, testDep.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    Assertions.assertEquals(defaultMajors, testDep.getNumberOfMajors());
    testDep.dropPersonFromMajor();
    Assertions.assertEquals(defaultMajors - 1, testDep.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorWhenEmptyTest() {
    for(int i = 0; i < defaultMajors; i++){
      testDep.dropPersonFromMajor();
    }
    Assertions.assertEquals(0, testDep.getNumberOfMajors());
    testDep.dropPersonFromMajor();
    testDep.dropPersonFromMajor();
    Assertions.assertEquals(0, testDep.getNumberOfMajors());
  }


  @Test
  public void toStringTest() {
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Course> entry : testCourses.entrySet()) {
      String key = entry.getKey();
      Course value = entry.getValue();
      result.append("COMS").append(" ").append(key).append(": ")
      .append(value.toString()).append("\n");
    }
    Assertions.assertEquals(result.toString(), testDep.toString());
  }
  
  @Test
  public void addCourseTest() {
    Course coms3134 = new Course("Brian Borowski", "301 URIS", "4:10-5:25", 250);
    testDep.addCourse("3134", coms3134);
    testCourses.put("3134", coms3134);

    Assertions.assertEquals(testDep.getCourseSelection(), testCourses);
  }

  @Test
  public void createCourseTest() {
    Course coms3134 = new Course("Brian Borowski", "301 URIS", "4:10-5:25", 250);
    testDep.createCourse("3134", "Brian Borowski", "301 URIS", "4:10-5:25", 250);
    testCourses.put("3134", coms3134);

    Assertions.assertEquals(testDep.getCourseSelection(), testCourses);
  }

  /** The test Department instance used for testing. */
  public static HashMap<String, Course> testCourses;
  public static int defaultMajors = 2700;
  public static Department testDep;
}
