package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This Class contains Unit tests for the {@link MyFileDatabase} class.
 *
 * <p>This class tests that the {@code MyFileDatabase} class works as expected. Tests include:
 * <li>Serialization and deserialization of {@code Department} and {@code Course} Objects</li>
 * <li>Handling exceptions for attempting to deserialize non-existed files</li>
 * <li>Attempt to utilize invalid object types during the deserialization process</li>
 * <li>Handling of various edge cases such as empty or missing files</li>
 */
public class MyFileDatabaseUnitTests {

  private MyFileDatabase myFileDatabase;
  private String testFilePath = "./unit_test_data.txt";

  /**
   *  Before each unit test, we initialize a brand-new file.
   *  This enables us to test the individual functionality of each method in
   *  the {@code @class MyFileDatabase} We add in some base functionality to ensure that
   *  the file was deleted properly after each test.
   */
  @BeforeEach
  public void setupTestDatabase() {
    File testFile = new File(testFilePath);
    if (!testFile.delete() && testFile.exists()) {
      throw new IllegalStateException("Could not delete file: " + testFilePath);
    }

    myFileDatabase = new MyFileDatabase(1, testFilePath);
  }

  /**
   * This method verifies the serialization and deserialization of the Department Object in
   * MyFileDatabase.
   *
   * <p>Firstly, this method creates a sample Department object, and appends it to a map.
   * Then it saves the contents to a file using the {@code saveContentsToFile()}
   * method. Then it will deserialize the file into a new MyFileDatabase object, and ensures
   * that the originally serialized object matches the deserialized one.
   */
  @Test
  public void testSerializationAndDeserialization() {
    Course testCourse = new Course("Donald Duck's Math",
        "Disneyland 1234", "12:55-12:56", 5000);
    Map<String, Course> courses = new HashMap<>();
    Map<String, Department> departmentMap = new HashMap<>();

    courses.put("1234", testCourse);
    Department disneyDepartment = new Department("DIS", courses,
        "Mickey Mouse", 10000);
    departmentMap.put("DIS", disneyDepartment);

    myFileDatabase.setMapping(departmentMap);
    myFileDatabase.saveContentsToFile();

    MyFileDatabase testDatabase = new MyFileDatabase(0, testFilePath);
    Map<String, Department> deserializedMap = testDatabase.getDepartmentMapping();

    assertNotNull(deserializedMap);
    assertEquals(departmentMap.toString(), deserializedMap.toString());
  }

  /**
   * This method verifies the output of the {@code toString()} method in the MyFileDatabase class.
   *
   * <p>Firstly, this method create two sample Course objects, and appends it to a map.
   * Then it saves the contents to a {@link Department} Object. It then asserts that
   * the toString() method and a defined expected String are the same.
   */
  @Test
  public void testMyFileDatabaseToString() {
    Course testCourse1 = new Course("Donald Duck", "Disneyland 1234",
        "12:55-12:56", 5000);
    Course testCourse2 = new Course("Goofy", "Disneyland 4251",
        "13:33-14:44", 2500);

    Map<String, Course> disneyCourses = new HashMap<>();
    disneyCourses.put("1234", testCourse1);
    disneyCourses.put("4251", testCourse2);

    Department disDept = new Department("DIS", disneyCourses,
        "Bob Iger", 8000);

    Map<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("DIS", disDept);

    MyFileDatabase myFileDatabase = new MyFileDatabase(1, testFilePath);
    myFileDatabase.setMapping(departmentMapping);

    String expectedString = "For the DIS department: \n"
        + "DIS 4251: \n"
        + "Instructor: Goofy; Location: Disneyland 4251; Time: 13:33-14:44; Capacity: 2500\n"
        + "DIS 1234: \n"
        + "Instructor: Donald Duck; Location: Disneyland 1234; Time: 12:55-12:56; Capacity: 5000\n";

    assertEquals(expectedString, myFileDatabase.toString());
  }

  /**
   * This function aims to stress test the Deserialize function by attempting to serialize a file
   * that does not exist. It then asserts that the mapping it receives in response is empty.
   */
  @Test
  public void testDeserializeFileNotFound() {
    MyFileDatabase myFileDatabase = new MyFileDatabase(0, "notRealFile.txt");
    Map<String, Department> departmentMapping = myFileDatabase.deSerializeObjectFromFile();
    assertNotNull(departmentMapping);
    assertTrue(departmentMapping.isEmpty());
  }

  /**
   * This function aims to stress test the {@code deSerializeObjectFromFile} method by
   * attempting to serialize an invalid object. It then asserts that the return is an
   * error with an empty file.
   */
  @Test
  public void testDeserializeInvalidObjectType() {
    try (ObjectOutputStream out = new ObjectOutputStream(
        new BufferedOutputStream(Files.newOutputStream(Paths.get(testFilePath))))) {
      out.writeObject("This is not a HashMap");  // Writing an invalid object type
    } catch (IOException e) {
      e.printStackTrace();
      fail("Failed to write invalid object type to file");
    }

    // check if deSerializeObjectFromFile() throws an IllegalArgumentException.
    assertThrows(IllegalArgumentException.class, () -> myFileDatabase.deSerializeObjectFromFile());
  }

  /**
   * This function aims to stress test the {@code deSerializeObjectFromFile}
   * method by attempting to serialize a file that does not exist.
   * It then asserts that the return is an error with an empty file.
   */
  @Test
  public void testDeserializeObjectFromFileException() {
    String invalidFilePath = "/invalid/directory/thisFileDoesNotExist.txt";

    MyFileDatabase myFileDatabase = new MyFileDatabase(0, invalidFilePath);
    Map<String, Department> result = myFileDatabase.deSerializeObjectFromFile();

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }
}