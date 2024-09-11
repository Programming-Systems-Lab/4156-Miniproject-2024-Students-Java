package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Course class.
 * This class contains tests to verify the behavior and functionality of the Course class.
 * It uses Spring's testing framework to set up the environment and run the tests.
 */
@SpringBootTest
@ContextConfiguration
@TestMethodOrder(OrderAnnotation.class)
public class DatabaseUnitTests {

  @Nested
  @TestMethodOrder(OrderAnnotation.class)
  class FlagZeroTests {
    
    @BeforeAll
    public static void setupDatabaseFlagZeroTest() {
      testDatabase = new MyFileDatabase(0, "./data.txt");
    }

    @Test
    @Order(1)
    public void toStringTest() {
      System.out.println("Flag 0 - MyFileDatabase content:");
      System.out.println(testDatabase.toString());
    }

    @Test
    @Order(2)
    public void setMappingTest() {
      // english department
      Course eng1 = new Course("Jane Austen", "101 SCH", "9:00-10:15", 100);
      eng1.setEnrolledStudentCount(95);
      Course eng2 = new Course("Mark Twain", "201 SCH", "11:00-12:15", 80);
      eng2.setEnrolledStudentCount(75);

      HashMap<String, Course> englishCourses = new HashMap<>();
      englishCourses.put("1011", eng1);
      englishCourses.put("1021", eng2);

      // math department
      Course math1 = new Course("Isaac Newton", "305 MATH", "10:00-11:15", 150);
      math1.setEnrolledStudentCount(140);
      Course math2 = new Course("Albert Einstein", "310 MATH", "2:00-3:15", 120);
      math2.setEnrolledStudentCount(110);

      HashMap<String, Course> mathCourses = new HashMap<>();
      mathCourses.put("1010", math1);
      mathCourses.put("2035", math2);

      // history department
      Course hist1 = new Course("Howard Zinn", "101 HAM", "9:00-10:30", 90);
      hist1.setEnrolledStudentCount(85);
      Course hist2 = new Course("Doris Kearns Goodwin", "202 HAM", "1:00-2:30", 75);
      hist2.setEnrolledStudentCount(70);

      HashMap<String, Course> historyCourses = new HashMap<>();
      historyCourses.put("3500", hist1);
      historyCourses.put("3830", hist2);

      // final map
      HashMap<String, Department> departmentMapping = new HashMap<>();
      departmentMapping.put("ENGL", new Department("ENGL", englishCourses, 
                                                            "Emily Dickinson", 500));
      departmentMapping.put("MATH", new Department("MATH", mathCourses, 
                                                            "Euclid", 1000));
      departmentMapping.put("HIST", new Department("HIST", historyCourses, 
                                                            "David McCullough", 300));

      testDatabase.setMapping(departmentMapping);
    }

    @Test
    @Order(3)
    public void newMappingTest() {
      Map<String, Department> map = testDatabase.getDepartmentMapping();
      assertTrue(map.containsKey("HIST"));
      assertFalse(map.containsKey("ELEN"));

      assertEquals(map.get("ENGL").getCourseSelection().get("1011")
                                .getInstructorName(), "Jane Austen");
      assertEquals(map.get("MATH").getNumberOfMajors(), 1000);
    }

    @Test
    @Order(4)
    public void saveContentsToFileTest() {
      testDatabase.saveContentsToFile();
    }

  }

  @Nested
  @TestMethodOrder(OrderAnnotation.class)
  class FlagOneTests {
 
    @BeforeAll
    public static void setupDatabaseFlagOneTest() {
      testDatabase = new MyFileDatabase(1, "./dummy-data.txt");
    }

    @Test
    @Order(1)
    public void setMappingOneTest() {
      // anthropology department
      Course anth1 = new Course("Margaret Mead", "101 MIL", "10:00-11:15", 120);
      anth1.setEnrolledStudentCount(110);
      Course anth2 = new Course("Franz Boas", "201 MIL", "1:00-2:15", 80);
      anth2.setEnrolledStudentCount(70);

      HashMap<String, Course> anthropologyCourses = new HashMap<>();
      anthropologyCourses.put("1001", anth1);
      anthropologyCourses.put("1002", anth2);

      // linguistics department
      Course ling1 = new Course("Noam Chomsky", "101 NWC", "9:00-10:15", 90);
      ling1.setEnrolledStudentCount(85);
      Course ling2 = new Course("Ferdinand de Saussure", "202 NWC", "11:00-12:15", 100);
      ling2.setEnrolledStudentCount(95);

      HashMap<String, Course> linguisticsCourses = new HashMap<>();
      linguisticsCourses.put("1101", ling1);
      linguisticsCourses.put("2202", ling2);

      // music department
      Course music1 = new Course("Ludwig van Beethoven", "101 DOD", "3:00-4:15", 150);
      music1.setEnrolledStudentCount(140);
      Course music2 = new Course("Johann Sebastian Bach", "202 DOD", "4:30-5:45", 120);
      music2.setEnrolledStudentCount(110);

      HashMap<String, Course> musicCourses = new HashMap<>();
      musicCourses.put("3001", music1);
      musicCourses.put("3002", music2);

      // final map
      HashMap<String, Department> departmentMapping = new HashMap<>();
      departmentMapping.put("ANTH", new Department("ANTH", anthropologyCourses, 
                                                            "Claude Lévi-Strauss", 600));
      departmentMapping.put("LING", new Department("LING", linguisticsCourses, 
                                                            "Edward Sapir", 700));
      departmentMapping.put("MUSI", new Department("MUSI", musicCourses, 
                                                            "Wolfgang Amadeus Mozart", 500));

      testDatabase.setMapping(departmentMapping);
    }

    @Test
    @Order(2)
    public void newMappingOneTest() {
      Map<String, Department> map = testDatabase.getDepartmentMapping();
      assertTrue(map.containsKey("ANTH"));
      assertFalse(map.containsKey("ELEN"));
      assertFalse(map.containsKey("COMS"));
      assertFalse(map.containsKey("HIST"));

      assertEquals(map.get("ANTH").getCourseSelection().get("1001")
                                .getCourseLocation(), "101 MIL");
      assertEquals(map.get("LING").getCourseSelection().get("1101")
                                        .getCourseTimeSlot(), "9:00-10:15");
      assertEquals(map.get("MUSI").getCourseSelection().size(), 2);

      assertEquals(map.get("MUSI").getCourseSelection().get("3002")
                                        .getCourseLocation(), "202 DOD");
    }

    @Test
    @Order(3)
    public void toStringOneTest() {
      System.out.println("Flag 1 - MyFileDatabase content:");
      System.out.println(testDatabase.toString());
    }

    @Test
    @Order(4)
    public void saveContentsOneToFileTest() {
      testDatabase.saveContentsToFile();
    }

  }

  /** The test course instance used for testing. */
  public static MyFileDatabase testDatabase;
}

