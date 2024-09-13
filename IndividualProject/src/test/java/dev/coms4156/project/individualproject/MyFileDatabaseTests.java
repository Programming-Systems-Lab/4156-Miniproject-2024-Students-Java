package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the {@link dev.coms4156.project.individualproject.MyFileDatabase} class.
 *
 * <p>This class contains test cases to validate the functionality of the {@link
 * dev.coms4156.project.individualproject.MyFileDatabase} class methods.
 */
@ExtendWith(MockitoExtension.class)
public class MyFileDatabaseTests {

  private static final String FILE_PATH = "src/test/resources/mockData.txt";
  private HashMap<String, Department> mockDepartmentMapping;
  @Mock private MyFileDatabase db;

  /**
   * Set up the myFileDatabase unit tests by initializing a test ECON and COMS department with 2
   * test courses per each department.
   */
  @BeforeEach
  public void setUp() throws IOException {
    // delete FILE_PATH (data.txt) if it exists
    Files.deleteIfExists(Paths.get(FILE_PATH));

    // init mock department mapping
    mockDepartmentMapping = new HashMap<>();

    // CS courses
    Course csCourse1 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 30);
    Course csCourse2 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    HashMap<String, Course> csCourses = new HashMap<>();
    csCourses.put("1004", csCourse1);
    csCourses.put("3251", csCourse2);
    Department csDepartment = new Department("COMS", csCourses, "Luca Carloni", 2700);

    // ECON courses
    Course econCourse1 = new Course("Waseem Noor", "309 HAV", "2:40-3:55", 210);
    Course econCourse2 = new Course("Tamrat Gashaw", "428 PUP", "10:10-11:25", 125);
    HashMap<String, Course> econCourses = new HashMap<>();
    econCourses.put("1105", econCourse1);
    econCourses.put("2257", econCourse2);
    Department econDepartment = new Department("ECON", econCourses, "Michael Woodford", 2345);

    mockDepartmentMapping.put("COMS", csDepartment);
    mockDepartmentMapping.put("ECON", econDepartment);

    // init MyFileDatabase instance
    db = new MyFileDatabase(1, FILE_PATH);
  }

  @Test
  public void constructorCorrectValidation() {
    // Validate that departmentMapping is initialized as an empty HashMap
    assertTrue(db.getDepartmentMapping().isEmpty());
  }

  @Test
  public void constructorWithInvalidFlag() {
    IllegalArgumentException thrown =
        assertThrows(IllegalArgumentException.class, () -> new MyFileDatabase(2, FILE_PATH));
    assertEquals("Invalid flag value: 2", thrown.getMessage());
  }

  @Test
  public void setMappingExpectedValueValidation() {
    db.setMapping(mockDepartmentMapping);
    assertEquals(mockDepartmentMapping, db.getDepartmentMapping());
  }

  @Test
  public void saveContentsToFileValidation() throws IOException, ClassNotFoundException {
    db.setMapping(mockDepartmentMapping);
    db.saveContentsToFile();

    // basically deSerializeObjectFromFile()
    ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH));
    @SuppressWarnings("unchecked")
    HashMap<String, Department> deserializedMapping = (HashMap<String, Department>) in.readObject();

    // ensure output from file matches the provided output in mockDepartmentMapping
    assertEquals(
        mockDepartmentMapping.toString(),
        deserializedMapping.toString(),
        "Expected mockDepartmentMapping and deserializedMapping to have the same string output");
  }

  @Test
  public void saveContentsToFileEnsureFileExists() {
    // set mapping for departments, then save to FILE_PATH
    db.setMapping(mockDepartmentMapping);
    db.saveContentsToFile();

    // Ensure file exists
    Path filePath = Paths.get(FILE_PATH);
    assertTrue(Files.exists(filePath), "Expected " + filePath + " to exist.");
  }

  @Test
  public void deSerializeFromFileValidation() {
    db.setMapping(mockDepartmentMapping);
    db.saveContentsToFile();
    HashMap<String, Department> deSerializedDepartmentMapping = db.deSerializeObjectFromFile();

    assertEquals(
        mockDepartmentMapping.toString(),
        deSerializedDepartmentMapping.toString(),
        "Expected mockDepartmentMapping and deSerializedDepartmentMapping to have the same string output");
  }

  @Test
  public void toStringExpectedValueValidation() {
    db.setMapping(mockDepartmentMapping);

    // Verify that the toString method produces the correct output
    String expected =
        """
            For the COMS department:\s
            COMS 1004:\s
            Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55
            COMS 3251:\s
            Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40
            For the ECON department:\s
            ECON 1105:\s
            Instructor: Waseem Noor; Location: 309 HAV; Time: 2:40-3:55
            ECON 2257:\s
            Instructor: Tamrat Gashaw; Location: 428 PUP; Time: 10:10-11:25
            """;
    assertEquals(expected, db.toString());
  }
}
