package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.HashMap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Represents a unit test class for the MyFileDatabase class. This class tests various
 * functionalities of the MyFileDatabase class under wide variety of conditions and makes sure they
 * work as expected.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class MyFileDatabaseUnitTests {

  @BeforeEach
  void setupFileDbForTesting() {
    deptMapping = new HashMap<>();
    testFileDB = spy(new MyFileDatabase(1, filePath));
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    Course coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
    coms3134.setEnrolledStudentCount(242);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);
    courses.put("3134", coms3134);
    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    deptMapping.put("COMS", compSci);

    // data for econ dept
    Course econ1105 = new Course("Waseem Noor", locations[1], times[3], 210);
    econ1105.setEnrolledStudentCount(187);
    Course econ2257 = new Course("Tamrat Gashaw", "428 PUP", times[2], 125);
    econ2257.setEnrolledStudentCount(63);

    courses = new HashMap<>();
    courses.put("1105", econ1105);
    courses.put("2257", econ2257);

    Department econ = new Department("ECON", courses, "Michael Woodford", 2345);
    deptMapping.put("ECON", econ);
  }

  @Test
  @Order(1)
  public void fileDbWithMode1Test() {
    verify(testFileDB, never()).deSerializeObjectFromFile();
  }

  @Test
  @Order(2)
  public void fileDbWithMode0ExceptionTest() {
    MyFileDatabase errorFileDb = new MyFileDatabase(0, filePath);
    assertNull(errorFileDb.getDepartmentMapping());
  }

  @Test
  @Order(3)
  public void deptMappingTest() {
    testFileDB.setMapping(deptMapping);
    assertEquals(2, testFileDB.getDepartmentMapping().size());
    assertEquals(2, testFileDB.getDepartmentMapping().size());
    // assertEquals(deptMapping, testFileDB.getDepartmentMapping());
  }

  @Test
  @Order(4)
  public void toStringTest() {
    testFileDB.setMapping(deptMapping);
    assertEquals(
        "For the COMS department: \n"
            + "COMS 1004: \n"
            + "Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55\n"
            + "3134: \n"
            + "Instructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25\n"
            + "For the ECON department: \n"
            + "ECON 1105: \n"
            + "Instructor: Waseem Noor; Location: 309 HAV; Time: 2:40-3:55\n"
            + "2257: \n"
            + "Instructor: Tamrat Gashaw; Location: 428 PUP; Time: 10:10-11:25\n",
        testFileDB.toString());
  }

  @Test
  @Order(5)
  public void saveContentsToFileTest() {
    testFileDB.setMapping(deptMapping);
    testFileDB.saveContentsToFile();
    File file = new File(filePath);
    assertTrue(file.exists());
  }

  /**
   * Sets up the teardown logic to run just before destroying this class object. This contains logic
   * for removing any temporary files which are created as part of the testing process.
   */
  @AfterAll
  public static void teardown() {
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
    }
  }

  private static MyFileDatabase testFileDB;
  private static final String filePath = "./testFilePath";
  private static HashMap<String, Department> deptMapping;
}
