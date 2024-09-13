package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Writes test cases to check methods in the MyFileDatabase.
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseUnitTests {

  /**
   * Sets temporary path.
   */
  @TempDir
  Path tempDir;

  /**
   * Sets up a testFilePath before each test.
   */
  @BeforeEach
  public void setupMyFileDatabaseForTesting() {
    testFilePath = tempDir.resolve("testData.txt").toString();
  }

  /**
   * Tests MyFileDatabase() constructor when flag is 0.
   *
   */
  @Test
  public void constructorTestFlagZero() throws IOException {
    Course coms1004 = new Course("Adam Cannon", "417 IAB",
                                 "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    Map<String, Department> departmentMap = new HashMap<>();
    Department coms = new Department("COMS", courses,
                                     "Luca Carloni", 2700);
    departmentMap.put("COMS", coms);

    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testFilePath))) {
      out.writeObject(departmentMap);
    }

    testDatabase = new MyFileDatabase(0, testFilePath);

    assertEquals(testFilePath, testDatabase.getFilePath());
    assertEquals(departmentMap.size(), testDatabase.getDepartmentMapping().size());

    Course actualCourse = testDatabase.getDepartmentMapping().get("COMS")
                          .getCourseSelection().get("1004");
    assertTrue(testDatabase.getDepartmentMapping().get("COMS")
                          .getCourseSelection().containsKey("1004"));
    assertEquals(courses.size(), testDatabase.getDepartmentMapping().get("COMS")
                          .getCourseSelection().size());
    assertNotNull(actualCourse);
    assertEquals(coms1004.getCourseLocation(), actualCourse.getCourseLocation());
    assertEquals(coms1004.getInstructorName(), actualCourse.getInstructorName());
    assertEquals(coms1004.getCourseTimeSlot(), actualCourse.getCourseTimeSlot());
    assertEquals(coms1004.getEnrolledStudentCount(), actualCourse.getEnrolledStudentCount());
  }

  /**
   * Tests MyFileDatabase() constructor when flag is non-zero.
   */
  @Test
  public void constructorTestFlagNonZero() {
    testDatabase = new MyFileDatabase(1, testFilePath);
    assertEquals(testFilePath, testDatabase.getFilePath());
    assertEquals(0, testDatabase.getDepartmentMapping().size());
  }

  /**
   * Tests deSerializeObjectFromFile() method.
   */
  @Test
  public void deSerializeObjectFromFileTest() {
    testDatabase = new MyFileDatabase(1, testFilePath);

    Course coms1004 = new Course("Adam Cannon", "417 IAB",
                                 "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    Map<String, Department> departmentMap = new HashMap<>();
    Department coms = new Department("COMS", courses,
                                     "Luca Carloni", 2700);
    departmentMap.put("COMS", coms);

    testDatabase.setMapping(departmentMap);
    testDatabase.saveContentsToFile();

    testDatabase = new MyFileDatabase(0, testFilePath);

    assertEquals(departmentMap.size(), testDatabase.getDepartmentMapping().size());

    Course actualCourse = testDatabase.getDepartmentMapping().get("COMS")
                          .getCourseSelection().get("1004");
    assertTrue(testDatabase.getDepartmentMapping().get("COMS")
                          .getCourseSelection().containsKey("1004"));
    assertEquals(courses.size(), testDatabase.getDepartmentMapping().get("COMS")
                          .getCourseSelection().size());
    assertNotNull(actualCourse);
    assertEquals(coms1004.getCourseLocation(), actualCourse.getCourseLocation());
    assertEquals(coms1004.getInstructorName(), actualCourse.getInstructorName());
    assertEquals(coms1004.getCourseTimeSlot(), actualCourse.getCourseTimeSlot());
    assertEquals(coms1004.getEnrolledStudentCount(), actualCourse.getEnrolledStudentCount());
  }

  /**
   * Tests deSerializeObjectFromFile() method when the first entry of Map is wrong.
   **/
  @Test
  public void deSerializeObjectFromFileTestInvalidFirstEntry() throws IOException {
    Course coms1004 = new Course("Adam Cannon", "417 IAB",
                                 "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    Map<Integer, Department> invalidDepartmentMap = new HashMap<>();
    Department compSci = new Department("COMS", courses,
                                        "Luca Carloni", 2700);
    invalidDepartmentMap.put(249, compSci);

    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testFilePath))) {
      out.writeObject(invalidDepartmentMap);
    }
    assertThrows(IllegalArgumentException.class,
                () -> testDatabase = new MyFileDatabase(0, testFilePath));
  }

  /**
   * Tests deSerializeObjectFromFile() method when the second entry of Map is wrong.
   * */
  @Test
  public void deSerializeObjectFromFileTestInvalidSecondEntry() throws IOException {
    Course coms1004 = new Course("Adam Cannon", "417 IAB",
                                 "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    Map<String, Integer> invalidDepartmentMap = new HashMap<>();
    //Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    invalidDepartmentMap.put("COMS", 249);

    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testFilePath))) {
      out.writeObject(invalidDepartmentMap);
    }
    assertThrows(IllegalArgumentException.class,
                () -> testDatabase = new MyFileDatabase(0, testFilePath));
  }

  /**
   * Tests deSerializeObjectFromFile() method when the object type is invalid.
   **/
  @Test
  public void deSerializeObjectFromFileTestInvalidObjectType() throws IOException {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testFilePath))) {
      out.writeObject("This is not an instance of HashMap<?, ?>");
    }
    assertThrows(IllegalArgumentException.class,
                () -> testDatabase = new MyFileDatabase(0, testFilePath));
  }

  /**
   * Tests deSerializeObjectFromFile() method when file path is invalid.
   */
  @Test
  public void deSerializeObjectFromFileTestInvalidPath() {
    testFilePath = "invalid/path/to/testData.txt";
    testDatabase = new MyFileDatabase(0, testFilePath);

    assertTrue(testDatabase.getDepartmentMapping().isEmpty());
  }

  /**
   * Tests saveContentsToFile() method.
   */
  @Test
  public void saveContentsToFileTest() {
    testDatabase = new MyFileDatabase(1, testFilePath);

    Course coms1004 = new Course("Adam Cannon", "417 IAB",
                                 "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    Map<String, Department> departmentMap = new HashMap<>();
    Department compSci = new Department("COMS", courses,
                                        "Luca Carloni", 2700);
    departmentMap.put("COMS", compSci);

    testDatabase.setMapping(departmentMap);
    testDatabase.saveContentsToFile();

    File actualFile = new File(testFilePath);
    assertTrue(actualFile.exists());

    testDatabase = new MyFileDatabase(0, testFilePath);

    assertEquals(departmentMap.size(), testDatabase.getDepartmentMapping().size());

    Course actualCourse = testDatabase.getDepartmentMapping().get("COMS")
        .getCourseSelection().get("1004");
    assertTrue(testDatabase.getDepartmentMapping().get("COMS")
        .getCourseSelection().containsKey("1004"));
    assertEquals(courses.size(), testDatabase.getDepartmentMapping().get("COMS")
        .getCourseSelection().size());
    assertNotNull(actualCourse);
    assertEquals(coms1004.getCourseLocation(), actualCourse.getCourseLocation());
    assertEquals(coms1004.getInstructorName(), actualCourse.getInstructorName());
    assertEquals(coms1004.getCourseTimeSlot(), actualCourse.getCourseTimeSlot());
    assertEquals(coms1004.getEnrolledStudentCount(), actualCourse.getEnrolledStudentCount());
  }

  /**
   * Tests saveContentsToFile() method when file path is invalid.
   */
  @Test
  public void saveContentsToFileTestInvalidPath() {
    testFilePath = "invalid/path/to/testData.txt";

    testDatabase = new MyFileDatabase(1, testFilePath);
    assertDoesNotThrow(() -> testDatabase.saveContentsToFile());
  }

  /**
   * Tests setMapping() method and getDepartmentMapping() method together.
   */
  @Test
  public void setAndGetDepartmentMappingTest() {
    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    Map<String, Department> departmentMap = new HashMap<>();
    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    departmentMap.put("COMS", compSci);

    testDatabase = new MyFileDatabase(0, testFilePath);
    testDatabase.setMapping(departmentMap);
    assertEquals(departmentMap.size(), testDatabase.getDepartmentMapping().size());
    assertTrue(testDatabase.getDepartmentMapping().containsKey("COMS"));

    Course actualCourse = testDatabase.getDepartmentMapping().get("COMS")
        .getCourseSelection().get("1004");
    assertTrue(testDatabase.getDepartmentMapping().get("COMS")
        .getCourseSelection().containsKey("1004"));
    assertEquals(courses.size(), testDatabase.getDepartmentMapping().get("COMS")
        .getCourseSelection().size());
    assertEquals(coms1004.getCourseLocation(), actualCourse.getCourseLocation());
    assertEquals(coms1004.getInstructorName(), actualCourse.getInstructorName());
    assertEquals(coms1004.getCourseTimeSlot(), actualCourse.getCourseTimeSlot());
    assertEquals(coms1004.getEnrolledStudentCount(), actualCourse.getEnrolledStudentCount());
  }

  /**
   * Tests getFilePath() method.
   */
  @Test
  public void getFilePathTest() {
    testDatabase = new MyFileDatabase(1, testFilePath);
    assertEquals(testFilePath, testDatabase.getFilePath());
  }

  /**
   * Tests toString() method.
   */
  @Test
  public void toStringTest() {
    testDatabase = new MyFileDatabase(1, testFilePath);

    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    Map<String, Department> departmentMap = new HashMap<>();
    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    departmentMap.put("COMS", compSci);

    testDatabase.setMapping(departmentMap);

    String expectedResult = "For the COMS department: \nCOMS 1004: \nInstructor: "
        + "Adam Cannon; Location: 417 IAB; Time: 11:40-12:55\n";

    assertEquals(expectedResult, testDatabase.toString());
  }

  /** The MyFileDatabase instance used for testing. */
  private MyFileDatabase testDatabase;

  /** The test file path used for testing. */
  private String testFilePath;
}
