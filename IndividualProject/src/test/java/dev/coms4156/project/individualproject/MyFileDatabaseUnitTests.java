package dev.coms4156.project.individualproject;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * This is used to test methods of Course class.
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseUnitTests {

  /**
   * This is pre-set up for each test case.
   */
  @BeforeEach
  public void setupMyFileDatabaseForTesting() {
    testFile = new File(tempDir, "testDatabase.ser");
    databaseTest = new MyFileDatabase(1, testFile.getAbsolutePath());
    courses = new HashMap<>();
    Course psyc1001 = new Course("Patricia G Lindemann", "501 SCH", "1:10-2:25", 200);
    psyc1001.setEnrolledStudentCount(191);
    Course psyc1610 = new Course("Christopher Baldassano", "200 SCH", "1:10-4:00", 45);
    psyc1610.setEnrolledStudentCount(42);
    courses.put("1001", psyc1001);
    courses.put("1610", psyc1610);
    mapping = new HashMap<>();
    mapping.put("ECON", new Department("ECON", courses, "Michael Woodford", 2345));
    mapping.put("CS", new Department("CS", courses, "David", 1111));

  }


  @Test
  public void setMappingTest() {
    databaseTest.setMapping(mapping);
    assertEquals(mapping, databaseTest.getDepartmentMapping());
  }

  @Test
  public void deSerializeObjectFromFileWithValidDataTest() throws IOException,
          ClassNotFoundException {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testFile))) {
      out.writeObject(mapping);
    }

    databaseSerValid = new MyFileDatabase(0, testFile.getAbsolutePath());
    HashMap<String, Department> deserializedMapping = databaseSerValid.deSerializeObjectFromFile();
    for (String key : mapping.keySet()) {
      Department originalDepartment = mapping.get(key);
      Department deserializedDepartment = deserializedMapping.get(key);

      assertEquals(originalDepartment.getDepartmentChair(),
              deserializedDepartment.getDepartmentChair());

      HashMap<String, Course> originalCourses = originalDepartment.getCourseSelection();
      HashMap<String, Course> deserializedCourses = deserializedDepartment.getCourseSelection();

      assertEquals(originalCourses.size(), deserializedCourses.size());

      for (String courseKey : originalCourses.keySet()) {
        Course originalCourse = originalCourses.get(courseKey);
        Course deserializedCourse = deserializedCourses.get(courseKey);

        assertEquals(originalCourse.getInstructorName(), deserializedCourse.getInstructorName());
        assertEquals(originalCourse.getCourseLocation(), deserializedCourse.getCourseLocation());
        assertEquals(originalCourse.getCourseTimeSlot(), deserializedCourse.getCourseTimeSlot());
      }
    }
  }

  /*
  @Test
  public void deSerializeObjectFromFileInvalidTest() throws IOException, ClassNotFoundException {
    File testInvalidFile = new File(tempDirInvalid, "testInvalid.ser");
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testInvalidFile))) {
      out.writeObject("Just String not a valid datatype.");
    }
    MyFileDatabase databaseInvalid = new MyFileDatabase(0, testInvalidFile.getAbsolutePath());
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      databaseInvalid.deSerializeObjectFromFile();
    });
    assertEquals("Invalid object type in file.", exception.getMessage());
  }
*/

  /** The test course instance used for testing. */
  @TempDir
  private static File tempDir;
  @TempDir
  private static File tempDirInvalid;
  private static File testFile;
  private static File testInvalidFile;
  private static MyFileDatabase databaseTest;
  private static MyFileDatabase databaseSerValid;
  private static Course psyc1001;
  private static Course psyc1610;
  private static HashMap<String, Course> courses;
  private static HashMap<String, Department> mapping;
}

