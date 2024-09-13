package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Writes test case to check methods in the Department.
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseUnitTests {

  @TempDir
  Path tempDir;

  @BeforeEach
  public void setupMyFileDatabaseForTesting() {
    testFilePath = tempDir.resolve("testData.txt").toString();
  }


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

    String expectedResult = "For the COMS department: \nCOMS 1004: \nInstructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55\n";

    assertEquals(expectedResult, testDatabase.toString());
  }


  @Test
  public void constructorTestFlagZero() {
    testDatabase = new MyFileDatabase(0, testFilePath);
    assertEquals(testFilePath, testDatabase.getFilePath());
    assertNotNull(testDatabase.getDepartmentMapping());
  }


  @Test
  public void constructorTestFlagNonZero() {
    testDatabase = new MyFileDatabase(1, testFilePath);
    assertEquals(testFilePath, testDatabase.getFilePath());
    assertNotNull(testDatabase.getDepartmentMapping());
  }

  @Test
  public void deSerializeObjectFromFileTest() {
    testDatabase = new MyFileDatabase(1, testFilePath);

    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    Map<String, Department> departmentMap = new HashMap<>();
    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    departmentMap.put("COMS", compSci);

    testDatabase.setMapping(departmentMap);
    testDatabase.saveContentsToFile();

    testDatabase = new MyFileDatabase(0, testFilePath);

    Department actualDepartment = testDatabase.getDepartmentMapping().get("COMS");
    Course actualCourse = actualDepartment.getCourseSelection().get("1004");

    assertNotNull(testDatabase.getDepartmentMapping());
    assertEquals(departmentMap.size(), testDatabase.getDepartmentMapping().size());
    assertTrue(testDatabase.getDepartmentMapping().containsKey("COMS"));
    assertNotNull(actualCourse);
    assertTrue(actualDepartment.getCourseSelection().containsKey("1004"));
    assertEquals(coms1004.getCourseLocation(), actualCourse.getCourseLocation());
    assertEquals(coms1004.getInstructorName(), actualCourse.getInstructorName());
    assertEquals(coms1004.getCourseTimeSlot(), actualCourse.getCourseTimeSlot());
    assertEquals(coms1004.getEnrolledStudentCount(), actualCourse.getEnrolledStudentCount());
  }


  @Test
  public void deSerializeObjectFromFileInvalidEntryTest() throws IOException {
    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    Map<Integer, Department> invalidDepartmentMap = new HashMap<>();
    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    invalidDepartmentMap.put(249, compSci);

    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testFilePath))) {
      out.writeObject(invalidDepartmentMap);
    }
    assertThrows(IllegalArgumentException.class, () -> testDatabase = new MyFileDatabase(0, testFilePath));
  }


  @Test
  public void doSerializeObjectToFileInvalidObjectTypeTest() throws IOException {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testFilePath))) {
      out.writeObject("This is not an instance of HashMap<?, ?>");
    }
    assertThrows(IllegalArgumentException.class, () -> testDatabase = new MyFileDatabase(0, testFilePath));
  }


  @Test
  public void deSerializeObjectFromFileInvalidDataTest() {
    testFilePath = "invalid/path/to/testData.txt";
    testDatabase = new MyFileDatabase(0, testFilePath);

    assertTrue(testDatabase.getDepartmentMapping().isEmpty());
  }


  @Test
  public void saveContentsToFileTest() {
    testDatabase = new MyFileDatabase(1, testFilePath);

    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    Map<String, Department> departmentMap = new HashMap<>();
    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    departmentMap.put("COMS", compSci);

    testDatabase.setMapping(departmentMap);
    testDatabase.saveContentsToFile();

    File actualFile = new File(testFilePath);
    assertTrue(actualFile.exists());
  }


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

    Department actualDepartment = testDatabase.getDepartmentMapping().get("COMS");
    Course actualCourse = actualDepartment.getCourseSelection().get("1004");

    assertNotNull(testDatabase.getDepartmentMapping());
    assertEquals(departmentMap.size(), testDatabase.getDepartmentMapping().size());
    assertTrue(testDatabase.getDepartmentMapping().containsKey("COMS"));
    assertNotNull(actualCourse);
    assertTrue(actualDepartment.getCourseSelection().containsKey("1004"));
    assertEquals(coms1004.getCourseLocation(), actualCourse.getCourseLocation());
    assertEquals(coms1004.getInstructorName(), actualCourse.getInstructorName());
    assertEquals(coms1004.getCourseTimeSlot(), actualCourse.getCourseTimeSlot());
    assertEquals(coms1004.getEnrolledStudentCount(), actualCourse.getEnrolledStudentCount());
  }


  @Test
  public void getFilePathTest() {
    testDatabase = new MyFileDatabase(1, testFilePath);
    assertEquals(testFilePath, testDatabase.getFilePath());
  }


  private MyFileDatabase testDatabase;
  private String testFilePath;
}
