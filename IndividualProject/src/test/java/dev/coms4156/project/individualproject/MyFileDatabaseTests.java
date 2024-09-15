package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains tests for the MyFileDatabase class.
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseTests {

  /**
   * Create variable for testing.
   */
  @BeforeAll
  public static void setupDepartmentForTesting() {
    testDb = new MyFileDatabase(1, "./data.txt");
    testDb2 = new MyFileDatabase(1, "data.java");
    testDb3 = new MyFileDatabase(3, "testdata.txt");

    testCourse = new Course("Ruben M Savizky", "309 HAV", "6:10-7:25", 120);
    testCourse.setEnrolledStudentCount(100);
    testCourse2 = new Course("Patricia G Lindemann", "501 SCH", "1:10-2:25", 200);
    testCourse2.setEnrolledStudentCount(191);

    testCourseMap = new HashMap<>();
    testCourseMap2 = new HashMap<>();

    testCourseMap.put("1403", testCourse);
    testCourseMap2.put("1001", testCourse2);
    testDept = new Department("PHYS", testCourseMap, "Dmitri N. Basov", 43);
    testDept2 = new Department("PSYC", testCourseMap2, "Nim Tottenham", 437);

    testDeptMap = new HashMap<>();
    testDeptMap.put("PHYS", testDept);
    testDeptMap.put("PSYC", testDept2);
  }

  @Test
  public void setMappingTest() {
    testDb.setMapping(testDeptMap);
    assertEquals(testDeptMap, testDb.getDepartmentMapping());
  }

  @Test
  public void toStringTest() {
    testDb.setMapping(testDeptMap);
    String expect = "For the PHYS department: \n"
            + "PHYS 1403: "
            + "Instructor: Ruben M Savizky; Location: 309 HAV; Time: 6:10-7:25\n"
            + "For the PSYC department: \n"
            + "PSYC 1001: "
            + "Instructor: Patricia G Lindemann; Location: 501 SCH; Time: 1:10-2:25\n";
    assertEquals(expect, testDb.toString());
  }

  @Test
  public void deSerializeObjectFromFileTest() {
    assertEquals(null, testDb2.deSerializeObjectFromFile());
    System.out.println("Print:" + testDb.deSerializeObjectFromFile());
  }

  /** The testdata.txt output can be find in IndividualProject/testdata.txt. */
  @Test
  public void saveContentsToFileTest() {
    testDb3.setMapping(testDeptMap);
    testDb3.saveContentsToFile();
  }

  public static MyFileDatabase testDb;
  public static MyFileDatabase testDb2;
  public static MyFileDatabase testDb3;

  public static Department testDept;
  public static Department testDept2;
  /** The test course instance used for testing. */
  public static Course testCourse;
  public static Course testCourse2;
  public static HashMap<String, Course> testCourseMap;
  public static HashMap<String, Course> testCourseMap2;
  public static HashMap<String, Department> testDeptMap;
}
