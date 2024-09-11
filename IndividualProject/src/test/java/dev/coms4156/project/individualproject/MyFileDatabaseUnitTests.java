package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Writes test case to check methods in the Department.
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseUnitTests {

  @BeforeEach
  public void setupMyFileDatabaseForTesting() {
    testFilePath = "./testData.txt";
  }


  @AfterEach
  public void deleteTestGeneratedFile() {
    File file = new File(testFilePath);
    if (file.exists()) {
      file.delete();
    }
  }


  @Test
  public void toStringTest() {
    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    HashMap<String, Department> testMap = new HashMap<>();
    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    testMap.put("COMS", compSci);

    String expectedResult = "\nFor the COMS department: \nCOMS 1004: Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55";

    MyFileDatabase testDatabase = new MyFileDatabase(0, testFilePath);
    assertEquals(expectedResult, testMap.toString());
  }


  @Test
  public void deSerializeObjectFromFileTest() {
    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    HashMap<String, Department> expectedResult = new HashMap<>();
    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    expectedResult.put("COMS", compSci);

    // Serialize the object
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testFilePath))) {
      out.writeObject(expectedResult);
    } catch (IOException e) {
      e.printStackTrace();
    }

    MyFileDatabase testDatabase = new MyFileDatabase(0, testFilePath);

    assertNotNull(testDatabase.getDepartmentMapping());
    assertEquals(expectedResult, testDatabase.getDepartmentMapping());
  }


  @Test
  public void deSerializeObjectFromFileInvalidDataTest() {
    // Serialize the object
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testFilePath))) {
      out.writeObject("Invalid Data");
    } catch (IOException e) {
      e.printStackTrace();
    }

    MyFileDatabase testDatabase = new MyFileDatabase(0, testFilePath);
    assertNull(testDatabase.getDepartmentMapping());
  }


  @Test
  public void serializationTest() {
    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    HashMap<String, Department> testMap = new HashMap<>();
    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    testMap.put("COMS", compSci);

    MyFileDatabase testDatabase = new MyFileDatabase(0, testFilePath);

    testDatabase.setMapping(testMap);
    testDatabase.saveContentsToFile();

    File testFile = new File(testFilePath);
    assertTrue(testFile.exists());

    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(testFile))) {
      Object obj = in.readObject();
      assertEquals(testMap, obj);
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }


  @Test
  public void setAndGetDepartmentMappingTest() {
    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    HashMap<String, Department> expectedResult = new HashMap<>();
    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    expectedResult.put("COMS", compSci);

    MyFileDatabase testDatabase = new MyFileDatabase(0, testFilePath);

    testDatabase.setMapping(expectedResult);
    assertEquals(expectedResult, testDatabase.getDepartmentMapping());
  }


  public String testFilePath;
}
