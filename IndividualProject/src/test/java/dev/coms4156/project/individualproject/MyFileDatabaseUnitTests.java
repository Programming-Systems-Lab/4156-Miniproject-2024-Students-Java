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
import java.lang.String;
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
    courses.put("2500", new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250));
    departmentMapping = new HashMap<>();
    departmentMapping.put("CS", new Department("CS", courses, "David", 1111));
    databaseTest.setMapping(departmentMapping);


  }


  @Test
  public void testSetMapping() {
    departmentMapping = new HashMap<>();
    departmentMapping.put("CS", new Department("CS", courses, "David", 1111));
    databaseTest.setMapping(departmentMapping);
    assertEquals(departmentMapping, databaseTest.getDepartmentMapping());
  }

  @Test
  public void testGetDepartmentMapping() {
    assertEquals(departmentMapping, databaseTest.getDepartmentMapping());
  }

  @Test
  public void deSerializeObjectFromFileWithValidDataTest() throws IOException,
          ClassNotFoundException {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testFile))) {
      out.writeObject(departmentMapping);
    }

    databaseSerValid = new MyFileDatabase(0, testFile.getAbsolutePath());
    HashMap<String, Department> deserializedMapping = databaseSerValid.deSerializeObjectFromFile();
    for (String key : departmentMapping.keySet()) {
      Department originalDepartment = departmentMapping.get(key);
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


  @Test
  public void testToString(){
    System.out.print("hahahahah execueted");

    String expectedResult = "For the CS department: \nCS 2500: \nInstructor: "
            + "Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n";
    assertEquals(expectedResult, databaseTest.toString());
  }

  @Test
  public void testSaveContentToFile(){

    /** create a file*/
    File tempFile = new File(tempFolder, "testFile.ser");
    putContent = new MyFileDatabase(0, tempFile.getAbsolutePath());
    /** write data to the file*/
    courses = new HashMap<>();
    courses.put("2500", new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250));
    departmentMapping = new HashMap<>();
    departmentMapping.put("CS", new Department("CS", courses, "David", 1111));
    putContent.setMapping(departmentMapping);
    putContent.saveContentsToFile();

    /** read from the file and valid it*/
    HashMap<String, Department> deserializedMapping = putContent.deSerializeObjectFromFile();
    for (String key : departmentMapping.keySet()) {
      Department originalDepartment = departmentMapping.get(key);
      Department deserializedDepartment = deserializedMapping.get(key);
      assertEquals(originalDepartment.getDepartmentChair(),
              deserializedDepartment.getDepartmentChair());
    }




  }

  /** The test course instance used for testing. */
  @TempDir
  private static File tempDir;
  private static File tempFolder;
  @TempDir
  private static File tempDirInvalid;
  private static File testFile;
  private static MyFileDatabase databaseTest;
  private static MyFileDatabase putContent;
  private static MyFileDatabase databaseSerValid;
  private static Course psyc1001;
  private static Course psyc1610;
  private static HashMap<String, Course> courses;
  private static HashMap<String, Department> departmentMapping;
}

